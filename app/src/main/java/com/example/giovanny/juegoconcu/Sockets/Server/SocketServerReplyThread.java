package com.example.giovanny.juegoconcu.Sockets.Server;

import com.example.giovanny.juegoconcu.SalaActividad;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Created by giovanny on 27/04/16.
 */
public class SocketServerReplyThread extends Thread {

    private Socket hostThreadSocket;
    SalaActividad activity;
    String msgReply;

    public SocketServerReplyThread(SalaActividad activity, Socket socket, String conectados) {
        hostThreadSocket = socket;
        this.activity=activity;
        msgReply = conectados;
    }

    @Override
    public void run() {///MANDO MENSAJE
        OutputStream outputStream;

        try {
            outputStream = hostThreadSocket.getOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            printStream.print(msgReply);
            printStream.close();

            //activity.addMessage( "replayed: " + msgReply + "\n");
            

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            activity.addMessage( "Something wrong! " + e.toString() + "\n");
        }

        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                activity.setGuestIP();
            }
        });
    }

}
