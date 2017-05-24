package com.example.romanchuk.appisode.models;

import com.example.romanchuk.appisode.tools.DateTimeManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by romanchuk on 05.01.2017.
 */

public class EpisodeItem {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getAir_date() {
        return air_date;
    }

    public void setAir_date(String air_date) {
        this.air_date = air_date;
    }

    public boolean getAired() {
        return aired;
    }

    public void setAired(boolean aired) {
        this.aired = aired;
    }

    public int getDays_left() {
        return days_left;
    }

    public void setDays_left(int days_left) {
        this.days_left = days_left;
    }

    public int getHous_left() {
        return hous_left;
    }

    public void setHous_left(int hous_left) {
        this.hous_left = hous_left;
    }

    private int id;
    private int number;
    private String air_date;

    public String getDetail_air_date() {
        return detail_air_date;
    }

    public void setDetail_air_date() {
        this.detail_air_date = DateTimeManager.getDay(air_date) + " " +
                DateTimeManager.getMonthName(DateTimeManager.getMonth(air_date)) + " " +
                DateTimeManager.getYear(air_date);
    }

    private String detail_air_date;
    private boolean aired;
    private int days_left;
    private int hous_left ;

    private boolean isSelected = false;

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable() {
        isEnable = true;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date strDate = null;
        try {
            strDate = sdf.parse(air_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (System.currentTimeMillis() > strDate.getTime()) {
            isEnable = false;
        }
    }

    private boolean isEnable = false;

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public boolean isSelected() {
        return isSelected;
    }


}
