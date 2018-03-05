package eva;

import java.util.Vector;

import com.exa.eva.OperatorManager.OMOperandType;
import com.exa.expression.OMMethod;
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
	
	private class MethodSQLString extends OMMethod.XPOrtStaticMethod<String> {

		public MethodSQLString(String symbol) {
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
		protected XPMethodResult createResultOperand(Vector<XPOperand<?>> params) {
			
			return new XPMethodResult(params) {
				
				@Override
				public String value(XPEvaluator evaluator) throws ManagedException {
					return "'" + params.get(0).asOPString().value(evaluator) + "'";
				}
			};
		}
		
	}

	
	public TTest() {
		super(null, ValueCls.class, "test");
	}
	
	@Override
	public void initialize() {
		OMMethod<String> osm = new OMMethod<>("execute", 0, OMOperandType.PRE_OPERAND);
		osm.addOperator(new MethodExecute("execute"));
		
		staticMethods.put("execute", new Method<>("execute", String.class, osm));
		
		osm = new OMMethod<>("sqlString", 1, OMOperandType.PRE_OPERAND);
		osm.addOperator(new MethodSQLString("sqlString"));
		
		staticMethods.put("sqlString", new Method<>("sqlString", String.class, osm));
	}
	
	

}
