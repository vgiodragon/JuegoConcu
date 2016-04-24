package com.example.giovanny.juegoconcu;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import com.example.giovanny.juegoconcu.Figuras.Cuadrado;
import com.example.giovanny.juegoconcu.Elementos.Pantalla;
import com.example.giovanny.juegoconcu.Figuras.Usuario;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by giovanny on 17/04/16.
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {



    Usuario user[];

    private Context ctx;

    private Pantalla pantalla;

    private static float angleCube = 0;     // rotational angle in degree for cube
    private static float speedCube = -9f; // rotational speed for cube

    float Mx;
    float My;

    float bbx;
    float bby;

    boolean dentroY;

    boolean activado;

    public MyGLRenderer(Context ctx){

        pantalla = new Pantalla();///limite de x:12.10   y:6.20
        user = new Usuario[]{new Usuario(R.drawable.bb1,R.drawable.bb12,R.drawable.dragun,R.drawable.dragun12,-4f,0f,10)};

        Mx=My=0f;
        bbx=bby=0f;

        activado=false;
        dentroY=false;

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
        CargarImagenes(gl);
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
        pantalla.Draw(gl, Mx, My,2);
        user[0].draw(gl, bbx, bby, angleCube, activado);
        // Update the rotational angle after each refresh.

        if(activado){
            VerificoX();
            VerificoY();
            angleCube += speedCube;
            angleCube=angleCube%360;
        }
        VerificoX();
        VerificoY();
        angleCube += speedCube;
        angleCube=angleCube%360;

    }

    public void VerificoX(){
        if(user[0].fueraX(bbx)==0)
            bbx+=Mx/10;
        else if(user[0].fueraX(bbx)==-1)
            bbx-=Mx/2;
        else if(user[0].fueraX(bbx)==1)
            bbx-=Mx/2;
    }

    public void VerificoY(){
        if(user[0].fueraY(bby)==0)
            bby += My / 10;
        else if(user[0].fueraY(bby)==-1)
            bby -= 3*My / 5;
        else if(user[0].fueraY(bby)==1)
            bby -= My / 2;
    }


    public void CargarImagenes(GL10 gl){
        pantalla.loadTexture(gl,ctx);
        user[0].loadTexture(gl, ctx);    // Load image into Texture (NEW)
        // Setup Texture, each time the surface is created (NEW)

    }
}
