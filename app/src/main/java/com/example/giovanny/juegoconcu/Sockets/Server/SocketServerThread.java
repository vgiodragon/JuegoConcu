package com.example.giovanny.juegoconcu.Sockets.Server;

import android.util.Log;

import com.example.giovanny.juegoconcu.SalaActividad;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by giovanny on 27/04/16.
 */
public class SocketServerThread extends Thread {
    ServerSocket serverSocket;
    int count = 0;
    static final int socketServerPORT = 8081;
    SalaActividad activity;

    public SocketServerThread(SalaActividad activity){
        this.activity=activity;
    }

    @Override
    public void run() {
        try {
            // create ServerSocket using specified port
            serverSocket = new ServerSocket(socketServerPORT);

            while (true) {
                // block the call until connection is created and return
                // Socket object
                Log.d("Hilo", "Espero alguien se conecte");
                Socket socket = serverSocket.accept();
                Log.d("Hilo", "Alguiense se conecto!");
                count++;
                activity.addMessage("#" + count + " from "
                        + socket.getInetAddress() + ":"
                        + socket.getPort() + "\n");

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.setGuestIP();
                    }
                });

                SocketServerReplyThread socketServerReplyThread =
                        new SocketServerReplyThread(activity,socket, count);
                socketServerReplyThread.run();

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void onDestroy() {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
