package com.bwie.xulits.bean;

/**
 * Created by 小傻瓜 on 2017/8/31.
 */

public class NewsBean {
    public String id;
    public String name;
    public boolean state;

    public NewsBean() {
    }

    public NewsBean(String id, String name, boolean state) {
        this.id = id;
        this.name = name;
        this.state = state;
    }

    public NewsBean(String name, boolean state) {
        this.name = name;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
