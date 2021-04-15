package tp2.pr2.simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import tp2.pr2.simulator.misc.Vector2D;
import tp2.pr2.simulator.model.Body;
import tp2.pr2.simulator.model.MassLossingBody;

public class MassLosingBodyBuilder extends Builder<Body>{
	private Vector2D p, v;
	private String id;
	private double m, freq, factor;
	
	public MassLosingBodyBuilder() {
		type = "mlb";
		desc = "Mass Lossing Body";
	}

	@Override
	protected Body createTheInstance(JSONObject info) {
		JSONArray posicion = info.getJSONArray("p");
		p = new Vector2D(posicion.getDouble(0), posicion.getDouble(1));
		 
		JSONArray velocidad = info.getJSONArray("v");
		v = new Vector2D(velocidad.getDouble(0), velocidad.getDouble(1));
		
		id = info.getString("id");
		m = info.getDouble("m");
		freq = info.getDouble("freq");
		factor = info.getDouble("factor");
		
		return new MassLossingBody(id, v, p, m, factor, freq);
	}
	
	@Override
	protected JSONObject createData() {
		JSONObject data = new JSONObject();
		data.put("id", id);
		data.put("p", p);
		data.put("v", v);
		data.put("m", m);
		data.put("freq", freq);
		data.put("factor", factor);
		
		return data;
		
	}

}
