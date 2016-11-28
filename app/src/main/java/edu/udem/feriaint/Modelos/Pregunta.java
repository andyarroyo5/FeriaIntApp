package edu.udem.feriaint.Modelos;

import java.util.ArrayList;

/**
 * Created by Andrea Arroyo on 27/11/2016.
 */

public class Pregunta {

    private String pregunta;
    private ArrayList<String> opciones;
    private int respuesta;
    private String imgPregunta;


    public Pregunta(String pregunta, ArrayList<String> opciones, int respuesta) {
        this.pregunta = pregunta;
        this.opciones = opciones;
        this.respuesta = respuesta;
    }


    public String getPregunta() {
        return pregunta;
    }

    public String getImgPregunta() {
        return imgPregunta;
    }

    public void setImgPregunta(String imgPregunta) {
        this.imgPregunta = imgPregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public ArrayList<String> getOpciones() {
        return opciones;
    }

    public void setOpciones(ArrayList<String> opciones) {
        this.opciones = opciones;
    }

    public int getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(int respuesta) {
        this.respuesta = respuesta;
    }
}
