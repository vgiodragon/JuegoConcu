package com.example.giovanny.juegoconcu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.giovanny.juegoconcu.Sockets.Server.SocketServerThread;

public class SalaActividad extends AppCompatActivity {

    TextView tGuestIP;
    TextView tHostIP;
    String CurrentIP="";
    String ServidorIP="";
    String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala_actividad);

        tHostIP = (TextView)findViewById(R.id.tHost);
        tGuestIP = (TextView)findViewById(R.id.tGuestIPs);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            CurrentIP = extras.getString("CurrentIP");
            ServidorIP = extras.getString("ServidorIP");
            if(CurrentIP.equals(ServidorIP)){
                setHost(ServidorIP);
            }
            else{
                setHost(ServidorIP);
                setHost(CurrentIP);

            }
        }

        Thread socketServerThread = new Thread(new SocketServerThread(this));
        socketServerThread.start();
    }

    public void addMessage(String message) {
        this.message += message;
    }

    private void setHost(String ip){
        tHostIP.setText("Player 1 _ "+ip);
    }

    public void setGuestIP(){
        tGuestIP.setText(message);
    }

}
