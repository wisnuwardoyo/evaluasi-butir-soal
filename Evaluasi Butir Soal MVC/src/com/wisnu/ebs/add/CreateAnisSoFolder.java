/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wisnu.ebs.add;

import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author Wisnu Wardoyo
 */
public class CreateAnisSoFolder {

    private final String DefaultFolder;

    public CreateAnisSoFolder() {
        this.DefaultFolder = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
    }

    protected boolean findAnissoFolder() {
        String[] listFolder = new File(DefaultFolder).list();
        for (String folder : listFolder) {
            if (new File(DefaultFolder +"\\"+folder).isDirectory()) {
                if (folder.equalsIgnoreCase("anisso")) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public void createAnissoFolder(){
        if(!findAnissoFolder()){
            File file = new File(DefaultFolder+"\\AnisSo\\");
            file.mkdir();
            System.out.println("Folder Created");
        }else{
            System.out.println("Folder Already Exist");
        }
    }
    
}
