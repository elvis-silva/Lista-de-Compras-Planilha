package com.elvis.shopping.list.model;

public class ListName {

    private int id;
    private String name;

    public ListName(){

    }

    public void setId(int pId) {
        id = pId;
    }

    public void setName(String pName) {
        name = pName;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
