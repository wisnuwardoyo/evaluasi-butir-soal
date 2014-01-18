/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wisnu.ebs.add;

public class ErrorMessage {

    public final String[] message = new String[15];
    
    
    public ErrorMessage(){
        message [0] = "Error while opening file";
        message [1] = "Error while saving file";
        message [2] = "Error while computing result";
        message [3] = "Error while editing";
    }
    
}
