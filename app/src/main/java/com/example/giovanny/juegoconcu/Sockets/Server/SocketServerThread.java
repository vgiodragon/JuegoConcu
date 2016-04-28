package com.example.giovanny.juegoconcu.Sockets.Server;

import android.util.Log;

import com.example.giovanny.juegoconcu.SalaActividad;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by giovanny on 27/04/16.
 */
public class SocketServerThread extends Thread {
    ServerSocket serverSocket;
    int count = 1;
    static final int socketServerPORT = 8081;
    SalaActividad activity;
    String respuesta="";

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
                Socket socket = serverSocket.accept();
                count++;
                activity.addMessage( "Player" + count + " _ "///OBTENGO INFO DEL CONECTADO
                        + socket.getInetAddress() + ":"+ socket.getPort() + "\n");

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.setGuestIP();
                    }
                });

                SocketServerReplyThread socketServerReplyThread =///MANDO MENSAJE
                        new SocketServerReplyThread(activity, socket,activity.getMessage());
                socketServerReplyThread.run();


               ///Voy a esperar un mensaje del cliente
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(
                        1024);
                byte[] buffer = new byte[1024];

                int bytesRead;
                InputStream inputStream = socket.getInputStream();

         /*
          * notice: inputStream.read() will block if no data return
          */
                while ((bytesRead = inputStream.read(buffer)) != -1) {///ESPERO MENSAJE!!
                    byteArrayOutputStream.write(buffer, 0, bytesRead);
                    respuesta += byteArrayOutputStream.toString("UTF-8");
                }
                Log.d("HILO",respuesta);

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
