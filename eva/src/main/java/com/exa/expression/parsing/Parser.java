package com.exa.expression.parsing;

import com.exa.buffer.CharReader;
import com.exa.chars.EscapeCharMan;
import com.exa.expression.TypeMan;
import com.exa.expression.XPConstant;
import com.exa.expression.XPOperand;
import com.exa.expression.eval.XPEvaluator;
import com.exa.expression.eval.functions.XPFunctSubstr;
import com.exa.expression.eval.operators.OSMBinary;
import com.exa.expression.eval.operators.OSMClosedParenthesis;
import com.exa.expression.eval.operators.OSMFunction;
import com.exa.expression.eval.operators.OSMOpenParenthesis;
import com.exa.expression.eval.operators.OSMParamSeparator;
import com.exa.expression.eval.operators.XPConcatenation;
import com.exa.lexing.ParsingException;
import com.exa.utils.ManagedException;

public class Parser {
	
	static final int READ_EOF = 0;
	static final int READ_NOT_EOF = 1;
	static final int READ_OK = 3;
	
	private XPLexingRules lexingRules = new XPLexingRules();
		
	private XPEvaluator evaluator = new XPEvaluator();
	
	public Parser() {
		OSMBinary osmm = new OSMBinary("+", 6);
		osmm.addOperator(new XPConcatenation(6));
		
		evaluator.addBinaryOp(osmm);
		
		OSMFunction osmf = new OSMFunction("substr", 3);
		
		osmf.addOperator(new XPFunctSubstr("substr", 3));
		
		evaluator.addFunction(osmf);
	}
	
	
	public XPOperand<?> parse(CharReader cr) throws ManagedException {
		evaluator.clear();

		if(readPreUnaryOPerator(cr) == READ_EOF) throw new ManagedException(String.format("No string to parse"));
		
		do { 
			int readResult = readOperand(cr);
			if(readResult != READ_OK) throw new ManagedException(String.format("Operand expected"));
			
			readResult = readPostUnaryOperator(cr);
			if(readResult == READ_EOF) break;
			
			readResult = readNotUnaryOperator(cr);
			if(readResult == READ_EOF) break;
			
			if(readResult != READ_OK) throw new ManagedException(String.format("Not unary operator expected"));
			
		} while(readPreUnaryOPerator(cr) != READ_EOF);
		
		return evaluator.compute();
	}
	
	private void readFunctionParams(CharReader cr) throws ManagedException {
		
		if(readPreUnaryOPerator(cr) == READ_EOF) throw new ManagedException(String.format("No string to parse"));
		
		do { 
			int readResult = readOperand(cr);
			if(readResult != READ_OK) throw new ManagedException(String.format("Operand expected"));
			
			readResult = readPostUnaryOperator(cr);
			if(readResult == READ_EOF) throw new ManagedException(String.format("Unexpected end of file. ',' expected in function params."));
			
			readResult = readNotUnaryOperator(cr);
			if(readResult == READ_EOF) throw new ManagedException(String.format("Unexpected end of file. ',' expected in function params."));
			
			if(readResult != READ_OK) {
				Character ch = lexingRules.nextNonBlankChar(cr);
				if(ch == null) throw new ManagedException(String.format("Unexpected end of file. ',' expected in function params."));
				
				if(')' == ch) {
					evaluator.push(XPEvaluator.OP_CLOSED_PARENTHESIS);
					return;
				}
				
				if(',' == ch) evaluator.push(XPEvaluator.OP_PARAMS_SEPARATOR);
				
			}
			
		} while(readPreUnaryOPerator(cr) != READ_EOF);
		
	}
	
	public XPOperand<?> parseString(String str) throws ManagedException {
		return parse(new CharReader(str));
	}

	public int readPreUnaryOPerator(CharReader cr) throws ManagedException {
		Character firstChar = lexingRules.nextForwardNonBlankChar(cr);
		if(firstChar == null) return READ_EOF;
		
		if('+' == firstChar || '-' == firstChar) {
			cr.nextChar();
			
			Character ch = lexingRules.nextForwardChar(cr);
			if(ch == null) throw new ParsingException(String.format("Operand expected after pre operator %s", firstChar.toString()));
			
			if(ch == firstChar) {
				String op = firstChar.toString()+firstChar.toString(); 
				evaluator.push(evaluator.getPreUnaryOp(op));
				
				return READ_OK;
			}
			
			if(XPLexingRules.NUMERIC_DIGITS.indexOf(ch) >= 0) {
				XPConstant<? extends Number> xpNum = readNumeric(cr, firstChar);
				
				evaluator.push(xpNum);
				return READ_OK;
			}
			
			if(lexingRules.isBlank(ch)) {
				ch = lexingRules.nextForwardNonBlankChar(cr);
				
				if(XPLexingRules.NUMERIC_DIGITS.indexOf(ch) >= 0) {
					XPConstant<? extends Number> xpNum = readNumeric(cr, firstChar);
					
					evaluator.push(xpNum);
					return READ_OK;
				}
				
				int readResult = readPreUnaryOPerator(cr);
				if(readResult == READ_EOF) throw new ParsingException(String.format("Operand expected after pre operator %s", firstChar.toString()));
				
			}
			
			evaluator.push(evaluator.getPreUnaryOp(firstChar.toString()));
			
			return READ_OK;
		}
	
		
		return READ_NOT_EOF;
	}
	
