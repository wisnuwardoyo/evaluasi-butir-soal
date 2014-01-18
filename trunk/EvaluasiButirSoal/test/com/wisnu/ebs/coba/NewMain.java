/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.wisnu.ebs.coba;

/**
 *
 * @author Wisnu Citra
 */
public class NewMain {
    
    model Model = new model();
    int [][] number;
    int [] number1;
    public NewMain(){
        number = new int[2][];
        number1 = new int [1];
        number[0] = number1;
        
        
    }
    
    public static void main(String[] args) {
        new NewMain();
    }
    
}
class model{
    private int [] angka;

    public int[] getAngka() {
        return angka;
    }

    public void setAngka(int[] angka) {
        this.angka = angka;
    }
    
}