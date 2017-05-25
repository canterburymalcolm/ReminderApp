package com.example.myfirstapp;

import java.util.Calendar;

/**
 * Created by malcolm on 5/9/2017.
 */

public class Reminder {

    private int pk;
    private String reminder_time;
    private String title;
    private String description;

    public Reminder(String title, String description){
        this.title = title;
        this.description = description;
    }

    public Reminder(){

    }

    public boolean isGreaterThan(Reminder reminder){
        if (reminder == null){
            return false;
        }
        return reminder.getDateDue().compareTo(getDateDue()) < 0;
    }

    public int getPk(){
        return pk;
    }

    public void setPk(int pk){
        this.pk = pk;
    }

    public String getFormattedDate(){
        Calendar dateDue = getDateDue();
        int month = dateDue.get(Calendar.MONTH);      // 0 to 11
        int day = dateDue.get(Calendar.DAY_OF_MONTH);
        int hour = dateDue.get(Calendar.HOUR_OF_DAY);
        int minute = dateDue.get(Calendar.MINUTE);

        return String.format("\n%02d/%02d at %02d:%02d", month+1, day, hour, minute);
    }

    public Calendar getDateDue(){
        //{"dateDue":{"year":2017,"month":4,"dayOfMonth":12,"hourOfDay":20,"minute":10,"second":4},"description":"","title":"a"}
        //{"title":"first development server api request","reminder_time":"2017-05-09T23:34:54.505639Z","description":"some random text"}
        Calendar dateDue = Calendar.getInstance();
        dateDue.set(Calendar.YEAR, Integer.parseInt(reminder_time.substring(0, 4)));
        dateDue.set(Calendar.MONTH, Integer.parseInt(reminder_time.substring(5, 7)));
        dateDue.set(Calendar.DAY_OF_MONTH, Integer.parseInt(reminder_time.substring(8, 10)));
        dateDue.set(Calendar.HOUR_OF_DAY, Integer.parseInt(reminder_time.substring(11, 13)));
        dateDue.set(Calendar.MINUTE, Integer.parseInt(reminder_time.substring(14, 16)));
        dateDue.set(Calendar.SECOND, 0);
        return dateDue;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public void setDateDue(Calendar dateDue){
        reminder_time = dateDue.get(Calendar.YEAR) + "-";
        reminder_time += String.format("%02d-",(dateDue.get(Calendar.MONTH)));
        reminder_time += String.format("%02dT",(dateDue.get(Calendar.DAY_OF_MONTH)));
        reminder_time += String.format("%02d:",(dateDue.get(Calendar.HOUR_OF_DAY)));
        reminder_time += String.format("%02d:",(dateDue.get(Calendar.MINUTE)));
        reminder_time += "00";
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;
    }
}
