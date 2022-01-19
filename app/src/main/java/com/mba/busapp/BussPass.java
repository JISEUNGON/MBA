package com.mba.busapp;

import android.graphics.drawable.Drawable;

public class BussPass {
    private String name;
    private Drawable d;

    public void setD(Drawable d) {
        this.d = d;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getD() {
        return d;
    }

    public String getName() {
        return name;
    }
}
