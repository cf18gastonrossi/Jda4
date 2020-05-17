package com.example.myapplication.ui.Model;

import org.json.JSONException;
import org.json.JSONObject;

public class Contacto {
    private String nombre, apellido, email, urlfoto;

    public Contacto(String nombre, String apellido, String email, String urlfoto) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.urlfoto = urlfoto;
    }

    public Contacto(String nombre, String apellido, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.urlfoto = "";
    }

    public Contacto() {

    }

    public Contacto(JSONObject jsonObject) {
        try {
            nombre = jsonObject.getString("nombre");
            apellido = jsonObject.getString("apellido");
            email = jsonObject.getString("email");
            urlfoto = jsonObject.getString("urlfoto");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrlfoto() {
        return urlfoto;
    }

    public void setUrlfoto(String urlfoto) {
        this.urlfoto = urlfoto;
    }

    public boolean checkInput() {
        if (nombre.equalsIgnoreCase("") || apellido.equalsIgnoreCase("") || email.equalsIgnoreCase("")) {
            return false;
        } else {
            return true;
        }
    }
}
