package tp2.prFinal.simulator.control;

import org.json.JSONObject;
import tp2.prFinal.simulator.misc.Vector2D;

public class EpsilonEqualStates implements StateComparator {
	private double eps;
	
	public EpsilonEqualStates(double _eps) {
		this.eps = _eps;
	}

	@Override
	public boolean equal(JSONObject s1, JSONObject s2) {		
		if(s1.getJSONArray("bodies").length() != s2.getJSONArray("bodies").length()) return false;
		if(s1.getDouble("time") != s2.getDouble("time")) return false;
		
		Vector2D vecF1, vecF2, vecP1, vecP2, vecV1, vecV2;
		
		for(int i = 0; i < s1.getJSONArray("bodies").length(); i++) {
			if(!s1.getJSONArray("bodies").getJSONObject(i).getString("id").equals(s2.getJSONArray("bodies").getJSONObject(i).getString("id"))) return false;

			vecP1 = new Vector2D(s1.getJSONArray("bodies").getJSONObject(i).getJSONArray("p").getDouble(0), s1.getJSONArray("bodies").getJSONObject(i).getJSONArray("p").getDouble(1));
			vecP2 = new Vector2D(s2.getJSONArray("bodies").getJSONObject(i).getJSONArray("p").getDouble(0), s2.getJSONArray("bodies").getJSONObject(i).getJSONArray("p").getDouble(1));
			if(vecP1.distanceTo(vecP2) > eps) return false;
			
			vecV1 = new Vector2D(s1.getJSONArray("bodies").getJSONObject(i).getJSONArray("v").getDouble(0), s1.getJSONArray("bodies").getJSONObject(i).getJSONArray("v").getDouble(1));
			vecV2 = new Vector2D(s1.getJSONArray("bodies").getJSONObject(i).getJSONArray("v").getDouble(0), s1.getJSONArray("bodies").getJSONObject(i).getJSONArray("v").getDouble(1));
			if(vecV1.distanceTo(vecV2) > eps) return false;
			
			vecF1 = new Vector2D(s1.getJSONArray("bodies").getJSONObject(i).getJSONArray("f").getDouble(0), s1.getJSONArray("bodies").getJSONObject(i).getJSONArray("f").getDouble(1));
			vecF2 = new Vector2D(s1.getJSONArray("bodies").getJSONObject(i).getJSONArray("f").getDouble(0), s1.getJSONArray("bodies").getJSONObject(i).getJSONArray("f").getDouble(1));
			if(vecF1.distanceTo(vecF2) > eps) return false;
			
			if(Math.abs(s1.getJSONArray("bodies").getJSONObject(i).getDouble("m") - s2.getJSONArray("bodies").getJSONObject(i).getDouble("m")) > eps) return false;
		}
		return true;
	}

}
