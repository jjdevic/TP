package tp2.pr2.simulator.factories;

import org.json.JSONObject;

import tp2.pr2.simulator.control.MassEqualStates;
import tp2.pr2.simulator.control.StateComparator;

public class MassEqualStatesBuilder extends Builder<StateComparator> {
	
	public MassEqualStatesBuilder() {
		type = "masseq";
		desc = "Mass iguality";
	}

	@Override
	protected StateComparator createTheInstance(JSONObject info) {
		return new MassEqualStates();
	}

}
