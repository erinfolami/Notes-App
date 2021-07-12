package com.example.notes.models;


public class Model {

    public Model(String title, String note) {
        this.title = title;
        this.note = note;
    }

    public Model() {
    }

    private String title;
    private String note;


    @Override
    public String toString() {
        return "Model{" +
                "title='" + title + '\'' +
                ", note='" + note + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


}
