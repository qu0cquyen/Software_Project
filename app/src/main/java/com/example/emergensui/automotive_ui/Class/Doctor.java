package com.example.emergensui.automotive_ui.Class;

public class Doctor {
    private String Medical_Degree;
    private String Name;
    private String Spec_ID;

    //Constructors
    public Doctor(){}
    public Doctor(String name, String medical_degree, String spec_id)
    {
        this.Name = name;
        this.Medical_Degree = medical_degree;
        this.Spec_ID = spec_id;
    }

    public String getName()
    {
        return Name;
    }

    public String getMedical_Degree()
    {
        return Medical_Degree;
    }

    public String getSpec_ID()
    {
        return Spec_ID;
    }


    public void setName(String name)
    {
        this.Name = name;
    }

    public void setMedical_Degree(String medical_degree)
    {
        this.Medical_Degree = medical_degree;
    }

    public void setSpec_ID(String spec_id)
    {
        this.Spec_ID = spec_id;
    }

}
