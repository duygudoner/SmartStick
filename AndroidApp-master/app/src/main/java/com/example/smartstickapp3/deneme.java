package com.example.smartstickapp3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
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
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

public class deneme extends AppCompatActivity {

    String address = null;
    private ProgressDialog progress;
    private Button btn;
    private TextView txtview;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deneme);

        myBluetooth = BluetoothAdapter.getDefaultAdapter();
        Intent newInt = getIntent();
        address = newInt.getStringExtra(BluetoothScreen.EXTRA_ADRESS);
        txtview=(TextView) findViewById(R.id.txtview);
        btn=(Button) findViewById(R.id.button);

        // Bluetootg bağlantı kontrol edilirken sürekli beklememek
        // Test işlemlerini hızlandırmak için bluetooth kontrol bağlantı kısmı yoruma alındı.
        new deneme.BTbaglan().execute();


    }

    /*private void writeData(String data) {
        try {
            this.outStream = this.btSocket.getOutputStream();
        } catch (IOException var6) {
            Log.d("Jon", "Bug BEFORE Sending stuff", var6);
        }

        byte[] msgBuffer = data.getBytes();

        try {
            this.outStream.write(msgBuffer);
        } catch (IOException var5) {
            Log.d("Jon", "Bug while sending stuff", var5);
        }

    }*/

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

                                            for(int i=0; i< dataLineCount; i++) {
                                                System.out.println(dataLines[i]);
                                            }
                                            txtview.setText(dataLines[0]);
                                            txtview.append(" ");
                                            txtview.append(dataLines[1]);
                                            txtview.append(" ");
                                            txtview.append(dataLines[2]);
                                            txtview.append(" ");
                                            txtview.append(dataLines[3]);
                                            txtview.append(" ");
                                            txtview.append(" ");

                                       /*     if(txtview.getText().toString().equals("..")) {

                                                txtview.setText(data);

                                            } else {
                                                txtview.append("\n"+data);
                                            }
                                            txtview.setText(data);*/
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
            progress = ProgressDialog.show(deneme.this, "Bağlanıyor...", "Lütfen Bekleyin");
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