	public int readOperand(CharReader cr) throws ManagedException {
		Character ch = lexingRules.nextForwardNonBlankChar(cr);
		if(ch == null) return READ_EOF;
		
		if(ch == '\'' || ch == '\"') {
			readString(cr, ch);

			return READ_OK;
		}
		
		if(XPLexingRules.NUMERIC_DIGITS.indexOf(ch) >= 0) {
			XPConstant<? extends Number> xpNum = readNumeric(cr, null);
			
			evaluator.push(xpNum);
			return READ_OK;
		}
		
		if(XPLexingRules.IDENTIFIER_CHARS_LC.indexOf(ch) >= 0) {
			String str = lexingRules.nextNonNullString(cr);
			
			if("true".equals(str)) {
				evaluator.push(XPEvaluator.TRUE);
				return READ_OK;
			}
			
			if("false".equals(str)) {
				evaluator.push(XPEvaluator.FALSE);
				return READ_OK;
			}
			
			ch = lexingRules.nextForwardNonBlankChar(cr);
			
			if('(' == ch) {
				lexingRules.nextNonBlankChar(cr);
				
				OSMFunction osmf = evaluator.getFunction(str);
				if(osmf == null) throw new ParsingException(String.format("The function %S is not defined.", str));
				
				evaluator.push(osmf);
				evaluator.push(XPEvaluator.OP_OPEN_PARENTHESIS);
				
				readFunctionParams(cr);
				return READ_OK;
			}
			
		}
		
		
		return READ_NOT_EOF;
		
	}
	
	public int readPostUnaryOperator(CharReader cr) throws ManagedException {
		Character ch = lexingRules.nextForwardNonBlankChar(cr);
		if(ch == null) return READ_EOF;
		
		return READ_NOT_EOF;
	}
	
	public int readNotUnaryOperator(CharReader cr) throws ManagedException {
		Character firstChar = lexingRules.nextForwardNonBlankChar(cr);
		if(firstChar == null) return READ_EOF;
		
		if('+' == firstChar || '-' == firstChar || '*' == firstChar || '/' == firstChar) {
			cr.nextChar();
			
			Character ch = lexingRules.nextForwardChar(cr);
			if(ch == null) throw new ParsingException(String.format("Operand expected after operator %s", firstChar.toString()));
			
			if(lexingRules.isBlank(ch) || operandFirstChar(ch))  {
				evaluator.push(evaluator.getBinaryOp(firstChar.toString()));
				return READ_OK;
			}
			
			if('=' == ch) {
				cr.nextChar();
				
				ch = lexingRules.nextForwardChar(cr);
				if(ch == null) throw new ParsingException(String.format("Operand expected after operator %s", firstChar.toString()));
				
				if(lexingRules.isBlank(ch) || operandFirstChar(ch))  {
					evaluator.push(evaluator.getBinaryOp(firstChar.toString() + ch));
					return READ_OK;
				}
			} 
			
		}
		
		
		return READ_NOT_EOF;
	}
	
	private boolean operandFirstChar(Character ch) {
		return (XPLexingRules.NUMERIC_DIGITS.indexOf(ch) >= 0) || (ch == '\'' || ch == '\"') || (XPLexingRules.IDENTIFIER_CHARS_LC.indexOf(ch) >= 0);
	}
	
	
	
