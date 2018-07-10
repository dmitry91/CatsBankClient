package com.dmitry.catsbankclient.entities;

import android.util.Base64;

public class Cat {

    private int id;
    private String text;
    private String photo;

    public Cat() {
    }

    public Cat(int id, String text, String photo) {
        this.id = id;
        this.text = text;
        this.photo = photo;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public byte[] getPhotoBytes(){
        return Base64.decode(photo,Base64.DEFAULT);
    }

    @Override
    public String toString() {
        return "Cat{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", photo=" + photo +
                '}';
    }
}
