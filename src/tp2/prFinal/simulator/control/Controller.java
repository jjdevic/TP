package tp2.prFinal.simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import tp2.prFinal.simulator.exceptions.NotEqualStatesException;
import tp2.prFinal.simulator.factories.Factory;
import tp2.prFinal.simulator.model.Body;
import tp2.prFinal.simulator.model.ForceLaws;
import tp2.prFinal.simulator.model.PhysicsSimulator;
import tp2.prFinal.simulator.model.SimulatorObserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

public class Controller {
	private Factory<Body> _bodyF;
	private Factory<ForceLaws> _fLawsF;
	private PhysicsSimulator _phsysicsSim;
	public static boolean _BODIES_LOADED = false;
	
	public Controller(PhysicsSimulator phsysicsSim, Factory<Body> bFactory, Factory<ForceLaws> fFactory) {
		this._phsysicsSim = phsysicsSim;
		this._bodyF = bFactory;
		this._fLawsF = fFactory;
	}
	
	public void loadBodies(InputStream in) {
		JSONObject jsonInput = new JSONObject(new JSONTokener(in));
		JSONArray jAux = jsonInput.getJSONArray("bodies");
		
		for(int i = 0; i < jAux.length(); i++) {
			_phsysicsSim.addBody(_bodyF.createInstance(jAux.getJSONObject(i)));
		}
		_BODIES_LOADED = true;
	}

	public void run(int n) {
		for(int i = 0; i < n; i++) {
			_phsysicsSim.advance();
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

	public void reset(){
		_BODIES_LOADED = false;
		_phsysicsSim.reset();
	}

	public void setDeltaTime(double dt){
		_phsysicsSim.setDeltaTime(dt);
	}

	public void addObserver(SimulatorObserver o) {
		_phsysicsSim.addObserver(o);
	}

	public List<JSONObject> getForceLawsInfo(){
		return _fLawsF.getInfo();
	}

	public void setForceLaws(JSONObject info) {
		_phsysicsSim.setForceLaws(_fLawsF.createInstance(info));
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
