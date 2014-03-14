package com.wisnu.ebs.model;

import com.wisnu.ebs.event.MainListener;
import com.wisnu.ebs.view.AnsPanel;
import com.wisnu.ebs.view.KeyPanel;
import com.wisnu.ebs.view.NewDocumentPanel;
import javax.swing.JTable;

/**
 *
 * @author Wisnu Wardoyo <mas.wisnu99@gmail.com>
 */
public class Database {

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

    public Database() {

    }

    public void newDocument(NewDocumentPanel panel) {
        NewDocumentPanel inputPanel = panel;

        setMaPel(inputPanel.getLabMaPel().getText());
        setNamaGuru(inputPanel.getLabGuru().getText());
        setNamaKelas(inputPanel.getLabKelas().getText());
        setJumlahBerkas(1);

        int jumlahSiswa = Integer.parseInt(inputPanel.getLabJumlahSiswa().getText());
        int jumlahSoal = Integer.parseInt(inputPanel.getLabSoal().getText());

        setKompetensi(new String[1]);
        setJmlSoal(new String[1]);
        setJmlSiswa(new String[1]);
        setTipeSoal(new String[1]);
        setKKM(new String[1]);
        String[][][] SoalBaru = new String[1][jumlahSiswa][jumlahSoal + 1];
        String[][] kunciBaru = new String[1][jumlahSoal];

        for (int i = 0; i < jumlahSiswa; i++) {
            for (int j = 0; j <= jumlahSoal; j++) {
                if (j == 0) {
                    SoalBaru[0][i][j] = "Siswa "+ String.valueOf((i+1));
                } else {
                    SoalBaru[0][i][j] = "";
                }
            }
        }
        for (int i = 0; i < jumlahSoal; i++) {
            kunciBaru[0][i] = "";
        }

        kompetensi[0] = inputPanel.getLabKompetensi().getText();
        jmlSoal[0] = inputPanel.getLabSoal().getText();
        jmlSiswa[0] = inputPanel.getLabJumlahSiswa().getText();
        tipeSoal[0] = String.valueOf(inputPanel.getRadio());
        KKM[0] = inputPanel.getLabKKM().getText();
        setKunci(kunciBaru);
        setSoal(SoalBaru);
        setBerkasAktif(0);

    }

    public void setKunci(KeyPanel panel) {
        if (panel != null) {
            JTable table = panel.getTable();
            for (int i = 0; i < Integer.parseInt(getJmlSoal()[getBerkasAktif()]); i++) {
                getKunci()[getBerkasAktif()][i] = table.getValueAt(i, 0).toString().toUpperCase();
            }
        }
    }

    public void setSoal(AnsPanel panel) {
        if (panel != null) {
            JTable table = panel.getTable();
            for (int i = 0; i < Integer.parseInt(getJmlSiswa()[getBerkasAktif()]); i++) {
                for (int j = 0; j <= Integer.parseInt(getJmlSoal()[getBerkasAktif()]); j++) {
                    getSoal()[getBerkasAktif()][i][j] = table.getValueAt(i, j).toString().toUpperCase();
                }
            }
        }
    }

    public void fireErrorMessage(int i,int j,String Error) {
        if (mainListener != null) {
            mainListener.fireErrorMessage(i,j,Error);
        }
    }

    //Border
    public void setMainListener(MainListener mainListener) {
        this.mainListener = mainListener;
    }

    public String[][] getKunci() {
        return kunci;
    }

    public void setKunci(String[][] kunci) {
        this.kunci = kunci;
    }

    public String[][][] getSoal() {
        return soal;
    }

    public void setSoal(String[][][] soal) {
        this.soal = soal;
    }

    public String[] getKompetensi() {
        return kompetensi;
    }

    public void setKompetensi(String[] kompetensi) {
        this.kompetensi = kompetensi;
    }

    public String[] getKKM() {
        return KKM;
    }

    public void setKKM(String[] KKM) {
        this.KKM = KKM;
    }

    public String[] getJmlSiswa() {
        return jmlSiswa;
    }

    public void setJmlSiswa(String[] jmlSiswa) {
        this.jmlSiswa = jmlSiswa;
    }

    public String[] getJmlSoal() {
        return jmlSoal;
    }

    public void setJmlSoal(String[] jmlSoal) {
        this.jmlSoal = jmlSoal;
    }

    public String[] getTipeSoal() {
        return tipeSoal;
    }

    public void setTipeSoal(String[] tipeSoal) {
        this.tipeSoal = tipeSoal;
    }

    public String getMaPel() {
        return maPel;
    }

    public void setMaPel(String maPel) {
        this.maPel = maPel;
    }

    public String getNamaGuru() {
        return namaGuru;
    }

    public void setNamaGuru(String namaGuru) {
        this.namaGuru = namaGuru;
    }

    public String getNamaKelas() {
        return namaKelas;
    }

    public void setNamaKelas(String namaKelas) {
        this.namaKelas = namaKelas;
    }

    public int getJumlahBerkas() {
        return jumlahBerkas;
    }

    public void setJumlahBerkas(int jumlahBerkas) {
        this.jumlahBerkas = jumlahBerkas;
    }

    public int getBerkasAktif() {
        return berkasAktif;
    }

    public void setBerkasAktif(int berkasAktif) {
        this.berkasAktif = berkasAktif;
    }

}
