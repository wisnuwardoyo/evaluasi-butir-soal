package com.wisnu.ebs.model.impl;

import com.wisnu.ebs.add.OddReability;
import com.wisnu.ebs.add.Pearson;
import com.wisnu.ebs.add.Statistics;
import com.wisnu.ebs.model.Database;
import com.wisnu.ebs.model.FindingResult;
import java.text.DecimalFormat;

/**
 *
 * @author Wisnu Wardoyo <mas.wisnu99@gmail.com>
 */
public class FindingResultImpl implements FindingResult {

    private Database database;
    private Pearson pearson;
    private Statistics statistics;
    private OddReability oddReliability;
    private String[][] letter;
    private String[][] number;
    private String[][] sortedNumber;
    private String[][] IPc;
    private String[] answerKey;
    private String[] ep;
    private String[] correlation;
    private String[] tempData = new String[7];
    private int[][] NRaW;
    private int[][] NRaW2;
    private int[][] nPc;
    private int[] tk;
    private int[] nB;
    private int[] score;
    private float[] db;
    private double[] correlationNumber;
    private int col;
    private int row;
    private int aktif;

    @Override
    public void initComponent() {
        aktif = database.getCurrentlySelectedItem();
        col = Integer.parseInt(database.getStudentsCount()[aktif]);
        row = Integer.parseInt(database.getItemCount()[aktif]);
        setLetter(new String[col][row + 1]);
        setNumber(new String[col][row + 3]);
        setNRaW(new int[col][2]);
        setNRaW2(new int[col][2]);
        setAnswerKey(new String[row]);
        setScore(new int[col]);
        setTk(new int[row]);
        setDb(new float[row]);
        setEp(new String[row]);
        setCorrelation(new String[row]);
        setCorrelationNumber(new double[row]);
        setLetter(database.getStudentsAnswer()[aktif]);
        setAnswerKey(database.getKey()[aktif]);
    }

