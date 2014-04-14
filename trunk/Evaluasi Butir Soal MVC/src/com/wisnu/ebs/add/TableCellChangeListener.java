/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.wisnu.ebs.add;

import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;

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
