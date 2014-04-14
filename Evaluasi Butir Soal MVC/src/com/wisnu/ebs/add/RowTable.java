package com.wisnu.ebs.add;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

/**
 *
 * @author Wisnu Wardoyo <mas.wisnu99@gmail.com>
 */

public class RowTable extends DefaultTableCellRenderer implements ListCellRenderer {

    JTable table;
    public JLabel label = new JLabel();

    public RowTable(JTable table) {
        this.table = table;
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JTableHeader header = table.getTableHeader();
        label.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(204, 204, 204), null, null));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setForeground(header.getForeground());
        label.setBackground(header.getBackground().brighter());
        label.setFont(header.getFont());
        label.setText(value.toString().toUpperCase());
        return label;
        
    }

    
}
