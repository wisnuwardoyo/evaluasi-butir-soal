/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wisnu.ebs.xml;

import com.wisnu.ebs.model.Database;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class WriteXMLFile {

    private int jumlahTest;

    private Database database;

    public boolean write(String path) {
        BufferedWriter printer = null;
        File file = new File(path + ".rmd\\");
        try {

            printer = new BufferedWriter(new FileWriter(file));
            printer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
            printer.newLine();
            printer.write("<root>");
            printer.newLine();

            printer.write("<config>");
            printer.newLine();

            printer.write("<mapel>");
            printer.write(database.getSubject());
            printer.write("</mapel>");
            printer.newLine();

            printer.write("<guru>");
            printer.write(database.getTeacherName());
            printer.write("</guru>");
            printer.newLine();

            printer.write("<berkas>");
            printer.write(String.valueOf(database.getFileCount()));
            printer.write("</berkas>");
            printer.newLine();

            printer.write("<kelas>");
            printer.write(database.getClassName());
            printer.write("</kelas>");
            printer.newLine();

            printer.write("</config>");
            printer.newLine();

            for (int i = 0; i < database.getFileCount(); i++) {
                printer.write("<item id=\"" + (i) + "\">");
                printer.newLine();

                printer.write("<kompetensi>");
                printer.write(database.getCompetency()[i]);
                printer.write("</kompetensi>");
                printer.newLine();

                printer.write("<siswa>");
                printer.write(database.getStudentsCount()[i]);
                printer.write("</siswa>");
                printer.newLine();

                printer.write("<jmlSoal>");
                printer.write(database.getItemCount()[i]);
                printer.write("</jmlSoal>");
                printer.newLine();

                printer.write("<tipe>");
                printer.write(database.getItemType()[i]);
                printer.write("</tipe>");
                printer.newLine();

                printer.write("<kkm>");
                printer.write(database.getMinimumPassValue()[i]);
                printer.write("</kkm>");
                printer.newLine();

                for (int j = 0; j < database.getKey()[i].length; j++) {
                    printer.write("<kunci>");
                    printer.write(database.getKey()[i][j].equals("") ? "?" : database.getKey()[i][j]);
                    printer.write("</kunci>");
                    printer.newLine();
                }

                for (int k = 0; k < database.getStudentsAnswer()[i].length; k++) {
                    for (int l = 0; l < database.getStudentsAnswer()[i][k].length; l++) {
                        printer.write("<soal>");
                        printer.write(database.getStudentsAnswer()[i][k][l].equals("") ? "?" : database.getStudentsAnswer()[i][k][l]);
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

            System.out.println("Saving File to : " + path + ".rmd");

            return true;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, new Object[]{
                "Maaf anda tidak dapat menyimpan pada folder ini",
                "Akses ke folder ini tidak diizinkan",
                "Silahkan pilih folder lain!"
            });
            return false;
        } finally {
            if (printer != null) {
                try {
                    printer.close();
                } catch (IOException ex) {

                }
            }
        }

    }

    public void setDatabase(Database database) {
        this.database = database;
    }

}
