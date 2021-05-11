package tp2.pr2.simulator.view;

import tp2.pr2.simulator.control.Controller;
import tp2.pr2.simulator.misc.Vector2D;
import tp2.pr2.simulator.model.Body;
import tp2.pr2.simulator.model.SimulatorObserver;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class Viewer extends JComponent implements SimulatorObserver {

    private int _centerX;
    private int _centerY;
    private double _scale;
    private List<Body> _bodies;
    private boolean _showHelp;
    private boolean _showVectors;

    public Viewer(Controller ctrl){
        initGui();
        ctrl.addObserver(this);

    }

    private void initGui() {
        _bodies = new ArrayList<>();
        _scale = 1.0;
        _showHelp = true;
        _showVectors = true;
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) { }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyChar()) {
                    case '-':
                        _scale = _scale * 1.1;
                        repaint();
                        break;
                    case '+':
                        _scale = Math.max(1000.0, _scale / 1.1);
                        repaint();
                        break;
                    case '=':
                        autoScale();
                        repaint();
                        break;
                    case 'h':
                        _showHelp = !_showHelp;
                        repaint();
                        break;
                    case 'v':
                        _showVectors = !_showVectors;
                        repaint();
                        break;
                    default:
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        addMouseListener(new MouseListener() {
            @Override
            public void mouseEntered(MouseEvent e) {
                requestFocus();
            }
            @Override
            public void mouseClicked(MouseEvent e) { }
            @Override
            public void mousePressed(MouseEvent e) { }
            @Override
            public void mouseReleased(MouseEvent e) { }
            @Override
            public void mouseExited(MouseEvent e) { }
        });
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 2), "Viewer", TitledBorder.LEFT, TitledBorder.TOP));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // use ’gr’ to draw not ’g’ --- it gives nicer results
        Graphics2D gr = (Graphics2D) g;
        gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        // calculate the center
        _centerX = getWidth() / 2;
        _centerY = getHeight() / 2;
        // TODO draw a cross at center
        gr.setColor(Color.red);
        gr.drawString("+", _centerX, _centerY);
        // TODO draw bodies (with vectors if _showVectors is true)
        for (Body body : _bodies) {
            gr.setColor(Color.blue);

            double x = body.getPosition().getX();
            double y = body.getPosition().getY();

            gr.drawOval(_centerX + (int) (x / _scale), _centerY - (int) (y / _scale), 8, 8);
            gr.fillOval(_centerX + (int) (x / _scale), _centerY - (int) (y / _scale), 8, 8);
            gr.setColor(Color.black);
            gr.drawString(body.getId(), _centerX + (int) (x / _scale), _centerY - (int) (y / _scale));
        }
        // TODO draw help if _showHelp is true
        if (this._showHelp) {
            gr.setColor(Color.red);
            gr.drawString("h: toggle help, +: zoom-in, -: zoom-out, =: fit", 5, 23);
            gr.drawString("Scaling ratio: " + this._scale, 5, 35);
        }

    }
    // other private/protected methods
    private void autoScale() {
        double max = 1.0;
        for (Body b : _bodies) {
            Vector2D p = b.getPosition();
            max = Math.max(max, Math.abs(p.getX()));
            max = Math.max(max, Math.abs(p.getY()));
        }
        double size = Math.max(1.0, Math.min(getWidth(), getHeight()));
        _scale = max > size ? 4.0 * max / size : 1.0;
    }

    private void drawLineWithArrow(Graphics g, int x1, int y1, int x2, int y2, int w, int h, Color lineColor, Color arrowColor) {
        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx * dx + dy * dy);
        double xm = D - w, xn = xm, ym = h, yn = -h, x;
        double sin = dy / D, cos = dx / D;

        x = xm * cos - ym * sin + x1;
        ym = xm * sin + ym * cos + y1;
        xm = x;
        x = xn * cos - yn * sin + x1;
        yn = xn * sin + yn * cos + y1;
        xn = x;

        int[] xpoints = { x2, (int) xm, (int) xn };
        int[] ypoints = { y2, (int) ym, (int) yn };

        g.setColor(lineColor);
        g.drawLine(x1, y1, x2, y2);
        g.setColor(arrowColor);
        g.fillPolygon(xpoints, ypoints, 3);
    }

    @Override
    public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
        _bodies = bodies;
        autoScale();
        repaint();
    }

    @Override
    public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
        _bodies = bodies;
        autoScale();
        repaint();
    }

    @Override
    public void onBodyAdded(List<Body> bodies, Body b) {
        _bodies = bodies;
        autoScale();
        repaint();
    }

    @Override
    public void onAdvance(List<Body> bodies, double time) {
        _bodies = bodies;
        repaint();
    }

    @Override
    public void onDeltaTimeChanged(double dt) {

    }

    @Override
    public void onForceLawsChanged(String fLawsDesc) {

    }
}
