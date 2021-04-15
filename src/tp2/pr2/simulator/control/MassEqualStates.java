package tp2.pr2.simulator.control;

import org.json.JSONObject;

public class MassEqualStates implements StateComparator {

	@Override
	public boolean equal(JSONObject s1, JSONObject s2) {

		if(s1.getDouble("time") != s2.getDouble("time")) return false;
		if(s1.getJSONArray("bodies").length() != s2.getJSONArray("bodies").length()) return false;

		for(int i = 0; i < s1.getJSONArray("bodies").length(); i++) {
			if(!s1.getJSONArray("bodies").getJSONObject(i).getString("id").equals(s2.getJSONArray("bodies").getJSONObject(i).getString("id"))) return false;
			if((s1.getJSONArray("bodies").getJSONObject(i).getDouble("m")) != (s2.getJSONArray("bodies").getJSONObject(i).getDouble("m"))) return false;
		}
		return true;
	}

}
