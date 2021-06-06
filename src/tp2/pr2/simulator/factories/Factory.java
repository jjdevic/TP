package tp2.pr2.simulator.factories;

import org.json.JSONObject;

import java.util.List;

public interface Factory<T> {
	public T createInstance(JSONObject info);
	public List<JSONObject> getInfo();
}
