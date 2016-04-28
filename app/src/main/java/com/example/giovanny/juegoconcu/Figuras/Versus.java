package com.example.giovanny.juegoconcu.Figuras;

import android.content.Context;
import android.util.Log;

import com.example.giovanny.juegoconcu.R;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by giovanny on 23/04/16.
 */
public class Versus {

    private Fuego Fire;
    private ArrayList <Usuario> adversarios;

    private Usuario user;

    private int cantFuego;


    public Versus(Usuario user, ArrayList<Usuario> adversarios){
        this.user=user;

        this.adversarios =adversarios;

        cantFuego=2;

        Fire= new Fuego(cantFuego);
    }

    public void loadTexture(GL10 gl, Context ctx){
        Fire.loadTexture(gl,ctx);

        for(Usuario element: adversarios){
            element.loadTexture2(gl,ctx);
        }
    }

    public void draw(GL10 gl,float bbx,float bby) {
        Fire.draw(gl);
        for(Usuario element: adversarios){
            element.draw2(gl,0f,0f);
        }
        quitaVida();
    }


    public void quitaVida(){
        int quito=0;
        for(int i=0;i<cantFuego;i++){
            quito+=seChocaron(Fire.getX(i),Fire.getY(i));
        }

        for(Usuario element: adversarios){
            quito+=seChocaron(element.getXi(),element.getYi());
        }
        user.resVida(quito);
    }

    public int seChocaron(float x,float y){
        float dist = (float) Math.hypot(user.getXi()-x,user.getYi()-y);
        if(dist<1.78f){
            Log.d("Choco", "choco!!");
            return 1;
        }

        return 0;
    }
}
