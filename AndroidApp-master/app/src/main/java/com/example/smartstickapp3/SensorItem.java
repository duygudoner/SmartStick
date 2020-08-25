package com.example.smartstickapp3;

// MyCurrentPlantActivity' de yer alacak olan recyclerview için class tanımlaması yapıyoruz.
public class SensorItem {

    String bitkiVeriTur, s_bar_current;
    int s_bar_min, s_bar_max,s_bar_optimum;

    public SensorItem(){

    }

    public SensorItem(String bitkiVeriTur, String s_bar_current, int s_bar_min, int s_bar_max, int s_bar_optimum) {
        this.bitkiVeriTur = bitkiVeriTur;
        this.s_bar_current = s_bar_current;
        this.s_bar_min = s_bar_min;
        this.s_bar_max = s_bar_max;
        this.s_bar_optimum = s_bar_optimum;
    }

    public String getS_bar_current() { return s_bar_current; }

    public void setS_bar_current(String s_bar_current) { this.s_bar_current = s_bar_current; }

    public int getS_bar_optimum() {
        return s_bar_optimum;
    }

    public void setS_bar_optimum(int s_bar_optimum) {
        this.s_bar_optimum = s_bar_optimum;
    }

    public String getBitkiVeriTur() {
        return bitkiVeriTur;
    }

    public void setBitkiVeriTur(String bitkiVeriTur) {
        this.bitkiVeriTur = bitkiVeriTur;
    }

    public int getS_bar_min() {
        return s_bar_min;
    }

    public void setS_bar_min(int s_bar_min) {
        this.s_bar_min = s_bar_min;
    }

    public int getS_bar_max() {
        return s_bar_max;
    }

    public void setS_bar_max(int s_bar_max) {
        this.s_bar_max = s_bar_max;
    }

    // Recyclerview'daki verileri doldurmak için bir metod oluşturuyoruz.
    // public static method sayesınde classtan nesne oluşturmadan çağırıyoruz.
    // Class tipinde (SensorItem) Arraylist oluşturuyoruz.

    /*public static List<SensorItem> getData(){
        ArrayList<SensorItem> dataList = new ArrayList<SensorItem>();

        int [] degerler  = {0,1000};
        // currrent value sensorden gelecek
        // optimum value ise firebaseden cicege ait optimum deger getirelecek
        // String currentValue = sendData() ;
        int optimumValue = 60 ;

        SensorItem ortamNemi = new SensorItem();
        SensorItem isikSiddeti= new SensorItem();
        SensorItem sicaklik=new SensorItem();
        SensorItem toprakNemi=new SensorItem();

        for (int i=0 ; i<4 ; i++){
            switch (i){
                case 0:
                    ortamNemi.setBitkiVeriTur("Ortam Nemi");
                    //ortamNemi.setS_bar_current(datalines[0]);
                    break;
                case 1:
                    isikSiddeti.setBitkiVeriTur("Işık Şiddeti");
                    //isikSiddeti.setS_bar_current(datalines[1]);
                    break;
                case 2:
                    sicaklik.setBitkiVeriTur("Sıcaklık");
                    //sicaklik.setS_bar_current(datalines[2]);
                    break;
                case 3:
                    toprakNemi.setBitkiVeriTur("Toprak Nemi");
                    //toprakNemi.setS_bar_current(datalines[3]);
                    break;
                default:
                    break;
            }
        }
        /*gecici.setS_bar_min(degerler[0]);
        gecici.setS_bar_max(degerler[1]);

        gecici.setS_bar_current(currentValue);
        gecici.setS_bar_optimum(optimumValue);

        dataList.add(ortamNemi);
        dataList.add(isikSiddeti);
        dataList.add(sicaklik);
        dataList.add(toprakNemi);

        return dataList;
    }*/
}
