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
    private String[] _colNames = { "ID", "Mass", "Position", "Velocity", "Force" };
    private String[] table;

    BodiesTableModel(Controller ctrl) {
        _bodies = new ArrayList<>();
        table = new String[_colNames.length];
        ctrl.addObserver(this);
    }

    @Override
    public int getRowCount() {
        return _bodies.size();
    }

    @Override
    public int getColumnCount() {
        return _colNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return _colNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        table[0] = _bodies.get(rowIndex).getId().toString();
        table[1] = String.valueOf(_bodies.get(rowIndex).getMass());
        table[2] = String.valueOf(_bodies.get(rowIndex).getPosition());
        table[3] = String.valueOf(_bodies.get(rowIndex).getVelocity());
        table[4] = String.valueOf(_bodies.get(rowIndex).getForce());
        return table[columnIndex];
    }

    @Override
    public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                _bodies = bodies;
                fireTableStructureChanged();
            }
        });
    }

    @Override
    public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                _bodies = bodies;
                fireTableStructureChanged();
            }
        });
    }

    @Override
    public void onBodyAdded(List<Body> bodies, Body b) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                _bodies = bodies;
                fireTableStructureChanged();
            }
        });
    }

    @Override
    public void onAdvance(List<Body> bodies, double time) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                _bodies = bodies;
                fireTableStructureChanged();
            }
        });
    }

    @Override
    public void onDeltaTimeChanged(double dt) { }

    @Override
    public void onForceLawsChanged(String fLawsDesc) { }
}
