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

/**
 *
 * @author Wisnu Wardoyo <mas.wisnu99@gmail.com>
 */
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
                database.setSubject(temp);
                break;
            case 2:
                database.setTeacherName(temp);
                break;
            case 3:
                database.setClassName(temp);
                break;
            default:
                break;
        }
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public void addFile(NewFilePanel panel) {
        String[][] kunci = database.getKey().clone();
        String[][][] soal = database.getStudentsAnswer().clone();
        String[] kompetensi = database.getCompetency().clone();
        String[] KKM = database.getMinimumPassValue().clone();
        String[] jmlSiswa = database.getStudentsCount().clone();
        String[] jmlSoal = database.getItemCount().clone();
        String[] tipeSoal = database.getItemType().clone();
        int jumlahBerkas = database.getFileCount() + 1;

        initVar(jumlahBerkas);

        int siswaBaru = Integer.parseInt(panel.getLabJumlahSiswa().getText());
        int soalBaru = Integer.parseInt(panel.getLabSoal().getText());

        database.getKey()[jumlahBerkas - 1] = new String[soalBaru];
        database.getStudentsAnswer()[jumlahBerkas - 1] = new String[siswaBaru][soalBaru + 1];

        for (int i = 0; i < jumlahBerkas; i++) {
            if (i == (jumlahBerkas - 1)) {
                database.getCompetency()[i] = panel.getLabKompetensi().getText();
                database.getMinimumPassValue()[i] = panel.getLabKKM().getText();
                database.getItemType()[i] = String.valueOf(panel.getRadio());
                database.getStudentsCount()[i] = panel.getLabJumlahSiswa().getText();
                database.getItemCount()[i] = panel.getLabSoal().getText();
                for (int j = 0; j < soalBaru; j++) {
                    database.getKey()[i][j] = "";
                }
                for (int j = 0; j < siswaBaru; j++) {
                    for (int k = 0; k <= soalBaru; k++) {
                        if (k == 0) {
                            database.getStudentsAnswer()[i][j][k] = "Siswa " + String.valueOf(j + 1);
                        } else {
                            database.getStudentsAnswer()[i][j][k] = "";
                        }
                    }
                }

            } else {
                database.getCompetency()[i] = kompetensi[i];
                database.getMinimumPassValue()[i] = KKM[i];
                database.getStudentsCount()[i] = jmlSiswa[i];
                database.getItemCount()[i] = jmlSoal[i];
                database.getItemType()[i] = tipeSoal[i];
                database.getKey()[i] = kunci[i];
                database.getStudentsAnswer()[i] = soal[i];
            }
        }
        database.setCurrentlySelectedItem(jumlahBerkas - 1);
        controllerUtama.openingConfigurationPanel();
    }

    public void changeFile(int number) {
        database.setCurrentlySelectedItem(number);
        controllerUtama.openingConfigurationPanel();
    }

    public void setAllLab() {
        confPanel.setLabMataPelajaran(database.getSubject());
        confPanel.setLabGuru(database.getTeacherName());
        confPanel.setLabKelas(database.getClassName());
    }

    public void editFile(NewFilePanel panel) {
        int aktif = this.database.getCurrentlySelectedItem();
        panel.setLabKompetensi(this.database.getCompetency()[aktif]);
        panel.setLabKKM(this.database.getMinimumPassValue()[aktif]);
        panel.setLabSiswa(this.database.getStudentsCount()[aktif]);
        panel.setLabSoal(this.database.getItemCount()[aktif]);
        panel.setRadio(this.database.getItemType()[aktif].equals("4") ? 4 : 5);
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
        int aktif = this.database.getCurrentlySelectedItem();
        if (!panel.getLabKompetensi().getText().equals(this.database.getCompetency()[aktif])) {
            this.database.getCompetency()[aktif] = panel.getLabKompetensi().getText();
        }
        if (!panel.getLabKKM().getText().equals(this.database.getMinimumPassValue()[aktif])) {
            this.database.getMinimumPassValue()[aktif] = panel.getLabKKM().getText();
        }
        if (!panel.getLabJumlahSiswa().getText().equals(this.database.getStudentsCount()[aktif])
                || !panel.getLabSoal().getText().equals(this.database.getItemCount()[aktif])) {
            int siswaBaru = Integer.parseInt(panel.getLabJumlahSiswa().getText());
            int soalBaru = Integer.parseInt(panel.getLabSoal().getText());
            int siswaLama = Integer.parseInt(this.database.getStudentsCount()[aktif]);
            int soalLama = Integer.parseInt(this.database.getItemCount()[aktif]);

            String[][] dataSoal = new String[siswaBaru][soalBaru + 1];
            String[] dataKunci = new String[soalBaru];

            //----------Data Soal--------------------//
            if ((siswaBaru == siswaLama && soalBaru > soalLama)
                    || (siswaBaru > siswaLama && soalBaru == soalLama)
                    || (siswaBaru > siswaLama && soalBaru > soalLama)) {
                for (int i = 0; i < siswaLama; i++) {
                    for (int j = 0; j <= soalLama; j++) {
                        dataSoal[i][j] = this.database.getStudentsAnswer()[aktif][i][j];
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
                                dataSoal[i][k] = "Siswa" + String.valueOf(i + 1);
                            } else {
                                dataSoal[i][k] = "";
                            }

                        }
                    }
                }
            } else {
                for (int i = 0; i < siswaBaru; i++) {
                    for (int j = 0; j <= soalBaru; j++) {
                        dataSoal[i][j] = this.database.getStudentsAnswer()[aktif][i][j];
                    }
                }
            }

            //---------Data Kunci-----------------//
            if (soalBaru > soalLama) {
                for (int i = 0; i < soalBaru; i++) {
                    if (i < soalLama) {
                        dataKunci[i] = this.database.getKey()[aktif][i];
                    } else {
                        dataKunci[i] = "";
                    }
                }
            } else {
                for (int i = 0; i < soalBaru; i++) {
                    dataKunci[i] = this.database.getKey()[aktif][i];
                }
            }

            //-------------Setting UP-----------//
            this.database.getStudentsAnswer()[aktif] = new String[siswaBaru][soalBaru + 1];
            this.database.getStudentsAnswer()[aktif] = dataSoal;
            this.database.getStudentsCount()[aktif] = panel.getLabJumlahSiswa().getText();
            this.database.getItemCount()[aktif] = panel.getLabSoal().getText();

        }
        if (panel.getRadio() != Integer.parseInt(this.database.getItemType()[aktif])) {
            this.database.getItemType()[aktif] = String.valueOf(panel.getRadio());
        }
    }

    public void deleteData() {
        int aktif = this.database.getCurrentlySelectedItem();

        int result = JOptionPane.showConfirmDialog(null,
                "Apakah anda yakin akan menghapus data?\n\n"
                + "Kompetensi \t: " + this.database.getCompetency()[aktif] + "\n"
                + "KKM \t: " + this.database.getMinimumPassValue()[aktif] + "\n"
                + "Siswa \t: " + this.database.getStudentsCount()[aktif] + "\n"
                + "Soal \t: " + this.database.getItemCount()[aktif] + "\n"
                + "Tipe \t: " + (this.database.getItemType()[aktif].equals("4") ? "A,B,C,D" : "A,B,C,D,E") + "\n",
                "Peringatan", JOptionPane.OK_OPTION);

        if (result == 0 && database.getFileCount() > 1) {
            int jumlahBerkas = database.getFileCount() - 1;
            String[][] kunci = database.getKey().clone();
            String[][][] soal = database.getStudentsAnswer().clone();
            String[] kompetensi = database.getCompetency().clone();
            String[] KKM = database.getMinimumPassValue().clone();
            String[] jmlSiswa = database.getStudentsCount().clone();
            String[] jmlSoal = database.getItemCount().clone();
            String[] tipeSoal = database.getItemType().clone();

            initVar(jumlahBerkas);

            int j = 0;
            for (int i = 0; i <= jumlahBerkas; i++) {
                if (i != aktif) {
                    database.getCompetency()[j] = kompetensi[i];
                    database.getMinimumPassValue()[j] = KKM[i];
                    database.getStudentsCount()[j] = jmlSiswa[i];
                    database.getItemCount()[j] = jmlSoal[i];
                    database.getItemType()[j] = tipeSoal[i];
                    database.getKey()[j] = kunci[i];
                    database.getStudentsAnswer()[j] = soal[i];
                    j++;
                } else {

                }

            }
            database.setCurrentlySelectedItem(0);
            controllerUtama.openingConfigurationPanel();

        } else if (result == 0 && database.getFileCount() <= 1) {

        }
    }

    public void initVar(int jumlahBerkas) {
        database.setKey(new String[jumlahBerkas][]);
        database.setStudentsAnswer(new String[jumlahBerkas][][]);
        database.setCompetency(new String[jumlahBerkas]);
        database.setMinimumPassValue(new String[jumlahBerkas]);
        database.setStudentsCount(new String[jumlahBerkas]);
        database.setItemCount(new String[jumlahBerkas]);
        database.setItemType(new String[jumlahBerkas]);
        database.setFileCount(jumlahBerkas);
    }

}
