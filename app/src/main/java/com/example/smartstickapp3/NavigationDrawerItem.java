package com.example.smartstickapp3;

import java.util.ArrayList;

public class NavigationDrawerItem {

    String baslik ;
    String id;
    int resimID;

    public String getBaslik() {
        return baslik;
    }

    public int getResimID() {
        return resimID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }

    public void setResimID(int resimID) {
        this.resimID = resimID;
    }

    public static ArrayList<NavigationDrawerItem> getData(){

        ArrayList<NavigationDrawerItem> dataList = new ArrayList<NavigationDrawerItem>();
        int[] resimID = getImages();
        String[] basliklar = getBasliklar();
        String [] idler = getID();

        for (int i=0 ; i<basliklar.length ; i++){
            NavigationDrawerItem navItem = new NavigationDrawerItem();
            navItem.setId(idler[i]);
            navItem.setBaslik(basliklar[i]);
            navItem.setResimID(resimID[i]);

            dataList.add(navItem);
        }
        
        return dataList;
    }

    private static int[] getImages() {
        return new int[]{
                R.drawable.tree_01,R.drawable.tree_02,R.drawable.tree_03,
                R.drawable.tree_04};
    }

    private static String[] getBasliklar() {
        return new String[]{
                "Bitki Ekle","Bitki Göster","Bitki Ara","Hakkında"
        };
    }
    private static String[] getID() {
        return new String[]{
                "id_bitki_ekle","id_bitki_göster","id_bitki_ara","id_hakkında"
        };
    }



}
