/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wisnu.ebs.main;

import com.wisnu.ebs.controller.MainController;
import com.wisnu.ebs.controller.thread.GenerateFolder;
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
    private Date now, limit;
    private final Thread folderGenerator = new GenerateFolder();

    public LockedMain() {
        try {
            now = sdf.parse(sdf.format(new Date()));
            limit = sdf.parse("2014-5-13");

            if (now.after(limit)) {
                JOptionPane.showMessageDialog(null, new Object[]{
                    "This Program has expired",
                    "Please contact Wisnu Wardoyo for renewal"}, "WARNING!", JOptionPane.WARNING_MESSAGE);
            } else if (now.before(limit) || now.equals(limit)) {
                folderGenerator.start();
                controllerUtama = new MainController();
            }

        } catch (ParseException ex) {
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