    /**
     * Mencocokan jawaban dengan kunci, kemudian dikonversi menjadi 1 dan 0, dan
     * ditampung kedalam variable number pada resModel.
     */
    @Override
    public void correcting() {
        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                if (!database.getStudentsAnswer()[aktif][i][j + 1].equals(database.getKey()[aktif][j])
                        || database.getStudentsAnswer()[aktif][i][j + 1].equals("")
                        || database.getStudentsAnswer()[aktif][i][j + 1].equals("?")) {
                    getNumber()[i][j + 1] = "0";
                } else {
                    getNumber()[i][j + 1] = "1";
                }

            }
        }

        for (int i = 0; i < col; i++) {
            /**
             * Menempatkan Nama Siswa kedalam variable number paling depan.
             */
            getNumber()[i][0] = getLetter()[i][0];
            /**
             * Menempatkan urutan nomor siswa kedalam variable number paling
             * belakang, digunakan untuk sorting.
             */
            getNumber()[i][row + 2] = String.valueOf(i + 1);
        }

    }

    /**
     * Mencari tingkat kesulitan suatu soal, jumlah benar dalam satu soal di
     * jumlahkan lalu di bagi dengan banyaknya siswa. (proses pembagian
     * dilakukan didalam view)
     */
    @Override
    public void tk() {
        for (int i = 1; i <= row; i++) {
            for (int j = 0; j < col; j++) {
                getTk()[i - 1] += Integer.parseInt(getNumber()[j][i]);
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
    @Override
    public void db() {
        String[][] angkaCopy ;//= new String[col][row + 2];
        // Mengakumulasikan score masing masing siswa.
        for (int i = 0; i < col; i++) {
            for (int j = 1; j <= row; j++) {
                getScore()[i] += Integer.parseInt(getNumber()[i][j]);
            }
        }
        //menempatkan score total di Variable number pada posisi row+1
        for (int i = 0; i < col; i++) {
            getNumber()[i][row + 1] = String.valueOf(getScore()[i]);
        }

        //Variable dummy untuk membantu proses pengurutan
        String[][] dummy = new String[col][row];

        // Menyalin variable number agar posisinya tetap dan dapat digunakan lain waktu
        angkaCopy = getNumber().clone();

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
        setSortedNumber(angkaCopy.clone());

        // Dua variable BA dan BB, untuk menampung siswa dalam batas atas dan bawah.
        String[][] BA = new String[(int) (col * 0.27)][row + 2];
        String[][] BB = new String[(int) (col * 0.27)][row + 2];

        //Menempatkan siswa dalam batas atas kedalam BA
        for (int i = 0; i < (int) (col * 0.27); i++) {
            BA[i] = angkaCopy[i];

        }

        //Menempatkan siswa dalam batas bawah kedalam BB
        for (int i = (col - ((int) (col * 0.27))); i < col; i++) {
            BB[i - (col - ((int) (col * 0.27)))] = angkaCopy[i];

        }

        //Layer digunakan untuk menampung jumlah dari masing masing hasil olahan BA dan BB
        float[] layer = new float[row * 2];
        for (int i = 0; i < row; i++) {
            for (String[] BA1 : BA) {
                layer[i] += Float.parseFloat(BA1[i + 1]);
            }
        }
        for (int i = row; i < layer.length; i++) {
            for (String[] BB1 : BB) {
                layer[i] += Float.parseFloat(BB1[(i - row) + 1]);
            }
        }

        //Perhitungan daya beda
        for (int i = 0; i < row; i++) {
            getDb()[i] = (layer[i] - layer[i + row]) / (float) BA.length;
            if (String.valueOf(getDb()[i]).equals("NaN")) {
                getDb()[i] = 0;
            }
        }
    }

    @Override
    public void ep() {
        String[] pola = new String[Integer.parseInt(database.getItemType()[aktif])];
        if (pola.length == 3) {
            pola[0] = "A"; //damn to make it simple
            pola[1] = "B";
            pola[2] = "C";
        } else if (pola.length == 4) {
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
        //--- Begin of checking zero chooser ----//
        int[] dump = new int[pola.length];
        for (int i = 0; i < pola.length; i++) {
            dump[i] = 0;
        }
        for (int i = 1; i <= row; i++) {
            for (int j = 0; j < pola.length; j++) {
                for (int k = 0; k < col; k++) {
                    if (getLetter()[k][i].equals(pola[j])) {
                        dump[j] = 1;
                        break;
                    }
                }

            }
            if (pola.length == 3 && (dump[0] == 1 && dump[1] == 1 && dump[2] == 1)) {
                getEp()[i - 1] = "Efektif";
            } else if (pola.length == 4 && (dump[0] == 1 && dump[1] == 1 && dump[2] == 1 && dump[3] == 1)) {
                getEp()[i - 1] = "Efektif";
            } else if (pola.length == 5 && (dump[0] == 1 && dump[1] == 1 && dump[2] == 1 && dump[3] == 1 && dump[4] == 1)) {
                getEp()[i - 1] = "Efektif";
            } else {
                getEp()[i - 1] = "Tidak Efektif";
            }
            for (int l = 0; l < Integer.parseInt(database.getItemType()[aktif]); l++) {
                dump[l] = 0;
            }
        }
        //----End Of Checking Zero Chooser---//

        //--Begin of checking score of options--//
        setIPc(new String[Integer.parseInt(database.getItemType()[aktif])][row]);
        setnPc(new int[Integer.parseInt(database.getItemType()[aktif])][row]);
        setnB(new int[row]);
        int a = 0;
        double[][] IPcopy = new double[Integer.parseInt(database.getItemType()[aktif])][row];
        for (int i = 0; i < pola.length; i++) {
            for (int j = 1; j <= row; j++) {
                getnPc()[i][j - 1] = 0;
                a = 0;
                for (int k = 0; k < col; k++) {
                    if (getLetter()[k][j].equals(pola[i])) {
                        a += 1;
                    }
                }
                getnPc()[i][j - 1] = a;
            }
        }
        for (int i = 1; i <= row; i++) {
            for (int j = 0; j < col; j++) {
                getnB()[i - 1] += Integer.parseInt(getNumber()[j][i]);

            }
        }
        for (int i = 0; i < pola.length; i++) {
            for (int j = 0; j < row; j++) {
                IPcopy[i][j] = (double) getnPc()[i][j];
                IPcopy[i][j] /= (float) (col - getnB()[j]) / (float) (pola.length - 1);
                IPcopy[i][j] *= 100;

            }

        }

        for (int i = 0; i < pola.length; i++) {
            for (int j = 0; j < row; j++) {
                if (IPcopy[i][j] >= 76 && IPcopy[i][j] <= 125) {

                    getIPc()[i][j] = getnPc()[i][j] + " (+ +)";

                } else if ((IPcopy[i][j] >= 51 && IPcopy[i][j] <= 75) || (IPcopy[i][j] >= 126 && IPcopy[i][j] <= 150)) {

                    getIPc()[i][j] = getnPc()[i][j] + " (+)";

                } else if ((IPcopy[i][j] >= 26 && IPcopy[i][j] <= 50) || (IPcopy[i][j] >= 151 && IPcopy[i][j] <= 175)) {

                    getIPc()[i][j] = getnPc()[i][j] + " (-)";

                } else if ((IPcopy[i][j] >= 0 && IPcopy[i][j] <= 25) || (IPcopy[i][j] >= 176 && IPcopy[i][j] <= 200)) {

                    getIPc()[i][j] = getnPc()[i][j] + " (- -)";

                } else {

                    getIPc()[i][j] = getnPc()[i][j] + " (- - -)";
                }
            }
        }

        for (int i = 0; i < row; i++) {
            int pos = 0;
            if (getAnswerKey()[i].equals("A")) {
                pos = 0;
            }
            if (getAnswerKey()[i].equals("B")) {
                pos = 1;
            }
            if (getAnswerKey()[i].equals("C")) {
                pos = 2;
            }
            if (getAnswerKey()[i].equals("D")) {
                pos = 3;
            }
            if (getAnswerKey()[i].equals("E")) {
                pos = 4;
            }
            getIPc()[pos][i] = String.valueOf(getnB()[i]) + " (**)";
        }

    }

    @Override
    public void reliability() {
        if(row % 2 == 0){
            evenReliability();
        }else{
            oddReliability();
        }
    }
    
    @Override
    public void evenReliability(){
        double[] jawab = new double[col];
        for (int i = 0; i < col; i++) {
            jawab[i] = converter(getNumber()[i][row + 1]);
        }
        double[] ganjil = new double[col];
        double[] genap = new double[col];

        for (int i = 0; i < col; i++) {
            for (int j = 1; j <= row; j += 2) {
                ganjil[i] += converter(getNumber()[i][j]);
            }
            for (int k = 2; k <= row; k += 2) {
                genap[i] += converter(getNumber()[i][k]);
            }
        }

        setPearson(new Pearson(col, ganjil, genap));
        double rtt = 0;
        double rxy = getPearson().getHasil();
        rtt = (2 * rxy) / (1 + rxy);

        setStatistics(new Statistics(jawab));
        getTempData()[0] = String.valueOf(new DecimalFormat("#.##").format(getStatistics().getMean()));
        getTempData()[1] = String.valueOf(new DecimalFormat("#.##").format(getStatistics().getStdDev()));
        getTempData()[2] = String.valueOf(new DecimalFormat("#.##").format(rxy));
        getTempData()[3] = String.valueOf(new DecimalFormat("#.##").format(rtt));
    }
    
    @Override
    public void oddReliability(){
        double[] jawab = new double[col];
        
        for (int i = 0; i < col; i++) {
            jawab[i] = converter(getNumber()[i][row + 1]);
        }
        double[][] numberForOdd = new double[col][row + 1];
        for(int i = 0; i < col; i++){
            for(int j = 0; j < row+1; j++){
                numberForOdd[i][j] = converter(getNumber()[i][j+1]);
            }
        }
        
        oddReliability = new OddReability((double)row, numberForOdd);
        setStatistics(new Statistics(jawab));
        getTempData()[0] = String.valueOf(new DecimalFormat("#.##").format(getStatistics().getMean()));
        getTempData()[1] = String.valueOf(new DecimalFormat("#.##").format(getStatistics().getStdDev()));
        getTempData()[2] = "Tidak tersedia(Soal ganjil)";
        getTempData()[3] = String.valueOf(new DecimalFormat("#.##").format(oddReliability.getReability()));
    }
    
    @Override
    public double converter(String a) {
        double temp;
        temp = Double.parseDouble(a);
        return temp;
    }

    /**
     * Mencari Validitas suatu soal.
     */
    @Override
    public void validity() {
        double[][] validity_cek = new double[row + 1][col];

        for (int i = 0; i <= row; i++) {
            for (int j = 0; j < col; j++) {
                validity_cek[i][j] = converter(getNumber()[j][i + 1]);
            }
        }
        double[] validity = new double[row];
        DecimalFormat df = new DecimalFormat("#.###");
        for (int i = 0; i < row; i++) {
            setPearson(new Pearson(col, validity_cek[i], validity_cek[row]));
            validity[i] = getPearson().getHasil();
        }

        for (int i = 0; i < row; i++) {
            getCorrelation()[i] = String.valueOf(df.format(validity[i]));
            getCorrelationNumber()[i] = Double.parseDouble(df.format(validity[i]).replace(",", "."));
        }

    }

    /**
     * Mencari benar dan salah siswa akan soal-soal yang diberikan. NRaW
     * menampung jawaban benar dan salah dengan alamat 0 untuk benar, dan alamat
     * 1 untuk salah.
     */
    @Override
    public void rightAndWrong() {
        for (int i = 0; i < col; i++) {
            getNRaW()[i][0] = 0;
            getNRaW()[i][1] = 0;
            for (int j = 1; j <= row; j++) {
                if (getSortedNumber()[i][j].equals("1")) {
                    getNRaW()[i][0] += 1;
                } else {
                    getNRaW()[i][1] += 1;
                }
            }
        }

    }

    @Override
    public void rightAndWrong2() {
        for (int i = 0; i < col; i++) {
            getNRaW2()[i][0] = 0;
            getNRaW2()[i][1] = 0;
            for (int j = 1; j <= row; j++) {
                if (getNumber()[i][j].equals("1")) {
                    getNRaW2()[i][0] += 1;
                } else {
                    getNRaW2()[i][1] += 1;
                }
            }
        }

    }

    @Override
    public void meanOfValue() {
        double[] data = new double[col];
        for (int i = 0; i < col; i++) {
            data[i] = ((double) NRaW[i][0] / (double) row) * 100;
        }
        getTempData()[4] = new DecimalFormat("#.##").format(new Statistics(data).getMean());

    }

    @Override
    public void sumPassGrade() {
        int lulus = 0;
        int tlulus = 0;
        for (int i = 0; i < col; i++) {
            if (((float) getNRaW()[i][0] / row) * 100 >= Float.parseFloat(database.getMinimumPassValue()[aktif])) {
                lulus += 1;
            } else {
                tlulus += 1;
            }
        }
        getTempData()[5] = String.valueOf(lulus);
        getTempData()[6] = String.valueOf(tlulus);
    }

//BORDER
    @Override
    public Database getDatabase() {
        return database;
    }

    @Override
    public void setDatabase(Database database) {
        this.database = database;
    }

    @Override
    public Pearson getPearson() {
        return pearson;
    }

    @Override
    public void setPearson(Pearson pearson) {
        this.pearson = pearson;
    }

    @Override
    public Statistics getStatistics() {
        return statistics;
    }

    @Override
    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    @Override
    public String[][] getLetter() {
        return letter;
    }

    @Override
    public void setLetter(String[][] letter) {
        this.letter = letter;
    }

    @Override
    public String[][] getNumber() {
        return number;
    }

    @Override
    public void setNumber(String[][] number) {
        this.number = number;
    }

    @Override
    public String[][] getSortedNumber() {
        return sortedNumber;
    }

    @Override
    public void setSortedNumber(String[][] sortedNumber) {
        this.sortedNumber = sortedNumber;
    }

    @Override
    public String[][] getIPc() {
        return IPc;
    }

    @Override
    public void setIPc(String[][] IPc) {
        this.IPc = IPc;
    }

    @Override
    public String[] getAnswerKey() {
        return answerKey;
    }

    @Override
    public void setAnswerKey(String[] answerKey) {
        this.answerKey = answerKey;
    }

    @Override
    public String[] getEp() {
        return ep;
    }

    @Override
    public void setEp(String[] ep) {
        this.ep = ep;
    }

    @Override
    public String[] getCorrelation() {
        return correlation;
    }

    @Override
    public void setCorrelation(String[] correlation) {
        this.correlation = correlation;
    }

    @Override
    public String[] getTempData() {
        return tempData;
    }

    @Override
    public void setTempData(String[] tempData) {
        this.tempData = tempData;
    }

    @Override
    public int[][] getNRaW() {
        return NRaW;
    }

    @Override
    public void setNRaW(int[][] NRaW) {
        this.NRaW = NRaW;
    }

    @Override
    public int[][] getnPc() {
        return nPc;
    }

    @Override
    public void setnPc(int[][] nPc) {
        this.nPc = nPc;
    }

    @Override
    public int[] getTk() {
        return tk;
    }

    @Override
    public void setTk(int[] tk) {
        this.tk = tk;
    }

    @Override
    public int[] getnB() {
        return nB;
    }

    @Override
    public void setnB(int[] nB) {
        this.nB = nB;
    }

    @Override
    public int[] getScore() {
        return score;
    }

    @Override
    public void setScore(int[] score) {
        this.score = score;
    }

    @Override
    public float[] getDb() {
        return db;
    }

    @Override
    public void setDb(float[] db) {
        this.db = db;
    }

    @Override
    public double[] getCorrelationNumber() {
        return correlationNumber;
    }

    @Override
    public void setCorrelationNumber(double[] correlationNumber) {
        this.correlationNumber = correlationNumber;
    }

    @Override
    public int[][] getNRaW2() {
        return NRaW2;
    }

    @Override
    public void setNRaW2(int[][] NRaW2) {
        this.NRaW2 = NRaW2;
    }
    
    
}
