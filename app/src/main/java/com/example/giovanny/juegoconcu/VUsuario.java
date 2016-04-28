package com.example.giovanny.juegoconcu;

import java.io.Serializable;

/**
 * Created by giovanny on 28/04/16.
 */
public class VUsuario implements Serializable {

    int bb,Sbb;
    int log1,log2;

    float xo,yo;
    float xi,yi;

    int vida;
    boolean activado;

    public VUsuario(int bb,int Sbb, int log1, int log2,float xo,float yo,int vida){
        this.bb=bb;
        this.Sbb=Sbb;
        this.xi=this.xo=xo;
        this.yi=this.yo=yo;
        this.vida=vida;
        this.log1=log1;
        this.log2=log2;
    }

    public String getEstado(){
        return xi+"&&"+yi+"&&"+activado;
    }

    public int getBb() {
        return bb;
    }

    public void setBb(int bb) {
        this.bb = bb;
    }

    public int getSbb() {
        return Sbb;
    }

    public void setSbb(int sbb) {
        Sbb = sbb;
    }

    public int getLog1() {
        return log1;
    }

    public void setLog1(int log1) {
        this.log1 = log1;
    }

    public int getLog2() {
        return log2;
    }

    public void setLog2(int log2) {
        this.log2 = log2;
    }

    public float getXo() {
        return xo;
    }

    public void setXo(float xo) {
        this.xo = xo;
    }

    public float getYo() {
        return yo;
    }

    public void setYo(float yo) {
        this.yo = yo;
    }

    public float getXi() {
        return xi;
    }

    public void setXi(float xi) {
        this.xi = xi;
    }

    public float getYi() {
        return yi;
    }

    public void setYi(float yi) {
        this.yi = yi;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public boolean isActivado() {
        return activado;
    }

    public void setActivado(boolean activado) {
        this.activado = activado;
    }
}
