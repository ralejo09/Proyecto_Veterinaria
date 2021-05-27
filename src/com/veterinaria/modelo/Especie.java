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
public class Especie implements Serializable{
    private byte codigoEspecie;
    private String nombre;
    
/////////////////////////////////////////////////////////////////////

    public Especie(byte codigoEspecie, String nombre) {
        this.codigoEspecie = codigoEspecie;
        this.nombre = nombre;
    }
    
/////////////////////////////////////////////////////////////////////

    public byte getCodigoEspecie() {
        return codigoEspecie;
    }

    public void setCodigoEspecie(byte codigoEspecie) {
        this.codigoEspecie = codigoEspecie;
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
        return nombre ;
    }
    
    
}

