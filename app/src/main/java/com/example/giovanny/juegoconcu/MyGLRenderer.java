package com.example.giovanny.juegoconcu;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import com.example.giovanny.juegoconcu.Figuras.Cuadrado;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by giovanny on 17/04/16.
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {

    private Cuadrado bb1;
    private Cuadrado bb12;
    private Cuadrado campo;
    private Cuadrado analogo;
    private Cuadrado joystick;
    private Cuadrado logo;
    private Cuadrado logo2;
    private Context ctx;
    private static float angleCube = 0;     // rotational angle in degree for cube
    private static float speedCube = -9f; // rotational speed for cube

    float Mx;
    float My;

    float bbx;
    float bby;

    boolean activado;

    public MyGLRenderer(Context ctx){
        bb1 = new Cuadrado(R.drawable.bb1,false);
        bb12 = new Cuadrado(R.drawable.bb12,false);
        campo = new Cuadrado(R.drawable.campo,true);
        analogo = new Cuadrado(R.drawable.analogo,false);
        joystick = new Cuadrado(R.drawable.joystick,false);
        logo = new Cuadrado(R.drawable.dragun,false);
        logo2 = new Cuadrado(R.drawable.dragun12,false);

        Mx=My=0f;
        bbx=bby=0f;

        activado=false;

        this.ctx=ctx;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Set the background frame color
        gl.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);  // Set color's clear-value to black
        gl.glClearDepthf(1.0f);            // Set depth's clear-value to farthest
        gl.glEnable(GL10.GL_DEPTH_TEST);   // Enables depth-buffer for hidden surface removal
        gl.glDepthFunc(GL10.GL_LEQUAL);    // The type of depth testing to do
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);  // nice perspective view
        gl.glShadeModel(GL10.GL_SMOOTH);   // Enable smooth shading of color
        gl.glDisable(GL10.GL_DITHER);      // Disable dithering for better performance

        // You OpenGL|ES initialization code here

        gl.glEnable(GL10.GL_TEXTURE_2D);  // Enable texture (NEW)
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);  // Set Alpha Blend Function

        // ......
        // Setup Texture, each time the surface is created (NEW)
        bb1.loadTexture(gl, ctx);    // Load image into Texture (NEW)
        bb12.loadTexture(gl, ctx);    // Load image into Texture (NEW)
        campo.loadTexture(gl, ctx);    // Load image into Texture (NEW)
        analogo.loadTexture(gl, ctx);    // Load image into Texture (NEW)
        joystick.loadTexture(gl, ctx);    // Load image into Texture (NEW)
        logo.loadTexture(gl, ctx);    // Load image into Texture (NEW)
        logo2.loadTexture(gl, ctx);    // Load image into Texture (NEW)
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // Adjust the viewport based on geometry changes,
        // such as screen rotation
        if (height == 0) height = 1;   // To prevent divide by zero
        float aspect = (float)width / height;

        // Set the viewport (display area) to cover the entire window
        gl.glViewport(0, 0, width, height);

        // Setup perspective projection, with aspect ratio matches viewport
        gl.glMatrixMode(GL10.GL_PROJECTION); // Select projection matrix
        gl.glLoadIdentity();                 // Reset projection matrix
        // Use perspective projection
        GLU.gluPerspective(gl, 45, aspect, 0.1f, 100.f);

        gl.glMatrixMode(GL10.GL_MODELVIEW);  // Select model-view matrix
        gl.glLoadIdentity();                 // Reset

        // You OpenGL|ES display re-sizing code here
        // ......
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // Draw background color
        // Clear color and depth buffers using clear-value set earlier
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glEnable(GL10.GL_BLEND);       // Turn blending on (NEW)

        // You OpenGL|ES rendering code here
        gl.glLoadIdentity();                 // Reset model-view matrix ( NEW )
        // Translate right, relative to the previous translation ( NEW )
        gl.glPushMatrix();
            gl.glTranslatef(0f, 0.0f, -7.0f);
            campo.draw(gl);
        gl.glPopMatrix();

        gl.glPushMatrix();
            gl.glTranslatef(-3.4f, -1.5f, -5.0f);
            gl.glScalef(0.4f, 0.4f, 0.4f);
            joystick.draw(gl);
        gl.glPopMatrix();

        gl.glPushMatrix();
            gl.glTranslatef(-3.32f, -1.45f, -4.9f);
            gl.glScalef(0.3f, 0.3f, 0.3f);
            gl.glTranslatef(Mx, My, 0f);
            analogo.draw(gl);
        gl.glPopMatrix();

        gl.glPushMatrix();
            gl.glTranslatef(3.4f, -1.5f, -5.0f);
            gl.glScalef(0.4f, 0.4f, 0.4f);
            if(activado)
                logo2.draw(gl);
            else
                logo.draw(gl);
        gl.glPopMatrix();

        gl.glPushMatrix();
            gl.glTranslatef(0f, 2.0f, -6.0f);  // Translate right and into the screen (NEW)
            gl.glScalef(0.4f, 0.4f, 0.4f);
            gl.glTranslatef(bbx, bby, 0f);
            gl.glRotatef(angleCube, 0f, 0f, 1f); // Rotate
            if(activado)
                bb12.draw(gl);                       // Draw quad ( NEW )
            else
                bb1.draw(gl);                       // Draw quad ( NEW )
        gl.glPopMatrix();
        // Update the rotational angle after each refresh.

        if(activado){
            bbx+=Mx/10;
            bby+=My/10;
            angleCube += speedCube;
            angleCube=angleCube%360;
        }
        bbx+=Mx/10;
        bby+=My/10;
        angleCube += speedCube;
        angleCube=angleCube%360;

    }
}
