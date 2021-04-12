package tp2.pr2.simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import tp2.pr1.simulator.misc.Vector2D;
import tp2.pr1.simulator.model.Body;

public class BasicBodyBuilder extends Builder<Body> {
	private Vector2D p, v;
	private String id;
	private Double m;
	
	public BasicBodyBuilder() {
		type = "basic";
		desc = "Basic Body";
	}

	@Override
	protected Body createTheInstance(JSONObject info) {
		 JSONArray posicion = info.getJSONArray("p");
		 p = new Vector2D(posicion.getDouble(0), posicion.getDouble(1));
		 
		 JSONArray velocidad = info.getJSONArray("v");
		 v = new Vector2D(velocidad.getDouble(0), velocidad.getDouble(1));
		 
		 id = info.getString("id");
		 m = info.getDouble("m");
		 
		 return new Body(id, v, p, m);
	}
	
	@Override
	protected JSONObject createData() {
		JSONObject data = new JSONObject();
		data.put("id", id);
		data.put("p", p);
		data.put("v", v);
		data.put("m", m);
		
		return data;
		
	}

}
