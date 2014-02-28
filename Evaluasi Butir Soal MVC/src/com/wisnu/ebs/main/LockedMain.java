/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wisnu.ebs.main;

import com.wisnu.ebs.controller.MainController;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Wisnu Wardoyo <mas.wisnu99@gmail.com>
 */
public class LockedMain {
    
    private MainController controllerUtama;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date now, limit;
    
    public LockedMain() {
        try {
            now = sdf.parse(sdf.format(new Date()));
            limit = sdf.parse("2014-3-2");
            
            if (now.after(limit)) {
                JOptionPane.showConfirmDialog(null, "This Program has expired", "Warning", JOptionPane.CLOSED_OPTION);
            } else if (now.before(limit) || now.equals(limit)) {
                controllerUtama = new MainController();
            }
            
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        
    }
    
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                LockedMain lockedMain = new LockedMain();
            }
        });
    }
    
}
