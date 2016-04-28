package com.example.giovanny.juegoconcu.Figuras;

import android.content.Context;
import android.util.Log;


import javax.microedition.khronos.opengles.GL10;

/**
 * Created by giovanny on 21/04/16.
 */
public class Usuario {

    private Cuadrado bb1;
    private Cuadrado bb12;
    private Cuadrado logo;
    private Cuadrado logo2;
    float speedCube = -9f;

    boolean activado;
    float angleCube = 0;

    private float xo,yo;
    private float xi,yi;

    public float getYi() {
        return yi;
    }

    public float getXi() {
        return xi;
    }

    int vida;

    public Usuario(int bb,int Sbb, int log1, int log2,float xo,float yo,int vida){
        bb1 = new Cuadrado(bb,false);
        bb12 = new Cuadrado(Sbb,false);
        logo = new Cuadrado(log1,false);
        logo2 = new Cuadrado(log2,false);
        this.xo=xo;
        this.yo=yo;
        this.vida=vida;
    }

    public Usuario(int bb,int Sbb, float xo,float yo){
        bb1 = new Cuadrado(bb,false);
        bb12 = new Cuadrado(Sbb,false);
        this.xo=xo;
        this.yo=yo;
    }

    public void draw(GL10 gl,float bbx,float bby){
        gl.glPushMatrix();
        gl.glTranslatef(3.4f, -1.5f, -5.0f);
        gl.glScalef(0.4f, 0.4f, 0.4f);
            if(activado)
                logo2.draw(gl);
            else
                logo.draw(gl);
        gl.glPopMatrix();

        xi=xo + bbx;
        yi=yo + bby;

        gl.glPushMatrix();
            gl.glScalef(0.4f, 0.4f, 0.4f);
            gl.glTranslatef(xi, yi, -14.0f);
            //Log.d("choco", "xo: " + (xo + bbx)+"_yo: "+(yo + bby));
            gl.glRotatef(angleCube, 0f, 0f, 1f); // Rotate
                if(activado)
                    bb12.draw(gl);                       // Draw quad ( NEW )
                else
                    bb1.draw(gl);                       // Draw quad ( NEW )
        gl.glPopMatrix();


        if(activado){
            angleCube += speedCube;
            angleCube=angleCube%360;
        }
        angleCube += speedCube;
        angleCube=angleCube%360;
    }

    public void draw2(GL10 gl,float bbx,float bby){
        gl.glPushMatrix();

        xi=xo + bbx;
        yi=yo + bby;

        gl.glPushMatrix();
        gl.glScalef(0.4f, 0.4f, 0.4f);
        gl.glTranslatef(xi, yi, -14.0f);
        //Log.d("choco", "xo: " + (xo + bbx)+"_yo: "+(yo + bby));
        gl.glRotatef(angleCube, 0f, 0f, 1f); // Rotate
        if(activado)
            bb12.draw(gl);                       // Draw quad ( NEW )
        else
            bb1.draw(gl);                       // Draw quad ( NEW )
        gl.glPopMatrix();

        if(activado){
            angleCube += speedCube;
        }
        angleCube += speedCube;
        angleCube=angleCube%360;
    }

    public int fueraX(float bbx){
        if(bbx+xo<-11.0f) return -1;
        else if(bbx+xo>11.0f) return +1;

        return 0;
    }

    public int fueraY(float bby){
     if(bby+yo<-5.8) return -1;
        else if(bby+yo>5.8) return +1;

        return 0;
    }

    public void loadTexture(GL10 gl, Context ctx){
        bb1.loadTexture(gl, ctx);    // Load image into Texture (NEW)
        bb12.loadTexture(gl, ctx);    // Load image into Texture (NEW)
        logo.loadTexture(gl, ctx);    // Load image into Texture (NEW)
        logo2.loadTexture(gl, ctx);    // Load image into Texture (NEW)
    }

    public void loadTexture2(GL10 gl, Context ctx){
        bb1.loadTexture(gl, ctx);    // Load image into Texture (NEW)
        bb12.loadTexture(gl, ctx);    // Load image into Texture (NEW)
    }

    public String getEstado(){
        return xi+"&&"+yi+"&&"+activado;
    }

    public void setEstado(){

    }

    public boolean getActivado() {
        return activado;
    }

    public void setActivado(boolean activado) {
        this.activado = activado;
    }

    public synchronized int getVida() {
        return vida;
    }

    public synchronized void resVida(int val) {
        if(vida >0)
        this.vida -=val;
    }

}
