package com.example.giovanny.juegoconcu.Sockets.Server;

import android.util.Log;

import com.example.giovanny.juegoconcu.Figuras.Usuario;
import com.example.giovanny.juegoconcu.HiloConexion;
import com.example.giovanny.juegoconcu.Juego.JuegoActividad;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by giovanny on 28/04/16.
 */
public class SocketServerJuego extends Thread {
    ServerSocket serverSocket;
    int count = 1;
    static final int socketServerPORT = 8082;
    JuegoActividad activity;
    Usuario user;
    ArrayList<Usuario> adversarios;

    public SocketServerJuego(JuegoActividad activity, Usuario user,ArrayList<Usuario> adversarios){
        this.activity=activity;
        this.user=user;
        this.adversarios=adversarios;
    }

    @Override
    public void run() {
        try {
            // create ServerSocket using specified port
            serverSocket = new ServerSocket(socketServerPORT);

            while (true) {
                Log.d("gioTo", "Espero que alguien se conecte a juego");
                Socket socket = serverSocket.accept();
                HiloConexion hc=new HiloConexion(socket,activity,user,adversarios);
                hc.start();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
