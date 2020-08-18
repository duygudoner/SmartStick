package com.example.smartstickapp3;

public class UserPlants {
    String PlantName,PlantAge,PlantType;
    public UserPlants(){

    }

    public UserPlants(String plantName, String plantAge, String plantType) {
        PlantName=plantName;
        PlantAge=plantAge;
        PlantType=plantType;
    }

    public String getPlantName() {
        return PlantName;
    }

    public void setPlantName(String plantName) { PlantName = plantName; }

    public String getPlantAge() {
        return PlantAge;
    }

    public void setPlantAge(String plantAge) {
        PlantAge=plantAge;
    }

    public String getPlantType() {
        return PlantType;
    }

    public void setPlantType(String plantType) {
        PlantType=plantType;
    }
}
