/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wisnu.ebs.util;

import com.wisnu.ebs.model.Database;
import com.wisnu.ebs.model.FindingResult;
import com.wisnu.ebs.model.impl.DatabaseImpl;
import com.wisnu.ebs.model.impl.FindingResultImpl;

/**
 *
 * @author Wisnu Wardoyo
 */
public class Utilities {

    private static Database database;
    private static FindingResult findingResult;

    public static Database getDatabase() {
        if (database == null) {
            database = new DatabaseImpl();
        }
        return database;
    }

    public static FindingResult getFindingResult() {
        if(findingResult == null){
            findingResult = new FindingResultImpl();
        }
        return findingResult;
    }
    
    
}
