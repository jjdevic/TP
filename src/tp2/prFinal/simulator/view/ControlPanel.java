package tp2.prFinal.simulator.view;

import org.json.JSONException;
import org.json.JSONObject;
import tp2.prFinal.simulator.control.Controller;
import tp2.prFinal.simulator.model.Body;
import tp2.prFinal.simulator.model.SimulatorObserver;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;

public class ControlPanel extends JPanel implements SimulatorObserver {
    private Controller _ctrl;
    private JSpinner _steps;
    private JTextField _dTime;
    private JButton fileB, forceB, runB, stopB, offB, OkB, cancelB;
    private JToolBar b;
    private JSONObject fLaw;
    private boolean _stopped;
    private String[][] aux;
    private int index;
    private JFileChooser fileChooser;
    private JComboBox<Object> cBox;

    public ControlPanel(Controller ctrl) {
        _ctrl = ctrl;
        _stopped = true;
        _ctrl.addObserver(this);
        initGUI();
    }
    private void initGUI() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
        b = new JToolBar();
        p.add(b, BorderLayout.PAGE_START);

        _steps = new JSpinner(new SpinnerNumberModel(10000, 1, 10000, 100));
        _steps.setToolTipText("Simulation tick to run: 1-10000");
        _steps.setPreferredSize(new Dimension(80, 40));

        _dTime = new JTextField("2500.00");
        _dTime.setPreferredSize(new Dimension(80, 40));

        fileB = new JButton();
        fileB.setIcon(new ImageIcon("resources/icons/open.png"));
        fileB.setCursor(new Cursor(Cursor.HAND_CURSOR));

        forceB = new JButton();
        forceB.setIcon(new ImageIcon("resources/icons/physics.png"));
        forceB.setCursor(new Cursor(Cursor.HAND_CURSOR));

        runB = new JButton();
        runB.setIcon(new ImageIcon("resources/icons/run.png"));
        runB.setCursor(new Cursor(Cursor.HAND_CURSOR));

        stopB = new JButton();
        stopB.setIcon(new ImageIcon("resources/icons/stop.png"));
        stopB.setCursor(new Cursor(Cursor.HAND_CURSOR));

        offB = new JButton();
        offB.setIcon(new ImageIcon("resources/icons/exit.png"));
        offB.setCursor(new Cursor(Cursor.HAND_CURSOR));

        b.add(fileB);
        b.add(forceB);
        b.add(runB);
        b.add(stopB);
        b.add(offB);

        b.add( new JLabel("Steps: "));
        b.add(_steps);
        b.add( new JLabel(" Delta-Time: "));
        b.add(_dTime);
        b.add(Box.createHorizontalStrut(340));
        b.add(offB);

        this.add(b);
        setVisible(true);

        fileB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    fileChooser = new JFileChooser();
                    fileChooser.setFileFilter(new FileNameExtensionFilter("JSON file (.json)", "json"));
                    fileChooser.setCurrentDirectory(new File("resources/examples"));

