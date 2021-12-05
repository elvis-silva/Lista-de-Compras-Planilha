package com.elvis.shopping.list.model;

import java.util.ArrayList;
import java.util.List;

public class Group {

    public String string;
    public final List<String> children = new ArrayList<>();

    public Group(String pString) {
        string = pString;
    }
}
