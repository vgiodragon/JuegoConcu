package com.example.giovanny.juegoconcu;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.giovanny.juegoconcu.Juego.JuegoActividad;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteOrder;

public class Principal extends AppCompatActivity {

    TextView tip1;
    EditText tserver;
    String CurrentIP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        tip1 = (TextView) findViewById(R.id.tip1);
        tserver = (EditText) findViewById(R.id.etServerIP);
        CurrentIP =wifiIpAddress(this);
        tip1.setText(CurrentIP);

    }

    public void LaunchSala(View view) {
        Intent intent = new Intent(this, SalaActividad.class);
        intent.putExtra("ServidorIP", CurrentIP);
        intent.putExtra("CurrentIP", CurrentIP);
        startActivity(intent);
    }

    public void Conectarse(View view) {
        String aux = String.valueOf(tserver.getText());
        Intent intent = new Intent(this, SalaActividad.class);
        intent.putExtra("ServidorIP", tserver.getText());
        intent.putExtra("CurrentIP", CurrentIP);
        startActivity(intent);
    }

    protected String wifiIpAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();

        // Convert little-endian to big-endianif needed
        if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
            ipAddress = Integer.reverseBytes(ipAddress);
        }

        byte[] ipByteArray = BigInteger.valueOf(ipAddress).toByteArray();

        String ipAddressString;
        try {
            ipAddressString = InetAddress.getByAddress(ipByteArray).getHostAddress();
        } catch (UnknownHostException ex) {
            Log.e("WIFIIP", "Unable to get host address.");
            ipAddressString = null;
        }

        return ipAddressString;
    }
}
