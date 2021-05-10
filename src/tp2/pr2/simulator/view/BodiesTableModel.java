package tp2.pr2.simulator.view;

import tp2.pr2.simulator.control.Controller;
import tp2.pr2.simulator.model.Body;
import tp2.pr2.simulator.model.SimulatorObserver;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {

    private List<Body> _bodies;

    BodiesTableModel(Controller ctrl) {
        _bodies = new ArrayList<>();
        ctrl.addObserver(this);
    }

    @Override
    public int getRowCount() {
        return 0;
    }

    @Override
    public int getColumnCount() {
        return 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
    }

    @Override
    public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {

    }

    @Override
    public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {

    }

    @Override
    public void onBodyAdded(List<Body> bodies, Body b) {

    }

    @Override
    public void onAdvance(List<Body> bodies, double time) {

    }

    @Override
    public void onDeltaTimeChanged(double dt) {

    }

    @Override
    public void onForceLawsChanged(String fLawsDesc) {

    }
}
