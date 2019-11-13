package com.example.emergensui.automotive_ui.Class;

public class UData {
    private String profession_id;
    private String type;

    public UData(){

    }

    public UData(String profession_id, String type){
        this.profession_id = profession_id;
        this.type = type;
    }

    public String getProfession_id(){
        return profession_id;
    }

    public void setProfession_id(String profession_id){
        this.profession_id = profession_id;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }
}
