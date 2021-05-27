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
public class RegistroServicio extends Mascota implements Serializable{
    private String fecha;
    
/////////////////////////////////////////////////////////////////////
   
    public RegistroServicio(String fecha, String codigoMascota, String nombre, Sexo sexo, Especie especie, Raza raza, Servicio servicio) {
        super(codigoMascota, nombre, sexo, especie, raza, servicio);
        this.fecha = fecha;
    }
    
/////////////////////////////////////////////////////////////////////

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    
    
}
