package tp2.pr1.simulator.factories;

import org.json.JSONObject;
import tp2.pr1.simulator.model.ForceLaws;
import tp2.pr1.simulator.model.NoForce;

public class NoForceBuilder extends Builder<ForceLaws>{

	@Override
	protected ForceLaws createTheInstance(JSONObject info) {
		return new NoForce();
	}
	

}
