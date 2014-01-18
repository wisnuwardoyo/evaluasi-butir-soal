/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.wisnu.ebs.event;

/**
 *
 * @author Wisnu Citra
 */
public interface MainListener {
    /*
        Create a New Document
    */
    public void newDocument();
    /*
        Load a Document from XML file
    */
    public void loadDocument();
    /*
        Save current Document to XML file
    */
    public void saveDocument();
    
    public void openResult();
    
    public void openConfiguration();
    
    public void fireErrorMessage(int i);
}
