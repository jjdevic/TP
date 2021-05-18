package tp2.pr2.simulator.view;

import tp2.pr2.simulator.control.Controller;
import tp2.pr2.simulator.model.Body;
import tp2.pr2.simulator.model.SimulatorObserver;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StatusBar extends JPanel implements SimulatorObserver {

    private JLabel _currTime; // for current time
    private JLabel _currLaws; // for gravity laws
    private JLabel _numOfBodies; // for number of bodies

    public StatusBar(Controller ctrl) {
        _currTime = new JLabel("Time: " + 0.0);
        _currTime.setPreferredSize(new Dimension(100, 30));
        _currLaws = new JLabel("Laws: ");
        _numOfBodies = new JLabel("Bodies: " + 0);
        initGUI();
        ctrl.addObserver(this);
    }

    private void initGUI() {
        this.setLayout( new FlowLayout( FlowLayout.LEFT ));
        this.setBorder( BorderFactory.createBevelBorder( 1 ));

        _currTime.setPreferredSize(new Dimension(100, 30));
        this.add(_currTime);
        _numOfBodies.setPreferredSize(new Dimension(100, 30));
        this.add(_numOfBodies);
        this.add(_currLaws);
    }

    @Override
    public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                _numOfBodies.setText("Bodies: " + String.valueOf(bodies.size()));
                _currTime.setText("Time: " + time);
                _currLaws.setText("Laws: " + fLawsDesc);
            }
        });
    }

    @Override
    public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                _numOfBodies.setText("Bodies: " + String.valueOf(bodies.size()));
                _currTime.setText(String.valueOf("Time: " + time));
                _currLaws.setText("Laws: " + fLawsDesc);
            }
        });
    }

    @Override
    public void onBodyAdded(List<Body> bodies, Body b) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                _numOfBodies.setText("Bodies: " + String.valueOf(bodies.size()));
            }
        });
    }

    @Override
    public void onAdvance(List<Body> bodies, double time) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                _currTime.setText("Time: " + time);
            }
        });
    }

    @Override
    public void onDeltaTimeChanged(double dt) {

    }

    @Override
    public void onForceLawsChanged(String fLawsDesc) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                _currLaws.setText("Laws: " + fLawsDesc);
            }
        });
    }
}
