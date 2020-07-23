package com.example.smartstickapp3;

public class PlantItem {

    String Title,Contex;
    int ic_logo;

    public PlantItem(String title, String contex, int ic_logo) {
        Title = title;
        Contex = contex;
        this.ic_logo = ic_logo;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContex() {
        return Contex;
    }

    public void setContex(String contex) {
        Contex = contex;
    }

    public int getIc_logo() {
        return ic_logo;
    }

    public void setIc_logo(int ic_logo) {
        this.ic_logo = ic_logo;
    }
}
