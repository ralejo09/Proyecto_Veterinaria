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
public class Estetica extends Servicio implements Serializable{

    public Estetica(byte codigoServicio, String nombreServicio, double precio) {
        super(codigoServicio, nombreServicio, precio);
    }

    @Override
    public double calcularPrecio(double precio) {
        return 50.000;
    }
    
}
