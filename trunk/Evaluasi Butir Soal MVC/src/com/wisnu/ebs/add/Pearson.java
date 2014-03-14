/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wisnu.ebs.add;

import static java.lang.Double.NaN;

/**
 *
 * @author Wisnu Citra
 */
public class Pearson {

    private double[] X;
    private double[] Y;
    private double hasil;
    
    public Pearson(int siswa,double [] dataX, double[] dataY) {
        setX(dataX);
        setY(dataY);
        double[] dump = new double[5];

        for (int i = 0; i < siswa; i++) {
            dump[0] += X[i];
            dump[1] += Y[i];
            dump[2] += X[i] * X[i];
            dump[3] += Y[i] * Y[i];
            dump[4] += X[i] * Y[i];
        }

        double Rxy = 0;
        
        Rxy = ((siswa * dump[4]) - (dump[0] * dump[1]))
                / (Math.sqrt((siswa * dump[2]) - (dump[0] * dump[0]))
                * Math.sqrt((siswa * dump[3]) - (dump[1] * dump[1])));
        
        if(String.valueOf(Rxy).equals("NaN")){
            Rxy = 0;
        }
        
        double Rtt = 0;
        Rtt = (2 * Rxy) / (1 + Rxy);
        
        this.hasil = Rxy;
       
    }
    public double getHasil(){
        return this.hasil;
    }

    public void setX(double[] data) {
        X = data;
    }

    public void setY(double[] data) {
        Y = data;
    }
}
