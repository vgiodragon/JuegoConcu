package com.example.giovanny.juegoconcu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.giovanny.juegoconcu.Juego.JuegoActividad;
import com.example.giovanny.juegoconcu.Sockets.Client.Client;
import com.example.giovanny.juegoconcu.Sockets.Server.SocketServerThread;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class SalaActividad extends AppCompatActivity {

    TextView tGuestIP;
    TextView tHostIP;
    String CurrentIP="";
    String ServidorIP="";
    String message = "";
    static final int socketServerPORT = 8081;

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
            setHost(ServidorIP);

            if(CurrentIP.equals(ServidorIP)){//es un servidor
                Thread socketServerThread = new Thread(new SocketServerThread(this));
                socketServerThread.start();
            }
            else{//es un cliente
                Client myClient = new Client(this, ServidorIP,socketServerPORT,tGuestIP);
                myClient.execute();
            }
        }
    }

    public void addMessage(String message) {
        this.message += message;
    }

    public String getMessage() {
        return message;
    }

    public void LaunchGame(View view) {
        Intent intent = new Intent(this, JuegoActividad.class);
        startActivity(intent);
    }

    private void setHost(String ip){
        tHostIP.setText("Player 1 _ "+ip);
    }

    public void setGuestIP(){
        tGuestIP.setText(message);
    }


    public void Mandar(Socket socket,String mnsj) throws IOException {
        OutputStream outputStream;
        DataOutputStream dOut;
        try {
            dOut = new DataOutputStream(socket.getOutputStream());
            dOut.writeUTF("This is the first type of message sueño.");
            dOut.flush(); // Send off the data

            dOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String Recibir(Socket socket){
        String respuesta="";
        ///Voy a esperar un mensaje del cliente

        try {
            DataInputStream dIn = new DataInputStream(socket.getInputStream());
            respuesta=dIn.readUTF();

            Log.d("HILO", "respuesta: "+respuesta);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return respuesta;
    }
}
