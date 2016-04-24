package com.example.giovanny.juegoconcu.Figuras;

import android.content.Context;


import javax.microedition.khronos.opengles.GL10;

/**
 * Created by giovanny on 21/04/16.
 */
public class Usuario {

    private Cuadrado bb1;
    private Cuadrado bb12;
    private Cuadrado logo;
    private Cuadrado logo2;

    float xo,yo;
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

    public void draw(GL10 gl,float bbx,float bby,float angleCube,boolean activado){

        gl.glPushMatrix();
            gl.glTranslatef(3.4f, -1.5f, -5.0f);
            gl.glScalef(0.4f, 0.4f, 0.4f);
            if(activado)
                logo2.draw(gl);
            else
                logo.draw(gl);
        gl.glPopMatrix();

        gl.glPushMatrix();
            gl.glScalef(0.4f, 0.4f, 0.4f);
            gl.glTranslatef(xo + bbx, yo + bby, -14.0f);
            gl.glRotatef(angleCube, 0f, 0f, 1f); // Rotate
            if(activado)
                bb12.draw(gl);                       // Draw quad ( NEW )
            else
                bb1.draw(gl);                       // Draw quad ( NEW )
        gl.glPopMatrix();

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



}
