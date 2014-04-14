/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wisnu.ebs.controller.thread;

import com.wisnu.ebs.add.CreateAnisSoFolder;

/**
 *
 * @author Wisnu Wardoyo
 */
public class GenerateFolder extends Thread {

    private final CreateAnisSoFolder anisSoFolder = new CreateAnisSoFolder();

    @Override
    public void run() {
        anisSoFolder.createAnissoFolder();
        
    }

}
