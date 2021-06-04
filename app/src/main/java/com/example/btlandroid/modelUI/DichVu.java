package com.example.btlandroid.modelUI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DichVu implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("image")
    @Expose
    private String img;

    @SerializedName("name")
    @Expose
    private String name;

    public DichVu() {
    }

    public DichVu(int id, String img, String name) {
        this.id = id;
        this.img = img;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
