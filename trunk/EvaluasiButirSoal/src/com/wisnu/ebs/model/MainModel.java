package com.wisnu.ebs.model;

import com.wisnu.ebs.event.AnsListener;
import com.wisnu.ebs.event.ConfListener;
import com.wisnu.ebs.event.KeyListener;
import com.wisnu.ebs.event.MainListener;
import com.wisnu.ebs.event.ResListener;

public class MainModel {

    private String[][] kunci;
    private String[][][] soal;
    private String[] kompetensi;
    private String[] KKM;
    private String[] jmlSiswa;
    private String[] jmlSoal;
    private String[] tipeSoal;

    private String maPel;
    private String namaGuru;
    private String namaKelas;
    private int jumlahBerkas;
    private int berkasAktif;

    private MainListener mainListener;
    private ConfListener confListener;
    private KeyListener keyListener;
    private AnsListener ansListener;
    private ResListener resListener;

    public MainModel() {
    }

    public void fireNewDocument() {
        if (mainListener != null) {
            mainListener.newDocument();
        }
    }

    public void fireOnLoad() {
        if (confListener != null) {
            confListener.onLoad(this);
        }
    }

    public void fireSaveDocument() {
        if (mainListener != null) {
            mainListener.saveDocument();
        }
    }
    
    public void fireLoadDocument(){
        if(mainListener != null){
            mainListener.loadDocument();
        }
    }

    public void fireKeyLoad() {
        keyListener.onLoad();
    }

    public void fireAnsLoad() {
        if (ansListener != null) {
            ansListener.onLoad();
        }
    }
    
    public void fireResLoad(){
        if(mainListener != null){
            mainListener.openResult();
        }
    }
    
    public void fireConfigurationLoad(){
        if(mainListener != null){
            mainListener.openConfiguration();
        }
    }
    
    public void fireNewFile(){
        if(confListener != null){
            confListener.newFile();
        }
    }
    
    //LIST OF GETTER
    public int getJumlahBerkas() {
        return jumlahBerkas;
    }

    public String[] getKompetensi() {
        return kompetensi;
    }

    public String[] getKKM() {
        return KKM;
    }

    public String getMaPel() {
        return maPel;
    }

    public String[][] getKunci() {
        return kunci;
    }

    public String[][][] getSoal() {
        return soal;
    }

    public String[] getJmlSiswa() {
        return jmlSiswa;
    }

    public String[] getJmlSoal() {
        return jmlSoal;
    }

    public String[] getTipeSoal() {
        return tipeSoal;
    }

    public String getNamaGuru() {
        return namaGuru;
    }

    public int getBerkasAktif() {
        return berkasAktif;
    }

    public String getNamaKelas() {
        return namaKelas;
    }

    /*
     LIST OF SETTER (ONLY SETTER, YOU DON'T KNOW??)
     */
    public void setResListener(ResListener resListener) {

        this.resListener = resListener;

    }

    public void setAnsListener(AnsListener ansListener) {

        this.ansListener = ansListener;

    }

    public void setMainListener(MainListener listener) {

        this.mainListener = listener;

    }

    public void setConfListener(ConfListener confListener) {
        this.confListener = confListener;
    }

    public void setKeyListener(KeyListener keyListener) {
        this.keyListener = keyListener;
    }

    public void setJumlahBerkas(int jumlahBerkas) {
        this.jumlahBerkas = jumlahBerkas;
    }

    public void setKompetensi(String[] kompetensi) {
        this.kompetensi = kompetensi;
    }

    public void setKKM(String[] KKM) {
        this.KKM = KKM;
    }

    public void setMaPel(String maPel) {
        this.maPel = maPel;
    }

    public void setNamaGuru(String namaGuru) {
        this.namaGuru = namaGuru;
    }

    public void setNamaKelas(String namaKelas) {
        this.namaKelas = namaKelas;
    }

    public void setKunci(String[][] kunci) {
        this.kunci = kunci;
    }

    public void setSoal(String[][][] soal) {
        this.soal = soal;
    }

    public void setJmlSiswa(String[] jmlSiswa) {
        this.jmlSiswa = jmlSiswa;
    }

    public void setJmlSoal(String[] jmlSoal) {
        this.jmlSoal = jmlSoal;
    }

    public void setBerkasAktif(int berkasAktif) {
        this.berkasAktif = berkasAktif;
    }

    public void setTipeSoal(String[] tipeSoal) {
        this.tipeSoal = tipeSoal;
    }
}
