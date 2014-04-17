/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.wisnu.ebs.model;

import com.wisnu.ebs.add.Pearson;
import com.wisnu.ebs.add.Statistics;

/**
 *
 * @author Wisnu Wardoyo
 */
public interface FindingResult {

    double converter(String a);

    /**
     * Mencocokan jawaban dengan kunci, kemudian dikonversi menjadi 1 dan 0, dan
     * ditampung kedalam variable number pada resModel.
     */
    void correcting();

    /**
     * Mencari daya beda soal melalui beberapa tahap, tahap pertama adalah
     * mengurutkan angka berdasarkan nomor urut awal dan score tertinggi.
     * selanjutnya dipisahkan antara siswa yang termasuk dalam urutan atas dan
     * urutan bawah. Selanjutnya di Olah dan dicari perbedaannya. (batas atas
     * dan batas bawah masing2 hanya diambil 27% dari total siswa)
     * 
     * Rumus
     * DP = ((BA - BB) / NA) * 100%
     * 
     * Dr. Purwanto M.Pd 2010 102
     */
    void db();
    
    void ep();

    void evenReliability();

    String[] getAnswerKey();

    String[] getCorrelation();

    double[] getCorrelationNumber();

    //BORDER
    Database getDatabase();

    float[] getDb();

    String[] getEp();

    String[][] getIPc();

    String[][] getLetter();

    int[][] getNRaW();

    int[][] getNRaW2();

    String[][] getNumber();

    Pearson getPearson();

    int[] getScore();

    String[][] getSortedNumber();

    Statistics getStatistics();

    String[] getTempData();

    int[] getTk();

    int[] getnB();

    int[][] getnPc();

    void initComponent();

    void meanOfValue();

    void oddReliability();

    void reliability();

    /**
     * Mencari benar dan salah siswa akan soal-soal yang diberikan. NRaW
     * menampung jawaban benar dan salah dengan alamat 0 untuk benar, dan alamat
     * 1 untuk salah.
     */
    void rightAndWrong();

    void rightAndWrong2();

    void setAnswerKey(String[] answerKey);

    void setCorrelation(String[] correlation);

    void setCorrelationNumber(double[] correlationNumber);

    void setDatabase(Database database);

    void setDb(float[] db);

    void setEp(String[] ep);

    void setIPc(String[][] IPc);

    void setLetter(String[][] letter);

    void setNRaW(int[][] NRaW);

    void setNRaW2(int[][] NRaW2);

    void setNumber(String[][] number);

    void setPearson(Pearson pearson);

    void setScore(int[] score);

    void setSortedNumber(String[][] sortedNumber);

    void setStatistics(Statistics statistics);

    void setTempData(String[] tempData);

    void setTk(int[] tk);

    void setnB(int[] nB);

    void setnPc(int[][] nPc);

    void sumPassGrade();

    /**
     * Mencari tingkat kesulitan suatu soal, jumlah benar dalam satu soal di
     * jumlahkan lalu di bagi dengan banyaknya siswa. (proses pembagian
     * dilakukan didalam controller)
     */
    void tk();

    /**
     * Mencari Validitas suatu soal.
     */
    void validity();
    
}
