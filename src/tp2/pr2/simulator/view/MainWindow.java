package tp2.pr2.simulator.view;

import tp2.pr2.simulator.control.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MainWindow extends JFrame {
    private Controller _ctrl;

    public MainWindow(Controller ctrl) {
        super("Physics Simulator");
        _ctrl = ctrl;
        initGUI();
    }

    private void initGUI(){
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        this.add(mainPanel);


        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        this.addWindowListener(new WindowListener() {
            @Override
            public void windowClosing(WindowEvent e) { System.exit(0); }

            @Override
            public void windowActivated(WindowEvent arg0) { }
            @Override
            public void windowClosed(WindowEvent arg0) { }
            @Override
            public void windowDeactivated(WindowEvent arg0) { }
            @Override
            public void windowDeiconified(WindowEvent arg0) { }
            @Override
            public void windowIconified(WindowEvent arg0) { }
            @Override
            public void windowOpened(WindowEvent arg0) { }
        });
        //BodiesTable bodiesTable = new BodiesTable();
        //BodiesInfo bodiesInfo = new BodiesInfo(_ctrl);
        //Viewer universalViewer = new Viewer(_ctrl);

        StatusBar statusBar = new StatusBar(_ctrl);
        ControlPanel controlPanel = new ControlPanel(_ctrl);
        StatusBar status = new StatusBar(_ctrl);

        mainPanel.add(controlPanel, BorderLayout.PAGE_START);
        mainPanel.add(statusBar, BorderLayout.PAGE_END);

		/*bodiesInfo.setPreferredSize(new Dimension(800, 300));
		controlPanel.add(bodiesInfo);*/

        //universalViewer.setPreferredSize(new Dimension(800, 600));
        //contentPanel.add(new JScrollPane(universalViewer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700, 300);
        pack();
        setVisible(true);
    };

}
