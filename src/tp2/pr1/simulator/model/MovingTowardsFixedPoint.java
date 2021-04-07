package tp2.pr1.simulator.model;

import java.util.List;

import tp2.pr1.simulator.misc.Vector2D;

public class MovingTowardsFixedPoint implements ForceLaws{
	private Vector2D _c;
	private double _g;
	
	public MovingTowardsFixedPoint(Vector2D _c, double _g) {
		this._c = new Vector2D(_c);
		this._g = _g;
	}

	@Override
	public void apply(List<Body> bs) {		
		for(Body b: bs) {
			b.addForce(_c.minus(b.getPosition()).direction().scale(_g*b.getMass()));
		}
	}
	
	public String toString() {
		return "Moving towards " + _c + " with constant accelation " +_g;
	}

}
