package com.example.giovanny.juegoconcu.Sockets;

import android.util.Log;

import com.example.giovanny.juegoconcu.Figuras.Usuario;
import com.example.giovanny.juegoconcu.Juego.JuegoActividad;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by giovanny on 28/04/16.
 */
public class HiloConexion extends Thread {
    Socket socket;
    JuegoActividad activity;
    private ArrayList<Usuario> adversarios;
    private Usuario user;
    private Usuario adver;

    public HiloConexion(Socket socket, JuegoActividad activity, Usuario user,ArrayList<Usuario> adversarios){
        this.socket=socket;
        this.activity=activity;
        this.user=user;
        this.adversarios=adversarios;

    }

    @Override
    public void run() {
        while(true){
            String estado="";
            String respuesta="";
            estado=user.getEstado();
            try {
                activity.Mandar(socket,estado);
                adver=adversarios.get(0);
                respuesta= activity.Recibir(socket);
                adver.setEstado(respuesta);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onDestroy(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
