package tp2.pr2.simulator.factories;

import org.json.JSONObject;

import tp2.pr1.simulator.control.EpsilonEqualStates;
import tp2.pr1.simulator.control.StateComparator;

public class EpsilonEqualStatesBuilder extends Builder<StateComparator> {
	private Double eps;
	
	public EpsilonEqualStatesBuilder() {
		type = "epseq";
		desc = "Epsilon module iguality";
	}

	@Override
	protected StateComparator createTheInstance(JSONObject info) {
		return new EpsilonEqualStates(info.has("eps") ? info.getDouble("eps") : 0.0);
	}
	
	@Override
	protected JSONObject createData() {
		JSONObject data = new JSONObject();
		data.put("eps", "allowed error");
			
		return data;	
	}
}
