package tp2.prFinal.simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;
import tp2.prFinal.simulator.misc.Vector2D;
import tp2.prFinal.simulator.model.Body;

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
		 JSONArray position = info.getJSONArray("p");
		 p = new Vector2D(position.getDouble(0), position.getDouble(1));
		 
		 JSONArray velocity = info.getJSONArray("v");
		 v = new Vector2D(velocity.getDouble(0), velocity.getDouble(1));
		 
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
