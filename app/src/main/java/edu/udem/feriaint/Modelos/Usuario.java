package edu.udem.feriaint.Modelos;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Andrea Arroyo on 06/11/2016.
 */

public class Usuario {

    private long id;
    private String correo;
    private String nombre;
    private String carrera;
    private String twitter;
    private int puntos;

    private ArrayList<Evento> listaEventosFavoritos;
    private ArrayList<ContenidoCultural> listaContCultFavoritos;

    public Usuario(){}
    public Usuario(String twitter) {
        this.twitter = twitter;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public ArrayList<Evento> getListaEventosFavoritos() {
        return listaEventosFavoritos;
    }

    public void setListaEventosFavoritos(ArrayList<Evento> listaEventosFavoritos) {
        this.listaEventosFavoritos = listaEventosFavoritos;
    }

    public ArrayList<ContenidoCultural> getListaContCultFavoritos() {
        return listaContCultFavoritos;
    }

    public void setListaContCultFavoritos(ArrayList<ContenidoCultural> listaContCultFavoritos) {
        this.listaContCultFavoritos = listaContCultFavoritos;
    }

    public String toString()
    {
        return getId()+" "+getNombre()+" "+getCorreo()+" "+getTwitter()+" "+getPuntos()+" "+getCarrera();
    }


}
