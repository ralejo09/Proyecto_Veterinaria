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
public class Raza implements Serializable{
    private byte codigoRaza;
    private String nombre;
/////////////////////////////////////////////////////////////////////

    public Raza(byte codigoRaza, String nombre) {
        this.codigoRaza = codigoRaza;
        this.nombre = nombre;
    }
/////////////////////////////////////////////////////////////////////

    public byte getCodigoRaza() {
        return codigoRaza;
    }

    public void setCodigoRaza(byte codigoRaza) {
        this.codigoRaza = codigoRaza;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
/////////////////////////////////////////////////////////////////////    

    @Override
    public String toString() {
        return  nombre;
    }
    
    
}
