package eva;

import com.exa.eva.OperatorManager.OMOperandType;
import com.exa.expression.OMMethod;
import com.exa.expression.Type;
import com.exa.expression.types.TObjectClass;

public class TGlobalParams extends TObjectClass<GlobalParams, Object> {

	public TGlobalParams() {
		super(null, GlobalParams.class, "GlobalParams");
	}

	@Override
	public void initialize() {
		OMMethod<String> omStr = new OMMethod<>("getString", 2, OMOperandType.POST_OPERAND);
		omStr.addOperator(new GPGetString());
		methods.put("getString", new Method<>("getString", String.class, omStr));
	}

	@Override
	public Type<GlobalParams> specificType() {
		return this;
	}

	
	
}
