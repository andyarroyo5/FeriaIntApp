package edu.udem.feriaint.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Andrea Arroyo on 07/10/2016.
 */

public class Evento implements Parcelable {

    private long id;
    private String titulo;
    private Date fechaInicio;
    private Date fechaFinal;
    private String lugar;
    private String descripcion;
    private Tema tema;
    private String hashtag;

    private String tipo;
    private boolean favorito=false;


    /*
    Alguna idea para mayor detalle de evento
    private String fecha;
    private String horarioInicio;
    private String horarioFinal;
    //int fotoId; si hay foto
 */



    public Evento() { }

    public Evento(Long id, String titulo, Date fechaInicio, Date fechaFinal, String lugar, String descripcion, Long tema_id, String hashtag) {
        this.id = id;
        this.titulo = titulo;
        this.lugar=lugar;
        this.fechaInicio=fechaInicio;
        this.fechaFinal=fechaFinal;
        this.descripcion=descripcion;
        tema=new Tema(tema_id);
        this.hashtag=hashtag;

        //Al crear favorito false
    }

    protected Evento(Parcel in) {
        id = in.readLong();
        titulo = in.readString();
        lugar = in.readString();
        descripcion = in.readString();
        tipo = in.readString();
        hashtag = in.readString();
        favorito = in.readByte() != 0;
    }







    public static final Creator<Evento> CREATOR = new Creator<Evento>() {
        @Override
        public Evento createFromParcel(Parcel in) {
            return new Evento(in);
        }

        @Override
        public Evento[] newArray(int size) {
            return new Evento[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    public Tema getTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }

    public String formatoFecha(Date fecha) throws ParseException {

      SimpleDateFormat formatoFecha=new SimpleDateFormat("EEEE dd  MMM");
       String f=formatoFecha.format(fecha);

       return f;
    }

    public String formatoHora(String horario) throws ParseException {

        SimpleDateFormat horaFormato=new SimpleDateFormat("HH:mm");
        Date horarioNuevo=horaFormato.parse(horario);
        String h= horaFormato.format(horarioNuevo);

       return h;
    }


    public String toString ()
    {
        return "ID: "+ getId()+" "+getTitulo()+" "+getFechaInicio()+" "+getFechaFinal()+" "+" favorito "+isFavorito()+" "+getLugar()+" "+getDescripcion();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(titulo);
        parcel.writeString(lugar);
        parcel.writeString(descripcion);
        parcel.writeString(tipo);
        parcel.writeString(hashtag);
        parcel.writeString((favorito ? "true" : "false"));
    }
}