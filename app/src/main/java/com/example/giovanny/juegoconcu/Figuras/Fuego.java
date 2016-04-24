package com.example.giovanny.juegoconcu.Figuras;

import android.content.Context;

import java.util.ArrayList;
import java.util.Random;

import com.example.giovanny.juegoconcu.R;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by giovanny on 24/04/16.
 */
public class Fuego {
    private ArrayList<Cuadrado> Fire;
    private ArrayList<Float> xj;
    private ArrayList<Float> yj;
    Random rand = new Random();

    public Fuego(int cntFuego) {
        Fire = new ArrayList<>();
        xj = new ArrayList<>();
        yj = new ArrayList<>();

        xj.add(12.f); xj.add(12.f);
        yj.add(3.f); yj.add(-2.f);

        for(int i=0;i<cntFuego;i++){
            Fire.add(new Cuadrado(R.drawable.fuego,false));
        }
    }
    public void loadTexture(GL10 gl, Context ctx){
        for(Cuadrado element: Fire)
            element.loadTexture(gl, ctx);    // Load image into Texture (NEW)
    }

    public void draw(GL10 gl) {
        int i=0;
        for(Cuadrado element: Fire ){
            gl.glPushMatrix();
                gl.glScalef(0.4f, 0.4f, 0.4f);
                gl.glTranslatef(xj.get(i), yj.get(i), -14.0f);
                element.draw(gl);
            gl.glPopMatrix();
            move(i);
            i++;
        }
    }

    private void move(int i){
        float aux=xj.get(i)-(i+1)*0.1f;
        if(aux>-11.2f)
            xj.set(i,aux);
        else{
            xj.set(i,12.f);
            yj.set(i, (float) (rand.nextFloat()*(5.6*2) - 5.6));
        }
    }

    public float getX(int i){
        return xj.get(i);
    }

    public float getY(int i){
        return yj.get(i);
    }
}
