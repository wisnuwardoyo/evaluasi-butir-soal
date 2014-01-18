/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wisnu.ebs.controller;

import com.wisnu.ebs.model.MainModel;
import com.wisnu.ebs.view.ConfPanel;
import com.wisnu.ebs.view.NewFilePanel;

public class ConfController {

    private MainModel model;
    private ConfPanel panel;

    public void changeProperty(int code, String temp) {
        switch (code) {
            case 1:
                model.setMaPel(temp);
                break;
            case 2:
                model.setNamaGuru(temp);
                break;
            case 3:
                model.setNamaKelas(temp);
                break;
            default:
                break;
        }
    }

    public void setModel(MainModel model) {
        this.model = model;
    }

    public void setPanel(ConfPanel panel) {
        this.panel = panel;
    }

    public void onLoad() {
        model.fireOnLoad();
    }

    public void setBerkasAktif(int aktif) {
        model.setBerkasAktif(aktif);
    }

    public void tambahBerkas(NewFilePanel panel) {
        String[][] kunci = model.getKunci().clone();
        String[][][] soal = model.getSoal().clone();
        String[] kompetensi = model.getKompetensi().clone();
        String[] KKM = model.getKKM().clone();
        String[] jmlSiswa = model.getJmlSiswa().clone();
        String[] jmlSoal = model.getJmlSoal().clone();
        String[] tipeSoal = model.getTipeSoal().clone();
        int jumlahBerkas = model.getJumlahBerkas() + 1;

        model.setKunci(new String[jumlahBerkas][]);
        model.setSoal(new String[jumlahBerkas][][]);
        model.setKompetensi(new String[jumlahBerkas]);
        model.setKKM(new String[jumlahBerkas]);
        model.setJmlSiswa(new String[jumlahBerkas]);
        model.setJmlSoal(new String[jumlahBerkas]);
        model.setTipeSoal(new String[jumlahBerkas]);
        model.setJumlahBerkas(jumlahBerkas);

        int siswaBaru = Integer.parseInt(panel.getLabJumlahSiswa().getText());
        int soalBaru = Integer.parseInt(panel.getLabSoal().getText());

        model.getKunci()[jumlahBerkas - 1] = new String[soalBaru];
        model.getSoal()[jumlahBerkas - 1] = new String[siswaBaru][soalBaru + 1];

        for (int i = 0; i < jumlahBerkas; i++) {
            if (i == (jumlahBerkas - 1)) {
                model.getKompetensi()[i] = panel.getLabKompetensi().getText();
                model.getKKM()[i] = panel.getLabKKM().getText();
                model.getTipeSoal()[i] = String.valueOf(panel.getRadio());
                model.getJmlSiswa()[i] = panel.getLabJumlahSiswa().getText();
                model.getJmlSoal()[i] = panel.getLabSoal().getText();
                for (int j = 0; j < soalBaru; j++) {
                    model.getKunci()[i][j] = "";
                }
                for (int j = 0; j < siswaBaru; j++) {
                    for (int k = 0; k <= soalBaru; k++) {
                        if (k == 0) {
                            model.getSoal()[i][j][k] = "Siswa " + String.valueOf(j + 1);
                        } else {
                            model.getSoal()[i][j][k] = "";
                        }
                    }
                }

            } else {
                model.getKompetensi()[i] = kompetensi[i];
                model.getKKM()[i] = KKM[i];
                model.getJmlSiswa()[i] = jmlSiswa[i];
                model.getJmlSoal()[i] = jmlSoal[i];
                model.getTipeSoal()[i] = tipeSoal[i];
                model.getKunci()[i] = kunci[i];
                model.getSoal()[i] = soal[i];
            }
        }
        model.setBerkasAktif(jumlahBerkas - 1);
        model.fireConfigurationLoad();

    }

    public void fireNewFile() {
        model.fireNewFile();
    }
}
