package tp2.pr1.simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import tp2.pr1.simulator.exceptions.NotEqualStatesException;
import tp2.pr1.simulator.factories.Factory;
import tp2.pr1.simulator.model.Body;
import tp2.pr1.simulator.model.PhysicsSimulator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class Controller {
	private Factory<Body> _factory;
	private PhysicsSimulator _phsysicsSim;
	
	
	public Controller(PhysicsSimulator phsysicsSim, Factory<Body> factory) {
		this._phsysicsSim = phsysicsSim;
		this._factory = factory;
	}
	
	public void loadBodies(InputStream in) {
		JSONObject jsonInupt = new JSONObject(new JSONTokener(in));
		JSONArray jAux = jsonInupt.getJSONArray("bodies");
		
		for(int i = 0; i < jAux.length(); i++) {
			_phsysicsSim.addBody(_factory.createInstance(jAux.getJSONObject(i)));
		}
	}
	
	public void run(int n, OutputStream out, InputStream expOut, StateComparator cmp) throws NotEqualStatesException {		
		JSONObject currState = null;
		JSONObject expState = null;
		JSONObject expOutJO = null;
		
		if(expOut != null) expOutJO = new JSONObject(new JSONTokener(expOut));
		
		if(out == null) {
			out = new OutputStream() {;
			
			@Override 
			public void write(int b) throws IOException { }
			};
		}
		PrintStream p = new PrintStream(out);
		p.println("{");
		p.println("\"states\": [");
		
		for(int i = 0; i < n - 1; i++) {
			currState = _phsysicsSim.getState();		
			if(expOut != null) {
				expState = expOutJO.getJSONArray("states").getJSONObject(i);
				String aux = i + " " + currState.toString() + expState.toString();
				if(!cmp.equal(currState, expState)) throw new NotEqualStatesException(aux);
			}
			p.println(currState);
			p.print(",");
			_phsysicsSim.advance();
		}
		p.println(currState);
		p.println("]");
		p.println("}");
	}
/*														//El controlador de 4 parametros es tambien capaz de funcionar con 2 por lo que el controlador de 2 elementos NO es necesario.											
	public void run(int n, OutputStream out) {
		JSONObject currState = null;
		
		if(out == null) {
			out = new OutputStream() {;
			
			@Override 
			public void write(int b) throws IOException { }
			};
		}
		PrintStream p = new PrintStream(out);
		p.println("{");
		p.println("\"states\": [");
		
		
		for(int i = 0; i < n-1; i++) {
			currState = _phsysicsSim.getState();
			p.println(currState);
			p.print(",");
			_phsysicsSim.advance();
		}
		p.println(currState);
		p.println("]");
		p.println("}");
	}
*/
}
