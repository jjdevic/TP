package tp2.prFinal.simulator.factories;

import org.json.JSONObject;

import java.util.List;

public interface Factory<T> {
	public T createInstance(JSONObject info);
	public List<JSONObject> getInfo();
}
