package com.wisnu.ebs.controller;

import com.wisnu.ebs.model.MainModel;
import com.wisnu.ebs.view.AnsPanel;
import com.wisnu.ebs.view.NewDocumentPanel;
import com.wisnu.ebs.view.KeyPanel;
import com.wisnu.ebs.xml.ConfigStaxParser;
import com.wisnu.ebs.xml.Item;
import com.wisnu.ebs.xml.ItemStaXParser;
import java.util.List;
import javax.swing.JTable;

public class MainController {

    private MainModel model;
    private NewDocumentPanel inputPanel;
    private ConfigStaxParser configReader = new ConfigStaxParser();
    private ItemStaXParser itemReader = new ItemStaXParser();

    public MainController() {

    }

    public void newDocument(NewDocumentPanel panel) {
        this.inputPanel = panel;

        setMapel(inputPanel.getLabMaPel().getText());
        setNamaGuru(inputPanel.getLabGuru().getText());
        setNamaKelas(inputPanel.getLabKelas().getText());
        setJumlahBerkas(1);

        int jumlahSiswa = Integer.parseInt(inputPanel.getLabJumlahSiswa().getText());
        int jumlahSoal = Integer.parseInt(inputPanel.getLabSoal().getText());

        String[] kompetensi = new String[1];
        String[] jmlSoal = new String[1];
        String[] jmlSiswa = new String[1];
        String[] tipeSoal = new String[1];
        String[] kkm = new String[1];
        String[][][] soal = new String[1][jumlahSiswa][jumlahSoal + 1];
        String[][] kunci = new String[1][jumlahSoal];

        for (int i = 0; i < jumlahSiswa; i++) {
            for (int j = 0; j <= jumlahSoal; j++) {
                soal[0][i][j] = "";
            }
        }
        for (int i = 0; i < jumlahSoal; i++) {
            kunci[0][i] = "";
        }

        kompetensi[0] = inputPanel.getLabKompetensi().getText();
        jmlSoal[0] = inputPanel.getLabSoal().getText();
        jmlSiswa[0] = inputPanel.getLabJumlahSiswa().getText();
        tipeSoal[0] = String.valueOf(inputPanel.getRadio());
        kkm[0] = inputPanel.getLabKKM().getText();

        setKompetensi(kompetensi);
        setJmlSiswa(jmlSiswa);
        setJmlSoal(jmlSoal);
        setTipeSoal(tipeSoal);
        setKKM(kkm);
        setKunci(kunci);
        setSoal(soal);
        setBerkasAktif(0);

        model.fireNewDocument();
    }

    public void saveDocument() {
        model.fireSaveDocument();
    }

    public void loadDocument() {
        model.fireLoadDocument();
    }

    public void openingFile(String path) {
        configReader.setModel(model);
        configReader.readConfig(path);
        int jumlahBerkas = model.getJumlahBerkas();
        if (jumlahBerkas > 0) {
            model.setKompetensi(new String[jumlahBerkas]);
            model.setJmlSiswa(new String[jumlahBerkas]);
            model.setJmlSoal(new String[jumlahBerkas]);
            model.setTipeSoal(new String[jumlahBerkas]);
            model.setKKM(new String[jumlahBerkas]);
            model.setKunci(new String[jumlahBerkas][]);
            model.setSoal(new String[jumlahBerkas][][]);

            List<Item> readConfig = itemReader.readConfig(path);

            for (Item item : readConfig) {
                int i = Integer.parseInt(item.getId());
                model.getKompetensi()[i] = item.getKompetensi();
                model.getJmlSiswa()[i] = item.getJumlahSiswa();
                model.getJmlSoal()[i] = item.getJumlahSoal();
                model.getTipeSoal()[i] = item.getTipe();
                model.getKKM()[i] = item.getKkm();
                model.getKunci()[i] = item.getKunci();
                model.getSoal()[i] = item.getSoal();
            }

            model.fireNewDocument();
        } else {
            model.fireErrorMessage(0);
        }
    }

    public void keyLoad() {
        model.fireKeyLoad();
    }

    public void ansLoad() {
        model.fireAnsLoad();
    }

    public void resLoad() {
        model.fireResLoad();
    }

    public void configLoad() {
        model.fireConfigurationLoad();
    }

    public void setKunci(KeyPanel panel) {
        if (panel != null) {
            //model.setKunci(new String[model.getJumlahBerkas()][Integer.parseInt(model.getJmlSoal()[model.getBerkasAktif()])]);
            JTable table = panel.getTable();
            for (int i = 0; i < Integer.parseInt(model.getJmlSoal()[model.getBerkasAktif()]); i++) {
                model.getKunci()[model.getBerkasAktif()][i] = table.getValueAt(i, 0).toString().toUpperCase();
            }
        }
    }

    public void setSoal(AnsPanel panel) {
        if (panel != null) {
            //model.setSoal(new String[model.getJumlahBerkas()][Integer.parseInt(model.getJmlSiswa()[model.getBerkasAktif()])][Integer.parseInt(model.getJmlSoal()[model.getBerkasAktif()]) + 1]);
            JTable table = panel.getTable();
            for (int i = 0; i < Integer.parseInt(model.getJmlSiswa()[model.getBerkasAktif()]); i++) {
                for (int j = 0; j <= Integer.parseInt(model.getJmlSoal()[model.getBerkasAktif()]); j++) {
                    model.getSoal()[model.getBerkasAktif()][i][j] = table.getValueAt(i, j).toString().toUpperCase();
                }
            }
        }
    }

    //LIST OF SETTERS. PLEASE KEEP IT HERE.
    public void setModel(MainModel model) {
        this.model = model;
    }

    public void setJumlahBerkas(int jumlah) {
        model.setJumlahBerkas(jumlah);
    }

    public void setBerkasAktif(int aktif) {
        model.setBerkasAktif(aktif);
    }

    public void setKompetensi(String[] kompetensi) {
        model.setKompetensi(kompetensi);
    }

    public void setJmlSiswa(String[] jmlSiswa) {
        model.setJmlSiswa(jmlSiswa);
    }

    public void setJmlSoal(String[] jmlSoal) {
        model.setJmlSoal(jmlSoal);
    }

    public void setTipeSoal(String[] tipeSoal) {
        model.setTipeSoal(tipeSoal);
    }

    public void setMapel(String mapel) {
        model.setMaPel(mapel);
    }

    public void setNamaKelas(String namakelas) {
        model.setNamaKelas(namakelas);
    }

    public void setNamaGuru(String namaGuru) {
        model.setNamaGuru(namaGuru);
    }

    public void setKKM(String[] kkm) {
        model.setKKM(kkm);
    }

    public void setSoal(String[][][] soal) {
        model.setSoal(soal);
    }

    public void setKunci(String[][] kunci) {
        model.setKunci(kunci);
    }
}
