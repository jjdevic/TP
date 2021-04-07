package tp2.pr1.simulator.control;

import org.json.JSONObject;

public interface StateComparator {
	boolean equal(JSONObject s1, JSONObject s2);
}
