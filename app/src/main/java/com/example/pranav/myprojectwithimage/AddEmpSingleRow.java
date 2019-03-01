package com.example.pranav.myprojectwithimage;

import android.graphics.Bitmap;

public class AddEmpSingleRow {
    String name,num;
    Bitmap image;

    public AddEmpSingleRow(String name, String num, Bitmap image) {
        this.name = name;
        this.num = num;
        this.image = image;
    }

    public String getName() {

        return name;
    }

    public String getNum() {
        return num;
    }

    public Bitmap getImage() {
        return image;
    }
}
