package com.xyram.ticketingTool.entity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateValidatorUsingDateFormat implements DateValidator {
    private String dateFormat;

    public DateValidatorUsingDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public boolean isValid(String dateStr) {
        DateFormat sdf = new SimpleDateFormat(this.dateFormat);
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
    
    public static Date compareDates(String date) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = inputFormat.parse(date);
            return date1;
        } catch (Exception e) {

        }
        return null;
    }
    
    public static boolean isValidated(String date1, String date2) {
        boolean isValidated = false;
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date11 = inputFormat.parse(date1);
            Date date22 = inputFormat.parse(date2);
            if (date11.compareTo(date22) >0) {
                isValidated = false;
            } else {
                isValidated = true;
            }
        } catch (Exception e) {
        }
                 return isValidated;
    }
    }