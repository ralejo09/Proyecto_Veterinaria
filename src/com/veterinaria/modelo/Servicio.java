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
public class Servicio implements Serializable{
    private byte codigoServicio;
    private String nombreServicio;
    private double precio;

/////////////////////////////////////////////////////////////////////
    
    
    public Servicio(byte codigoServicio) {
        this.codigoServicio = codigoServicio;
    }

    public Servicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }
 
    public Servicio(byte codigoServicio, String nombreServicio, double precio) {
        this.codigoServicio = codigoServicio;
        this.nombreServicio = nombreServicio;
        this.precio = precio;
    }
    
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public byte getCodigoServicio() {
        return codigoServicio;
    }

    public void setCodigoServicio(byte codigoServicio) {
        this.codigoServicio = codigoServicio;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    
    public double calcularPrecio(double precio)
    {
        return precio;
    }

    @Override
    public String toString() {
        return nombreServicio + "  __ " + "VALOR: " +"$" + precio ;
    }
    
    
}

