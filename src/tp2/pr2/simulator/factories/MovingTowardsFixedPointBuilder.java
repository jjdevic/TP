package tp2.pr2.simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import tp2.pr2.simulator.misc.Vector2D;
import tp2.pr2.simulator.model.ForceLaws;
import tp2.pr2.simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws>{
	private Vector2D c;
	
	public MovingTowardsFixedPointBuilder() {
		type = "mtfp";
		data = "c : the point towards which bodies move (a json list of 2 numbers, e.g., [100.0,50.0])\n" + "g : the length of the acceleration vector (a number)";
		desc = "Moving towards a fixed point";
	}

	@Override
	protected ForceLaws createTheInstance(JSONObject info) {
		c = new Vector2D(0.0, 0.0);	
		if(info.has("c")) {
			c = new Vector2D(info.getJSONArray("c").getDouble(0), info.getJSONArray("c").getDouble(1));
		}
		return new MovingTowardsFixedPoint(c, info.has("g") ? info.getDouble("g") : 9.81);
	}
	
	@Override
	protected JSONObject createData() {
		JSONObject data = new JSONObject();
		data.put("c", c);
		data.put("g","gravity");
		
		return data;
		
	}

}