	private XPConstant<? extends Number> readNumeric(CharReader cr, Character sign) throws ManagedException {
		String str = lexingRules.nextNonNullString(cr);
		if(str == null) return null;
		
		StringBuilder res = sign == null ? new StringBuilder() : new StringBuilder(sign.toString());
		res.append(str);
		
		if(!lexingRules.isInteger(res.toString(), true)) throw new ManagedException(String.format("%s is not a  valid number", res.toString()));
		
		if(XPLexingRules.EXTENDED_NUMERIC_TERMINATION.indexOf(str.charAt(str.length() - 1)) >= 0) return TypeMan.DOUBLE.constant(Double.valueOf(res.toString()));
		
		Character ch = lexingRules.nextForwardChar(cr);
		
		if(ch == null) return TypeMan.INTEGER.constant(Integer.valueOf(res.toString()));
		
		if(ch == '.') {
			cr.nextChar();
			
			ch = lexingRules.nextForwardChar(cr);
			if(ch == null || XPLexingRules.NUMERIC_DIGITS.indexOf(ch) < 0) 	return TypeMan.DOUBLE.constant(Double.valueOf(res.toString()));
			
			String str2 = lexingRules.nextNonNullString(cr);
			
			if(!lexingRules.isInteger(str, true)) throw new ManagedException(String.format("%s is not not numeric", str2));
			res.append("." + str2);
			
			return TypeMan.DOUBLE.constant(Double.valueOf(res.toString()));
		}
		
		return TypeMan.INTEGER.constant(Integer.valueOf(res.toString()));
	}
	
	private int readString(CharReader cr, Character end) throws ManagedException {
		String str = readStringReturnString(cr, end);
		
		evaluator.push(TypeMan.STRING.constant(str));
		
		return READ_OK;
	}
	
	private String readStringReturnString(CharReader cr, Character end) throws ManagedException {
		String str = lexingRules.nextNonNullString(cr);
		
		if(!str.endsWith(end.toString())) throw new ManagedException(String.format("%s is not a valid string", str));
		
		StringBuilder sb = new StringBuilder(str.substring(1, str.length()-1));
		EscapeCharMan.STANDARD.normalized(sb);
		return sb.toString();
	}
	
	/*@Override
	public boolean readOperand(CharReader cr) throws ManagedException {
		Character ch = lexingRules.nextForwardNonBlankChar(cr);
		if(ch == null) return false;
		
		FirstCharMan<XPLexingRules> fcm = getOperandFCM(ch);
		
		if(fcm == null) throw new ManagedException(String.format("Error near %s", ch.toString()));
		
		fcm.consume(cr);
		return true;
		
		
	}
	
	public boolean readOperator(CharReader cr) throws ManagedException {
		Character ch = lexingRules.nextForwardNonBlankChar(cr);
		if(ch == null) return false;
		
		FirstCharMan<XPLexingRules> fcm = getOperatorFCM(ch);
		
		if(fcm == null) throw new ManagedException(String.format("Error near %s", ch.toString()));
		
		fcm.consume(cr);
		return true;
	}*/
	
	/*private FirstCharMan<XPLexingRules> getOperandFCM(Character ch) {
		if('"' == ch) return fcmStringDQuote;
		
		if(XPLexingRules.NUMERIC_DIGITS.indexOf(ch) >= 0) return fcmNumber;
		
		if('\'' == ch) return fcmStringSQuote;
		
		if(XPLexingRules.IDENTIFIER_CHARS_LC.indexOf(ch) >= 0) return fcmIdentifier;
		
		if('-' == ch) return fcmMinus;
		
		if('+' == ch) return fcmPlus;
		
		return null;
	}
	
	private FirstCharMan<XPLexingRules> getOperatorFCM(Character ch) {
		
		if('-' == ch) return fcmMinus;
		
		if('+' == ch) return fcmPlus;
		
		return null;
	}
	*/


	/*@Override
	public XPConstant<? extends Number> readNumeric(CharReader cr) throws ManagedException {
		
		String str = lexingRules.nextNonNullString(cr);
		
		if(!lexingRules.isInteger(str, true)) throw new ManagedException(String.format("%s is not a  valid number", str));
		
		if(XPLexingRules.EXTENDED_NUMERIC_TERMINATION.indexOf(str.charAt(str.length() - 1)) >= 0) return TypeMan.DOUBLE.constant(Double.valueOf(str));
		
		Character ch = lexingRules.nextForwardChar(cr);
		
		if(ch == null) return TypeMan.INTEGER.constant(Integer.valueOf(str));

		if(ch == '.') {
			cr.nextChar();
			
			ch = lexingRules.nextForwardChar(cr);
			if(ch == null || XPLexingRules.NUMERIC_DIGITS.indexOf(ch) < 0) 
				return TypeMan.DOUBLE.constant(Double.valueOf(str));
			
			
			String str2 = lexingRules.nextNonNullString(cr);
			
			if(!lexingRules.isInteger(str, true)) throw new ManagedException(String.format("%s is not not numeric", str));
			
			String res = str+"."+str2;
			
			return TypeMan.DOUBLE.constant(Double.valueOf(res));
		}
		
		return TypeMan.INTEGER.constant(Integer.valueOf(str));
	}
	*/
}
