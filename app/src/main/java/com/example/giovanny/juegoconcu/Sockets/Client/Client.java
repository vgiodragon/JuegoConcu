package com.example.giovanny.juegoconcu.Sockets.Client;

import android.os.AsyncTask;
import android.widget.TextView;

import com.example.giovanny.juegoconcu.SalaActividad;
import com.example.giovanny.juegoconcu.Sockets.Server.SocketServerReplyThread;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
            /*ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(
                    1024);
            byte[] buffer = new byte[1024];

            int bytesRead;
            InputStream inputStream = socket.getInputStream();


          //notice: inputStream.read() will block if no data return

            while ((bytesRead = inputStream.read(buffer)) != -1) {///ESPERO MENSAJE!!
                byteArrayOutputStream.write(buffer, 0, bytesRead);
                response += byteArrayOutputStream.toString("UTF-8");
            }
            */


            SocketServerReplyThread socketServerReplyThread =///MANDO MENSAJE
                    new SocketServerReplyThread(activity, socket, "Te estoy mandando mi estado desde el cliente");
            socketServerReplyThread.run();


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