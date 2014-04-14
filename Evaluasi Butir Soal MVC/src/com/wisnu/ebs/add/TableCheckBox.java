package com.wisnu.ebs.add;

import javax.swing.JTable;
/**
 *
 * @author Wisnu Wardoyo <mas.wisnu99@gmail.com>
 */
public class TableCheckBox extends JTable {

    @Override
    public Class getColumnClass(int column) {
        switch (column) {
            case 0:
                return String.class;
            case 1:
                return String.class;
            case 2:
                return Integer.class;
            case 3:
                return Integer.class;
            case 4:
                return Integer.class;
            case 5:
                return Integer.class;
            case 6:
                return Boolean.class;
            default:
                return String.class;
        }
    }
}
