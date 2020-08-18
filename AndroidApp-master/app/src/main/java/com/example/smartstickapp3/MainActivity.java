package com.example.smartstickapp3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.os.AsyncTask;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    BluetoothAdapter myBluetooth = null;
    Toolbar toolbar;
    private CardView addCard, showCard, searchCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myBluetooth = BluetoothAdapter.getDefaultAdapter();
        setUpToolBar();
        setUpDrawer();
        setUpCardView();

    }

    // ------------------------------------  cardView kısmı ---------------------------------------

    private void setUpCardView() {
        addCard = findViewById(R.id.add_box);
        showCard = findViewById(R.id.show_box);
        searchCard = findViewById(R.id.search_box);

        addCard.setOnClickListener(this);
        showCard.setOnClickListener(this);
        searchCard.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.add_box:
                i = new Intent(this, AddActivity.class);
                Toast.makeText(this, "Bitki Ekle", Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;
            case R.id.show_box:
                i = new Intent(this, ShowActivity.class);
                Toast.makeText(this, "Bitki Göster", Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;
            case R.id.search_box:
                i = new Intent(this, Search.class);
                Toast.makeText(this, "Bitki Ara", Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;
            default:
                break;
        }
    }
    // --------------------------------  navigation drawer kısmı  ----------------------------------

    private void setUpDrawer() {

        NavigationDrawerFragment navFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.fragment);
        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        navFragment.setUpNavigationDrawer(drawerLayout, toolbar);
    }

    private void setUpToolBar() {

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Smart Stick App");
        toolbar.setSubtitle("Hoş Geldiniz");

        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                String mesaj = " ";
                switch (item.getItemId()) {
                    case R.id.bluetooth:
                        mesaj = bluetoothDurumuYaz();
                        if (!myBluetooth.isEnabled()) {
                            // Bluetooth kapalı ise icona tıklanınca açılıyor.
                            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                            startActivity(enableBTIntent);
                            mesaj="Açık";
                            Toast.makeText(MainActivity.this, mesaj, Toast.LENGTH_SHORT).show();

                        }
                        if (myBluetooth.isEnabled()) {
                            //Buton açık ise icına tıklanınca kapanıyor.
                            myBluetooth.disable();
                            mesaj="Kapalı";
                            Toast.makeText(MainActivity.this, mesaj, Toast.LENGTH_SHORT).show();
                        }

                        //Toast.makeText(MainActivity.this, mesaj, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.hakkinda:
                        mesaj = "HAKKINDA";
                        Toast.makeText(MainActivity.this, mesaj, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, HakkindaActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.logout:
                        uygulamadanCikisYap();
                        break;
                }
                return true;
            }
        });
    }
    public String bluetoothDurumuYaz(){
        if (!myBluetooth.isEnabled()) {
            return "Kapalı";
        }
        else{
            return "Açık";
        }
    }

    public void uygulamadanCikisYap() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        //builder.setTitle(R.string.app_name);
        builder.setTitle("Uyarı");
        builder.setIcon(R.drawable.ic_launcher);
        builder.setMessage("Çıkış yapmak istiyor musunuz?")
                .setCancelable(false)
                .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static class SearchActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_search);
        }
    }
}
