package tp2.simulator.control;

import org.json.JSONObject;

public interface StateComparator {
	boolean equal(JSONObject s1, JSONObject s2);
}
