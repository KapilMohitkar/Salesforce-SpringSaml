package com.relecotech.helper;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeChecker {

    public static final String inputFormat = "HH:mm";

    private Date date;
    private Date dateCompareOne;
    private Date dateCompareTwo;

    public String compareStringOne;
    public String compareStringTwo;

    SimpleDateFormat inputParser = new SimpleDateFormat(inputFormat, Locale.US);

    public  boolean compareDates() {
        Calendar now = Calendar.getInstance();

        int hour = now.get(Calendar.HOUR);
        int minute = now.get(Calendar.MINUTE);

        date = parseDate(hour + ":" + minute);
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
        TimeChecker application1= new TimeChecker();
        application1.compareStringOne="07:09 AM";
        application1.compareStringTwo="08:15 AM";
        boolean compareDates = application1.compareDates();
        System.out.println(compareDates);
    }

}
