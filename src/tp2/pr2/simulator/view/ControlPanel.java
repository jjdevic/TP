package tp2.pr2.simulator.view;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import tp2.pr2.simulator.control.Controller;
import tp2.pr2.simulator.model.Body;
import tp2.pr2.simulator.model.SimulatorObserver;

import javax.swing.*;
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
    private JButton fileB, forceB, runB, stopB, offB;
    private JToolBar b;
    private JSONObject fLaw;
    private boolean _opened;
    private boolean _stopped;
    private String[][] aux;
    private int cont;

    public ControlPanel(Controller ctrl) {
        _ctrl = ctrl;
        _opened = false;
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
                try{
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("resources/examples"));
                    int selection = fileChooser.showOpenDialog(null);
                    if (selection == JFileChooser.APPROVE_OPTION){
                        File file = fileChooser.getSelectedFile();
                        _ctrl.reset();
                        _ctrl.loadBodies(new FileInputStream(file));
                        _opened = true;
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
                forceSet(_ctrl);
            }
        });

        runB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(_opened) {
                    _stopped = false;
                    _ctrl.setDeltaTime(Double.parseDouble(_dTime.getText()));
                    fileB.setVisible(false);
                    forceB.setVisible(false);
                    offB.setVisible(false);
                    runSimulator((int) _steps.getValue());
                }
                else JOptionPane.showMessageDialog(null, "No bodies list loaded");
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

    private void forceSet(Controller _ctrl) {
        String[] _colNames = new String[] { "Key", "Value", "Description" };
        JLabel message = new JLabel("Select a force law and provide values for the parametes in the <b>Value column</b>");
        JLabel lFLaws = new JLabel("Force Laws: ");
        JTable tabla = new JTable();
        JComboBox<Object> cBox = new JComboBox<>();

        cBox.addItem("--Choose an option--");
        for(int i = 0; i < _ctrl.getForceLawsInfo().size(); i++){
            cBox.addItem(_ctrl.getForceLawsInfo().get(i).get("desc"));
        }

        cBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //int cont = 0;
                for(int i = 0; i < _ctrl.getForceLawsInfo().size(); i++){
                    if(cBox.getSelectedItem().equals(_ctrl.getForceLawsInfo().get(i).get("desc"))) {
                        cont = i;
                        break;
                    }
                }

                Iterator<String> keys =  _ctrl.getForceLawsInfo().get(cont).getJSONObject("data").keys();
                int i = 0;
                aux = new String[ _ctrl.getForceLawsInfo().get(cont).getJSONObject("data").length()][_colNames.length];
                while(keys.hasNext()) {
                    String k = keys.next();
                    aux[i][0] = k;
                    aux[i][2] = _ctrl.getForceLawsInfo().get(cont).getJSONObject("data").getString(k);
                    i++;
                }

                DefaultTableModel tmodel = new DefaultTableModel(aux, _colNames) {
                    private static final long serialVersionUID = 1L;
                    @Override
                    public boolean isCellEditable(int row, int col) {
                        return col == 1;
                    }
                };
                tabla.setModel(tmodel);
                tabla.getColumnModel().getColumn(2).setPreferredWidth(400);
            }
        });

        JScrollPane p = new JScrollPane(tabla);
        JPanel view = new JPanel();
        view.setLayout(new BoxLayout(view, BoxLayout.Y_AXIS));
        view.add(message);
        view.add(p);
        view.add(lFLaws, BorderLayout.PAGE_END);
        view.add(cBox, BorderLayout.PAGE_END);
        view.setPreferredSize(new Dimension(800, 250));

        String[] option = new String[] {"Cancelar", "Ok"};
        if(JOptionPane.showOptionDialog(null, view,"Force Laws Selection", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, null) == 1) {
            fLaw = new JSONObject();
            JSONObject jAux = new JSONObject();
            Iterator<String> keys2 =  _ctrl.getForceLawsInfo().get(cont).getJSONObject("data").keys();
            int j = 0;
            while(keys2.hasNext()) {
                String k = keys2.next();
                if(String.valueOf(tabla.getValueAt(j, 1)).charAt(0) == '[') {
                    JSONArray jAuxArr = new JSONArray();
                    StringBuilder aux3 = new StringBuilder();
                    int y = 0;
                    for(int x = 0; x < 2; x++){
                        y++;
                        do{
                            aux3.append(String.valueOf(tabla.getValueAt(j, 1)).charAt(y));
                            y++;
                        } while(String.valueOf(tabla.getValueAt(j, 1)).charAt(y) != ',' && String.valueOf(tabla.getValueAt(j, 1)).charAt(y) != ']');
                        jAuxArr.put(aux3.toString());
                        aux3 = new StringBuilder(new String(""));
                    }
                    jAuxArr.put(aux3.toString());
                    jAux.put(k, jAuxArr);
                }
                else {
                    jAux.put(k, tabla.getValueAt(j, 1));
                }
                j++;
            }
            fLaw.put("type", _ctrl.getForceLawsInfo().get(cont).get("type"));
            fLaw.put("data", jAux);
            try {
                _ctrl.setForceLaws(fLaw);
            } catch (JSONException ex) {
                JOptionPane.showMessageDialog(null, "Datos incorrectos");
            }
        }
    }

    private void runSimulator(int _steps) {
        if ( _steps > 0 && !_stopped ) {
            try {
                _ctrl.run(1);
            }
            catch (Exception e) {
                fileB.setVisible(true);
                forceB.setVisible(true);
                offB.setVisible(true);
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
            fileB.setVisible(true);
            forceB.setVisible(true);
            offB.setVisible(true);
        }
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

}
