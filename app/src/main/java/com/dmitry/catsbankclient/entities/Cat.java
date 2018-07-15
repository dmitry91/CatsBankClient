package com.dmitry.catsbankclient.entities;

import android.util.Base64;

public class Cat {

    private int id;
    private String text;
    private String photoName;

    public Cat() {
    }

    public Cat(int id, String text, String photoName) {
        this.id = id;
        this.text = text;
        this.photoName = photoName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public byte[] getPhotoBytes(){
        return Base64.decode(photoName,Base64.DEFAULT);
    }

    @Override
    public String toString() {
        return "Cat{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", photoName=" + photoName +
                '}';
    }
}
