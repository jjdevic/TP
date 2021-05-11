package tp2.pr2.simulator.view;

import tp2.pr2.simulator.control.Controller;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BodiesTable extends JPanel {

    private BodiesTableModel bTable;

    BodiesTable(Controller ctrl) {
        bTable = new BodiesTableModel(ctrl);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 2), "Bodies", TitledBorder.LEFT, TitledBorder.TOP));

        String[][] aux = new String[bTable.getColumnCount()][bTable.getRowCount()];
        for(int i = 0; i < bTable.getRowCount(); i ++){
            for(int j = 0; j < bTable.getColumnCount(); j++){
                aux[i][j] = (String) bTable.getValueAt(i, j);
            }
        }
        JTable tabla = new JTable();
        DefaultTableModel tmodel = new DefaultTableModel(aux, BodiesTableModel._colNames) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tabla.setModel(tmodel);
        JScrollPane p = new JScrollPane(tabla);
        p.setPreferredSize(new Dimension(800, 200));
        add(p);
        this.add(p);
    }


}
