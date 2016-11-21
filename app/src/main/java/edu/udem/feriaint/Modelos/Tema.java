package edu.udem.feriaint.Modelos;

import java.util.ArrayList;

/**
 * Created by Andrea Arroyo on 25/10/2016.
 */

public class Tema {

    long id;
    String nombre;
    ArrayList<Evento> listaEventos;
    ArrayList<Evento> listaContCultural;
    //color?

    public Tema(String tema) {
        this.nombre = tema;
    }

    public Tema(Long temaId, String temaNombre) {
        this.id=temaId;
        this.nombre=temaNombre;
    }

    public Tema() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String tema) {
        this.nombre = tema;
    }

    public String toString()
    {
        return getId()+" Tema: "+getNombre();
    }
}
