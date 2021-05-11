package tp2.pr2.simulator.view;

import tp2.pr2.simulator.control.Controller;
import tp2.pr2.simulator.model.Body;
import tp2.pr2.simulator.model.SimulatorObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

public class ControlPanel extends JPanel implements SimulatorObserver {
    private Controller _ctrl;
    private JSpinner _time;
    private JTextField _dTime;
    private boolean _stopped;

    public ControlPanel(Controller ctrl) {
        _ctrl = ctrl;
        _stopped = true;
        initGUI();
        _ctrl.addObserver(this);
    }
    private void initGUI() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
        JToolBar b = new JToolBar();
        p.add(b, BorderLayout.PAGE_START);
        JButton fileB, forceB, playB, stopB, offB;

        _time = new JSpinner(new SpinnerNumberModel(10000, 1, 10000, 1));
        _time.setToolTipText("Simulation tick to run: 1-10000");
        _time.setPreferredSize(new Dimension(80, 40));

        _dTime = new JTextField("2500.00");
        _dTime.setPreferredSize(new Dimension(80, 40));

        fileB = new JButton();
        fileB.setIcon(new ImageIcon("resources/icons/open.png"));
        fileB.setCursor(new Cursor(Cursor.HAND_CURSOR));

        forceB = new JButton();
        forceB.setIcon(new ImageIcon("resources/icons/physics.png"));
        forceB.setCursor(new Cursor(Cursor.HAND_CURSOR));

        playB = new JButton();
        playB.setIcon(new ImageIcon("resources/icons/run.png"));
        playB.setCursor(new Cursor(Cursor.HAND_CURSOR));

        stopB = new JButton();
        stopB.setIcon(new ImageIcon("resources/icons/stop.png"));
        stopB.setCursor(new Cursor(Cursor.HAND_CURSOR));

        offB = new JButton();
        offB.setIcon(new ImageIcon("resources/icons/exit.png"));
        offB.setCursor(new Cursor(Cursor.HAND_CURSOR));

        b.add(fileB);
        b.add(forceB);
        b.add(playB);
        b.add(stopB);
        b.add(offB);

        b.add( new JLabel("Steps: "));
        b.add(_time);
        b.add( new JLabel("Delta-Time: "));
        b.add(_dTime);
        b.add(Box.createHorizontalStrut(340));
        b.add(offB);

        this.add(b);
        setVisible(true);


        fileB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("resources/examples"));
                    int selection = fileChooser.showOpenDialog(p);
                    if (selection == JFileChooser.APPROVE_OPTION){
                        File file = fileChooser.getSelectedFile();
                        _ctrl.reset();
                        _ctrl.loadBodies(new FileInputStream(file));
                    }
                    else if(selection == JFileChooser.CANCEL_OPTION) {
                        System.out.println("Cancelled");
                    }
                    else if(selection == JFileChooser.ERROR_OPTION) {
                        System.out.println("Something went wrong...");
                    }

                } catch(Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        forceB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {




            }
        });

        playB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileB.setEnabled(false);
                forceB.setEnabled(false);
                playB.setEnabled(false);
                offB.setEnabled(false);

                _stopped = false;
                _ctrl.setDeltaTime(Double.parseDouble(_dTime.getText()));
                runSimulator((int) _time.getValue());
            }
        });

        stopB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _stopped = true;
            }
        });

        offB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int n = JOptionPane.showOptionDialog(new JFrame(), "Are you sure you want to quit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (n == 0) {System.exit(0); }
            }
        });
    }

    private void runSimulator(int _steps) {
        if ( _steps > 0 && !_stopped ) {
            try {
                _ctrl.run(_steps, null, null, null);
            }
            catch (Exception e) {
                // TODO show the error in a dialog box
                // TODO enable all buttons
                _stopped = true;
                return;
            }
            SwingUtilities.invokeLater( new Runnable() {
                @Override
                public void run() {
                    runSimulator(_steps - 1);
                }
            });
        }
        else {
            _stopped = true;
        }
    }

    @Override
    public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
        _dTime = new JTextField(Double.toString(dt));
    }

    @Override
    public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
        _dTime = new JTextField(Double.toString(dt));
    }

    @Override
    public void onBodyAdded(List<Body> bodies, Body b) {

    }

    @Override
    public void onAdvance(List<Body> bodies, double time) {

    }

    @Override
    public void onDeltaTimeChanged(double dt) {
        _dTime = new JTextField(Double.toString(dt));
    }

    @Override
    public void onForceLawsChanged(String fLawsDesc) {

    }
}
