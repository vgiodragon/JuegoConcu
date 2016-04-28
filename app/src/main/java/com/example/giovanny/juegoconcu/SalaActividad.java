package com.example.giovanny.juegoconcu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.giovanny.juegoconcu.Figuras.Usuario;
import com.example.giovanny.juegoconcu.Juego.JuegoActividad;
import com.example.giovanny.juegoconcu.Sockets.Client.Client;
import com.example.giovanny.juegoconcu.Sockets.Client.Client2;
import com.example.giovanny.juegoconcu.Sockets.Server.SocketServerThread;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

public class SalaActividad extends AppCompatActivity {

    TextView tGuestIP;
    TextView tHostIP;
    String CurrentIP="";
    String ServidorIP="";
    String message = "";

    ArrayList<Usuario> adversarios;
    Usuario user;

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
                user=new Usuario(R.drawable.bb1,R.drawable.bb12,R.drawable.dragun,R.drawable.dragun12,-4f,0f,50);
                adversarios = new ArrayList<>();
                adversarios.add(new Usuario(R.drawable.bb3,R.drawable.bb32,0f,0f));
                Thread socketServerThread = new Thread(new SocketServerThread(this,user,adversarios));
                socketServerThread.start();
            }
            else{//es un cliente
                user=new Usuario(R.drawable.bb3,R.drawable.bb32,R.drawable.dragun,R.drawable.dragun12,0f,0f,50);
                adversarios = new ArrayList<>();
                adversarios.add(new Usuario(R.drawable.bb1,R.drawable.bb12,-4f,0f));
                Client2 myClient = new Client2(this, ServidorIP,socketServerPORT,tGuestIP,user,adversarios);
                myClient.start();
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
        intent.putExtra("usuario", (Serializable) user);
        intent.putExtra("adversarios", (Serializable)adversarios);
        startActivity(intent);
    }

    private void setHost(String ip){
        tHostIP.setText("Player 1 _ "+ip);
    }

    public void setGuestIP(){
        tGuestIP.setText(message);
    }


    public void Mandar(Socket socket,String mnsj) throws IOException {
        DataOutputStream dOut;
        try {
            dOut = new DataOutputStream(socket.getOutputStream());
            dOut.writeUTF(mnsj);
            dOut.flush(); // Send off the data

            //dOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String Recibir(Socket socket){
        String respuesta="";
        ///Voy a esperar un mensaje del cliente

        try {
            Log.d("HILO","socket:Closed "+socket.isClosed()+"_conected:"+socket.isConnected());
            DataInputStream dIn = new DataInputStream(socket.getInputStream());
            respuesta=dIn.readUTF();

            Log.d("HILO", "respuesta: "+respuesta);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return respuesta;
    }
}
