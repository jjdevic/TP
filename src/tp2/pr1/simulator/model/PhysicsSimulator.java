package tp2.pr1.simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class PhysicsSimulator {
	List<Body> bs;
	
	private double dTime;
	private double aTime;
	private ForceLaws fLaws;
	
	public PhysicsSimulator(ForceLaws fLaws, double dt) {
		bs = new ArrayList<Body>();
		aTime = 0.0;
		this.fLaws = fLaws;
		this.dTime = dt;
		
		if(fLaws == null || dTime < 0) throw new IllegalArgumentException();
	}
	
	public void advance() {
		for(Body b: bs) b.resetForce();		
		fLaws.apply(bs);
		for(Body b: bs) b.move(dTime);
		aTime += dTime;
	}
	
	public void addBody(Body b) {
		for(Body body: bs) {
			if(b.equals(body)) throw new IllegalArgumentException("Two bodies with the same ID");
		}
		bs.add(b);
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
	
	public String toString() {
		return getState().toString();
	}
}
