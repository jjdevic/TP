package tp2.pr2.simulator.model;

import tp2.pr1.simulator.misc.Vector2D;

public class MassLossingBody extends Body{
	private double _lossFactor;
	private double _lossFrecuency;
	private double _c;

	public MassLossingBody(String id, Vector2D v, Vector2D p, double m, double lossFactor, double lossFrecuency) {
		super(id, v, p, m);
		this._lossFactor = lossFactor;
		this._lossFrecuency = lossFrecuency;
		_c = 0.0;
	}
	
	@Override
	void move(double t) {
		super.move(t);
		_c += t;
		if(_c >= _lossFrecuency) {
			_m = _m* (1 - _lossFactor);
			_c = 0.0;
		}
	}
	
	

}
