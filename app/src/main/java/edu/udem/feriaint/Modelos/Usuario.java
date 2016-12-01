package edu.udem.feriaint.Modelos;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Andrea Arroyo on 06/11/2016.
 */

public class Usuario implements Parcelable {

    private long id;
    private Uri imgPerfil;
    private String correo;
    private String nombre;
    private String carrera;
    private String twitter;
    private int puntos;


    private ArrayList<Evento> listaEventosFavoritos;
    private ArrayList<ContenidoCultural> listaContCultFavoritos;

    public Usuario(){

        listaEventosFavoritos=new ArrayList<Evento>();
        listaContCultFavoritos=new ArrayList<ContenidoCultural>();

    }
    public Usuario(String twitter) {

        this.twitter = twitter;

        listaEventosFavoritos=new ArrayList<Evento>();
        listaContCultFavoritos=new ArrayList<ContenidoCultural>();

    }


    protected Usuario(Parcel in) {
        id = in.readLong();
        correo = in.readString();
        nombre = in.readString();
        carrera = in.readString();
        twitter = in.readString();
        puntos = in.readInt();
        listaEventosFavoritos = in.createTypedArrayList(Evento.CREATOR);
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

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

    public Uri getImgPerfil() {
        return imgPerfil;
    }

    public void setImgPerfil(Uri imgPerfil) {
        this.imgPerfil = imgPerfil;
    }

    public void agregarPuntos(int puntos)
    {
        this.puntos+=puntos;
    }

    public String toString()
    {
        return getId()+" "+"NOMBRE "+getNombre()+" "+"CORREO "+getCorreo()+" "+"CARRERA "+getCarrera()+" "+"TWITTER "+getTwitter()+" "+"PUNTOS "+getPuntos();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(nombre);
        parcel.writeString(correo);
        parcel.writeString(carrera);
        parcel.writeString(twitter);

    }
}
