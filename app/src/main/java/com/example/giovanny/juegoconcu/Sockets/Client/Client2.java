package com.example.giovanny.juegoconcu.Sockets.Client;

import android.util.Log;
import android.widget.TextView;

import com.example.giovanny.juegoconcu.Figuras.Usuario;
import com.example.giovanny.juegoconcu.SalaActividad;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by giovanny on 28/04/16.
 */
public class Client2 extends Thread {

    String dstAddress;
    int dstPort;
    String response = "";
    TextView textResponse;
    SalaActividad activity;
    private ArrayList<Usuario> adversarios;
    private Usuario user;

    public Client2(SalaActividad activity , String addr, int port, TextView textResponse, Usuario user,ArrayList<Usuario> adversarios) {
        dstAddress = addr;
        dstPort = port;
        this.textResponse = textResponse;
        this.activity=activity;
        this.user=user;
        this.adversarios=adversarios;
    }

    @Override
    public void run() {
        Socket socket = null;

        try {
            socket = new Socket(dstAddress, dstPort);

            String aux=activity.Recibir(socket);
            Log.d("gioTo", aux);

            aux=user.getEstado();
            activity.Mandar(socket, "Estado Cliente"+aux);

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "UnknownHostException: " + e.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "IOException: " + e.toString();
        } finally {
            if (socket != null) {
              /*  try {
                    socket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }*/
            }
        }

    }
}
