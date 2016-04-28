package com.example.giovanny.juegoconcu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SalaActividad extends AppCompatActivity {

    TextView tGuestIP;
    TextView tHostIP;
    String CurrentIP;
    String ServidorIP;
    String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala_actividad);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            CurrentIP = extras.getString("CurrentIP");
            ServidorIP = extras.getString("ServidorIP");
            if(CurrentIP.equals(ServidorIP)){
                setHost(ServidorIP);
            }
            else{
                setHost(ServidorIP);

            }
        }
        tHostIP = (TextView)findViewById(R.id.tHostIP);
        tGuestIP = (TextView)findViewById(R.id.tGuestIPs);
    }

    public void addMessage(String message) {
        this.message += message;
    }

    private void setHost(String ip){
        tHostIP.setText("Player 1 _ "+ip);
    }

    public void setGuestIP(){
        tHostIP.setText(message);
    }

}
