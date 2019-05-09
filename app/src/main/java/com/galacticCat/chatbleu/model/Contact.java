package com.galacticCat.chatbleu.model;

public class Contact {
    private int imgFoto;
    private String nombre;
    private String numero;

    public Contact(int imgFoto, String nombre, String numero) {
        this.imgFoto = imgFoto;
        this.nombre = nombre;
        this.numero = numero;
    }

    public Contact(String nombre, String numero) {
        this.nombre = nombre;
        this.numero = numero;
    }

    public int getImgFoto() {
        return imgFoto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNumero() {
        return numero;
    }


}
