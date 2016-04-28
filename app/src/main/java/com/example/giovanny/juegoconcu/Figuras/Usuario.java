package com.example.giovanny.juegoconcu.Figuras;

import android.content.Context;

import com.example.giovanny.juegoconcu.VUsuario;

import java.io.Serializable;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by giovanny on 21/04/16.
 */
public class Usuario implements Serializable {

    Cuadrado bb1;
    Cuadrado bb12;
    Cuadrado logo;
    Cuadrado logo2;
    float speedCube = -9f;

    int bb,Sbb;
    int log1,log2;
    boolean activado;
    float angleCube = 0;

    float xo,yo;
    float xi,yi;
    VUsuario vu;

    public float getYi() {
        return yi;
    }

    public float getXi() {
        return xi;
    }

    int vida;

    public Usuario(VUsuario vu){
        this.vu= vu;
        this.bb=vu.getBb();
        this.Sbb=vu.getSbb();
        this.xi=this.xo=vu.getXo();
        this.yi=this.yo=vu.getYo();
        this.vida=vu.getVida();
        this.log1=vu.getLog1();
        this.log2=vu.getLog2();
    }

    public Usuario(int bb,int Sbb, int log1, int log2,float xo,float yo,int vida){
        this.bb=bb;
        this.Sbb=Sbb;
        this.xi=this.xo=xo;
        this.yi=this.yo=yo;
        this.vida=vida;
        this.log1=log1;
        this.log2=log2;

    }

    public Usuario(int bb,int Sbb, float xo,float yo){
        this.bb=bb;
        this.Sbb=Sbb;
        bb1 = new Cuadrado(this.bb,false);
        bb12 = new Cuadrado(this.Sbb,false);
        this.xi=this.xo=xo;
        this.yi=this.yo=yo;
    }

    public void InicioC(){
        bb1 = new Cuadrado(this.bb,false);
        bb12 = new Cuadrado(this.Sbb,false);
    }

    public void InicioLyC(){
        logo = new Cuadrado(this.log1,false);
        logo2 = new Cuadrado(this.log2,false);
        InicioC();
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
        vu.setXi(xi);
        vu.setYi(yi);

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
