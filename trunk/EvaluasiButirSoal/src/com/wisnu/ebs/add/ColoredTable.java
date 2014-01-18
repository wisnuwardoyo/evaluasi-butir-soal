package com.wisnu.ebs.add;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class ColoredTable extends JTable {

    public ColoredTable(DefaultTableModel model) {
        super(model);
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
        Component cell = super.prepareRenderer(renderer, row, col);
        cell.setForeground(Color.BLACK);
        if (row < this.getRowCount() / 3) {
            if (row % 2 == 0) {
                cell.setBackground(new Color(153, 255, 153));
            } else {
                cell.setBackground(new Color(102, 255, 102));
            }
        } else if (row >= this.getRowCount() / 3 && row <= (this.getRowCount() * 2) / 3) {
            if (row % 2 == 0) {
                cell.setBackground(new Color(255, 255, 255));
            } else {
                cell.setBackground(new Color(204, 204, 204));
            }
        } else {
            if (row % 2 == 0) {
                cell.setBackground(new Color(255, 153, 153));
            } else {
                cell.setBackground(new Color(255, 102, 102));
            }
        }
        return cell;
    }
}
