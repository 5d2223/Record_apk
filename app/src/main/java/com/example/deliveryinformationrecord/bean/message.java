package com.example.deliveryinformationrecord.bean;

import org.litepal.crud.LitePalSupport;


//数据库存储表对应的实体类，也是适配器存储的实体类，包含公司名称，地点，网址

public class message extends LitePalSupport{

    private String name;

    private String location;

    private  String web;

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getWeb() {
        return web;
    }
}
