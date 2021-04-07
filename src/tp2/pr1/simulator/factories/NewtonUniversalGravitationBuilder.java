package tp2.pr1.simulator.factories;

import org.json.JSONObject;

import tp2.pr1.simulator.model.ForceLaws;
import tp2.pr1.simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws>{
	
	public NewtonUniversalGravitationBuilder() {
		type = "nlug";
		desc = "Newton’s law of universal gravitation";
	}

	@Override
	protected ForceLaws createTheInstance(JSONObject info) {
		Double G = info.has("G") ? info.getDouble("G") : 6.67E-11;
		 
		return new NewtonUniversalGravitation(G);
	}
	
	@Override
	protected JSONObject createData() {
		JSONObject data = new JSONObject();
		data.put("G", "");
		
		return data;
		
	}

}
