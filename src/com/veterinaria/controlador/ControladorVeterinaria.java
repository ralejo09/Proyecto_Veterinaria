/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.veterinaria.controlador;

import com.veterinaria.modelo.TotalRaza;
import com.veterinaria.excepciones.VeterinariaExcepcion;
import com.veterinaria.modelo.Especie;
import com.veterinaria.modelo.Raza;
import com.veterinaria.modelo.RegistroServicio;
import com.veterinaria.modelo.Servicio;
import com.veterinaria.modelo.Sexo;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ALEJO CARMONA
 */
public class ControladorVeterinaria {
    public static final int NUMERO_MASCOTAS=2000;
    private RegistroServicio[]regServicios;
    private int contadorServicios=0;
    private int contadorRaza=0;
    private Sexo[] sexos;
    private Especie[] especies;
    private Raza[] razas;
    private Servicio[] servicio;
    private TotalRaza[] totalRaza;
    
    
    public ControladorVeterinaria() 
    {
        regServicios= new RegistroServicio[NUMERO_MASCOTAS];
        inicializarSexo();
        inicializarEspecie();
        inicializarRaza((byte)1);
        
//        inicializarRaza1();
        
        inicializarServicio();
        inicializarMascotas();
        escribirFichero();
//        elementosRepetidos(razas,NUMERO_MASCOTAS);
//        LeerFichero();
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    //Arreglos de El Sexo,Especie,Raza,Servicio total de la raza
    
    private void inicializarSexo()
    {
        sexos= new Sexo[2];
        sexos[0] = new Sexo((byte)1, "Macho");
        sexos[1] = new Sexo((byte)2, "Hembra");
    }
    
    private void inicializarEspecie()
    {
        especies= new Especie[2];
        especies[0] = new Especie((byte)1, "Gato");
        especies[1] = new Especie((byte)2, "Perro");
    }
    
    public void inicializarRaza(byte codigoEspecie)
    {
        switch(codigoEspecie)
        {
            case 1:
                razas =new Raza[45];
                razas[0]= new Raza((byte)1, "Maine Coon");
                razas[1]= new Raza((byte)2, "Gato Persa");           
                razas[2]= new Raza((byte)3, "Sphynx");           
                razas[3]= new Raza((byte)4, "Gato Esfinge");           
                razas[4]= new Raza((byte)5, "Gato Saimés");           
                razas[5]= new Raza((byte)6, "Gato Bengalí");           
                razas[6]= new Raza((byte)7, "Gato Exótico");           
                razas[7]= new Raza((byte)8, "Bosque de Noruega");           
                razas[8]= new Raza((byte)9, "Gato Siberiano");           
                razas[9]= new Raza((byte)10, "Azul Ruso");           
                razas[10]= new Raza((byte)11, "Gato Ragdoll");           
                razas[11]= new Raza((byte)12, "British Shorthair");           
                razas[12]= new Raza((byte)13, "Gato Oriental");           
                razas[13]= new Raza((byte)14, "Gato Birmano");           
                razas[14]= new Raza((byte)15, "Angora Turco");           
                razas[15]= new Raza((byte)16, "Van Turco");           
                razas[16]= new Raza((byte)17, "Gato Himalayo");           
                razas[17]= new Raza((byte)18, "Savannah");           
                razas[18]= new Raza((byte)19, "Nebelung");           
                razas[19]= new Raza((byte)20, "PeterBald");           
                razas[20]= new Raza((byte)21, "Lykoi");           
                razas[21]= new Raza((byte)22, "Munchkin");           
                razas[22]= new Raza((byte)23, "Burmés");           
                razas[23]= new Raza((byte)24, "Tonkinés");           
                razas[24]= new Raza((byte)25, "Curl Americano");           
                razas[25]= new Raza((byte)26, "Scottish Fold");           
                razas[26]= new Raza((byte)27, "Burmilla");           
                razas[27]= new Raza((byte)28, "Abisinio");           
                razas[28]= new Raza((byte)29, "Gato Bombay");           
                razas[29]= new Raza((byte)30, "Korat");           
                razas[30]= new Raza((byte)31, "Bobtail Japonés");           
                razas[31]= new Raza((byte)32, "Gato Balinés");           
                razas[32]= new Raza((byte)33, "Singapura");           
                razas[33]= new Raza((byte)34, "Laperm");           
                razas[34]= new Raza((byte)35, "Devon Rex");           
                razas[35]= new Raza((byte)36, "Cornish Rex");           
                razas[36]= new Raza((byte)37, "Selkirk Rex");           
                razas[37]= new Raza((byte)38, "Gato Común Europeo");           
                razas[38]= new Raza((byte)39, "Snowshoe");           
                razas[39]= new Raza((byte)40, "Gato Cartujo");           
                razas[40]= new Raza((byte)41, "Mau Egipcio");           
                razas[41]= new Raza((byte)42, "Ocicat");           
                razas[42]= new Raza((byte)43, "Toyger");           
                razas[43]= new Raza((byte)44, "Gato Manx");           
                razas[44]= new Raza((byte)45, "Gato Ragamuffin");       
                break;
                
            case 2:
                razas =new Raza[25];
                razas[0]= new Raza((byte)46, "Cocker spaniel");
                razas[1]= new Raza((byte)47, "Pomerania");  
                razas[2]= new Raza((byte)48, "Bulldog Francés");
                razas[3]= new Raza((byte)49, "Carlino");
                razas[4]= new Raza((byte)50, "Pug");
                razas[5]= new Raza((byte)51, "Chihuahua");
                razas[6]= new Raza((byte)52, "Perro salchicha");
                razas[7]= new Raza((byte)53, "Teckel");
                razas[8]= new Raza((byte)54, "Pequinés");
                razas[9]= new Raza((byte)55, "Pinscher mini");
                razas[10]= new Raza((byte)56, "Boston Terrier");
                razas[11]= new Raza((byte)57, "Scottish Terrier");
                razas[12]= new Raza((byte)58, "Basenji");
                razas[13]= new Raza((byte)59, "Corgi");
                razas[14]= new Raza((byte)60, "Terrier Tibetano");
                razas[15]= new Raza((byte)61, "Alabai");
                razas[16]= new Raza((byte)62, "Crestado Chino");
                razas[17]= new Raza((byte)63, "Chow Chow");
                razas[18]= new Raza((byte)64, "Lhasa Apso");
                razas[19]= new Raza((byte)65, "Shar Pei");
                razas[20]= new Raza((byte)66, "Akita Inu japonés");
                razas[21]= new Raza((byte)67, "Chongqing");
                razas[22]= new Raza((byte)68, "Bichón Maltés");                
                razas[23]= new Raza((byte)69, "french poodle");                
                razas[24]= new Raza((byte)70, "Criollo");                
                break;
        }   
        
    } 
    
    private void inicializarServicio()
    {
        servicio= new Servicio[4];
        servicio[0] = new Servicio((byte)1, "Baño", 35000);
        servicio[1] = new Servicio((byte)2, "Consulta Medica", 40000);
        servicio[2] = new Servicio((byte)3, "Estetica", 50000);
        servicio[3] = new Servicio((byte)4, "Desparasitacion", 15000);
    }
    
    
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    //Se inicializa mascota quemada
    
    private void inicializarMascotas()
    {
//        servicios[0]= new RegistroServicio("dsf",(double)464 , "jahdas", new Sexo((byte)1, "hembra"), new Especie((byte)2, "gato"), new Raza((byte)2, "persa"),(byte)45);
        regServicios[0]= new RegistroServicio("2021-12-21", "101", "Bruno", sexos[0], especies[0], razas[0], servicio[1]);
        contadorServicios++;
    }
    
    
//    private void inicializarRaza1()
//    {
////        Raza r = new Raza((byte)1, "Maine Coon");
//        totalRaza[0]= new TotalRaza((byte)1,razas[0].getNombre().toString());
//        contadorRaza++;
//    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    //Metodos para adicionar una mascota y eliminar una mascota
    
    public String adicionarMascota(RegistroServicio regser)
    {
        //Como hago yo para saber en que posicion se puede agregar
        if(contadorServicios<NUMERO_MASCOTAS)
        {
            // lo puede adicionar
            regServicios[contadorServicios]=regser;
            contadorServicios++;
            return "Servicio adicionado";
        }
        else
        {
            return "No se pueden adicionar mas servicios";
        }
    }    
    
    public void eliminarMascota(int pos)
    {
        contadorServicios--;
        JOptionPane.showMessageDialog(null, "Mascota Borrada");
    }        
    
    public  RegistroServicio verificarMascota(String fecha, String codigoMascota, String nombre, Sexo sexo, Especie especie, Raza raza, Servicio servicio) throws VeterinariaExcepcion{
            if (nombre==null || nombre.equals("")|| codigoMascota==null || codigoMascota.equals(""))
            {
                throw new VeterinariaExcepcion("Debe diligenciar todos los datos ");
            }
                if (!validarFecha(fecha)){
                    throw new VeterinariaExcepcion("Ingrese una fecha correcta");
                }
                if (!validarNombre(nombre)){
                    throw new VeterinariaExcepcion("Ingrese un nombre valido para la Mascota");
                }
                if(codigoMascota.length()<3){
                    throw new VeterinariaExcepcion(" El codigo no es valido, debe tener minimo 3 caracteres");
                }


                return new RegistroServicio(fecha, codigoMascota, nombre, sexo, especie, raza, servicio);
    }
    
//////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
    
    //Metodos de validacion de nombre y fecha y escribirFichero
    
    private boolean validarNombre(String nombre) {

            Pattern pattern = Pattern.compile("[a-zA-Z]+\\.?");
            Matcher matcher = pattern.matcher(nombre);

                  return matcher.find() ;
        }    
    
    private boolean validarFecha(String fecha) {
    //AAAA-MM-DD
            Pattern pattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
            Matcher matcher = pattern.matcher(fecha);

                  return matcher.find() ;
        }
    
    public static void escribirFichero()
    {
        FileWriter fichero=null;
        try {
            fichero = new FileWriter("src/prueba.txt");
            PrintWriter printW=new PrintWriter(fichero);
            printW.println("hola");
            printW.println("hola");
            
            for (int i=0; i<200; i++) 
            {
                printW.println("saludo "+ i);
            }
            
        } catch (IOException ex) {
            System.out.println("Error al crear el archivo: "+ex.getMessage());
        } finally {
            try {
                fichero.close();
            } catch (IOException ex) {
                System.out.println("Error al cerrar el archivo: "+ex.getMessage());
            }
        }
        
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public  static int elementosRepetidos(int[]array, int elemento)
    {
        int repetidos=0;
        for (int i=0; i < array.length; i++)
        {
            if (array[i]==elemento)
            {
                repetidos++;
            }
        }
        return repetidos;
    }   
    
////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public TotalRaza[] getTotalRaza() {
        return totalRaza;
    }


    //Guetters and Setters
    public void setTotalRaza(TotalRaza[] totalRaza) {
        this.totalRaza = totalRaza;
    }

    public RegistroServicio[] getRegServicios() {
        return regServicios;
    }

    public void setRegServicios(RegistroServicio[] regServicios) {
        this.regServicios = regServicios;
    }

    public int getContadorServicios() {
        return contadorServicios;
    }

    public void setContadorServicios(int contadorServicios) {
        this.contadorServicios = contadorServicios;
    }

    public Sexo[] getSexos() {
        return sexos;
    }

    public void setSexos(Sexo[] sexos) {
        this.sexos = sexos;
    }

    public Especie[] getEspecies() {
        return especies;
    }

    public void setEspecies(Especie[] especies) {
        this.especies = especies;
    }

    public Raza[] getRazas() {
        return razas;
    }

    public void setRazas(Raza[] razas) {
        this.razas = razas;
    }

    public Servicio[] getServicio() {
        return servicio;
    }

    public void setServicio(Servicio[] servicio) {
        this.servicio = servicio;
    }
}

////////////////////////////////////////////////////////////////////////////////    

//Pruebas de metodos 

