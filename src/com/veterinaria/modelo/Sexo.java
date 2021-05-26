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
public class Sexo implements Serializable{
    private byte codigoSexo;
    private String nombre;
/////////////////////////////////////////////////////////////////////

    public Sexo(byte codigoSexo, String nombre) {
        this.codigoSexo = codigoSexo;
        this.nombre = nombre;
    }
/////////////////////////////////////////////////////////////////////

    public byte getCodigoSexo() {
        return codigoSexo;
    }

    public void setCodigoSexo(byte codigoSexo) {
        this.codigoSexo = codigoSexo;
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
        return nombre;
    }
    
    
}
