package com.example.giovanny.juegoconcu.Juego;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import com.example.giovanny.juegoconcu.Figuras.Usuario;
import com.example.giovanny.juegoconcu.R;
import com.example.giovanny.juegoconcu.Sockets.Client.ClienteJuego;
import com.example.giovanny.juegoconcu.Sockets.Server.SocketServerJuego;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class JuegoActividad extends AppCompatActivity {

    MyGLSurfaceView mGLView;
    Usuario user;
    ArrayList<Usuario> adversarios;

    static final int socketServerPORT = 8082;
    String ServidorIP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);
        boolean isServer;
        String id="";
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            isServer = false;
        } else {
            isServer = extras.getBoolean("isServer");
            ServidorIP = extras.getString("ServidorIP");
            id=extras.getString("idU");;
        }
        Log.d("GIOTOVIR","mi id:"+id);
        if(isServer){
            user=new Usuario(R.drawable.bb1,R.drawable.bb12,R.drawable.dragun,R.drawable.dragun12,-4f,0f,80,id);
            adversarios = new ArrayList<>();
            adversarios.add(new Usuario(R.drawable.bb3,R.drawable.bb32,0f,0f,80));
            Thread socketServerThread = new Thread(new SocketServerJuego(this,user,adversarios));
            socketServerThread.start();
        }
        else{
            user=new Usuario(R.drawable.bb3,R.drawable.bb32,R.drawable.dragun,R.drawable.dragun12,0f,0f,80,id);
            adversarios = new ArrayList<>();
            adversarios.add(new Usuario(R.drawable.bb1,R.drawable.bb12,-4f,0f,80));
            ClienteJuego myClient = new ClienteJuego(this, ServidorIP,socketServerPORT,user,adversarios);
            myClient.start();
        }


        // as the ContentView for this Activity
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        mGLView = new MyGLSurfaceView(this,displaymetrics.heightPixels,displaymetrics.widthPixels, user , adversarios);
        setContentView(mGLView);

    }

    @Override
    protected void onPause() {
        super.onPause();
        // The following call pauses the rendering thread.
        // If your OpenGL application is memory intensive,
        // you should consider de-allocating objects that
        // consume significant memory here.
        mGLView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // The following call resumes a paused rendering thread.
        // If you de-allocated graphic objects for onPause()
        // this is a good place to re-allocate them.
        mGLView.onResume();
    }


    public void Mandar(Socket socket,String mnsj) throws IOException {
        DataOutputStream dOut;
        try {
            dOut = new DataOutputStream(socket.getOutputStream());
            dOut.writeUTF(mnsj);
            dOut.flush(); // Send off the data

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String Recibir(Socket socket){
        String respuesta="";

        try {
            //Log.d("HILO","socket:Closed "+socket.isClosed()+"_conected:"+socket.isConnected());
            DataInputStream dIn = new DataInputStream(socket.getInputStream());
            respuesta=dIn.readUTF();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return respuesta;
    }
}
