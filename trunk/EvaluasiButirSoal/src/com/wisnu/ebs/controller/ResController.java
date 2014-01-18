/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wisnu.ebs.controller;

import com.wisnu.ebs.add.Pearson;
import com.wisnu.ebs.add.Statistics;
import com.wisnu.ebs.model.MainModel;
import com.wisnu.ebs.model.ResModel;
import java.text.DecimalFormat;

/**
 *
 * @author Wisnu Citra
 */
public class ResController {

    MainModel mainModel;
    ResModel resModel;
    int col;
    int row;
    int aktif;

    public ResController() {

    }

    /**
     * Pengaturan model utama yang akan di gunakan dalam proses penghitungan.
     * Model utama merupakan Class yang berisi variable - variable yang akan
     * diolah menjadi suatu perhitungan.
     *
     * @param model
     */
    public void setMainModel(MainModel model) {
        this.mainModel = model;
        col = Integer.parseInt(mainModel.getJmlSiswa()[mainModel.getBerkasAktif()]);
        row = Integer.parseInt(mainModel.getJmlSoal()[mainModel.getBerkasAktif()]);
        aktif = mainModel.getBerkasAktif();
        initComponents();
    }

    /**
     * Pengaturan model result, yang berisi kumpulan variable sementara yang
     * digunakan untuk menampung proses pengolahan data.
     *
     * @param model
     */
    public void setResModel(ResModel model) {
        this.resModel = model;
    }

    /**
     * memuat data hasil olahan kedalam view
     */
    public void fireOnload() {
        resModel.fireOnLoad();
    }

    /**
     * menginisiasi variable - variable di dalam resModel.
     */
    public void initComponents() {
        resModel.setLetter(new String[col][row + 1]);
        resModel.setNumber(new String[col][row + 3]);
        resModel.setNRaW(new int[col][2]);
        resModel.setAnswerKey(new String[row]);
        resModel.setScore(new int[col]);
        resModel.setTk(new int[row]);
        resModel.setDb(new float[row]);
        resModel.setEp(new String[row]);
        resModel.setCorrelation(new String[row]);
        resModel.setCorrelationNumber(new double[row]);
        resModel.setLetter(mainModel.getSoal()[aktif]);
        resModel.setAnswerKey(mainModel.getKunci()[aktif]);
    }

