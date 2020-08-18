package com.example.smartstickapp3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

public class MyCurrentPlantActivity extends AppCompatActivity {

    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    BluetoothDevice remoteDevice;
    BluetoothServerSocket nmserver;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private boolean connected;
    private SerialListener listener;
    private InputStream inStream = null;
    private OutputStream outStream = null;
    Handler handler = new Handler();
    byte delimiter = 10;
    boolean stopWorker = false;
    int readBufferPosition = 0;
    byte[] readBuffer = new byte[1024];
    public Thread workerThread;
    RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_current_plant);

        myBluetooth = BluetoothAdapter.getDefaultAdapter();
        Intent newInt = getIntent();
        address = newInt.getStringExtra(BluetoothScreen.EXTRA_ADRESS);
        new MyCurrentPlantActivity.BTbaglan().execute();

        recyclerView = findViewById(R.id.my_recycler_view);
        MCPlantAdapter mcPlantAdapter = new MCPlantAdapter(this,SensorItem.getData());
        recyclerView.setAdapter(mcPlantAdapter);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManager);

    }

    protected void onDestroy() {
        super.onDestroy();

        try {
            this.btSocket.close();
        } catch (IOException var2) {
        }

    }

    public void beginListenForData(View view)   {
        try {
            inStream = btSocket.getInputStream();
        } catch (IOException e) {
        }
        workerThread = new Thread(new Runnable()
        {
            public void run()
            {
                while(!Thread.currentThread().isInterrupted() && !stopWorker)
                {
                    try
                    {
                        int bytesAvailable = inStream.available();
                        if(bytesAvailable > 0)
                        {
                            byte[] packetBytes = new byte[bytesAvailable];
                            inStream.read(packetBytes);
                            for(int i=0;i<bytesAvailable;i++)
                            {
                                byte b = packetBytes[i];
                                if(b == delimiter)
                                {
                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                    final String data = new String(encodedBytes, "US-ASCII");
                                    Log.d("msg",data);
                                    readBufferPosition = 0;
                                    handler.post(new Runnable()
                                    {
                                        public void run()
                                        {
                                            String[] dataLines = data.split(" ");
                                            int dataLineCount = dataLines.length;
                                            MCPlantAdapter mcPlantAdapter = new MCPlantAdapter(this,SensorItem.getData(dataLines));
                                            recyclerView.setAdapter(mcPlantAdapter);
                                        }
                                    });
                                }
                                else
                                {
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }
                    }
                    catch (IOException ex)
                    {
                        stopWorker = true;
                    }
                }
            }
        });

        workerThread.start();
    }



    // ------------------------------------  Bluetooth bağlantı kısmı ---------------------------------------

    private void Disconnect(){
        if (btSocket !=null){
            try {
                btSocket.close();
            } catch (IOException e) {
                //msg("Error");
            }
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Disconnect();
    }

    private class BTbaglan extends AsyncTask<Void, Void, Void> {
        private boolean ConnectSuccess = true;

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (!ConnectSuccess) {
                //msg("Baglantı hatası, lütfen tekrar deneyin");
                Toast.makeText(getApplicationContext(), "Bağlantı Hatası Tekrar Deneyin", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                //   msg("Baglantı Basarılı");
                Toast.makeText(getApplicationContext(), "Bağlantı Başarılı", Toast.LENGTH_SHORT).show();
                isBtConnected = true;
            }
            progress.dismiss();
        }

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(MyCurrentPlantActivity.this, "Bağlanıyor...", "Lütfen Bekleyin");
        }

        @Override
        protected Void doInBackground(Void... devices) {
            try {
                if (btSocket == null || !isBtConnected) {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice cihaz = myBluetooth.getRemoteDevice(address);
                    btSocket = cihaz.createInsecureRfcommSocketToServiceRecord(myUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();
                }
            } catch (IOException e) {
                ConnectSuccess = false;
            }
            return null;
        }

    }
}
