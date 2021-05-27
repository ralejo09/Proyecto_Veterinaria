/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.veterinaria.controlador;


import com.veterinaria.excepciones.VeterinariaExcepcion;
import com.veterinaria.modelo.Rol;
import com.veterinaria.modelo.Usuario;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 *
 * @author ALEJO CARMONA
 */

public class ControladorUsuario {
    private Rol[] roles;
    private List<Usuario> usuarios;

    public ControladorUsuario() {
        inicializarColecciones();        
    }
    
    private void inicializarColecciones()
    {
        ///////////////////////////////////////////////////////
        roles = new Rol[2];
        roles[0]= new Rol((byte)1, "Administrador");
        roles[1]= new Rol((byte)2, "Veterinario");
        ///////////////////////////////////////////////////////
        usuarios = new ArrayList<>();//Polimorfismo
        // Donde yo cargaria los usuarios de una base de datos
        // Un archivo de excel, un archivo plano o un SGBD
        // Mysql, Postgres, Oracle, SqlSErver, 
        // Mariadb, MOngo, Dynamo
        usuarios.add(new Usuario("rloaiza@umanizales.edu.co","123456",roles[0]," Administrador \n"+"             Roger Loaiza"));
        usuarios.add(new Usuario("rcarmona@umanizales.edu.co","654321",roles[1]," Veterinario \n"+"         Raul Carmona"));
        usuarios.add(new Usuario("carloaiza@umanizales.edu.co","123456",roles[1]," Veterinario \n"+"         Carlos Loaiza"));
        
        
//        usuarios = new ArrayList<>();
//        cargarDatosUsuarios();
        
    }
    
    
//    public boolean verificarUsuario(String correo, String contrasenia)
//    {
//        for(Usuario usu: usuarios)
//        {
//            if(usu.getCorreo().equals(correo) && usu.getContrasenia().equals(contrasenia))
//            {
//                return true;
//            }
//        }
//        return false;                                                                  
//    }


    public Usuario verificarUsuario(String correo, String contrasenia) 
            throws VeterinariaExcepcion
    {
        if (correo == null || correo.equals("") || contrasenia == null || contrasenia.equals("")) 
        {
           throw new VeterinariaExcepcion("Debe diligenciar todos los datos");
        }
        if(!validarCorreo(correo))
        {
            throw new VeterinariaExcepcion("En el campo correo debe ingresar un " + " correo valido");
        }
        if(contrasenia.length()<6)
        {
            throw new VeterinariaExcepcion("La contraseña debe de tener al menos " + "6 caracteres");
        }
        for(Usuario usu: usuarios)
        {
            if(usu.getCorreo().equals(correo) && usu.getContrasenia().equals(contrasenia))
            {
                return usu;
            }                                                               
        }    
        //Significa que el usuario o la contraseña son erradas
        throw new VeterinariaExcepcion("Los datos ingresados no son correctos");
        
    }        
    
    
//    private void cargarDatosUsuarios() 
//    {
//        File archivo;
//        FileReader fr = null;
//        BufferedReader br;
//
//        try 
//        {
//            archivo = new File("C:\\Users\\ALEJO CARMONA\\Documents\\usuarios.txt");
//            fr = new FileReader(archivo);
//            br = new BufferedReader(fr);
//
//            String linea;
//            String[] datos;
//            while ((linea = br.readLine()) != null) 
//            {
//                datos = linea.split(":");
//                if (validarCorreo(datos[0]) && datos.length==3) 
//                {
//                    if (datos[2].equals("1")) 
//                    {
//                        usuarios.add(new Usuario(datos[0], datos[1], roles[0],datos[3]));
//                    } 
//                    else if (datos[2].equals("2")) 
//                    {
//                        usuarios.add(new Usuario(datos[0], datos[1], roles[1],datos[3]));
//                    }
//                }
//            }
//        } 
//        catch (Exception e) 
//        {
//            e.printStackTrace();
//        } 
//        finally 
//        {
//            try 
//            {
//                if (null != fr) 
//                {
//                    fr.close();
//                }
//            } 
//            catch (Exception e2) 
//            {
//                e2.printStackTrace();
//            }
//        }
//    }

    private boolean validarCorreo(String email) 
    {
        boolean espacios = true;

        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher matcher = pattern.matcher(email);

        for (int i = 0; i < email.length(); i++) 
        {
            if (email.charAt(i) == ' ') 
            {
                espacios = false;
                break;
            }
        }

        return matcher.find() && espacios;
    }
}
