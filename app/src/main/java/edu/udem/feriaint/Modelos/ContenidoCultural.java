package edu.udem.feriaint.Modelos;

import java.util.ArrayList;

/**
 * Created by Andrea Arroyo on 11/10/2016.
 */

public class ContenidoCultural {

    private String titulo;
    private int img;
    private ArrayList contenido;


    public ContenidoCultural(String titulo) {
        this.titulo = titulo;
    }

    public ContenidoCultural(String titulo, int img) {
        this.titulo = titulo;
        this.img = img;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
