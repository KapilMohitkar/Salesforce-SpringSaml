package com.relecotech.helper;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeChecker {

    public static final String inputFormat = "HH:mm";

    public Date date;
    private Date dateCompareOne;
    private Date dateCompareTwo;

    public String compareStringOne;
    public String compareStringTwo;
    

    //For date/time formatting 
    SimpleDateFormat inputParser = new SimpleDateFormat(inputFormat, Locale.US);

    public TimeChecker(String timeZoneSting) {
        //To get current time
        TimeZone timeZone = TimeZone.getTimeZone(timeZoneSting);
        Calendar now = Calendar.getInstance(timeZone);
        Date time = now.getTime();
        System.out.println(time);
        int hour = now.get(Calendar.HOUR);
        int minute = now.get(Calendar.MINUTE);
      
        

       date = parseDate(hour + ":" + minute);
    }

    public  boolean compareDates() {
//        Calendar now = Calendar.getInstance();
//
//        int hour = now.get(Calendar.HOUR);
//        int minute = now.get(Calendar.MINUTE);

     //   date = parseDate(hour + ":" + minute);
         //date=parseDate(new SimpleDateFormat("hh:mm a").format(new Date()));
        dateCompareOne = parseDate(compareStringOne);
        dateCompareTwo = parseDate(compareStringTwo);

        if (dateCompareOne.before(date) && dateCompareTwo.after(date)) {
           
            return true;
        }
        return false;
    }

    private Date parseDate(String date) {

        try {
            return inputParser.parse(date);
        } catch (java.text.ParseException e) {
            return new Date(0);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TimeChecker application1= new TimeChecker("");
        application1.compareStringOne="07:09 AM";
        application1.compareStringTwo="08:15 AM";
        System.out.println(application1.date);
        boolean compareDates = application1.compareDates();
        System.out.println(compareDates);
    }

}
