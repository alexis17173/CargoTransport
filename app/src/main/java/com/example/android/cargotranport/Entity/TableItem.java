package com.example.android.cargotranport.Entity;

/**
 * Created by millesca on 15/11/2015.
 */
public class TableItem {

    private String mName;
    private int mState = Integer.MIN_VALUE;

    public TableItem(String name) {
        this.setName(name);
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public int getState() {
        return mState;
    }

    public void setState(int state) {
        this.mState = state;
    }
}
