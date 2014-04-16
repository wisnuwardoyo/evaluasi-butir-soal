/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wisnu.ebs.add;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Wisnu Wardoyo
 */
public class DateChecker {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static Date now, limit;
    
    protected static void initComponents(){
        try {
            now = sdf.parse(sdf.format(new Date()));
            limit = sdf.parse("2014-5-13");
        } catch (ParseException ex) {
            
        }
    }
    
    public static boolean isOutOfDate() {
        if(now == null || limit == null){
            initComponents();
        }
        return now.after(limit);
    }
}
