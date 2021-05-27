package tp2.prFinal.simulator.view;

import tp2.prFinal.simulator.control.Controller;
import tp2.prFinal.simulator.misc.Vector2D;
import tp2.prFinal.simulator.model.Body;
import tp2.prFinal.simulator.model.SimulatorObserver;

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
        Graphics2D gr = (Graphics2D) g;
        gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int _centerX = getWidth() / 2;
        int _centerY = getHeight() / 2;

        int _radius = 5;
        int _width = 5;

        g.setColor(Color.RED);
        g.drawLine(_centerX, _centerY, _centerX - _radius, _centerY);
        g.drawLine(_centerX, _centerY, _centerX, _centerY - _radius);
        g.drawLine(_centerX, _centerY, _centerX, _centerY + _radius);
        g.drawLine(_centerX, _centerY, _centerX + _radius, _centerY);

        if(_showHelp) {
            gr.setColor(Color.red);
            gr.drawString("h: toggle help, +: zoom-in, -: zoom-out, =: fit", 8, 25);
            gr.drawString("Scaling ratio: " + this._scale, 8, 37);
        }

        for(Body b : _bodies) {
            if(_showVectors) {
                drawLineWithArrow(g, _centerX + (int)(b.getPosition().getX() /_scale), _centerY + (int)(b.getPosition().getY() /_scale),
                        (int)(_centerX + b.getPosition().getX() /_scale + 4 * _radius * b.getVelocity().direction().getX()),
                        (int)(_centerY + b.getPosition().getY() /_scale + 4 * _radius * b.getVelocity().direction().getY()),
                        _width, _radius, Color.green, Color.green);
                drawLineWithArrow(g, _centerX + (int)(b.getPosition().getX() /_scale), _centerY + (int)(b.getPosition().getY() /_scale),
                        _centerX + (int)(b.getPosition().getX() /_scale + 4 * _radius * b.getForce().direction().getX()),
                        _centerY + (int)(b.getPosition().getY() /_scale + 4 * _radius * b.getForce().direction().getY()),
                        _width, _radius, Color.red, Color.red);
            }

            g.setColor(Color.black);
            g.drawString(b.getId(), _centerX + (int)(b.getPosition().getX() /_scale) + 2*_radius, _centerY + (int)(b.getPosition().getY() /_scale) + 3*_radius);

            g.setColor(Color.blue);
            g.drawOval(_centerX + (int)(b.getPosition().getX() /_scale) - _radius, _centerY + (int)(b.getPosition().getY() /_scale) - _radius, 2*_radius, 2*_radius);
            g.fillOval(_centerX + (int)(b.getPosition().getX() /_scale) - _radius, _centerY + (int)(b.getPosition().getY() /_scale) - _radius, 2*_radius, 2*_radius);
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

    // This method draws a line from (x1,y1) to (x2,y2) with an arrow.
    // The arrow is of height h and width w.
    // The last two arguments are the colors of the arrow and the line
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
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                _bodies = bodies;
                autoScale();
                repaint();
            }
        });

    }

    @Override
    public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                _bodies = bodies;
                autoScale();
                repaint();
            }
        });
    }

    @Override
    public void onBodyAdded(List<Body> bodies, Body b) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                _bodies = bodies;
                autoScale();
                repaint();
            }
        });
    }

    @Override
    public void onAdvance(List<Body> bodies, double time) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                _bodies = bodies;
                repaint();
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
