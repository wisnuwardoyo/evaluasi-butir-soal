/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wisnu.ebs.xml;

import com.wisnu.ebs.model.Database;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

public class WriteXMLFile {

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
                
                for (String key : database.getKey()[i]) {
                    printer.write("<kunci>");
                    printer.write(key.equals("") ? "?" : key);
                    printer.write("</kunci>");
                    printer.newLine();
                }
                for (String[] students : database.getStudentsAnswer()[i]) {
                    for (String studentsAnswer : students) {
                        printer.write("<soal>");
                        printer.write(studentsAnswer.equals("") ? "?" : studentsAnswer);
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
