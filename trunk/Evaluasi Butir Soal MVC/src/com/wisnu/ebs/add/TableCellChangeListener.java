/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.wisnu.ebs.add;

import java.awt.Component;
import java.util.EventObject;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Wisnu Wardoyo <mas.wisnu99@gmail.com> 
 */
public class TableCellChangeListener implements CellEditorListener {

    @Override
    public void editingStopped(ChangeEvent ce) {
        System.out.println("a");
    }

    @Override
    public void editingCanceled(ChangeEvent ce) {
    }

   

}
