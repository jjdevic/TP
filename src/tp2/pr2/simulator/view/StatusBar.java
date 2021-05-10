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
        initGUI();
        ctrl.addObserver(this);
    }

    private void initGUI(){
        this.setLayout( new FlowLayout( FlowLayout.LEFT ));
        this.setBorder( BorderFactory.createBevelBorder( 1 ));

        //this.add(_currLaws);
        //this.add(_currTime);
        //this.add(_numOfBodies);

    }

    @Override
    public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
        _numOfBodies = new JLabel(Integer.toString(bodies.size()));
        _currTime = new JLabel(Double.toString(time));
        _currLaws = new JLabel(fLawsDesc);
    }

    @Override
    public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
        _numOfBodies = new JLabel(Integer.toString(bodies.size()));
        _currTime = new JLabel(Double.toString(time));
        _currLaws = new JLabel(fLawsDesc);
    }

    @Override
    public void onBodyAdded(List<Body> bodies, Body b) {
        _numOfBodies = new JLabel(Integer.toString(bodies.size()));
    }

    @Override
    public void onAdvance(List<Body> bodies, double time) {
        _currTime = new JLabel(Double.toString(time));
    }

    @Override
    public void onDeltaTimeChanged(double dt) {

    }

    @Override
    public void onForceLawsChanged(String fLawsDesc) {
        _currLaws = new JLabel(fLawsDesc);
    }
}
