package com.example.emergensui.automotive_ui.Class;

import java.io.Serializable;
import java.util.ArrayList;

public class Patient implements Serializable {
    private String DoB;
    private String Patient_Type;
    private String Room_Number;
    private String Patient_Name;
    private String Patient_Sex;
    private String Phone_Number;
    private String Patient_ID;
    private int pos;

    private ArrayList<Patient> lstPatient;

    public Patient(){}

    public Patient(String dob, String patient_type, String room_number, String Name, String sex, String phone_Number)
    {
        this.DoB = dob;
        this.Patient_Type = patient_type;
        this.Room_Number = room_number;
        this.Patient_Name = Name;
        this.Patient_Sex = sex;
        this.Phone_Number = phone_Number;
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

    public void setLstPatient(ArrayList<Patient> lst)
    {
        this.lstPatient = lst;
    }

    public ArrayList<Patient> getLstPatient()
    {
        return this.lstPatient;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String getPatient_Sex() {
        return Patient_Sex;
    }

    public void setPatient_Sex(String patient_Sex) {
        Patient_Sex = patient_Sex;
    }

    public String getPhone_Number() {
        return Phone_Number;
    }

    public void setPhone_Number(String phone)
    {
        this.Phone_Number = phone;
    }

    public String getPatient_ID() {
        return Patient_ID;
    }

    public void setPatient_ID(String patient_ID) {
        Patient_ID = patient_ID;
    }
}
