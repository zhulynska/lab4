package ua.edu.sumdu.Zhulynska.dates;

import java.util.ArrayList;
import java.util.Date;

/**
 * Used to work with date
 *
 * @author user
 */
public class DateClass {

    /**
     * to fill up a collection of day of month numbers
     *
     * @return the collection of days
     */
    public static ArrayList<Integer> getDays() {
        ArrayList<Integer> days = new ArrayList<Integer>();
        for (int i = 1; i <= 31; i++) {
            days.add(i);
        }
        return days;
    }

    /**
     * to fill up a collection of month numbers
     *
     * @return the collection of month
     */
    public static ArrayList<Integer> getMonths() {
        ArrayList<Integer> months = new ArrayList<Integer>();
        for (int i = 1; i <= 12; i++) {
            months.add(i);
        }
        return months;
    }

    /**
     * to fill up a collection of possible years of workers birthday
     *
     * @return the collection of years
     */
    public static ArrayList<Integer> getYears() {
        ArrayList<Integer> years = new ArrayList<Integer>();
        java.util.Calendar calendar = java.util.Calendar.getInstance(java.util.TimeZone.getDefault(), java.util.Locale.getDefault());
        calendar.setTime(new java.util.Date());
        int currentYear = calendar.get(java.util.Calendar.YEAR);

        for (int i = currentYear - 100; i <= currentYear - 16; i++) {
            years.add(i);
        }
        return years;
    }

    /**
     * to get day of month from date
     *
     * @param date
     * @return
     */
    public static int getDayFromDate(Date date) {
        int day = 0;
        if (date != null) {
            java.util.Calendar calendar = java.util.Calendar.getInstance(java.util.TimeZone.getDefault(), java.util.Locale.getDefault());
            calendar.setTime(date);
            day = calendar.get(java.util.Calendar.DAY_OF_MONTH);
        }
        return day;
    }

    /**
     * to get number of month from date
     *
     * @param date
     * @return
     */
    public static int getMonthFromDate(Date date) {
        int month = 0;
        if (date != null) {
            java.util.Calendar calendar = java.util.Calendar.getInstance(java.util.TimeZone.getDefault(), java.util.Locale.getDefault());
            calendar.setTime(date);
            month = calendar.get(java.util.Calendar.MONTH) + 1;
        }
        return month;
    }

    /**
     * to get year number from date
     *
     * @param date
     * @return
     */
    public static int getYearFromDate(Date date) {
        int year = 0;
        if (date != null) {
            java.util.Calendar calendar = java.util.Calendar.getInstance(java.util.TimeZone.getDefault(), java.util.Locale.getDefault());
            calendar.setTime(date);
            year = calendar.get(java.util.Calendar.YEAR);
        }
        return year;
    }

}
