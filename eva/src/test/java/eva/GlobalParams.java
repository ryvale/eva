package eva;

import java.util.Map;

public class GlobalParams {
	
	private Map<String, Object> params;

	public GlobalParams(Map<String, Object> params) {
		super();
		this.params = params;
	}
	
	
	public String getString(String name) {
		Object value = params.get(name);
		
		return value == null ? null : value.toString();
	}
	

}
