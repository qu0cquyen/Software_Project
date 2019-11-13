package com.example.emergensui.automotive_ui.Class;

public class Visit_Doctor {
    private String date;
    private String doc_note;

    public Visit_Doctor(){};

    public Visit_Doctor(String Date, String Doc_Note)
    {
        this.date = Date;
        this.doc_note = Doc_Note;
    }

    public void setDate(String Date)
    {
        this.date = Date;
    }

    public String getDate()
    {
        return date;
    }

    public void setDoc_note(String Doc_Note)
    {
        this.doc_note = Doc_Note;
    }

    public String getDoc_note()
    {
        return doc_note;
    }
}
