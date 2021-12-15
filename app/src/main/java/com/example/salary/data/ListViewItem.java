package com.example.salary.data;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public class ListViewItem {
    private Drawable drawable;
    private String companyName;
    private String companyAddress;

    public ListViewItem()
    {
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }
}
