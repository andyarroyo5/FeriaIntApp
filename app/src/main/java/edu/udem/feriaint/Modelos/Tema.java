package edu.udem.feriaint.Modelos;

import android.graphics.Color;

import java.util.Random;

import edu.udem.feriaint.Activities.MainActivity;

/**
 * Created by Andrea Arroyo on 25/10/2016.
 */

public class Tema {

   private long id;
   private String nombre;
   private int color;
   private boolean isEvento;
   private boolean isContCult;
   private boolean seleccionado;

    public Tema() {  }

    public Tema(long temaId) {
        this.id=temaId;
    }

    public Tema(Long temaId, String tema) {
        this.id=temaId;
        this.nombre=tema;

    }

    public Tema(Long temaId, String temaNombre,String tipo) {
        this.id=temaId;
        this.nombre=temaNombre;
        setTipo(tipo, true);
        this.color= getColorRandom();
        seleccionado=false;
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

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isEvento() {
        return isEvento;
    }


   public void setTipo(String tipo, boolean bool)
   {

       //TODO ejemplo de var estatica, hacer CONS
       //if (tipo.equals(android.R.string.EVENTO))

       //Si quiere decir que el tipo es de evento
       if (tipo.equals("evento") && bool)
       {
           setEvento(true);
           setContCult(false);
       }
       else
       {
           setContCult(true);
           setEvento(false);
       }
   }


    public void setEvento(boolean evento) {
        isEvento = evento;

    }

    public boolean isContCult() {
        return isContCult;
    }

    public void setContCult(boolean contCult) {
        isContCult = contCult;
    }

    public String toString()
    {
        return getId()+" Tema: "+getNombre();
    }


    public int getColorRandom() {
        final int colorBase = Color.WHITE;
        Random random = new Random();
        final int baseRojo = Color.red(colorBase);
        final int baseVerde = Color.green(colorBase);
        final int baseAzul = Color.blue(colorBase);

        final int rojo = (baseRojo + random.nextInt(256)) / 2;
        final int verde = (baseVerde + random.nextInt(256)) / 2;
        final int azul = (baseAzul + random.nextInt(256)) / 2;

        return Color.rgb(rojo, verde, azul);
    }

    public void checarRepetido(int colorChecar)
    {
        if (color==colorChecar)
            color= getColorRandom();
    }

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }
}
