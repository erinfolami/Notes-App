package com.example.notes.models;


public class NoteModel {

    public NoteModel(String title, String note) {
        this.title = title;
        this.note = note;
    }

    public NoteModel() {
    }

    private String title;
    private String note;
    private int id;


    @Override
    public String toString() {
        return "NoteModel{" +
                "ID='" + id + '\'' +
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

    public void setId(int id){ this.id = id;}

    public int getId(){return id;}

}
