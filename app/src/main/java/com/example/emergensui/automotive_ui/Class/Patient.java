package com.example.emergensui.automotive_ui.Class;

public class Patient {
    private String DoB;
    private String Patient_Type;
    private String Room_Number;
    private String Patient_Name;

    public Patient(){}

    public Patient(String dob, String patient_type, String room_number, String Name)
    {
        this.DoB = dob;
        this.Patient_Type = patient_type;
        this.Room_Number = room_number;
        this.Patient_Name = Name;
    }

    public void setDoB(String dob)
    {
        this.DoB = dob;
    }

    public String getDoB()
    {
        return DoB;
    }

    public void setPatient_Type(String patient_type)
    {
        this.Patient_Type = patient_type;
    }

    public String getPatient_Type()
    {
        return Patient_Type;
    }

    public void setRoom_Number(String room_number)
    {
        this.Room_Number = room_number;
    }

    public String getRoom_Number()
    {
        return Room_Number;
    }

    public void setPatient_Name(String name) {this.Patient_Name = name;}

    public String getPatient_Name(){return Patient_Name;}


}
