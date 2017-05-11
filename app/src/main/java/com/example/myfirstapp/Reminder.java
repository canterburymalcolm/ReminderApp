package com.example.myfirstapp;

import java.util.Calendar;

/**
 * Created by malcolm on 5/9/2017.
 */

public class Reminder {

    private Calendar dateDue;
    private String title;
    private String description;

    public Reminder(Calendar dateDue, String title, String description){
        this.dateDue = dateDue;
        this.title = title;
        this.description = description;
    }

    public Reminder(){

    }

    public boolean isGreaterThan(Reminder reminder){
        if (reminder == null){
            return false;
        }
        return reminder.getDateDue().compareTo(dateDue) < 0;
    }

    public String getFormattedDate(){
        int month = dateDue.get(Calendar.MONTH);      // 0 to 11
        int day = dateDue.get(Calendar.DAY_OF_MONTH);
        int hour = dateDue.get(Calendar.HOUR);
        int minute = dateDue.get(Calendar.MINUTE);

        return String.format("\n%02d/%02d at %02d:%02d", month+1, day, hour, minute);
    }

    public Calendar getDateDue(){
        return dateDue;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public void setDateDue(Calendar dateDue){
        this.dateDue = dateDue;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;
    }
}
