package edu.udem.feriaint.Modelos;

import java.util.ArrayList;

/**
 * Created by Andrea Arroyo on 25/10/2016.
 */

public class Tema {

    int id;
    String tema;
    ArrayList<Evento> listaEventos;
    //color?

    public Tema(String tema) {
        this.tema = tema;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }
}
