package com.example.smartstickapp3;

import java.util.ArrayList;

public class UserPlants {
    String PlantName,PlantAge,PlantWaterDay;
    int PlantImageId;
    public UserPlants(){ }

    public UserPlants(String plantName, String plantAge, String plantWaterDay,int plantImageId) {
        PlantName=plantName;
        PlantAge=plantAge;
        PlantWaterDay=plantWaterDay;
        PlantImageId = plantImageId;
    }


    public String getPlantName() {
        return PlantName;
    }

    public void setPlantName(String plantName) {
        PlantName = plantName;
    }

    public String getPlantAge() {
        return PlantAge;
    }

    public void setPlantAge(String plantAge) {
        PlantAge = plantAge;
    }

    public String getPlantWaterDay() {
        return PlantWaterDay;
    }

    public void setPlantWaterDay(String plantWaterDay) {
        PlantWaterDay = plantWaterDay;
    }

    public int getPlantImageId() {
        return PlantImageId;
    }

    public void setPlantImageId(int plantImageId) {
        PlantImageId = plantImageId;
    }

    public static ArrayList<UserPlants> getData(){

        ArrayList<UserPlants> userPlantObjects = new ArrayList<>();



        String[] names =  {"Bitki Adı: Cam Güzeli","Bitki Adı: Kauçuk","Bitki Adı: Sukulent Kaktüs","Bitki Adı: Kalanşo"};
        String[] ages = {"Yaş: 1","Yaş: 3","Yaş: 7","Yaş :2"};
        String[] sulamaGun = {"Sulama sıklığı: 15","Sulama sıklığı 7","Sulama sıklığı: 30","Sulama sıklığı: 5"};
        int[]    images = {R.drawable.cam_guzeli,R.drawable.kaucuk_bitkisi,R.drawable.sukulent_kaktus,R.drawable.kalanso};

        for(int i=0 ; i<names.length ; i++){
            userPlantObjects.add(new UserPlants(names[i],ages[i],sulamaGun[i],images[i]));
        }

        return  userPlantObjects;
    }
}
