package tp2.pr2.simulator.model;

import org.json.JSONObject;
import tp2.pr2.simulator.misc.Vector2D;

public class Body {
	protected String id;
	protected Vector2D _v;
	protected Vector2D _f;
	protected Vector2D _p;
	protected Double _m;
	
	public Body(String id, Vector2D v, Vector2D p, Double m) {
		this.id = id;
		this._p = p;
		this._v = v;
		this._m = m;
		_f = new Vector2D(0.0, 0.0);
	}
	
	public String getId() {
		return id;
	}
	
	public Vector2D getVelocity() {
		return _v;
	}
	
	public Vector2D getForce() {
		return _f;
	}
	
	public Vector2D getPosition() {
		return _p;
	}
	
	public Double getMass() {
		return _m;
	}
	
	void addForce(Vector2D f) {
		_f = _f.plus(f);
	}
	
	void resetForce() {
		_f = new Vector2D(0.0, 0.0);
	}
	
	void move(double t) {
		Vector2D _a = new Vector2D(0.0, 0.0);
		
		if(_m != 0.0) _a = _f.scale(1 / _m);
		_p = _p.plus(_v.scale(t).plus(_a.scale(0.5*t*t)));
		_v = _v.plus(_a.scale(t));
	}
	
	public JSONObject getState() {
		JSONObject jo = new JSONObject();
		jo.put("id", getId());
		jo.put("m", getMass());
		jo.put("v", getVelocity().asJSONArray());
		jo.put("p", getPosition().asJSONArray());
		jo.put("f", getForce().asJSONArray());
		
		return jo;
	}
	
	public String toString() {
		return getState().toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Body other = (Body) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	

}
