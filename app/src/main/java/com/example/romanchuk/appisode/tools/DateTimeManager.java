package com.example.romanchuk.appisode.tools;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by romanchuk on 08.01.2017.
 */

public class DateTimeManager {

    private static final String DATE_TIME_MANAGER_TAG = "DATE_TIME_MANAGER_TAG";

    public static String getDate(String dateString){
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = fmt.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yyyy");
        return fmtOut.format(date);
    }

    public static int getYear(String dateString){
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        final Calendar c = Calendar.getInstance();
        try {
            c.setTime(df.parse(dateString));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return c.get(Calendar.YEAR);
    }
    public static int getMonth(String dateString){
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        final Calendar c = Calendar.getInstance();
        try {
            c.setTime(df.parse(dateString));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return c.get(Calendar.MONTH);
    }
    public static int getDay(String dateString){
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        final Calendar c = Calendar.getInstance();
        try {
            c.setTime(df.parse(dateString));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return c.get(Calendar.DAY_OF_MONTH);
    }

    public static String getMonthName(int month){
        switch(month + 1){
            case 1:
                return "января";
            case 2:
                return "февраля";
            case 3:
                return "марта";
            case 4:
                return "Apr";
            case 5:
                return "мая";
            case 6:
                return "июня";
            case 7:
                return "июля";
            case 8:
                return "августа";
            case 9:
                return "сентября";
            case 10:
                return "окбятря";
            case 11:
                return "ноября";
            case 12:
                return "декабря";
        }
        return "getMonthName(" + String.valueOf(month) + ")";
    }

    public static String DeclOfNumJustText1(int number)
    {
        int[] cases = { 2, 0, 1, 1, 1, 2 };
        String[] titles = { "день", "дня", "дней" };
        String s = "0";
        try {
            s = titles[(number % 100 > 4 && number % 100 < 20) ? 2 : cases[(number % 10 < 5) ? number % 10 : 5]];
        } catch (Exception je) {
            Log.i(DATE_TIME_MANAGER_TAG, " " + je.getLocalizedMessage());
        }
        return s;
    }

    public static String DeclOfNumJustText2(int number)
    {
        int[] cases = { 2, 0, 1, 1, 1, 2 };
        String[] titles = { "найден", "найдено", "найдено" };
        String s = "0";
        try {
            s = titles[(number % 100 > 4 && number % 100 < 20) ? 2 : cases[(number % 10 < 5) ? number % 10 : 5]];
        } catch (Exception je) {
            Log.i(DATE_TIME_MANAGER_TAG, " " + je.getLocalizedMessage());
        }
        return s;
    }

    public static String DeclOfNumJustText3(int number)
    {
        int[] cases = { 2, 0, 1, 1, 1, 2 };
        String[] titles = { "сериал", "сериала", "сериалов" };
        String s = "0";
        try {
            s = titles[(number % 100 > 4 && number % 100 < 20) ? 2 : cases[(number % 10 < 5) ? number % 10 : 5]];
        } catch (Exception je) {
            Log.i(DATE_TIME_MANAGER_TAG, " " + je.getLocalizedMessage());
        }
        return s;
    }

    public static String DeclOfNumJustText4(int number)
    {
        int[] cases = { 2, 0, 1, 1, 1, 2 };
        String[] titles = { "сезон", "сезона", "сезонов" };
        String s = "0";
        try {
            s = titles[(number % 100 > 4 && number % 100 < 20) ? 2 : cases[(number % 10 < 5) ? number % 10 : 5]];
        } catch (Exception je) {
            Log.i(DATE_TIME_MANAGER_TAG, " " + je.getLocalizedMessage());
        }
        return s;
    }

    public static String DeclOfNumJustText5(int number)
    {
        int[] cases = { 2, 0, 1, 1, 1, 2 };
        String[] titles = { "вышел", "вышло", "вышло" };
        String s = "0";
        try {
            s = titles[(number % 100 > 4 && number % 100 < 20) ? 2 : cases[(number % 10 < 5) ? number % 10 : 5]];
        } catch (Exception je) {
            Log.i(DATE_TIME_MANAGER_TAG, " " + je.getLocalizedMessage());
        }
        return s;
    }
}
