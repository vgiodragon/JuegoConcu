package com.example.giovanny.juegoconcu.Sockets.Client;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.example.giovanny.juegoconcu.SalaActividad;
import com.example.giovanny.juegoconcu.Sockets.Server.SocketServerReplyThread;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by giovanny on 27/04/16.
 */
public class Client extends AsyncTask<Void, Void, Void> {

    String dstAddress;
    int dstPort;
    String response = "";
    TextView textResponse;
    SalaActividad activity;

    public Client(SalaActividad activity , String addr, int port, TextView textResponse) {
        dstAddress = addr;
        dstPort = port;
        this.textResponse = textResponse;
        this.activity=activity;
    }

    @Override
    protected Void doInBackground(Void... arg0) {

        Socket socket = null;

        try {
            socket = new Socket(dstAddress, dstPort);
            //RECIBO MENSAJE
            //response=activity.Recibir(socket);
            Log.d("HILO", "mando desde el cliente");
            activity.Mandar(socket, "desde el CLIENTE!!!");
            Log.d("HILO", "Ahora voy a recibir lo del servidor");
            //activity.Recibir(socket);

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

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        textResponse.setText(response);
        super.onPostExecute(result);
    }

}