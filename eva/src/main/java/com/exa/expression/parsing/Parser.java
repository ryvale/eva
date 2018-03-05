package com.exa.expression.parsing;

import com.exa.buffer.CharReader;
import com.exa.chars.EscapeCharMan;
import com.exa.eva.ComputedOperatorManager;
import com.exa.expression.Identifier;
import com.exa.expression.OMFunction;
import com.exa.expression.Identifier.IDType;
import com.exa.expression.OM;
import com.exa.expression.Type;
import com.exa.expression.Variable;
import com.exa.expression.VariableContext;
import com.exa.expression.VariableIdentifier;
import com.exa.expression.XPConstant;
import com.exa.expression.XPIdentifier;
import com.exa.expression.XPOperand;
import com.exa.expression.XPOperator;
import com.exa.expression.XPression;
import com.exa.expression.eval.ClassesMan;
import com.exa.expression.eval.MapVariableContext;
import com.exa.expression.eval.StandardXPEvaluator;
import com.exa.expression.eval.XPEvaluator;
import com.exa.lexing.ParsingException;
import com.exa.utils.ManagedException;

public class Parser {
	public static interface TerminationChecker {
		boolean check(XPLexingRules lexingRules, CharReader cr) throws ManagedException;
	}
	
	static final int READ_EOF = 0;
	static final int READ_NOT_EOF = 1;
	static final int READ_OK = 3;
	
	private XPLexingRules lexingRules = new XPLexingRules();
		
	private XPEvaluator evaluator;
	
	private ClassesMan classesMan;
	
	public Parser(VariableContext variableContext) {
		
		evaluator = new StandardXPEvaluator(variableContext);
		this.classesMan = evaluator.classesMan();
	}
	
	public Parser() {
		this(new MapVariableContext());
	}
	
	public XPEvaluator evaluator() {
		return evaluator;
	}
	
	
	public XPOperand<?> parse(CharReader cr, String context) throws ManagedException {
		evaluator.clear();

		if(readPreUnaryOPerator(cr) == READ_EOF) throw new ManagedException(String.format("No string to parse"));
		
		do { 
			int readResult = readOperand(cr, context);
			if(readResult != READ_OK) throw new ManagedException(String.format("Operand expected"));
			
			readResult = readPostUnaryOperator(cr);
			if(readResult == READ_EOF) break;
			
			readResult = readNotUnaryOperator(cr);
			if(readResult == READ_EOF) break;
			
			if(readResult != READ_OK) throw new ManagedException(String.format("Not unary operator expected"));
			
		} while(readPreUnaryOPerator(cr) != READ_EOF);
	
		
		return evaluator.compute();
	}
	
	public XPOperand<?> parse(CharReader cr) throws ManagedException {
		return parse(cr, evaluator.getDefaultVariableContext());
	}
	
	public XPOperand<?> parse(CharReader cr, TerminationChecker checker, String context) throws ManagedException {
		evaluator.clear();

		if(readPreUnaryOPerator(cr) == READ_EOF) throw new ManagedException(String.format("No string to parse"));
		
		do { 
			int readResult = readOperand(cr, context);
			if(readResult != READ_OK) throw new ManagedException(String.format("Operand expected"));
			
			readResult = readPostUnaryOperator(cr);
			if(readResult == READ_EOF) break;
			
			readResult = readNotUnaryOperator(cr);
			if(readResult == READ_EOF) break;
			
			if(readResult != READ_OK) break;
			
		} while(readPreUnaryOPerator(cr) != READ_EOF);
	
		if(checker.check(lexingRules, cr)) return evaluator.compute();
		throw new ManagedException(String.format("Error while parsing expressin."));
	}
	
	private void readExpressionInBracket(CharReader cr, Character closeBracket, String context) throws ManagedException {
		if(readPreUnaryOPerator(cr) == READ_EOF) throw new ManagedException(String.format("No string to parse"));
		
		do { 
			int readResult = readOperand(cr, context);
			if(readResult != READ_OK) throw new ManagedException(String.format("Operand expected"));
			
			readResult = readPostUnaryOperator(cr);
			if(readResult == READ_EOF) break;
			
			readResult = readNotUnaryOperator(cr);
			if(readResult == READ_EOF) break;
			
			if(readResult != READ_OK) {
				Character ch = lexingRules.nextNonBlankChar(cr);
				
				if(ch == closeBracket)	{
					evaluator.push(XPEvaluator.OP_CLOSED_PARENTHESIS);
					return;
				}
				throw new ManagedException(String.format("Bad parenthesis experssion termination. %s expected instead of %s", closeBracket.toString(), ch));
			}
			
		} while(readPreUnaryOPerator(cr) != READ_EOF);
		
		throw new ManagedException(String.format("Bad parenthesis experssion termination. %s expected.", closeBracket.toString()));
		
	}
	
