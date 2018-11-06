package eva;

import java.util.Vector;

import com.exa.eva.OperatorManager.OMOperandType;
import com.exa.expression.OMMethod;
import com.exa.expression.OMMethod.XPOrtMethod;
import com.exa.expression.OMMethod.XPOrtStaticMethod;
import com.exa.expression.Type;
import com.exa.expression.XPOperand;
import com.exa.expression.eval.ClassesMan;
import com.exa.expression.eval.XPEvaluator;
import com.exa.expression.types.TObjectClass;
import com.exa.utils.ManagedException;

public class TTest extends TObjectClass<ValueCls, Object> {
	public final static TTest DEFAULT = new TTest();
	
	private class MethodExecute extends OMMethod.XPOrtStaticMethod<String> {

		public MethodExecute(String symbol) {
			super(symbol, 0);
		}

		@Override
		public boolean canManage(XPEvaluator eval, int order, int nbOperands) throws ManagedException {
			return true;
		}

		@Override
		public Type<?> type() {
			return ClassesMan.T_STRING;
		}

		@Override
		protected XPMethodResult createResultOperand(Vector<XPOperand<?>> params) {
			
			return new XPMethodResult(params) {
				
				@Override
				public String value(XPEvaluator evaluator) throws ManagedException {
					return "OK";
				}
			};
		}
		
	}
	
	private class MethodExecute2 extends OMMethod.XPOrtStaticMethod<String> {

		public MethodExecute2(String symbol) {
			super(symbol, 1);
		}

		@Override
		public boolean canManage(XPEvaluator eval, int order, int nbOperands) throws ManagedException {
			return true;
		}

		@Override
		public Type<?> type() {
			return ClassesMan.T_STRING;
		}

		@Override
		protected XPOrtStaticMethod<String>.XPMethodResult createResultOperand(Vector<XPOperand<?>> params) {
			return new XPMethodResult(params) {
				
				@Override
				public String value(XPEvaluator evaluator) throws ManagedException {
					return "OK2";
				}
			};
		}
		
	}
	
	private class MethodSQLString extends OMMethod.XPOrtMethod<TTest, String> {

		public MethodSQLString(String symbol) {
			super(symbol, 2);
		}

		@Override
		public boolean canManage(XPEvaluator eval, int order, int nbOperands) throws ManagedException {
			return true;
		}

		@Override
		public Type<?> type() {
			return ClassesMan.T_STRING;
		}

		@Override
		protected XPOrtMethod<TTest, String>.XPMethodResult createResultOperand(XPOperand<TTest> xpOb,	Vector<XPOperand<?>> params) {
			// TODO Auto-generated method stub
			return new XPMethodResult(xpOb, params) {

				@Override
				public String value(XPEvaluator evaluator) throws ManagedException {
					return "'" + params.get(0).asOPString().value(evaluator) + "'";
				}
				
				/*@Override
				public String value(XPEvaluator eval) throws ManagedException {
					String fieldName = xpFieldName.get(0).asOPString().value(eval);
					DataReader<?> dr = xpDR.value(eval);
					
					return dr.getDate(fieldName);
				}*/
			};
		}

		/*@Override
		protected XPMethodResult createResultOperand(Vector<XPOperand<?>> params) {
			
			return new XPMethodResult(params) {
				
				@Override
				public String value(XPEvaluator evaluator) throws ManagedException {
					return "'" + params.get(0).asOPString().value(evaluator) + "'";
				}
			};
		}*/
		
	}

	public TTest() {
		super(null, ValueCls.class, "test");
	}
	
	@Override
	public void initialize() {
		OMMethod<String> osm = new OMMethod<>("execute", 0, OMOperandType.PRE_OPERAND);
		osm.addOperator(new MethodExecute("execute"));
		
		staticMethods.put("execute", new Method<>("execute", String.class, osm));
		
		osm = new OMMethod<>("execute2", 1, OMOperandType.PRE_OPERAND);
		osm.addOperator(new MethodExecute2("execute2"));
		
		staticMethods.put("execute2", new Method<>("execute2", String.class, osm));
		
		osm = new OMMethod<>("sqlString", 2, OMOperandType.POST_OPERAND);
		osm.addOperator(new MethodSQLString("sqlString"));
		methods.put("sqlString", new Method<>("sqlString", String.class, osm));
		//methods.put(key, value)
		
		staticMethods.put("sqlString", new Method<>("sqlString", String.class, osm));
	}
	
	

}
