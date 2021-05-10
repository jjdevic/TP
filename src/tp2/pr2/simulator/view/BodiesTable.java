package tp2.pr2.simulator.view;

import tp2.pr2.simulator.control.Controller;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class BodiesTable extends JPanel {

    private BodiesTableModel bTable;

    BodiesTable(Controller ctrl) {
        bTable = new BodiesTableModel(ctrl);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 2), "Bodies", TitledBorder.LEFT, TitledBorder.TOP));

        JTable tabla = new JTable();
        JScrollPane p = new JScrollPane(tabla);
        this.add(p);
    }


}
