/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wisnu.ebs.controller;

import com.wisnu.ebs.model.Database;
import com.wisnu.ebs.view.ConfPanel;
import com.wisnu.ebs.view.NewFilePanel;
import javax.swing.JOptionPane;

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

    public void addFile(NewFilePanel panel) {
        String[][] kunci = database.getKunci().clone();
        String[][][] soal = database.getSoal().clone();
        String[] kompetensi = database.getKompetensi().clone();
        String[] KKM = database.getKKM().clone();
        String[] jmlSiswa = database.getJmlSiswa().clone();
        String[] jmlSoal = database.getJmlSoal().clone();
        String[] tipeSoal = database.getTipeSoal().clone();
        int jumlahBerkas = database.getJumlahBerkas() + 1;

        initVar(jumlahBerkas);

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
        controllerUtama.openingConfigurationPanel();
    }

    public void setAllLab() {
        confPanel.setLabMataPelajaran(database.getMaPel());
        confPanel.setLabGuru(database.getNamaGuru());
        confPanel.setLabKelas(database.getNamaKelas());
    }

    public void editFile(NewFilePanel panel) {
        int aktif = this.database.getBerkasAktif();
        panel.setLabKompetensi(this.database.getKompetensi()[aktif]);
        panel.setLabKKM(this.database.getKKM()[aktif]);
        panel.setLabSiswa(this.database.getJmlSiswa()[aktif]);
        panel.setLabSoal(this.database.getJmlSoal()[aktif]);
        panel.setRadio(this.database.getTipeSoal()[aktif].equals("4") ? 4 : 5);
        panel.setTitle();
        int result = JOptionPane.showConfirmDialog(null, panel, "Edit File", JOptionPane.YES_NO_OPTION);
        if (result == 0) {
            if (panel.itemCheck()) {
                editFileAction(panel);
                try {
                    controllerUtama.openingConfigurationPanel();
                } finally {
                    int save = JOptionPane.showConfirmDialog(null, "Dokumen berhasil diubah"
                            + "\nApakah Anda akan menyimpannya?", "Warning", JOptionPane.OK_OPTION);
                    if (save == 0) {
                        controllerUtama.saveDocumentAction(controllerUtama.getPath().replace(".xml", "").replace(".XML", ""));
                    }
                }
            }
        }
    }

    public void editFileAction(NewFilePanel panel) {
        int aktif = this.database.getBerkasAktif();
        if (!panel.getLabKompetensi().getText().equals(this.database.getKompetensi()[aktif])) {
            this.database.getKompetensi()[aktif] = panel.getLabKompetensi().getText();
        }
        if (!panel.getLabKKM().getText().equals(this.database.getKKM()[aktif])) {
            this.database.getKKM()[aktif] = panel.getLabKKM().getText();
        }
        if (!panel.getLabJumlahSiswa().getText().equals(this.database.getJmlSiswa()[aktif])
                || !panel.getLabSoal().getText().equals(this.database.getJmlSoal()[aktif])) {
            int siswaBaru = Integer.parseInt(panel.getLabJumlahSiswa().getText());
            int soalBaru = Integer.parseInt(panel.getLabSoal().getText());
            int siswaLama = Integer.parseInt(this.database.getJmlSiswa()[aktif]);
            int soalLama = Integer.parseInt(this.database.getJmlSoal()[aktif]);

            String[][] dataSoal = new String[siswaBaru][soalBaru + 1];
            String[] dataKunci = new String[soalBaru];

            //----------Data Soal--------------------//
            if ((siswaBaru == siswaLama && soalBaru > soalLama)
                    || (siswaBaru > siswaLama && soalBaru == soalLama)
                    || (siswaBaru > siswaLama && soalBaru > soalLama)) {
                for (int i = 0; i < siswaLama; i++) {
                    for (int j = 0; j <= soalLama; j++) {
                        dataSoal[i][j] = this.database.getSoal()[aktif][i][j];
                    }
                }

                for (int i = 0; i < siswaBaru; i++) {
                    if (i < siswaLama) {
                        for (int j = soalLama + 1; j <= soalBaru; j++) {
                            dataSoal[i][j] = "";
                        }
                    } else {
                        for (int k = 0; k <= soalBaru; k++) {
                            if (k == 0) {
                                dataSoal[i][k] = "Siswa" + String.valueOf(i+1);
                            } else {
                                dataSoal[i][k] = "";
                            }

                        }
                    }
                }
            } else {
                for (int i = 0; i < siswaBaru; i++) {
                    for (int j = 0; j <= soalBaru; j++) {
                        dataSoal[i][j] = this.database.getSoal()[aktif][i][j];
                    }
                }
            }

            //---------Data Kunci-----------------//
            if (soalBaru > soalLama) {
                for (int i = 0; i < soalBaru; i++) {
                    if (i < soalLama) {
                        dataKunci[i] = this.database.getKunci()[aktif][i];
                    } else {
                        dataKunci[i] = "";
                    }
                }
            } else {
                for (int i = 0; i < soalBaru; i++) {
                    dataKunci[i] = this.database.getKunci()[aktif][i];
                }
            }

            //-------------Setting UP-----------//
            this.database.getSoal()[aktif] = new String[siswaBaru][soalBaru + 1];
            this.database.getSoal()[aktif] = dataSoal;
            this.database.getJmlSiswa()[aktif] = panel.getLabJumlahSiswa().getText();
            this.database.getJmlSoal()[aktif] = panel.getLabSoal().getText();

        }
        if (panel.getRadio() != Integer.parseInt(this.database.getTipeSoal()[aktif])) {
            this.database.getTipeSoal()[aktif] = String.valueOf(panel.getRadio());
        }
    }

    public void deleteData() {
        int aktif = this.database.getBerkasAktif();

        int result = JOptionPane.showConfirmDialog(null,
                "Apakah anda yakin akan menghapus data?\n\n"
                + "Kompetensi \t: " + this.database.getKompetensi()[aktif] + "\n"
                + "KKM \t: " + this.database.getKKM()[aktif] + "\n"
                + "Siswa \t: " + this.database.getJmlSiswa()[aktif] + "\n"
                + "Soal \t: " + this.database.getJmlSoal()[aktif] + "\n"
                + "Tipe \t: " + (this.database.getTipeSoal()[aktif].equals("4") ? "A,B,C,D" : "A,B,C,D,E") + "\n",
                "Peringatan", JOptionPane.OK_OPTION);

        if (result == 0 && database.getJumlahBerkas() > 1) {
            int jumlahBerkas = database.getJumlahBerkas() - 1;
            String[][] kunci = database.getKunci().clone();
            String[][][] soal = database.getSoal().clone();
            String[] kompetensi = database.getKompetensi().clone();
            String[] KKM = database.getKKM().clone();
            String[] jmlSiswa = database.getJmlSiswa().clone();
            String[] jmlSoal = database.getJmlSoal().clone();
            String[] tipeSoal = database.getTipeSoal().clone();

            initVar(jumlahBerkas);

            int j = 0;
            for (int i = 0; i <= jumlahBerkas; i++) {
                if (i != aktif) {
                    database.getKompetensi()[j] = kompetensi[i];
                    database.getKKM()[j] = KKM[i];
                    database.getJmlSiswa()[j] = jmlSiswa[i];
                    database.getJmlSoal()[j] = jmlSoal[i];
                    database.getTipeSoal()[j] = tipeSoal[i];
                    database.getKunci()[j] = kunci[i];
                    database.getSoal()[j] = soal[i];
                    j++;
                } else {

                }

            }
            database.setBerkasAktif(0);
            controllerUtama.openingConfigurationPanel();

        } else if (result == 0 && database.getJumlahBerkas() <= 1) {

        }
    }

    public void initVar(int jumlahBerkas) {
        database.setKunci(new String[jumlahBerkas][]);
        database.setSoal(new String[jumlahBerkas][][]);
        database.setKompetensi(new String[jumlahBerkas]);
        database.setKKM(new String[jumlahBerkas]);
        database.setJmlSiswa(new String[jumlahBerkas]);
        database.setJmlSoal(new String[jumlahBerkas]);
        database.setTipeSoal(new String[jumlahBerkas]);
        database.setJumlahBerkas(jumlahBerkas);
    }

}
