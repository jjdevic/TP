package tp2.simulator.factories;

import org.json.JSONObject;

public abstract class Builder<T> {
	protected String type;
	protected String desc;
	
	public Builder() {
		type="";
		desc="";
	}
	
	public T createInstance(JSONObject info) {
		T aux = null;
		
		if(type != null && type.equals(info.getString("type"))) {
			aux = createTheInstance(info.has("data") ? info.getJSONObject("data") : null);
		}
		return aux;
	}

	public JSONObject getBuilderInfo() {
		JSONObject aux = new JSONObject();
		aux.put("type", type);
		aux.put("data", createData());
		aux.put("desc", desc);
		
		return aux;
	}
	
	protected JSONObject createData() {
		return new JSONObject();
	}
	
	protected abstract T createTheInstance(JSONObject info);
}