package com.example.giovanny.juegoconcu.Sockets.Client;

import android.util.Log;
import android.widget.TextView;

import com.example.giovanny.juegoconcu.SalaActividad;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by giovanny on 28/04/16.
 */
public class Client2 extends Thread {

    String dstAddress;
    int dstPort;
    String response = "";
    TextView textResponse;
    SalaActividad activity;

    public Client2(SalaActividad activity , String addr, int port, TextView textResponse) {
        dstAddress = addr;
        dstPort = port;
        this.textResponse = textResponse;
        this.activity=activity;
    }

    @Override
    public void run() {
        Socket socket = null;

        try {
            socket = new Socket(dstAddress, dstPort);

            activity.Recibir(socket);

            activity.Mandar(socket, "desde el CLIENTE!!!");
            //Log.d("HILO", "socket:Closed " + socket.isClosed() + "_conected:" + socket.isConnected());

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
