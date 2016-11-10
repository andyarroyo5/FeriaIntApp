package edu.udem.feriaint.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    private String tipo;

    private String hashtag;

    private boolean favorito;

    private String fecha;
    private String horarioInicio;
    private String horarioFinal;

    //int fotoId;


    public Evento() { }


    public Evento(String titulo, String fecha, String lugar) {
        this.titulo = titulo;
        this.fecha = fecha;
        this.lugar=lugar;
        //this.fotoId = photoId;
    }
   /* public Evento(String titulo, String fecha,String horarioInicio, String horarioFinal, String lugar,String descripcion) {
        this.titulo = titulo;
        this.fecha = fecha;
        this.lugar=lugar;
        this.horarioInicio=horarioInicio;
        this.horarioFinal=horarioFinal;
        this.descripcion=descripcion;
        //this.fotoId = photoId;
    }*/

    public Evento(String titulo, Date fechaInicio, Date fechaFinal, String lugar,String descripcion) {
        this.titulo = titulo;
        this.lugar=lugar;
        this.fechaInicio=fechaInicio;
        this.fechaFinal=fechaFinal;
        this.descripcion=descripcion;
        //this.fotoId = photoId;
    }
    /*public Evento(String titulo, String fechaInicio, String fechaFinal, String lugar, String horario,String descripcion) {
        this.titulo = titulo;
       // this.fecha = fechaInicio+" al "+fechaFinal;
        this.lugar=lugar;
        //this.horario=horario;
        this.descripcion=descripcion;
        this.fechaInicio=fechaInicio;
        this.fechaFinal=fechaFinal;
        //this.fotoId = photoId;
    }*/

    protected Evento(Parcel in) {
        id = in.readLong();
        titulo = in.readString();
        lugar = in.readString();
        descripcion = in.readString();
        tipo = in.readString();
        hashtag = in.readString();
        favorito = in.readByte() != 0;
        fecha = in.readString();
        horarioInicio = in.readString();
        horarioFinal = in.readString();
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

    public String getFecha(){
        return fecha;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(String horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public String getHorarioFinal() {
        return horarioFinal;
    }

    public void setHorarioFinal(String horarioFinal) {
        this.horarioFinal = horarioFinal;
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

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
        return getId()+" "+getTitulo()+" "+getFechaInicio()+" "+getFechaFinal()+" "+getLugar()+" "+getDescripcion();
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
        parcel.writeByte((byte) (favorito ? 1 : 0));
        parcel.writeString(fecha);
        parcel.writeString(horarioInicio);
        parcel.writeString(horarioFinal);
    }
}