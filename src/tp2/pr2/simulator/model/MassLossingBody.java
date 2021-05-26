package tp2.pr2.simulator.model;

import org.json.JSONObject;
import tp2.pr2.simulator.misc.Vector2D;

public class MassLossingBody extends Body{
	private double _lossFactor;
	private double _lossFrequency;
	private double _c;

	public MassLossingBody(String id, Vector2D v, Vector2D p, double m, double lossFactor, double lossFrequency) {
		super(id, v, p, m);
		this._lossFactor = lossFactor;
		this._lossFrequency = lossFrequency;
		_c = 0.0;
	}
	
	@Override
	void move(double t) {
		super.move(t);
		_c += t;
		if(_c >= _lossFrequency) {
			_m = _m * (1 - _lossFactor);
			_c = 0.0;
		}
	}

	@Override
	public JSONObject getState(){
		JSONObject jo = super.getState();;
		jo.put("lossFactor", getLossFactor());
		jo.put("_lossFrequency", getLossFrequency());

		return jo;
	}

	public double getLossFactor(){
		return _lossFactor;
	}

	public double getLossFrequency(){
		return _lossFrequency;
	}

}
