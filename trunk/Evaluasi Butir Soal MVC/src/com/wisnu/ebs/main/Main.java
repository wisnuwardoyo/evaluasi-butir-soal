/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.wisnu.ebs.main;

import com.wisnu.ebs.controller.ControllerUtama;

/**
 *
 * @author Wisnu Wardoyo <mas.wisnu99@gmail.com>
 */
public class Main {
    private ControllerUtama controllerUtama;
    
    public Main(){
        controllerUtama = new ControllerUtama();
    }
   
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
               new Main();
            }
        });
    }
    
}
