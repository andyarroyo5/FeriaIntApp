package edu.udem.feriaint.Modelos;

import java.util.Date;

/**
 * Created by Andrea Arroyo on 09/10/2016.
 */

public class Edicion {

    private String pais;
    private Date fechaInicio;
    private Date fechaFinal;
    private String imgLogo;


    public Edicion(){}


    public Edicion(String pais, Date fechaInicio, Date fechaFinal) {
        this.pais = pais;
        this.fechaInicio=fechaInicio;
        this.fechaFinal=fechaFinal;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
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


    public String toString()
    {
        return  getPais()+" "+getFechaInicio()+" "+getFechaFinal();
    }
}
