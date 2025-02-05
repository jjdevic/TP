package tp2.pr2.simulator.model;

import tp2.pr2.simulator.misc.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class NewtonUniversalGravitation implements ForceLaws{
	private double _G;
	
	public NewtonUniversalGravitation(double _G) {
		this._G = _G;
	}
	
	@Override
	public void apply(List<Body> bs) {
		List<Body> bodyList = new ArrayList<Body>(bs);
		
		for(Body bs1 : bodyList) {
			for(Body bs2 : bodyList) {
				if(!bs1.getId().equals(bs2.getId())) {
					if(bs1.getMass() == 0.0) {
						bs2._v =  new Vector2D(0, 0);
					}
					else {
						bs2.addForce((bs2.getPosition().minus(bs1.getPosition())).direction().scale(-force(bs1, bs2).magnitude()));
					}
				}
			}
		}
	}

	private Vector2D force(Body a, Body b) {
	    Vector2D delta = b.getPosition().minus(a.getPosition());
	    double dist = delta.magnitude();
	    double magnitude = dist > 0 ? (_G * a.getMass() * b.getMass()) / (dist * dist) : 0.0;
	    return delta.direction().scale(magnitude);
	}
	
	public String toString() {
		return "Newton Universal Gravitation Law. G value: " +_G;
	}
}
