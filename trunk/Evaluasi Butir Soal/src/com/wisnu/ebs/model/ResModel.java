/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.wisnu.ebs.model;

import com.wisnu.ebs.add.Pearson;
import com.wisnu.ebs.add.Statistics;
import com.wisnu.ebs.event.ResListener;


public class ResModel {
    MainModel model;
    ResListener listener;
    
    Pearson pearson;
    Statistics statistics;
    private String[][] letter;
    private String[][] number;
    private String[][] sortedNumber;
    private String[][] IPc;
    private String[] answerKey;
    private String[] ep;
    private String[] correlation;
    private String[] tempData = new String[5];
    private int[][] NRaW;
    private int[][] nPc;
    private int[] tk;
    private int[] nB;
    private int[] score;
    private float[] db;
    private double[] correlationNumber;

    
    public void fireOnLoad(){
        if(listener != null){
            listener.onLoad();
        }
    }
    
    
    
    //LIST OF SETTER
    public void setModel(MainModel model) {
        this.model = model;
    }

    public void setListener(ResListener listener) {
        this.listener = listener;
    }
    
    public void setPearson(Pearson pearson) {
        this.pearson = pearson;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }
    
    public void setLetter(String[][] letter) {
        this.letter = letter;
    }

    public void setNumber(String[][] number) {
        this.number = number;
    }

    public void setSortedNumber(String[][] sortedNumber) {
        this.sortedNumber = sortedNumber;
    }

    public void setIPc(String[][] IPc) {
        this.IPc = IPc;
    }

    public void setAnswerKey(String[] answerKey) {
        this.answerKey = answerKey;
    }

    public void setEp(String[] ep) {
        this.ep = ep;
    }

    public void setCorrelation(String[] correlation) {
        this.correlation = correlation;
    }

    public void setTempData(String[] tempData) {
        this.tempData = tempData;
    }

    public void setNRaW(int[][] NRaW) {
        this.NRaW = NRaW;
    }

    public void setnPc(int[][] nPc) {
        this.nPc = nPc;
    }

    public void setTk(int[] tk) {
        this.tk = tk;
    }

    public void setnB(int[] nB) {
        this.nB = nB;
    }

    public void setScore(int[] score) {
        this.score = score;
    }

    public void setDb(float[] db) {
        this.db = db;
    }

    public void setCorrelationNumber(double[] correlationNumber) {
        this.correlationNumber = correlationNumber;
    }
    
    //LIST OF GETTER
    public MainModel getModel() {
        return model;
    }

    public Pearson getPearson() {
        return pearson;
    }

    public Statistics getStatistics() {
        return statistics;
    }
    
    public String[][] getLetter() {
        return letter;
    }

    public String[][] getNumber() {
        return number;
    }

    public String[][] getSortedNumber() {
        return sortedNumber;
    }

    public String[][] getIPc() {
        return IPc;
    }

    public String[] getAnswerKey() {
        return answerKey;
    }

    public String[] getEp() {
        return ep;
    }

    public String[] getCorrelation() {
        return correlation;
    }

    public String[] getTempData() {
        return tempData;
    }

    public int[][] getNRaW() {
        return NRaW;
    }

    public int[][] getnPc() {
        return nPc;
    }

    public int[] getTk() {
        return tk;
    }

    public int[] getnB() {
        return nB;
    }

    public int[] getScore() {
        return score;
    }

    public float[] getDb() {
        return db;
    }

    public double[] getCorrelationNumber() {
        return correlationNumber;
    }
    
    
}
