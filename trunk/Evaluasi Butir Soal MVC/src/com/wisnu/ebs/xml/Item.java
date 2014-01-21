package com.wisnu.ebs.xml;

public class Item {

    private String kompetensi;
    private String id;
    private String jumlahSiswa;
    private String jumlahSoal;
    private String tipe;
    private String kkm;
    private String [] kunci;
    private String [][] soal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getKompetensi() {
        return kompetensi;
    }

    public void setKompetensi(String kompetensi) {
        this.kompetensi = kompetensi;
    }

    public String getJumlahSiswa() {
        return jumlahSiswa;
    }

    public void setJumlahSiswa(String jumlahSiswa) {
        this.jumlahSiswa = jumlahSiswa;
    }

    public String getJumlahSoal() {
        return jumlahSoal;
    }

    public void setJumlahSoal(String jumlahSoal) {
        this.jumlahSoal = jumlahSoal;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getKkm() {
        return kkm;
    }

    public void setKkm(String kkm) {
        this.kkm = kkm;
    }

    public String[] getKunci() {
        return kunci;
    }

    public void setKunci(String[] kunci) {
        this.kunci = kunci;
    }

    public String[][] getSoal() {
        return soal;
    }

    public void setSoal(String[][] soal) {
        this.soal = soal;
    }
    
    

    
}
