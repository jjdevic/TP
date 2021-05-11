package tp2.pr2.simulator.view;

import tp2.pr2.simulator.control.Controller;
import tp2.pr2.simulator.model.Body;
import tp2.pr2.simulator.model.SimulatorObserver;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {

    private List<Body> _bodies;
    static final String[] _colNames = { "ID", "Mass", "Position", "Velocity", "Force" };
    private String[][] table;

    BodiesTableModel(Controller ctrl) {
        _bodies = new ArrayList<>();
        this.table = new String[_bodies.size()][_colNames.length];
        ctrl.addObserver(this);
    }

    @Override
    public int getRowCount() {
        return _colNames.length;
    }

    @Override
    public int getColumnCount() {
        return _bodies.size();
    }

    @Override
    public String getColumnName(int column) {
        return _colNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return table[rowIndex][columnIndex];
    }

    @Override
    public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                _bodies = new ArrayList<>(bodies);
                fireTableStructureChanged();
            }
        });
    }

    @Override
    public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                _bodies = new ArrayList<>(bodies);
                fireTableStructureChanged();
            }
        });
    }

    @Override
    public void onBodyAdded(List<Body> bodies, Body b) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                _bodies = new ArrayList<>(bodies);
                fireTableStructureChanged();
            }
        });
    }

    @Override
    public void onAdvance(List<Body> bodies, double time) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                _bodies = new ArrayList<>(bodies);
                fireTableStructureChanged();
            }
        });
    }

    @Override
    public void onDeltaTimeChanged(double dt) {

    }

    @Override
    public void onForceLawsChanged(String fLawsDesc) {

    }
}
