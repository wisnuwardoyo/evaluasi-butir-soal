/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.wisnu.ebs.model;

import com.wisnu.ebs.event.MainListener;
import com.wisnu.ebs.view.AnsPanel;
import com.wisnu.ebs.view.KeyPanel;
import com.wisnu.ebs.view.NewDocumentPanel;

/**
 *
 * @author Wisnu Wardoyo
 */
public interface Database {

    void fireErrorMessage(int i, int j, String Error);

    String getClassName();

    String[] getCompetency();

    int getCurrentlySelectedItem();

    int getFileCount();

    String[] getItemCount();

    String[] getItemType();

    String[][] getKey();

    String[] getMinimumPassValue();

    String[][][] getStudentsAnswer();

    String[] getStudentsCount();

    String getSubject();

    String getTeacherName();

    void newDocument(NewDocumentPanel panel);

    void setClassName(String className);

    void setCompetency(String[] competency);

    void setCurrentlySelectedItem(int currentlySelectedItem);

    void setFileCount(int fileCount);

    void setItemCount(String[] itemCount);

    void setItemType(String[] itemType);

    void setKey(KeyPanel panel);

    void setKey(String[][] key);

    //Border
    void setMainListener(MainListener mainListener);

    void setMinimumPassValue(String[] minimumPassValue);

    void setStudentAnswer(AnsPanel panel);

    void setStudentsAnswer(String[][][] studentsAnswer);

    void setStudentsCount(String[] studentsCount);

    void setSubject(String subject);

    void setTeacherName(String teacherName);
    
}
