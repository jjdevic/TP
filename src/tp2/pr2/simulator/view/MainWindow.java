package tp2.pr2.simulator.view;

import tp2.pr2.simulator.control.Controller;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private Controller _ctrl;

    public MainWindow(Controller ctrl) {
        super("Physics Simulator");
        _ctrl = ctrl;
        initGUI();
    }

    private void initGUI(){
        ControlPanel controlPanel = new ControlPanel(_ctrl);
        BodiesTable bodiesTable =  new BodiesTable(_ctrl);
        Viewer universalViewer = new Viewer(_ctrl);
        StatusBar statusBar = new StatusBar(_ctrl);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel auxPanel = new JPanel();
        setContentPane(mainPanel);

        auxPanel.setLayout(new BoxLayout(auxPanel, BoxLayout.Y_AXIS));
        auxPanel.add(bodiesTable);
        universalViewer.setPreferredSize(new Dimension(800, 500));
        auxPanel.add(universalViewer);

        mainPanel.add(controlPanel,BorderLayout.PAGE_START);
        mainPanel.add(auxPanel, BorderLayout.CENTER);
        mainPanel.add(statusBar,BorderLayout.PAGE_END);

        pack();
        setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    };

}
