/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wisnu.ebs.main;

import com.wisnu.ebs.add.DateChecker;
import com.wisnu.ebs.controller.MainController;
import com.wisnu.ebs.controller.thread.GenerateFolder;
import javax.swing.JOptionPane;

/**
 *
 * @author Wisnu Wardoyo <mas.wisnu99@gmail.com>
 */
public class LockedMain {

    private MainController controllerUtama;
    private final Thread folderGenerator = new GenerateFolder();

    public LockedMain() {

        if (DateChecker.isOutOfDate()) {
            JOptionPane.showMessageDialog(null, new Object[]{
                "This Program has expired",
                "Please contact Wisnu Wardoyo for renewal"}, "WARNING!", JOptionPane.WARNING_MESSAGE);
        } else {
            folderGenerator.start();
            controllerUtama = new MainController();
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
