package edu.udem.feriaint.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Andrea Arroyo on 11/10/2016.
 */

public class ContenidoCultural implements Parcelable {


    private long id;
    private String titulo;
    private Tema tema;
    private String tipo;

    private String imgPortada;
    private String temaNombre;
    private ArrayList <String> formato;
    private ArrayList<String> contenido;

    private boolean favorito;


    public ContenidoCultural(String titulo, Tema tema) {
        this.titulo = titulo;
        this.temaNombre=tema.getNombre();
        this.tema=tema;
        formato=new ArrayList<>();
        contenido=new ArrayList<>();
    }

    public ContenidoCultural(String titulo, int img) {
        this.titulo = titulo;

    }

    protected ContenidoCultural(Parcel in) {
        titulo = in.readString();
        imgPortada = in.readString();
        temaNombre = in.readString();
        formato = in.createStringArrayList();
        contenido = in.createStringArrayList();
    }

    public static final Creator<ContenidoCultural> CREATOR = new Creator<ContenidoCultural>() {
        @Override
        public ContenidoCultural createFromParcel(Parcel in) {
            return new ContenidoCultural(in);
        }

        @Override
        public ContenidoCultural[] newArray(int size) {
            return new ContenidoCultural[size];
        }
    };

    public ContenidoCultural(String titulo, String tema) {

        this.titulo = titulo;
        this.temaNombre=tema;
        formato=new ArrayList<>();
        contenido=new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImgPortada() {
        return imgPortada;
    }

    public void setImgPortada(String imgPortada) {
        this.imgPortada = imgPortada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Tema getTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public ArrayList<String> getFormato() {
        return formato;
    }

    public void setFormato(ArrayList<String> formato) {
        this.formato = formato;
    }

    public ArrayList<String> getContenido() {
        return contenido;
    }

    public void setContenido(ArrayList<String> contenido) {
        this.contenido = contenido;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    public String toString ()
    {


        return getTitulo()+" "+getTema()+" "+getFormato().size()+" "+getContenido().size();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {


        parcel.writeString(titulo);
        parcel.writeString(temaNombre);
        parcel.writeStringList(formato);
        parcel.writeStringList(contenido);

    }
}
