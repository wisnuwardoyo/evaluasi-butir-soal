/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wisnu.ebs.xml;

import com.wisnu.ebs.model.MainModel;
import java.io.*;

public class WriteXMLFile {

    private int jumlahTest;

    private MainModel model;

    public void write(String path) {

        try {
            BufferedWriter printer = new BufferedWriter(new FileWriter(path + ".xml"));
            printer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
            printer.newLine();
            printer.write("<root>");
            printer.newLine();

            printer.write("<config>");
            printer.newLine();

            printer.write("<mapel>");
            printer.write(model.getMaPel());
            printer.write("</mapel>");
            printer.newLine();

            printer.write("<guru>");
            printer.write(model.getNamaGuru());
            printer.write("</guru>");
            printer.newLine();

            printer.write("<berkas>");
            printer.write(String.valueOf(model.getJumlahBerkas()));
            printer.write("</berkas>");
            printer.newLine();

            printer.write("<kelas>");
            printer.write(model.getNamaKelas());
            printer.write("</kelas>");
            printer.newLine();

            printer.write("</config>");
            printer.newLine();

            for (int i = 0; i < model.getJumlahBerkas(); i++) {
                printer.write("<item id=\"" + (i) + "\">");
                printer.newLine();

                printer.write("<kompetensi>");
                printer.write(model.getKompetensi()[i]);
                printer.write("</kompetensi>");
                printer.newLine();

                printer.write("<siswa>");
                printer.write(model.getJmlSiswa()[i]);
                printer.write("</siswa>");
                printer.newLine();

                printer.write("<jmlSoal>");
                printer.write(model.getJmlSoal()[i]);
                printer.write("</jmlSoal>");
                printer.newLine();

                printer.write("<tipe>");
                printer.write(model.getTipeSoal()[i]);
                printer.write("</tipe>");
                printer.newLine();

                printer.write("<kkm>");
                printer.write(model.getKKM()[i]);
                printer.write("</kkm>");
                printer.newLine();

                for (int j = 0; j < model.getKunci()[i].length; j++) {
                    printer.write("<kunci>");
                    printer.write(model.getKunci()[i][j].equals("") ? "?" : model.getKunci()[i][j]);
                    printer.write("</kunci>");
                    printer.newLine();
                }

                for (int k = 0; k < model.getSoal()[i].length; k++) {
                    for (int l = 0; l < model.getSoal()[i][k].length; l++) {
                        printer.write("<soal>");
                        printer.write(model.getSoal()[i][k][l].equals("") ? "?" : model.getSoal()[i][k][l]);
                        printer.write("</soal>");
                        printer.newLine();
                    }
                }

                printer.write("</item>");
                printer.newLine();

            }

            printer.write("</root>");
            printer.newLine();

            printer.close();

            System.out.println("Saving File to : " + path + ".xml");

            //return read.read();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void setModel(MainModel model) {
        this.model = model;
    }

    
}
