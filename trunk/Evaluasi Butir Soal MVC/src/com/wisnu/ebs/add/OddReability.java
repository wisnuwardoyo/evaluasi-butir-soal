/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wisnu.ebs.add;

/**
 *
 * @author Wisnu Wardoyo
 */
public class OddReability {

    private double itemCount;
    private double[][] studentsScore;
    private double Xt;
    private double Xt2;
    private double[] p;
    private double[] q;
    private double pq;

    public OddReability(double itemCount, double[][] studentsScore) {
        this.pq = 0;
        this.itemCount = itemCount;
        this.studentsScore = studentsScore;
        this.p = new double[(int) this.itemCount];
        this.q = new double[(int) this.itemCount];
        initComponents();
    }

    public final void initComponents() {
        for (double[] studentsScore1 : this.studentsScore) {
            double dump = studentsScore1[(int) itemCount];
            this.Xt += dump;
            this.Xt2 += dump * dump;
        }
        int a = 0;

        for (int i = 0; i < itemCount; i++) {
            double dump = 0;
            for (double[] studentsScore1 : studentsScore) {
                dump += studentsScore1[i];
            }
            this.p[i] = dump / studentsScore.length;
            this.q[i] = 1 - p[i];
            this.pq += this.p[i] * this.q[i];
        }

    }

    protected double sumOfVariance() {
        double sumOfVariance;
        double N = (double) studentsScore.length;
        sumOfVariance = (Xt2 - ((Xt * Xt) / N)) / N;
        return sumOfVariance;
    }

    protected double sumOfMean() {
        double sumOfMean = Xt / (double) studentsScore.length;
        return sumOfMean;
    }

    public double getReability() {
        double r = 0;
        r = (itemCount / (itemCount - 1.0)) * (1.0 - ((sumOfMean() * (itemCount - sumOfMean())) / (itemCount * sumOfVariance())));
        if (String.valueOf(r).equals("NaN")) {
            r = 0;
        }
        if (r > 1.00) {
            r = 1.00;
        }
        return r;
    }

}
