package com.example.btlandroid.modelUI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SongOnline implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("name")
    @Expose
    private String name;



    @SerializedName("file")
    @Expose
    private String file;

    @SerializedName("nghesi")
    @Expose
    private NgheSi nghesi;
    public SongOnline() {
    }

    public SongOnline(int id, String image, String name, String file, NgheSi nghesi) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.file = file;
        this.nghesi = nghesi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public NgheSi getNghesi() {
        return nghesi;
    }

    public void setNghesi(NgheSi nghesi) {
        this.nghesi = nghesi;
    }
}
