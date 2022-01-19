package main;

import java.text.SimpleDateFormat;

public class Date
{
    private String date;
    
    Date(){
        date = " ";
    }

    public void updateTime(){
        java.util.Date d = new java.util.Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        
        date = dateFormat.format(d);
        
    }

    public String toString(){return date;}

    public void setTime(String t){
        date = t;
    }
}
