package tp2.pr2.simulator.exceptions;

import org.json.JSONObject;

public class NotEqualStatesException extends Exception{
	
	public NotEqualStatesException(String message) {
		super(message);
	}

	public NotEqualStatesException(JSONObject expState, JSONObject currState, int i) {

	}
}
