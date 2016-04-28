package com.example.giovanny.juegoconcu.Figuras;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import com.example.giovanny.juegoconcu.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by giovanny on 20/04/16.
 */
public class Cuadrado {
    private FloatBuffer vertexBuffer;  // Buffer for vertex-array
    private FloatBuffer texBuffer;    // Buffer for texture-coords-array (NEW)

    private float[] vertices = {  // Vertices for the square
            -1.0f, -1.0f,  0.0f,  // 0. left-bottom
            1.0f, -1.0f,  0.0f,  // 1. right-bottom
            -1.0f,  1.0f,  0.0f,  // 2. left-top
            1.0f,  1.0f,  0.0f   // 3. right-top
    };


    float[] texCoords = { // Texture coords for the above face (NEW)
            0.0f, 1.0f,  // A. left-bottom (NEW)
            1.0f, 1.0f,  // B. right-bottom (NEW)
            0.0f, 0.0f,  // C. left-top (NEW)
            1.0f, 0.0f   // D. right-top (NEW)
    };
    int[] textureIDs = new int[1];   // Array for 1 texture-ID (NEW)

    int img;

    public Cuadrado(int img, boolean campo){

        this.img=img;

        if(campo){
            setVertices(3);
        }

        // Setup vertex array buffer. Vertices in float. A float has 4 bytes
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder()); // Use native byte order
        vertexBuffer = vbb.asFloatBuffer(); // Convert from byte to float
        vertexBuffer.put(vertices);         // Copy data into buffer
        vertexBuffer.position(0);           // Rewind

        // Setup texture-coords-array buffer, in float. An float has 4 bytes (NEW)
        ByteBuffer tbb = ByteBuffer.allocateDirect(texCoords.length * 4);
        tbb.order(ByteOrder.nativeOrder());
        texBuffer = tbb.asFloatBuffer();
        texBuffer.put(texCoords);
        texBuffer.position(0);

    }

    private void setVertices(float v){
        vertices[0]=vertices[6]=-2*v;
        vertices[3]=vertices[9]=2*v;
        vertices[1]=vertices[4]=-v;
        vertices[7]=vertices[10]=v;

    }

    // Load an image into GL texture
    public void loadTexture(GL10 gl, Context context) {
        gl.glGenTextures(1, textureIDs, 0); // Generate texture-ID array

        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[0]);   // Bind to texture ID
        // Set up texture filters
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

        // Construct an input stream to texture image "res\drawable\nehe.png"
        InputStream istream = context.getResources().openRawResource(img);

        Bitmap bitmap;
        try {
            // Read and decode input as bitmap
            bitmap = BitmapFactory.decodeStream(istream);
        } finally {
            try {
                istream.close();
            } catch(IOException e) { }
        }

        // Build Texture from loaded bitmap for the currently-bind texture ID
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();
    }

    // Render the shape
    public void draw(GL10 gl) {

        gl.glFrontFace(GL10.GL_CCW);    // Front face in counter-clockwise orientation
        gl.glEnable(GL10.GL_CULL_FACE); // Enable cull face
        gl.glCullFace(GL10.GL_BACK);    // Cull the back face (don't display)

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);  // Enable texture-coords-array (NEW)
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texBuffer); // Define texture-coords buffer (NEW)

        // Draw the primitives from the vertex-array directly
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[0]);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length / 3);

        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);  // Disable texture-coords-array (NEW)
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisable(GL10.GL_CULL_FACE);
    }
}
