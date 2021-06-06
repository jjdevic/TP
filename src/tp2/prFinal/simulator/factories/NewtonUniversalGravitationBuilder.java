package tp2.prFinal.simulator.factories;

import org.json.JSONObject;
import tp2.prFinal.simulator.model.ForceLaws;
import tp2.prFinal.simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws>{
	
	public NewtonUniversalGravitationBuilder() {
		type = "nlug";
		data = "G : the gravitational constant (a number)";
		desc = "Newton's law of universal gravitation";
	}

	@Override
	protected ForceLaws createTheInstance(JSONObject info) {
		return new NewtonUniversalGravitation(info.has("G") ? info.getDouble("G") : 6.67E-11);
	}
	
	@Override
	protected JSONObject createData() {
		JSONObject data = new JSONObject();
		data.put("G", "The gravitational constant (a number)");

		return data;
	}

}
