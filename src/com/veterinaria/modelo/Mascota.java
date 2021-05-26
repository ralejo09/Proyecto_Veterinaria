/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.veterinaria.modelo;

import java.io.Serializable;

/**
 *
 * @author ALEJO CARMONA
 */
public class Mascota implements Serializable{
    private String codigoMascota;
    private String nombre;
    private float edad;
    private Sexo sexo;
    private Especie especie;
    private Raza raza;
    private Servicio servicio;
    
/////////////////////////////////////////////////////////////////////

    
    
    public Mascota(String codigoMascota, String nombre, Sexo sexo, Especie especie, Raza raza, Servicio servicio) {
        this.codigoMascota = codigoMascota;
        this.nombre = nombre;
        this.sexo = sexo;
        this.especie = especie;
        this.raza = raza;
        this.servicio = servicio;
    }

    public Mascota(String codigoMascota, String nombre, float edad, Sexo sexo, Especie especie, Raza raza, Servicio servicio) {
        this.codigoMascota = codigoMascota;
        this.nombre = nombre;
        this.edad = edad;
        this.sexo = sexo;
        this.especie = especie;
        this.raza = raza;
        this.servicio = servicio;
    }

    

    
    
/////////////////////////////////////////////////////////////////////
    

    public String getCodigoMascota() {
        return codigoMascota;
    }

    public void setCodigoMascota(String codigoMascota) {
        this.codigoMascota = codigoMascota;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getEdad() {
        return edad;
    }

    public void setEdad(float edad) {
        this.edad = edad;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public Especie getEspecie() {
        return especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    public Raza getRaza() {
        return raza;
    }

    public void setRaza(Raza raza) {
        this.raza = raza;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }
/////////////////////////////////////////////////////////////////////
    
    
}
