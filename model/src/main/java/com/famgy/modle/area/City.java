package com.famgy.modle.area;

import org.litepal.crud.DataSupport;

/**
 * Created by famgy on 3/12/18.
 */

public class City extends DataSupport{

    private int id;

    private String cityName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityeName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
