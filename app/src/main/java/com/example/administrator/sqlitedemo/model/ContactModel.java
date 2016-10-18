package com.example.administrator.sqlitedemo.model;

import java.io.Serializable;

/**
 * 联系人信息
 * Created by lifengmei on 2016/10/17 17:36.
 */
public class ContactModel implements Serializable {
    private int id;
    private String name;
    private String phoneNum;
    private int type;

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