    //metodo para calcular precio

////////    public double calcularPrecio()
////////    {
////////        double salario=0;
////////        for (RegistroServicio regser : this.servicios)
////////        {
////////            if(regser instanceof Servicio)
////////            {
////////                regser.calcularPrecio(salario);
////////            }
////////        }
////////    }
    
    // metodo para leer fichero
    
////////    public void LeerFichero()
////////    {
////////        FileReader fr=null;
////////        try 
////////        {
////////            File archivo=new File("src/prueba.txt");
////////            fr = new FileReader(archivo);
////////            BufferedReader br = new BufferedReader(fr);
////////            
////////            String linea;
////////            String[] datos;
////////            while ((linea = br.readLine()) != null) 
////////            {
////////                datos = linea.split(":");
////////                if (Boolean.valueOf(datos[0]) && datos.length==3) 
////////                {
////////                    if (datos[2].equals("1")) 
////////                    {
////////                        Servicio.add(new Servicio((datos[0]), datos[1], datos[2]));
////////                    } 
////////                    else if (datos[2].equals("2")) 
////////                    {
////////                        Servicio.add(new Servicio((datos[0]), datos[1],datos[3]));
////////                    }
////////                }
////////            }
////////        }
////////       catch (Exception e) 
////////        {
////////            e.printStackTrace();
////////        } 
////////        finally 
////////        {
////////            try 
////////            {
////////                if (null != fr) 
////////                {
////////                    fr.close();
////////                }
////////            } 
////////            catch (Exception e2) 
////////            {
////////                e2.printStackTrace();
////////            }
////////        }
////////    }        
//////            
//////            
//////            
////////            while ((linea=br.readLine())!=null)
////////            {
////////                System.out.println(linea);
////////            }
////////            
////////        }   catch (Exception ex) 
////////            {
////////            System.out.println("Error archivo inexistente");
////////            } 
////////            finally 
////////            {
////////            try 
////////            {
////////                fr.close();
////////            } catch (Exception ex) 
////////            {
////////                System.out.println("Error al cerrar el archivo ");
////////            }   
////////            }
    
    
 
