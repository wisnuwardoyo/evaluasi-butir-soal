package com.wisnu.ebs.model.impl;

import com.wisnu.ebs.event.MainListener;
import com.wisnu.ebs.model.Database;
import com.wisnu.ebs.view.AnsPanel;
import com.wisnu.ebs.view.KeyPanel;
import com.wisnu.ebs.view.NewDocumentPanel;
import javax.swing.JTable;

/**
 *
 * @author Wisnu Wardoyo <mas.wisnu99@gmail.com>
 */
public class DatabaseImpl implements Database {

    private String[][] key;
    private String[][][] studentsAnswer;
    private String[] competency;
    private String[] minimumPassValue;
    private String[] studentsCount;
    private String[] itemCount;
    private String[] itemType;

    private String subject;
    private String teacherName;
    private String className;
    private int fileCount;
    private int currentlySelectedItem;

    private MainListener mainListener;

    public DatabaseImpl() {

    }

    @Override
    public void newDocument(NewDocumentPanel panel) {
        NewDocumentPanel inputPanel = panel;

        setSubject(inputPanel.getLabMaPel().getText());
        setTeacherName(inputPanel.getLabGuru().getText());
        setClassName(inputPanel.getLabKelas().getText());
        setFileCount(1);

        int jumlahSiswa = Integer.parseInt(inputPanel.getLabJumlahSiswa().getText());
        int jumlahSoal = Integer.parseInt(inputPanel.getLabSoal().getText());

        setCompetency(new String[1]);
        setItemCount(new String[1]);
        setStudentsCount(new String[1]);
        setItemType(new String[1]);
        setMinimumPassValue(new String[1]);
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

        competency[0] = inputPanel.getLabKompetensi().getText();
        itemCount[0] = inputPanel.getLabSoal().getText();
        studentsCount[0] = inputPanel.getLabJumlahSiswa().getText();
        itemType[0] = String.valueOf(inputPanel.getRadio());
        minimumPassValue[0] = inputPanel.getLabKKM().getText();
        setKey(kunciBaru);
        setStudentsAnswer(SoalBaru);
        setCurrentlySelectedItem(0);

    }

    @Override
    public void setKey(KeyPanel panel) {
        if (panel != null) {
            JTable table = panel.getTable();
            for (int i = 0; i < Integer.parseInt(getItemCount()[getCurrentlySelectedItem()]); i++) {
                getKey()[getCurrentlySelectedItem()][i] = table.getValueAt(i, 0).toString().toUpperCase();
            }
        }
    }

    @Override
    public void setStudentAnswer(AnsPanel panel) {
        if (panel != null) {
            JTable table = panel.getTable();
            for (int i = 0; i < Integer.parseInt(getStudentsCount()[getCurrentlySelectedItem()]); i++) {
                for (int j = 0; j <= Integer.parseInt(getItemCount()[getCurrentlySelectedItem()]); j++) {
                    getStudentsAnswer()[getCurrentlySelectedItem()][i][j] = table.getValueAt(i, j).toString().toUpperCase();
                }
            }
        }
    }

    @Override
    public void fireErrorMessage(int i,int j,String Error) {
        if (mainListener != null) {
            mainListener.fireErrorMessage(i,j,Error);
        }
    }

    //Border
    @Override
    public void setMainListener(MainListener mainListener) {
        this.mainListener = mainListener;
    }

    @Override
    public String[][] getKey() {
        return key;
    }

    @Override
    public void setKey(String[][] key) {
        this.key = key;
    }

    @Override
    public String[][][] getStudentsAnswer() {
        return studentsAnswer;
    }

    @Override
    public void setStudentsAnswer(String[][][] studentsAnswer) {
        this.studentsAnswer = studentsAnswer;
    }

    @Override
    public String[] getCompetency() {
        return competency;
    }

    @Override
    public void setCompetency(String[] competency) {
        this.competency = competency;
    }

    @Override
    public String[] getMinimumPassValue() {
        return minimumPassValue;
    }

    @Override
    public void setMinimumPassValue(String[] minimumPassValue) {
        this.minimumPassValue = minimumPassValue;
    }

    @Override
    public String[] getStudentsCount() {
        return studentsCount;
    }

    @Override
    public void setStudentsCount(String[] studentsCount) {
        this.studentsCount = studentsCount;
    }

    @Override
    public String[] getItemCount() {
        return itemCount;
    }

    @Override
    public void setItemCount(String[] itemCount) {
        this.itemCount = itemCount;
    }

    @Override
    public String[] getItemType() {
        return itemType;
    }

    @Override
    public void setItemType(String[] itemType) {
        this.itemType = itemType;
    }

    @Override
    public String getSubject() {
        return subject;
    }

    @Override
    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String getTeacherName() {
        return teacherName;
    }

    @Override
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public int getFileCount() {
        return fileCount;
    }

    @Override
    public void setFileCount(int fileCount) {
        this.fileCount = fileCount;
    }

    @Override
    public int getCurrentlySelectedItem() {
        return currentlySelectedItem;
    }

    @Override
    public void setCurrentlySelectedItem(int currentlySelectedItem) {
        this.currentlySelectedItem = currentlySelectedItem;
    }

}
