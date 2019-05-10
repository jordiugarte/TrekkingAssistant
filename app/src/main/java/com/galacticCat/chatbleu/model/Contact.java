package com.galacticCat.chatbleu.model;

public class Contact {
    private String nombre;
    private String numero;

    public Contact(String nombre, String numero) {
        this.nombre = nombre;
        this.numero = numero;
    }

    //public Contact(String nombre, String numero) {
      //  this.nombre = nombre;
        //this.numero = numero;
    //}



    public String getNombre() {
        return nombre;
    }

    public String getNumero() {
        return numero;
    }


}
