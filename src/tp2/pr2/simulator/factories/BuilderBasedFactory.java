package tp2.pr2.simulator.factories;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BuilderBasedFactory<T> implements Factory<T> {
	private List<Builder<T>> _builders;
	private List<JSONObject> _factoryElements;
	
	public BuilderBasedFactory(List<Builder<T>> builders) {
		this._builders = new ArrayList<>(builders);
		_factoryElements = new ArrayList<>();
		
		for(Builder<T> b:_builders) {
			_factoryElements.add(b.getBuilderInfo());
		}	
	}

	@Override
	public T createInstance(JSONObject info) {
		if(info != null) {
			for(Builder<T> b : _builders) {
				T aux = b.createInstance(info);
				if(aux != null) return aux;
			}
		}
		throw new IllegalArgumentException();
	}

	@Override
	public List<JSONObject> getInfo() {
		return _factoryElements;
	}

}
