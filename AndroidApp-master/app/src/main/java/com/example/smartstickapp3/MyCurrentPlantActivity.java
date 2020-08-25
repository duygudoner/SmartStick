package com.example.smartstickapp3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MyCurrentPlantActivity extends AppCompatActivity {

    String address = null;
    private ProgressDialog progress;
    //private Button btn;
    //private TextView txtview;
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
    private String dataToSend;
    Handler handler = new Handler();
    byte delimiter = 10;
    boolean stopWorker = false;
    int readBufferPosition = 0;
    byte[] readBuffer = new byte[1024];
    public Thread workerThread;
    String[] datarr = new String[4];
    public String modulData=null;

    RecyclerView recyclerView;
    Button button;
    MCPlantAdapter adapter;
    List<SensorItem> objects;
    TextView deger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_current_plant);

        myBluetooth = BluetoothAdapter.getDefaultAdapter();
        Intent newInt = getIntent();
        address = newInt.getStringExtra(BluetoothScreen.EXTRA_ADRESS);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        deger = (TextView) findViewById(R.id.textViewDeger);
        //button = findViewById(R.id.guncelle);

        objects = new ArrayList<>();
        objects.add(new SensorItem("Ortam Nemi" , "0" , 0 , 0 , 100 , 50 ));
        objects.add(new SensorItem("Işık Şiddeti" , "0", 0 , 0 , 1000, 500));
        objects.add(new SensorItem("Sıcaklık" , "0" , 0 , 0 , 100 , 30));
        objects.add(new SensorItem("Toprak Nemi" , "0" , 0 , 0 , 1000 , 500));

        adapter = new MCPlantAdapter(getApplicationContext(), objects);

        // CHANGE LINEAR LAYOUT TO GRID LAYOUT
        /*
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
        */

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        // Bluetootg bağlantı kontrol edilirken sürekli beklememek
        // Test işlemlerini hızlandırmak için bluetooth kontrol bağlantı kısmı yoruma alındı.
        new MyCurrentPlantActivity.BTbaglan().execute();


    }

    public void writeData(View view) {
        try {
            this.outStream = this.btSocket.getOutputStream();
        } catch (IOException var6) {
            Log.d("Jon", "Bug BEFORE Sending stuff", var6);
        }
        try {
            byte [] data = ("a").getBytes();
            this.outStream.write(data);
            Toast.makeText(getApplicationContext(), "Bitkiniz Sulanıyor", Toast.LENGTH_SHORT).show();
        } catch (IOException var5) {
            Log.d("Jon", "Bug while sending stuff", var5);
        }

    }

    protected void onDestroy() {
        super.onDestroy();

        try {
            this.btSocket.close();
        } catch (IOException var2) {
        }

    }

    /*public void run() { // connect & read
        try {
            btSocket = remoteDevice.createRfcommSocketToServiceRecord(UUID.fromString(address));
            btSocket.connect();
            if(listener != null)
                listener.onSerialConnect();
        } catch (Exception e) {
            if(listener != null)
                listener.onSerialConnectError(e);
            try {
                btSocket.close();
            } catch (Exception ignored) {
            }
            btSocket = null;
            return;
        }
        connected = true;
        try {
            byte[] buffer = new byte[1024];
            int len;
            //noinspection InfiniteLoopStatement
            while (true) {
                len = btSocket.getInputStream().read(buffer);
                byte[] data = Arrays.copyOf(buffer, len);
                if(listener != null)
                    listener.onSerialRead(data);
            }
        } catch (Exception e) {
            connected = false;
            if (listener != null)
                listener.onSerialIoError(e);
            try {
                btSocket.close();
            } catch (Exception ignored) {
            }
            btSocket = null;
        }
    }*/


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
                                            //  datarr[k] = data ;
                                            //  Log.d("test",datarr[1]);
                                            //txtview.setText(data);
                                            modulData=data;
                                            String[] dataLines = modulData.split(" ");
                                            int dataLineCount = dataLines.length;

                                            SensorItem sensor1= new SensorItem();
                                            sensor1.setBitkiVeriTur("Ortam Nemi");
                                            sensor1.setS_bar_current(dataLines[0]+" rh");
                                            sensor1.setS_bar_min(0);
                                            sensor1.setS_bar_max(100);
                                            sensor1.setS_bar_optimum(50);
                                            //sensor1.setProgressbar(Integer.parseInt(dataLines[0]));

                                            SensorItem sensor2 = new SensorItem();
                                            sensor2.setBitkiVeriTur("Işık Şiddeti");
                                            sensor2.setS_bar_current(dataLines[1]+" lux");
                                            sensor2.setS_bar_min(0);
                                            sensor2.setS_bar_max(1000);
                                            sensor2.setS_bar_optimum(500);
                                            //sensor2.setProgressbar(Integer.parseInt(dataLines[1]));


                                            SensorItem sensor3 = new SensorItem();
                                            sensor3.setBitkiVeriTur("Sıcaklık");
                                            sensor3.setS_bar_current(dataLines[2]+"°C");
                                            sensor3.setS_bar_min(0);
                                            sensor3.setS_bar_max(100);
                                            sensor3.setS_bar_optimum(30);
                                            //sensor3.setProgressbar(Integer.parseInt(dataLines[2]));

                                            SensorItem sensor4 = new SensorItem();
                                            sensor4.setBitkiVeriTur("Toprak Nemi");
                                            sensor4.setS_bar_current(dataLines[3]+" rh");
                                            sensor4.setS_bar_min(0);
                                            sensor4.setS_bar_max(1000);
                                            sensor4.setS_bar_optimum(700);
                                            //sensor4.setProgressbar(Integer.parseInt(dataLines[3]));

                                            objects.set(0, sensor1);
                                            objects.set(1, sensor2);
                                            objects.set(2, sensor3);
                                            objects.set(3, sensor4);

                                            adapter = new MCPlantAdapter(getApplicationContext(), objects);

                                            // CHANGE LINEAR LAYOUT TO GRID LAYOUT
                                            //recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                            //recyclerView.setAdapter(adapter);

                                            GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2,GridLayoutManager.VERTICAL,false);
                                            recyclerView.setLayoutManager(gridLayoutManager);
                                            recyclerView.setAdapter(adapter);

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
        try {
                this.outStream = this.btSocket.getOutputStream();
            }
        catch (IOException var6) {
                Log.d("Jon", "Bug BEFORE Sending stuff", var6);
            }
        try {
                byte [] data = ("d").getBytes(); //kırmızı renk
                this.outStream.write(data);
            /*if(objects.get(1).getS_bar_optimum() == Integer.parseInt(objects.get(1).getS_bar_current())){
                byte [] data1 = ("c").getBytes(); //mavi renk
                this.outStream.write(data1);
            }
            if(objects.get(1).getS_bar_optimum() < Integer.parseInt(objects.get(1).getS_bar_current())){
                byte [] data2 = ("d").getBytes(); //yeşil renkthis.outStream.write(data2);
            }*/
        } catch (IOException var5) {
            Log.d("Jon", "Bug while sending stuff", var5);
        }

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