                    int selection = fileChooser.showOpenDialog(null);
                    if (selection == JFileChooser.APPROVE_OPTION){
                        File file = fileChooser.getSelectedFile();
                        _ctrl.reset();
                        _ctrl.loadBodies(new FileInputStream(file));
                    }
                    else if(selection == JFileChooser.ERROR_OPTION) {
                        JOptionPane.showMessageDialog(null, "Failure");
                    }

                } catch(Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        forceB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JSONObject fLaw;
                setForce(_ctrl);
            }
        });

        runB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Controller._BODIES_LOADED) {
                    _ctrl.setDeltaTime(Double.parseDouble(_dTime.getText()));
                    setEnable(false);
                    runSimulator((int) _steps.getValue());
                }
                else JOptionPane.showMessageDialog(null, "No body list loaded");
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

    private void setForce(Controller _ctrl) {
        String[] _colNames = new String[] { "Key", "Value", "Description" };
        JTable tabla = new JTable();
        cBox = new JComboBox<>();
        cBox.setPreferredSize(new Dimension(400, 25));

        for(int i = 0; i < _ctrl.getForceLawsInfo().size(); i++){
            cBox.addItem(_ctrl.getForceLawsInfo().get(i).get("desc"));
        }

        cBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                index = cBox.getSelectedIndex();
                aux = new String[ _ctrl.getForceLawsInfo().get(index).getJSONObject("data").length()][_colNames.length];
                Iterator<String> keys =  _ctrl.getForceLawsInfo().get(index).getJSONObject("data").keys();
                for(int i = 0; keys.hasNext(); i++){
                    String k = keys.next();
                    aux[i][0] = k;
                    aux[i][2] = _ctrl.getForceLawsInfo().get(index).getJSONObject("data").getString(k);
                }

                DefaultTableModel tModel = new DefaultTableModel(aux, _colNames) {
                    @Override
                    public boolean isCellEditable(int row, int col) {
                        return col == 1;
                    }
                };
                tabla.setModel(tModel);
                tabla.getColumnModel().getColumn(2).setPreferredWidth(400);
            }
        });

        JPanel pAux = new JPanel();
        JPanel pAux2 = new JPanel();
        JPanel view = new JPanel(new BorderLayout());
        JDialog dialog = new JDialog();



        pAux.add(new JLabel("Force Laws: "));
        pAux.add(cBox);

        view.add(new JScrollPane(tabla));
        view.add(pAux, BorderLayout.PAGE_END);
        view.setPreferredSize(new Dimension(800, 250));

        pAux2.add(OkB = new JButton("OK"));
        pAux2.add(cancelB = new JButton("Cancel"));

        OkB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               try {
                   fLaw = new JSONObject();
                   fLaw.put("data", getData(tabla));
                   fLaw.put("type", _ctrl.getForceLawsInfo().get(index).get("type"));
                   _ctrl.setForceLaws(fLaw);
                   dialog.setVisible(false);
               } catch (JSONException ex){
                   JOptionPane.showMessageDialog(null, "Incorrect Format Argument");
               }
            }
        });

        cancelB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
            }
        });

        dialog.add(new JLabel("<html><p>Select a force law and provide values for the parameters in the <b>Value column</b> " +
                "(default values are used for parameters with no value).</p></html>"), BorderLayout.PAGE_START);
        dialog.add(view, BorderLayout.CENTER);
        dialog.add(pAux2, BorderLayout.PAGE_END);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private void runSimulator(int _steps) {
        if ( _steps > 0 && !_stopped ) {
            try {
                _ctrl.run(1);
            }
            catch (Exception e) {
                setEnable(true);
                return;
            }
            SwingUtilities.invokeLater( new Runnable() {
                @Override
                public void run() {
                    runSimulator(_steps - 1);
                }
            });
        }
        else setEnable(true);
    }

    @Override
    public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                _ctrl.setDeltaTime(dt);
            }
        });
    }

    @Override
    public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                _ctrl.setDeltaTime(dt);
            }
        });
    }

    @Override
    public void onBodyAdded(List<Body> bodies, Body b) { }

    @Override
    public void onAdvance(List<Body> bodies, double time) { }

    @Override
    public void onDeltaTimeChanged(double dt) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                _ctrl.setDeltaTime(dt);
            }
        });
    }

    @Override
    public void onForceLawsChanged(String fLawsDesc) { }

    private JSONObject getData(JTable tabla) {
        StringBuilder s = new StringBuilder();
        JSONObject jAux;
        s.append('{');
        for (int i = 0; i < aux.length; i++) {
            if (tabla.getValueAt(i, 0) != null && tabla.getValueAt(i, 1) != null) {
                s.append('"');
                s.append(tabla.getValueAt(i, 0));
                s.append('"');
                s.append(':');
                s.append(tabla.getValueAt(i, 1));
                s.append(',');
            }
        }
        if (s.length() > 1) s.deleteCharAt(s.length() - 1);
        s.append('}');
        return jAux = new JSONObject(s.toString());
    }

    private void setEnable(boolean state) {
        _stopped = state;
        fileB.setEnabled(state);
        forceB.setEnabled(state);
        offB.setEnabled(state);
    }

}
