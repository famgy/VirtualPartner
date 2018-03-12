package com.famgy.modle.area;

import org.litepal.crud.DataSupport;

/**
 * Created by famgy on 3/12/18.
 */

public class Province extends DataSupport{

    private int id;

    private String provinceName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }
}
