package com.wisnu.ebs.add;

import java.awt.Component;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Wisnu Wardoyo <mas.wisnu99@gmail.com>
 */
public class TableCellListener extends DefaultTableCellRenderer {

    int type = 0;

    public TableCellListener(int type) {
        this.type = type;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
                row, column);

        if (type == 3) {
            if (value.toString().equalsIgnoreCase("A")
                    || value.toString().equalsIgnoreCase("B")
                    || value.toString().equalsIgnoreCase("C")
                    || value.toString().equals("")) {

            } else {
                table.getModel().setValueAt("", row, column);
                table.changeSelection(row, column, false, false);
                JOptionPane.showMessageDialog(null, "ISI DATA DENGAN BENAR"
                        + "\nA,B, atau D", "WARNING", JOptionPane.WARNING_MESSAGE);

            }
        } else if (type == 4) {
            if (value.toString().equalsIgnoreCase("A")
                    || value.toString().equalsIgnoreCase("B")
                    || value.toString().equalsIgnoreCase("C")
                    || value.toString().equalsIgnoreCase("D")
                    || value.toString().equals("")) {

            } else {
                table.getModel().setValueAt("", row, column);
                table.changeSelection(row, column, false, false);
                JOptionPane.showMessageDialog(null, "ISI DATA DENGAN BENAR"
                        + "\nA,B,C atau D", "WARNING", JOptionPane.WARNING_MESSAGE);

            }
        } else {
            if (value.toString().equalsIgnoreCase("A")
                    || value.toString().equalsIgnoreCase("B")
                    || value.toString().equalsIgnoreCase("C")
                    || value.toString().equalsIgnoreCase("D")
                    || value.toString().equalsIgnoreCase("E")
                    || value.toString().equals("")) {

            } else {
                table.setValueAt("", row, column);
                JOptionPane.showMessageDialog(null, "ISI DATA DENGAN BENAR"
                        + "\nA,B,C,D atau E", "WARNING", JOptionPane.WARNING_MESSAGE);
            }
        }

        return component;
    }

    



}