	private void readFunctionParams(CharReader cr, String context) throws ManagedException {
		
		if(readPreUnaryOPerator(cr) == READ_EOF) throw new ManagedException(String.format("No string to parse"));
		boolean firstParamRead = false;
		do { 
			int readResult = readOperand(cr, context);
			
			if(readResult == READ_OK) firstParamRead = true;
			else {
				if(firstParamRead) throw new ManagedException(String.format("Operand expected"));
				Character ch = lexingRules.nextNonBlankChar(cr);
				if(ch == null) throw new ManagedException(String.format("Unexpected end of file. ')' expected in function params."));
				
				if(')' == ch) {
					evaluator.push(XPEvaluator.OP_CLOSED_PARENTHESIS);
					return;
				}
				
				throw new ManagedException(String.format("Operand  or ')' expected."));
			}
			
			readResult = readPostUnaryOperator(cr);
			if(readResult == READ_EOF) break;
			
			readResult = readNotUnaryOperator(cr);
			if(readResult == READ_EOF) break;
			
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
		
		throw new ManagedException(String.format("Unexpected end of file. ')' expected in function params."));
		
	}
	
	public XPOperand<?> parseString(String str, String context) throws ManagedException {
		return parseString(str, (lexingRules, cr) -> (lexingRules.nextForwardNonBlankChar(cr)==null), context );
	}
	
	public XPOperand<?> parseString(String str) throws ManagedException {
		return parseString(str, (lexingRules, cr) -> (lexingRules.nextForwardNonBlankChar(cr)==null), evaluator.getDefaultVariableContext());
	}
	
	public XPOperand<?> parseString(String str, TerminationChecker checker, String context) throws ManagedException {
		return parse(new CharReader(str), checker, context);
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
	
	public int readOperand(CharReader cr, String context) throws ManagedException {
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
			
			if(ch == null) {
				
				ComputedOperatorManager<OM, XPression<?>> cosm = evaluator.topOperatorManager();
				
				Variable<?> var = null;
				if(cosm == null || !cosm.item().symbol().equals(".")) {
					Type<?> t = classesMan.getType(str);
					if(t != null) throw new ManagedException(String.format("Unexpected end of file near the type % . expect .", str));
					
					var = evaluator.getVariable(str, context);
					if(var == null) {
						var = evaluator.getVariable("this", context);
						if(var == null) 
							throw new ManagedException(String.format("%s . Unknown identifier.", str));
						
						
						Type<?> type = classesMan.getType(var.valueClass());
						if(type == null) 
							throw new ManagedException(String.format("%s . Unknown identifier.", str));
						
						if(type.propertyValueClass(str) == null)
							throw new ManagedException(String.format("%s . Unknown identifier.", str));
						
						//Identifier identifier = new VariableIdentifier(var.name(), var.valueClass());
						
						evaluator.push(new XPIdentifier<>(new VariableIdentifier(var.name(), classesMan.getType(var.valueClass())), context));
						evaluator.push(evaluator.getBinaryOp("."));
						
						//identifier = new Identifier(str, IDType.PROPERTY);
						evaluator.push(new XPIdentifier<>(new Identifier(str, IDType.PROPERTY), context));
						
						return READ_OK;
					}
					
					evaluator.push(new XPIdentifier<>(new VariableIdentifier(var.name(), classesMan.getType(var.valueClass())), context));
					return READ_OK;
				}
				
				//identifier = new Identifier(str, IDType.PROPERTY);
			
				evaluator.push(new XPIdentifier<>(new Identifier(str, IDType.PROPERTY), context));
				return READ_OK;
			}
			
			
			if('(' == ch) {
				lexingRules.nextNonBlankChar(cr);
				
				ComputedOperatorManager<OM, XPression<?>> cosm = evaluator.topOperatorManager();
				if(cosm == null || !cosm.item().symbol().equals(".")) {
					OMFunction<?> osmf = evaluator.getFunction(str);
					if(osmf == null) {
						Variable<?> var = evaluator.getVariable("this", context);
						if(var == null)
							throw new ParsingException(String.format("The function %s is not defined.", str));
						
						Type<?> type = classesMan.getType(var.valueClass());
						if(type == null) 
							throw new ParsingException(String.format("The function %s is not defined.", str));
						
						OMFunction<?> method = type.methodOSM(str);
						if(method == null)
							throw new ParsingException(String.format("The function %s is not defined.", str));
						
						evaluator.push(new XPIdentifier<>(new VariableIdentifier(var.name(), classesMan.getType(var.valueClass())), context));
						
						evaluator.push(method);
						evaluator.push(XPEvaluator.OP_OPEN_PARENTHESIS);
						readFunctionParams(cr, context);
						return READ_OK;
					}
					
					evaluator.push(osmf);
					evaluator.push(XPEvaluator.OP_OPEN_PARENTHESIS);
					readFunctionParams(cr, context);
					
					return READ_OK;
				}
				
				OM osm = cosm.item();
				
				XPression<?> xp = evaluator.stackOperand(0).item();
				XPOperand<?> oprd = xp.asOperand();
				if(oprd != null) {
				
					XPIdentifier<?> xpI = xp.asOperand().asOPIdentifier();
					if(xpI != null) {
						if(xpI.identifier().idType().equals(IDType.CLASS)) {
							Type<?> t = classesMan.getType(xpI.identifier().name());
							if(t != null) {
								OMFunction<?> om = t.staticMethodOSM(str);
								if(om != null) {
									evaluator.decNbFreeOperand();
									evaluator.popOperand();
									evaluator.popOperator();
									evaluator.push(om);
									evaluator.push(XPEvaluator.OP_OPEN_PARENTHESIS);
									readFunctionParams(cr, context);
									return READ_OK;
								}
							}
						}
					}
				}
				XPOperator<?> oprt =  osm.operatorOf(evaluator, cosm.order(), cosm.nbOperands());
				if(oprt == null) throw new ManagedException(String.format("Unexpected error near %s", str));
				
				OMFunction<?> osmf = oprt.type().methodOSM(str);
				if(osmf == null) throw new ManagedException(String.format("The method % is not defined.", str));
				
				if(cosm.nbOperands() < 2) 
					evaluator.popOperator();
				else
					evaluator.popOperatorInOperandStack();
					
				evaluator.push(osmf);
				evaluator.push(XPEvaluator.OP_OPEN_PARENTHESIS);
				readFunctionParams(cr, context);
				return READ_OK;
			}
			
			//Identifier identifier = null;
			ComputedOperatorManager<OM, XPression<?>> cosm = evaluator.topOperatorManager();
			if(cosm == null || !cosm.item().symbol().equals(".")) {
				Type<?> t = classesMan.getType(str);
				if(t != null) {
					if('.' != ch) throw new ManagedException(String.format("Syntax error near %s . Unexpected %S", str, ch.toString()));
					
					evaluator.push(new XPIdentifier<>(new Identifier(str, IDType.CLASS), context));
					return READ_OK;
					
				}
				
				Variable<?> var = evaluator.getVariable(str, context);
				if(var == null) {
					var = evaluator.getVariable("this", context);
					if(var == null) 
						throw new ManagedException(String.format("%s . Unknown identifier.", str));
					
					
					Type<?> type = classesMan.getType(var.valueClass());
					if(type == null) 
						throw new ManagedException(String.format("%s . Unknown identifier.", str));
					
					if(type.propertyValueClass(str) == null)
						throw new ManagedException(String.format("%s . Unknown identifier.", str));
					
					evaluator.push(new XPIdentifier<>(new VariableIdentifier(var.name(), classesMan.getType(var.valueClass())), context));
					evaluator.push(evaluator.getBinaryOp("."));
					
					//identifier = new Identifier(str, IDType.PROPERTY);
					evaluator.push(new XPIdentifier<>(new Identifier(str, IDType.PROPERTY), context));
					
					return READ_OK;
				}
				
				evaluator.push(new XPIdentifier<>(new VariableIdentifier(var.name(), classesMan.getType(var.valueClass())), context));
				return READ_OK;
			}
			
			//identifier = new Identifier(str, IDType.PROPERTY);
			
			evaluator.push(new XPIdentifier<>(new Identifier(str, IDType.PROPERTY), context));
			return READ_OK;
		}
		
		if('(' == ch) {
			lexingRules.nextNonBlankChar(cr);
			evaluator.push(XPEvaluator.OP_OPEN_PARENTHESIS);
			readExpressionInBracket(cr, ')', context);
			
			return READ_OK;			
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
			
			return READ_NOT_EOF;
		}
		
		if('.' == firstChar) {
			cr.nextChar();
			evaluator.push(evaluator.getBinaryOp(firstChar.toString()));
			
			return READ_OK;
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
		
		if(XPLexingRules.EXTENDED_NUMERIC_TERMINATION.indexOf(str.charAt(str.length() - 1)) >= 0) return ClassesMan.T_DOUBLE.constant(Double.valueOf(res.toString()));
		
		Character ch = lexingRules.nextForwardChar(cr);
		
		if(ch == null) return ClassesMan.T_INTEGER.constant(Integer.valueOf(res.toString()));
		
		if(ch == '.') {
			cr.nextChar();
			
			ch = lexingRules.nextForwardChar(cr);
			if(ch == null || XPLexingRules.NUMERIC_DIGITS.indexOf(ch) < 0) 	return ClassesMan.T_DOUBLE.constant(Double.valueOf(res.toString()));
			
			String str2 = lexingRules.nextNonNullString(cr);
			
			if(!lexingRules.isInteger(str, true)) throw new ManagedException(String.format("%s is not not numeric", str2));
			res.append("." + str2);
			
			return ClassesMan.T_DOUBLE.constant(Double.valueOf(res.toString()));
		}
		
		return ClassesMan.T_INTEGER.constant(Integer.valueOf(res.toString()));
	}
	
	private int readString(CharReader cr, Character end) throws ManagedException {
		String str = readStringReturnString(cr, end);
		
		evaluator.push(ClassesMan.T_STRING.constant(str));
		
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
