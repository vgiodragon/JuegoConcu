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
    int cnt;
    SalaActividad activity;

    SocketServerReplyThread(SalaActividad activity,Socket socket, int c) {
        hostThreadSocket = socket;
        cnt = c;
        this.activity=activity;
    }

    @Override
    public void run() {
        OutputStream outputStream;
        String msgReply = "Hello from Server, you are #" + cnt;

        try {
            outputStream = hostThreadSocket.getOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            printStream.print(msgReply);
            printStream.close();

            activity.addMessage( "replayed: " + msgReply + "\n");

            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    activity.setGuestIP();
                }
            });

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
