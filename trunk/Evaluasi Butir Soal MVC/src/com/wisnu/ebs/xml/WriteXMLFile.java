/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wisnu.ebs.xml;

import com.wisnu.ebs.model.Database;
import java.io.*;

public class WriteXMLFile {

    private int jumlahTest;

    private Database database;

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
            printer.write(database.getMaPel());
            printer.write("</mapel>");
            printer.newLine();

            printer.write("<guru>");
            printer.write(database.getNamaGuru());
            printer.write("</guru>");
            printer.newLine();

            printer.write("<berkas>");
            printer.write(String.valueOf(database.getJumlahBerkas()));
            printer.write("</berkas>");
            printer.newLine();

            printer.write("<kelas>");
            printer.write(database.getNamaKelas());
            printer.write("</kelas>");
            printer.newLine();

            printer.write("</config>");
            printer.newLine();

            for (int i = 0; i < database.getJumlahBerkas(); i++) {
                printer.write("<item id=\"" + (i) + "\">");
                printer.newLine();

                printer.write("<kompetensi>");
                printer.write(database.getKompetensi()[i]);
                printer.write("</kompetensi>");
                printer.newLine();

                printer.write("<siswa>");
                printer.write(database.getJmlSiswa()[i]);
                printer.write("</siswa>");
                printer.newLine();

                printer.write("<jmlSoal>");
                printer.write(database.getJmlSoal()[i]);
                printer.write("</jmlSoal>");
                printer.newLine();

                printer.write("<tipe>");
                printer.write(database.getTipeSoal()[i]);
                printer.write("</tipe>");
                printer.newLine();

                printer.write("<kkm>");
                printer.write(database.getKKM()[i]);
                printer.write("</kkm>");
                printer.newLine();

                for (int j = 0; j < database.getKunci()[i].length; j++) {
                    printer.write("<kunci>");
                    printer.write(database.getKunci()[i][j].equals("") ? "?" : database.getKunci()[i][j]);
                    printer.write("</kunci>");
                    printer.newLine();
                }

                for (int k = 0; k < database.getSoal()[i].length; k++) {
                    for (int l = 0; l < database.getSoal()[i][k].length; l++) {
                        printer.write("<soal>");
                        printer.write(database.getSoal()[i][k][l].equals("") ? "?" : database.getSoal()[i][k][l]);
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
            
        }

    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    
}