    /**
     * Mencocokan jawaban dengan kunci, kemudian dikonversi menjadi 1 dan 0, dan
     * ditampung kedalam variable number pada resModel.
     */
    public void correcting() {
        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                if (!mainModel.getSoal()[aktif][i][j + 1].equals(mainModel.getKunci()[aktif][j])
                        || mainModel.getSoal()[aktif][i][j + 1].equals("")
                        || mainModel.getSoal()[aktif][i][j + 1].equals("?")) {
                    resModel.getNumber()[i][j + 1] = "0";
                } else {
                    resModel.getNumber()[i][j + 1] = "1";
                }

            }
        }
        for (int i = 0; i < col; i++) {
            /**
             * Menempatkan Nama Siswa kedalam variable number paling depan.
             */
            resModel.getNumber()[i][0] = resModel.getLetter()[i][0];
            /**
             * Menempatkan urutan nomor siswa kedalam variable number paling
             * belakang, digunakan untuk sorting.
             */
            resModel.getNumber()[i][row + 2] = String.valueOf(i + 1);
        }

    }

    /**
     * Mencari tingkat kesulitan suatu soal, jumlah benar dalam satu soal di
     * jumlahkan lalu di bagi dengan banyaknya siswa. (proses pembagian
     * dilakukan didalam view)
     */
    public void tk() {
        for (int i = 1; i <= row; i++) {
            for (int j = 0; j < col; j++) {
                resModel.getTk()[i - 1] += Integer.parseInt(resModel.getNumber()[j][i]);
            }
        }
    }

    /**
     * Mencari daya beda soal melalui beberapa tahap, tahap pertama adalah
     * mengurutkan angka berdasarkan nomor urut awal dan score tertinggi.
     * selanjutnya dipisahkan antara siswa yang termasuk dalam urutan atas dan
     * urutan bawah. Selanjutnya di Olah dan dicari perbedaannya. (batas atas
     * dan batas bawah masing2 hanya diambil 27% dari total siswa)
     */
    public void db() {
        String[][] angkaCopy = new String[col][row + 2];
        // Mengakumulasikan score masing masing siswa.
        for (int i = 0; i < col; i++) {
            for (int j = 1; j <= row; j++) {
                resModel.getScore()[i] += Integer.parseInt(resModel.getNumber()[i][j]);
            }
        }
        //menempatkan score total di Variable number pada posisi row+1
        for (int i = 0; i < col; i++) {
            resModel.getNumber()[i][row + 1] = String.valueOf(resModel.getScore()[i]);
        }
        
        //Variable dummy untuk membantu proses pengurutan
        String[][] dummy = new String[row][col];
        
        // Menyalin variable number agar posisinya tetap dan dapat digunakan lain waktu
        angkaCopy = resModel.getNumber().clone();
        
        //Mengurutkan angkaCopy menggunakan teknik buble sort
        for (int i = 0; i < col; i++) {
            for (int j = 0; j < col; j++) {
                if (Integer.parseInt(angkaCopy[i][row + 1]) > Integer.parseInt(angkaCopy[j][row + 1])) {

                    dummy[i] = angkaCopy[i];
                    angkaCopy[i] = angkaCopy[j];
                    angkaCopy[j] = dummy[i];

                } else if (Integer.parseInt(angkaCopy[i][row + 1]) == Integer.parseInt(angkaCopy[j][row + 1])) {
                    if (Integer.parseInt(angkaCopy[i][row + 2]) < Integer.parseInt(angkaCopy[j][row + 2])) {
                        dummy[i] = angkaCopy[i];
                        angkaCopy[i] = angkaCopy[j];
                        angkaCopy[j] = dummy[i];
                    }

                }
            }
        }
        
        // Menyalin angkaCopy kedalam variable sortedNumber agar dapat digunakan secara global
        resModel.setSortedNumber(angkaCopy.clone());
        
        // Dua variable BA dan BB, untuk menampung siswa dalam batas atas dan bawah.
        String[][] BA = new String[col][row + 2];
        String[][] BB = new String[col][row + 2];
        
        //Menempatkan siswa dalam batas atas kedalam BA
        for (int i = 0; i < (int) (col * 0.33); i++) {
            BA[i] = angkaCopy[i];

        }
        
        //Menempatkan siswa dalam batas bawah kedalam BB
        for (int i = (col - ((int) (col * 0.33))); i < col; i++) {
            BB[i - (col - ((int) (col * 0.33)))] = angkaCopy[i];

        }
        
        //Layer digunakan untuk menampung jumlah dari masing masing hasil olahan BA dan BB
        float[] layer = new float[col * row];
        for (int i = 1; i <= row; i++) {
            for (int j = 0; j < (int) (col * 0.33); j++) {
                layer[i - 1] += Float.parseFloat(BA[j][i]);
            }
        }
        for (int i = row + 1; i <= (row * 2); i++) {
            for (int j = 0; j < (int) (col * 0.33); j++) {
                layer[i - 1] += Float.parseFloat(BB[j][i - (row)]);
            }
        }
        //Perhitungan daya beda
        for (int i = 0; i < row; i++) {
            resModel.getDb()[i] = (layer[i] / ((int) (row * 0.33))) - (layer[i + row] / ((int) (row * 0.33)));
        }
    }

    public void ep() {
        int z = col;

        String[] pola = new String[Integer.parseInt(mainModel.getTipeSoal()[aktif])];
        if (Integer.parseInt(mainModel.getTipeSoal()[aktif]) == 4) {
            pola[0] = "A"; //damn to make it simple
            pola[1] = "B";
            pola[2] = "C";
            pola[3] = "D";
        } else {
            pola[0] = "A";
            pola[1] = "B";
            pola[2] = "C";
            pola[3] = "D";
            pola[4] = "E";
        }

        int[] dump = new int[Integer.parseInt(mainModel.getTipeSoal()[aktif])];
        for (int i = 0; i < Integer.parseInt(mainModel.getTipeSoal()[aktif]); i++) {
            dump[i] = 0;
        }
        for (int i = 1; i <= row; i++) {
            for (int j = 0; j < Integer.parseInt(mainModel.getTipeSoal()[aktif]); j++) {
                for (int k = 0; k < col; k++) {
                    if (resModel.getLetter()[k][i].equals(pola[j])) {
                        dump[j] = 1;
                        break;
                    }
                }

            }
            if (Integer.parseInt(mainModel.getTipeSoal()[aktif]) == 4 && (dump[0] == 1 && dump[1] == 1 && dump[2] == 1 && dump[3] == 1)) {
                resModel.getEp()[i - 1] = "Efektif";
            } else if (Integer.parseInt(mainModel.getTipeSoal()[aktif]) == 5 && (dump[0] == 1 && dump[1] == 1 && dump[2] == 1 && dump[3] == 1 && dump[4] == 1)) {
                resModel.getEp()[i - 1] = "Efektif";
            } else {
                resModel.getEp()[i - 1] = "Tidak Efektif";
            }
            for (int l = 0; l < Integer.parseInt(mainModel.getTipeSoal()[aktif]); l++) {
                dump[l] = 0;
            }
        }

        resModel.setIPc(new String[Integer.parseInt(mainModel.getTipeSoal()[aktif])][row]);
        resModel.setnPc(new int[Integer.parseInt(mainModel.getTipeSoal()[aktif])][row]);
        resModel.setnB(new int[row]);
        int a = 0;
        float[][] IPcopy = new float[Integer.parseInt(mainModel.getTipeSoal()[aktif])][row];
        for (int i = 0; i < pola.length; i++) {
            for (int j = 1; j <= row; j++) {
                resModel.getnPc()[i][j - 1] = 0;
                a = 0;
                for (int k = 0; k < col; k++) {
                    if (resModel.getLetter()[k][j].equals(pola[i])) {
                        a += 1;
                    }
                }
                resModel.getnPc()[i][j - 1] = a;
            }
        }
        for (int i = 1; i <= row; i++) {
            for (int j = 0; j < col; j++) {
                resModel.getnB()[i - 1] += Integer.parseInt(resModel.getNumber()[j][i]);

            }
        }
        for (int i = 0; i < pola.length; i++) {
            for (int j = 0; j < row; j++) {
                IPcopy[i][j] = (float) (resModel.getnPc()[i][j] / (float) ((float) (col - resModel.getnB()[i]) / (float) (Integer.parseInt(mainModel.getTipeSoal()[aktif]) - 1))) * 100;
                resModel.getIPc()[i][j] = String.valueOf(IPcopy[i][j]);
            }

        }
        for (int i = 0; i < pola.length; i++) {
            for (int j = 0; j < row; j++) {
                if (IPcopy[i][j] >= 76 && IPcopy[i][j] <= 125) {
                    resModel.getIPc()[i][j] = resModel.getIPc()[i][j] + " + +";
                } else if ((IPcopy[i][j] >= 51 && IPcopy[i][j] <= 75) || (IPcopy[i][j] >= 126 && IPcopy[i][j] <= 150)) {
                    resModel.getIPc()[i][j] = resModel.getIPc()[i][j] + " +";
                } else if ((IPcopy[i][j] >= 26 && IPcopy[i][j] <= 50) || (IPcopy[i][j] >= 151 && IPcopy[i][j] <= 175)) {
                    resModel.getIPc()[i][j] = resModel.getIPc()[i][j] + " -";
                } else if ((IPcopy[i][j] >= 0 && IPcopy[i][j] <= 25) || (IPcopy[i][j] >= 176 && IPcopy[i][j] <= 200)) {
                    resModel.getIPc()[i][j] = resModel.getIPc()[i][j] + " - -";
                } else {
                    resModel.getIPc()[i][j] = resModel.getIPc()[i][j] + " - - -";
                }
            }
        }

        for (int i = 0; i < row; i++) {
            int pos = 0;
            if (resModel.getAnswerKey()[i].equals("A")) {
                pos = 0;
            }
            if (resModel.getAnswerKey()[i].equals("B")) {
                pos = 1;
            }
            if (resModel.getAnswerKey()[i].equals("C")) {
                pos = 2;
            }
            if (resModel.getAnswerKey()[i].equals("D")) {
                pos = 3;
            }
            if (resModel.getAnswerKey()[i].equals("E")) {
                pos = 4;
            }
            resModel.getIPc()[pos][i] = String.valueOf(IPcopy[pos][i]) + " **";
        }

    }

    public void reliability() {
        double[] jawab = new double[col];
        for (int i = 0; i < col; i++) {
            jawab[i] = converter(resModel.getNumber()[i][row + 1]);
        }
        double[] ganjil = new double[col];
        double[] genap = new double[col];

        for (int i = 0; i < col; i++) {
            for (int j = 1; j <= row; j += 2) {
                ganjil[i] += converter(resModel.getNumber()[i][j]);
            }
            for (int k = 2; k <= row; k += 2) {
                genap[i] += converter(resModel.getNumber()[i][k]);
            }
        }

        resModel.setPearson(new Pearson(col, ganjil, genap));
        double rtt = 0;
        double rxy = resModel.getPearson().getHasil();
        rtt = (2 * rxy) / (1 + rxy);

        resModel.setStatistics(new Statistics(jawab));
        resModel.getTempData()[0] = String.valueOf(new DecimalFormat("#.##").format(resModel.getStatistics().getMean()));
        resModel.getTempData()[1] = String.valueOf(new DecimalFormat("#.##").format(resModel.getStatistics().getStdDev()));
        resModel.getTempData()[2] = String.valueOf(new DecimalFormat("#.##").format(rxy));
        resModel.getTempData()[3] = String.valueOf(new DecimalFormat("#.##").format(rtt));

    }

    public double converter(String a) {
        double temp = 0;
        temp = Double.parseDouble(a);
        return temp;
    }

    public void validity() {
        double[][] validity_cek = new double[row + 1][col];

        for (int i = 0; i <= row; i++) {
            for (int j = 0; j < col; j++) {
                validity_cek[i][j] = converter(resModel.getNumber()[j][i + 1]);
            }
        }
        double[] validity = new double[row];
        DecimalFormat df = new DecimalFormat("#.###");
        for (int i = 0; i < row; i++) {
            resModel.setPearson(new Pearson(col, validity_cek[i], validity_cek[row]));
            validity[i] = resModel.getPearson().getHasil();
        }
        for (int i = 0; i < row; i++) {
            resModel.getCorrelation()[i] = String.valueOf(df.format(validity[i]));
            resModel.getCorrelationNumber()[i] = Double.parseDouble(df.format(validity[i]).replace(",", "."));
        }

    }

    public void rightAndWrong() {
        for (int i = 0; i < col; i++) {
            resModel.getNRaW()[i][0] = 0;
            resModel.getNRaW()[i][1] = 0;
            for (int j = 1; j <= row; j++) {
                if (resModel.getSortedNumber()[i][j].equals("1")) {
                    resModel.getNRaW()[i][0] += 1;
                } else {
                    resModel.getNRaW()[i][1] += 1;
                }
            }
        }

    }
}
