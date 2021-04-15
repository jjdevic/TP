package tp2.pr2.simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class PhysicsSimulator {
	private List<Body> bs;
	private List<SimulatorObserver> sObs;
	private double dTime;
	private double aTime;
	private ForceLaws fLaws;
	
	public PhysicsSimulator(ForceLaws fLaws, double dt) {
		bs = new ArrayList<Body>();
		sObs = new ArrayList<SimulatorObserver>();
		aTime = 0.0;
		this.fLaws = fLaws;
		this.dTime = dt;
		if(fLaws == null || dTime < 0) throw new IllegalArgumentException();
	}
	
	public void reset() {
		bs.clear();
		dTime = 0.0;
		aTime = 0.0;
		for(SimulatorObserver o : sObs) o.onReset(bs, aTime, dTime, fLaws.toString());
	}
	
	public void advance() {
		for(Body b: bs) b.resetForce();		
		fLaws.apply(bs);
		for(Body b: bs) b.move(dTime);
		aTime += dTime;
		for(SimulatorObserver o : sObs) o.onAdvance(bs, aTime);
	}
	
	public void addBody(Body b) {
		for(Body body: bs) {
			if(b.equals(body)) throw new IllegalArgumentException("Two bodies with the same ID");
		}
		bs.add(b);
		for(SimulatorObserver o : sObs) o.onBodyAdded(bs, b);
	}
	
	public JSONObject getState() {
		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();
		
		jo.put("time", aTime);
		for(Body b: bs) {
			ja.put(b.getState());
			jo.put("bodies", ja);
		}
		return jo;
	}
	
	public void setDeltaTime(double dt) {
		if (dt < 0) throw new IllegalArgumentException();
		dTime = dt;
		for(SimulatorObserver o : sObs) o.onDeltaTimeChanged(dt);
	}
	
	public void setForceLaws(ForceLaws fLaws) {
		if(fLaws == null) throw new IllegalArgumentException();
		this.fLaws = fLaws;
		for(SimulatorObserver o : sObs) o.onForceLawsChanged(fLaws.toString());
	}
	
	public String toString() {
		return getState().toString();
	}

	public void addObserver(SimulatorObserver o) {
		sObs.add(o);
		o.onRegister(bs, aTime, dTime, fLaws.toString());
	}
	
	
}
