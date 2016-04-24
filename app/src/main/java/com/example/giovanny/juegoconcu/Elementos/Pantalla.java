package com.example.giovanny.juegoconcu.Elementos;

import android.content.Context;

import com.example.giovanny.juegoconcu.Figuras.Cuadrado;
import com.example.giovanny.juegoconcu.R;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by giovanny on 21/04/16.
 */
public class Pantalla {

    private Cuadrado campo;
    private Cuadrado joystick;
    private Cuadrado analogo;
    private Cuadrado vida;

    public Pantalla(){
        campo = new Cuadrado(R.drawable.campo,true);
        vida = new Cuadrado(R.drawable.barra_vida,true);
        joystick = new Cuadrado(R.drawable.joystick,false);
        analogo = new Cuadrado(R.drawable.analogo,false);
    }

    public void loadTexture(GL10 gl, Context ctx){
        campo.loadTexture(gl, ctx);    // Load image into Texture (NEW)
        vida.loadTexture(gl, ctx);    // Load image into Texture (NEW)
        joystick.loadTexture(gl, ctx);    // Load image into Texture (NEW)
        analogo.loadTexture(gl, ctx);    // Load image into Texture (NEW)
    }

    public void Draw(GL10 gl,float Mx,float My, int cantVida){
        gl.glPushMatrix();
            gl.glTranslatef(0f, 0.0f, -7.0f);
            campo.draw(gl);
        gl.glPopMatrix();

        gl.glPushMatrix();
            gl.glTranslatef(-3.4f, -1.5f, -5.0f);
            gl.glScalef(0.4f, 0.4f, 1f);
            joystick.draw(gl);
        gl.glPopMatrix();

        gl.glPushMatrix();
            gl.glTranslatef(-3.32f, -1.45f, -4.9f);
            gl.glScalef(0.3f, 0.3f, 1f);
            gl.glTranslatef(Mx, My, 0f);
            analogo.draw(gl);
        gl.glPopMatrix();


        gl.glPushMatrix();
            gl.glTranslatef(-3.9f, 1.95f, -5.0f);
            gl.glScalef(0.002f, 0.05f, 1f);
                for(int i=0;i<cantVida;i++){
                    vida.draw(gl);
                    gl.glTranslatef(12f, 0f, 0f);
                }
        gl.glPopMatrix();
    }
}
