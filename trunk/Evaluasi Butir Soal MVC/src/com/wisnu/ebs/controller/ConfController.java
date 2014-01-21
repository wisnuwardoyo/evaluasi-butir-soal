/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wisnu.ebs.controller;

import com.wisnu.ebs.model.Database;
import com.wisnu.ebs.view.ConfPanel;
import com.wisnu.ebs.view.NewFilePanel;

public class ConfController {

    private Database database;
    private ConfPanel confPanel;
    private MainController controllerUtama;

    public void setControllerUtama(MainController controllerUtama) {
        this.controllerUtama = controllerUtama;
    }

    public void setConfPanel(ConfPanel confPanel) {
        this.confPanel = confPanel;
    }

    public void changeProperty(int code, String temp) {
        switch (code) {
            case 1:
                database.setMaPel(temp);
                break;
            case 2:
                database.setNamaGuru(temp);
                break;
            case 3:
                database.setNamaKelas(temp);
                break;
            default:
                break;
        }
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public void tambahBerkas(NewFilePanel panel) {
        String[][] kunci = database.getKunci().clone();
        String[][][] soal = database.getSoal().clone();
        String[] kompetensi = database.getKompetensi().clone();
        String[] KKM = database.getKKM().clone();
        String[] jmlSiswa = database.getJmlSiswa().clone();
        String[] jmlSoal = database.getJmlSoal().clone();
        String[] tipeSoal = database.getTipeSoal().clone();
        int jumlahBerkas = database.getJumlahBerkas() + 1;

        database.setKunci(new String[jumlahBerkas][]);
        database.setSoal(new String[jumlahBerkas][][]);
        database.setKompetensi(new String[jumlahBerkas]);
        database.setKKM(new String[jumlahBerkas]);
        database.setJmlSiswa(new String[jumlahBerkas]);
        database.setJmlSoal(new String[jumlahBerkas]);
        database.setTipeSoal(new String[jumlahBerkas]);
        database.setJumlahBerkas(jumlahBerkas);

        int siswaBaru = Integer.parseInt(panel.getLabJumlahSiswa().getText());
        int soalBaru = Integer.parseInt(panel.getLabSoal().getText());

        database.getKunci()[jumlahBerkas - 1] = new String[soalBaru];
        database.getSoal()[jumlahBerkas - 1] = new String[siswaBaru][soalBaru + 1];

        for (int i = 0; i < jumlahBerkas; i++) {
            if (i == (jumlahBerkas - 1)) {
                database.getKompetensi()[i] = panel.getLabKompetensi().getText();
                database.getKKM()[i] = panel.getLabKKM().getText();
                database.getTipeSoal()[i] = String.valueOf(panel.getRadio());
                database.getJmlSiswa()[i] = panel.getLabJumlahSiswa().getText();
                database.getJmlSoal()[i] = panel.getLabSoal().getText();
                for (int j = 0; j < soalBaru; j++) {
                    database.getKunci()[i][j] = "";
                }
                for (int j = 0; j < siswaBaru; j++) {
                    for (int k = 0; k <= soalBaru; k++) {
                        if (k == 0) {
                            database.getSoal()[i][j][k] = "Siswa " + String.valueOf(j + 1);
                        } else {
                            database.getSoal()[i][j][k] = "";
                        }
                    }
                }

            } else {
                database.getKompetensi()[i] = kompetensi[i];
                database.getKKM()[i] = KKM[i];
                database.getJmlSiswa()[i] = jmlSiswa[i];
                database.getJmlSoal()[i] = jmlSoal[i];
                database.getTipeSoal()[i] = tipeSoal[i];
                database.getKunci()[i] = kunci[i];
                database.getSoal()[i] = soal[i];
            }
        }
        database.setBerkasAktif(jumlahBerkas - 1);
        controllerUtama.openingConfigurationPanel();
    }

    public void changeFile(int number) {
        database.setBerkasAktif(number);
    }

    public void setAllLab() {
        confPanel.setLabMataPelajaran(database.getMaPel());
        confPanel.setLabGuru(database.getNamaGuru());
        confPanel.setLabKelas(database.getNamaKelas());
    }
}
