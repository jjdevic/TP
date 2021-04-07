package tp2.pr1.simulator.control;

import org.json.JSONObject;

public class MassEqualStates implements StateComparator {

	@Override
	public boolean equal(JSONObject s1, JSONObject s2) {

		if(s1.getDouble("time") != s2.getDouble("time")) return false;
		if(s1.length() != s2.length()) return false;
		
		for(int i = 0; i < s1.length() || i < s2.length(); i++) {
			if((s1.getJSONArray("bodies").getJSONObject(i)).equals(s2.getJSONArray("bodies").getJSONObject(i))) return false;
			if((s1.getJSONArray("bodies").getJSONObject(i).getDouble("m")) != (s2.getJSONArray("bodies").getJSONObject(i).getDouble("m"))) return false;
			
			
		}
		return true;
	}

}
