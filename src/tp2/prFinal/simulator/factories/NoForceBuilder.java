package tp2.prFinal.simulator.factories;

import org.json.JSONObject;

import tp2.prFinal.simulator.model.ForceLaws;
import tp2.prFinal.simulator.model.NoForce;

public class NoForceBuilder extends Builder<ForceLaws>{

	public NoForceBuilder() {
		type = "ng";
		data = " ";
		desc = "No force";
	}

	@Override
	protected ForceLaws createTheInstance(JSONObject info) {
		return new NoForce();
	}


	

}
