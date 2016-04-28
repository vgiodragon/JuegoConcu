package com.example.giovanny.juegoconcu;

import android.util.Log;

import com.example.giovanny.juegoconcu.Figuras.Usuario;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by giovanny on 28/04/16.
 */
public class HiloConexion extends Thread {
    Socket socket;
    SalaActividad activity;
    private ArrayList<Usuario> adversarios;
    private Usuario user;

    public HiloConexion(Socket socket, SalaActividad activity,Usuario user,ArrayList<Usuario> adversarios){
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
                activity.Mandar(socket, "Estado Servidor :"+estado);
                respuesta= activity.Recibir(socket);
                Log.d("gioTo", respuesta);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
