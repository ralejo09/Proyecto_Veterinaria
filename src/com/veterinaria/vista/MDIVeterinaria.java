/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.veterinaria.vista;

import com.veterinaria.controlador.ControladorUsuario;
import com.veterinaria.controlador.ControladorVeterinaria;
import com.veterinaria.excepciones.VeterinariaExcepcion;
import com.veterinaria.modelo.Especie;
import com.veterinaria.modelo.Mascota;
import com.veterinaria.modelo.Raza;
import com.veterinaria.modelo.RegistroServicio;
import com.veterinaria.modelo.Servicio;
import com.veterinaria.modelo.Sexo;
import com.veterinaria.modelo.Usuario;
import java.awt.Color;
import java.beans.PropertyVetoException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author ALEJO CARMONA
 */
public class MDIVeterinaria extends javax.swing.JFrame {
    DefaultTableModel model= new DefaultTableModel();
    private ControladorUsuario controlUsuarios;
    private Usuario usuarioAutenticado;
    private ControladorVeterinaria controlVeterinaria;
    private RegistroServicio mascotaAutenticicada;
    
    //graficos
    BarChartEx grafico = new BarChartEx();
    PieChartEx grafico1 = new PieChartEx();
    JFrame frame= new JFrame("qwqwqwqwqwqwqwq");
    /**
     * Creates new form MDIVeterinaria
     */

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public MDIVeterinaria() {
        initComponents();       //Crean todas las instancias
        controladorEstadoMenus();
        controlUsuarios = new ControladorUsuario();
        controlVeterinaria = new ControladorVeterinaria();
        txtCorreo.setText("rloaiza@umanizales.edu.co");
        txtContrasenia.setText("123456");
        
        llenarMascotas();
        
        controlVeterinaria= new ControladorVeterinaria();
        llenarCombosSexos();
        llenarComboEspecies();
        llenarCombosRazas();
        llenarCombosServicios();
        
        //Grafico especie
        grafico.setIconifiable(true);
        grafico.setClosable(true);
        grafico.setMaximizable(true);
        grafico.setResizable(true);
        grafico.setDefaultCloseOperation(HIDE_ON_CLOSE);
        desktopPane.add(grafico);
        
        frame.setSize(500, 270);
        frame.setLocationRelativeTo(getRootPane());
        this.setLocationRelativeTo(getRootPane());
        
        //grafico servicios
        grafico1.setIconifiable(true);
        grafico1.setClosable(true);
        grafico1.setMaximizable(true);
        grafico1.setResizable(true);
        grafico1.setDefaultCloseOperation(HIDE_ON_CLOSE);
        desktopPane.add(grafico1);
        
    }
    
/////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    
//Graficas
//    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) 
//    {
//        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        dataset.addValue(1.0, "Row 1", "Column 1");
//        dataset.addValue(5.0, "Row 1", "Column 2");
//        dataset.addValue(3.0, "Row 1", "Column 3");
//        dataset.addValue(2.0, "Row 2", "Column 1");
//        dataset.addValue(3.0, "Row 2", "Column 2");
//        dataset.addValue(2.0, "Row 2", "Column 3");
//        dataset.addValue(10.0,"Row 3", "Column 4");
//        dataset.addValue(10.0,"Row 3", "Column 4"); 
//        return dataset;
//        
//        JFreeChart chart = ChartFactory.createBarChart("Ejemplo básico para Linea de Codigo", "Categorías", "Valores", dataset, PlotOrientation.VERTICAL.VERTICAL, true, true, true);
//        ChartPanel chartPanel = new ChartPanel(chart, false);
//        frame.setContentPane(chartPanel);
//        frame.setVisible(true);
//    } 
    
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    //combos de los arreglos
    
    private void llenarCombosSexos()
    {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cmbSexoMascota.getModel();
        model.removeAllElements();
        for(Sexo sexo:controlVeterinaria.getSexos())
        {
            model.addElement(sexo);
        }   
    }
    
    private void llenarComboEspecies()
    {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cmbEspecie.getModel();
        model.removeAllElements();
         
        for (Especie especie:controlVeterinaria.getEspecies())
        {
            model.addElement(especie);
        }
        if (controlVeterinaria.getEspecies().length>0)
        {    
        cmbEspecie.setSelectedIndex(1);
        llenarCombosRazas();
        }
    }
    
    private void llenarCombosRazas()
    {
        DefaultComboBoxModel model= (DefaultComboBoxModel) cmbRaza.getModel();
        model.removeAllElements();
        controlVeterinaria.inicializarRaza(((Especie)cmbEspecie.getSelectedItem()).getCodigoEspecie());
        for(Raza raza:controlVeterinaria.getRazas())
        {
            model.addElement(raza);
        }    
    }        
            
    private void llenarCombosServicios()
    {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cmbServicio.getModel();
        model.removeAllElements();
        for(Servicio servicios:controlVeterinaria.getServicio())
        {
            model.addElement(servicios);
        }   
    }
   
/////////////////////////////////////////////////////////////////////////////////////////////////////////////            
    
    //Control de los estados de los menus, sgun su cargo
    public void controladorEstadoMenus()
    {
        menuListarMascotas.setEnabled(false);
        menuAgregarMascota.setEnabled(false);
        menuGraficoServicio.setEnabled(false);
        
        
        menuListarMascotas.setVisible(false);
        menuAgregarMascota.setVisible(false);
        menuGraficoServicio.setVisible(false);
        
        
        if(usuarioAutenticado!=null)
        {
            switch(usuarioAutenticado.getRol().getCodigo())
            {
                case 1:
                    menuListarMascotas.setEnabled(true);
                    menuAgregarMascota.setEnabled(true);
                    menuGraficoServicio.setEnabled(true);
                    
                    menuListarMascotas.setVisible(true);
                    menuAgregarMascota.setVisible(true);
                    menuGraficoServicio.setVisible(true);
                    break;
                case 2:
                    menuListarMascotas.setEnabled(true);
                    menuGraficoServicio.setEnabled(true);                   
                    
                    menuListarMascotas.setVisible(true);
                    menuGraficoServicio.setVisible(true);                  
                    break;
            }
            
            }
        else
        {
            
        
        }
    
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        desktopPane = new javax.swing.JDesktopPane();
        jifLogin = new javax.swing.JInternalFrame();
        lblUsuario = new javax.swing.JLabel();
        lblContrasenia = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        txtContrasenia = new javax.swing.JPasswordField();
        btmIngresar = new javax.swing.JButton();
        jifListarMascotas = new javax.swing.JInternalFrame();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMascotas = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        btnEliminar = new javax.swing.JButton();
        btnGraficoServicio = new javax.swing.JButton();
        jifRegistrarMasota = new javax.swing.JInternalFrame();
        btmRegistrar = new javax.swing.JButton();
        jlbFecha = new javax.swing.JLabel();
        jlbCodigoMascota = new javax.swing.JLabel();
        jlbNombreMascota = new javax.swing.JLabel();
        jlbSexoMascota = new javax.swing.JLabel();
        jlbEspecie = new javax.swing.JLabel();
        jlbRaza = new javax.swing.JLabel();
        jlbServicio = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        txtCodigoMascota = new javax.swing.JTextField();
        txtNombreMascota = new javax.swing.JTextField();
        cmbSexoMascota = new javax.swing.JComboBox<>();
        cmbEspecie = new javax.swing.JComboBox<>();
        cmbRaza = new javax.swing.JComboBox<>();
        cmbServicio = new javax.swing.JComboBox<>();
        menuBar = new javax.swing.JMenuBar();
        menuInicio = new javax.swing.JMenu();
        menuListarMascotas = new javax.swing.JMenuItem();
        menuAgregarMascota = new javax.swing.JMenuItem();
        menuGraficoServicio = new javax.swing.JMenuItem();
        menuSalir = new javax.swing.JMenuItem();

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jifLogin.setIconifiable(true);
        jifLogin.setTitle("Inicio De Sesión ");
        jifLogin.setToolTipText("");
        jifLogin.setVisible(true);

        lblUsuario.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblUsuario.setText("* Correo: ");

        lblContrasenia.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblContrasenia.setText("* Contraseña: ");

        jLabel1.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 24)); // NOI18N
        jLabel1.setText("WELCOME");
        jLabel1.setToolTipText("");

        txtCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCorreoActionPerformed(evt);
            }
        });

        btmIngresar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btmIngresar.setText("Ingresar");
        btmIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btmIngresarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jifLoginLayout = new javax.swing.GroupLayout(jifLogin.getContentPane());
        jifLogin.getContentPane().setLayout(jifLoginLayout);
        jifLoginLayout.setHorizontalGroup(
            jifLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jifLoginLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jifLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jifLoginLayout.createSequentialGroup()
                        .addGroup(jifLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblContrasenia)
                            .addComponent(lblUsuario))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jifLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCorreo)
                            .addComponent(txtContrasenia))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jifLoginLayout.createSequentialGroup()
                        .addGap(0, 69, Short.MAX_VALUE)
                        .addGroup(jifLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jifLoginLayout.createSequentialGroup()
                                .addComponent(btmIngresar)
                                .addGap(106, 106, 106))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jifLoginLayout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(60, 60, 60))))))
        );
        jifLoginLayout.setVerticalGroup(
            jifLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jifLoginLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jifLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsuario)
                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jifLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblContrasenia)
                    .addComponent(txtContrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btmIngresar)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        desktopPane.add(jifLogin);
        jifLogin.setBounds(550, 210, 310, 210);

        jifListarMascotas.setClosable(true);
        jifListarMascotas.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        jifListarMascotas.setIconifiable(true);
        jifListarMascotas.setMaximizable(true);
        jifListarMascotas.setResizable(true);
        jifListarMascotas.setTitle("Lista de Mascotas");
        jifListarMascotas.setVisible(false);

        tblMascotas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "Codigo Mascota", "Nombre", "Sexo", "Especie", "Raza", "Servicio y Precio"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMascotas.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblMascotas.getTableHeader().setResizingAllowed(false);
        tblMascotas.getTableHeader().setReorderingAllowed(false);
        tblMascotas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblMascotasKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblMascotasKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblMascotas);
        if (tblMascotas.getColumnModel().getColumnCount() > 0) {
            tblMascotas.getColumnModel().getColumn(0).setResizable(false);
            tblMascotas.getColumnModel().getColumn(0).setPreferredWidth(100);
            tblMascotas.getColumnModel().getColumn(1).setResizable(false);
            tblMascotas.getColumnModel().getColumn(1).setPreferredWidth(110);
            tblMascotas.getColumnModel().getColumn(2).setResizable(false);
            tblMascotas.getColumnModel().getColumn(2).setPreferredWidth(110);
            tblMascotas.getColumnModel().getColumn(3).setResizable(false);
            tblMascotas.getColumnModel().getColumn(3).setPreferredWidth(90);
            tblMascotas.getColumnModel().getColumn(4).setResizable(false);
            tblMascotas.getColumnModel().getColumn(4).setPreferredWidth(90);
            tblMascotas.getColumnModel().getColumn(5).setResizable(false);
            tblMascotas.getColumnModel().getColumn(5).setPreferredWidth(150);
            tblMascotas.getColumnModel().getColumn(6).setResizable(false);
            tblMascotas.getColumnModel().getColumn(6).setPreferredWidth(305);
        }

        jLabel2.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 24)); // NOI18N
        jLabel2.setText("MASCOTAS");

        btnEliminar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnEliminar.setText("Eliminar Mascota");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnGraficoServicio.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnGraficoServicio.setText("Grafico de Servicios");
        btnGraficoServicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGraficoServicioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jifListarMascotasLayout = new javax.swing.GroupLayout(jifListarMascotas.getContentPane());
        jifListarMascotas.getContentPane().setLayout(jifListarMascotasLayout);
        jifListarMascotasLayout.setHorizontalGroup(
            jifListarMascotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jifListarMascotasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jifListarMascotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jifListarMascotasLayout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jifListarMascotasLayout.createSequentialGroup()
                        .addGap(0, 121, Short.MAX_VALUE)
                        .addGroup(jifListarMascotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jifListarMascotasLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(407, 407, 407))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jifListarMascotasLayout.createSequentialGroup()
                                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(363, 363, 363)
                                .addComponent(btnGraficoServicio)
                                .addGap(127, 127, 127))))))
        );
        jifListarMascotasLayout.setVerticalGroup(
            jifListarMascotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jifListarMascotasLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addGroup(jifListarMascotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGraficoServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        desktopPane.add(jifListarMascotas);
        jifListarMascotas.setBounds(200, 70, 1000, 520);

        jifRegistrarMasota.setClosable(true);
        jifRegistrarMasota.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        jifRegistrarMasota.setIconifiable(true);
        jifRegistrarMasota.setMaximizable(true);
        jifRegistrarMasota.setResizable(true);
        jifRegistrarMasota.setTitle("Registrar Mascota");
        jifRegistrarMasota.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jifRegistrarMasota.setVisible(false);

        btmRegistrar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btmRegistrar.setText("Registrar Mascota");
        btmRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btmRegistrarActionPerformed(evt);
            }
        });

        jlbFecha.setText("* Fecha (AAAA-MM-DD) : ");

        jlbCodigoMascota.setText("* Codigo de la Mascota: ");

        jlbNombreMascota.setText("* Nombre de la Mascota: ");

        jlbSexoMascota.setText("* Sexo de la Mascota: ");

        jlbEspecie.setText("* Especie: ");

        jlbRaza.setText("* Raza de la Mascota: ");

        jlbServicio.setText("* Servicio: ");

        jLabel10.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 24)); // NOI18N
        jLabel10.setText("REGISTRO DE MASCOTAS");

        cmbEspecie.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbEspecieItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jifRegistrarMasotaLayout = new javax.swing.GroupLayout(jifRegistrarMasota.getContentPane());
        jifRegistrarMasota.getContentPane().setLayout(jifRegistrarMasotaLayout);
        jifRegistrarMasotaLayout.setHorizontalGroup(
            jifRegistrarMasotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jifRegistrarMasotaLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jifRegistrarMasotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jlbServicio)
                    .addComponent(jlbRaza)
                    .addComponent(jlbEspecie)
                    .addComponent(jlbSexoMascota)
                    .addComponent(jlbNombreMascota)
                    .addComponent(jlbCodigoMascota)
                    .addComponent(jlbFecha))
                .addGap(18, 18, 18)
                .addGroup(jifRegistrarMasotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbSexoMascota, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbEspecie, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbRaza, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbServicio, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigoMascota)
                    .addComponent(txtNombreMascota))
                .addGap(66, 66, 66))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jifRegistrarMasotaLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(128, 128, 128))
            .addGroup(jifRegistrarMasotaLayout.createSequentialGroup()
                .addGap(225, 225, 225)
                .addComponent(btmRegistrar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jifRegistrarMasotaLayout.setVerticalGroup(
            jifRegistrarMasotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jifRegistrarMasotaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addGap(29, 29, 29)
                .addGroup(jifRegistrarMasotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbFecha)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jifRegistrarMasotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbCodigoMascota)
                    .addComponent(txtCodigoMascota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jifRegistrarMasotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbNombreMascota)
                    .addComponent(txtNombreMascota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jifRegistrarMasotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbSexoMascota)
                    .addComponent(cmbSexoMascota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jifRegistrarMasotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbEspecie)
                    .addComponent(cmbEspecie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jifRegistrarMasotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbRaza)
                    .addComponent(cmbRaza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jifRegistrarMasotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbServicio)
                    .addComponent(cmbServicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55)
                .addComponent(btmRegistrar)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        desktopPane.add(jifRegistrarMasota);
        jifRegistrarMasota.setBounds(410, 50, 620, 580);

        menuInicio.setMnemonic('f');
        menuInicio.setText("Inicio");

        menuListarMascotas.setMnemonic('o');
        menuListarMascotas.setText("Listar Macotas");
        menuListarMascotas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuListarMascotasActionPerformed(evt);
            }
        });
        menuInicio.add(menuListarMascotas);

        menuAgregarMascota.setMnemonic('s');
        menuAgregarMascota.setText("Agregar Mascota");
        menuAgregarMascota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAgregarMascotaActionPerformed(evt);
            }
        });
        menuInicio.add(menuAgregarMascota);

        menuGraficoServicio.setMnemonic('a');
        menuGraficoServicio.setText("Grafico Servicio");
        menuGraficoServicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuGraficoServicioActionPerformed(evt);
            }
        });
        menuInicio.add(menuGraficoServicio);

        menuSalir.setMnemonic('x');
        menuSalir.setText("Salir");
        menuSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSalirActionPerformed(evt);
            }
        });
        menuInicio.add(menuSalir);

        menuBar.add(menuInicio);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1366, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 724, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSalirActionPerformed
       
        JOptionPane.showMessageDialog(rootPane, "Gracias por utilizar mi app");
        System.exit(0);
    }//GEN-LAST:event_menuSalirActionPerformed

    private void menuGraficoServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuGraficoServicioActionPerformed
        // TODO add your handling code here:
        grafico1.show();
    }//GEN-LAST:event_menuGraficoServicioActionPerformed

    private void menuListarMascotasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuListarMascotasActionPerformed
        // TODO add your handling code here:
        if(jifListarMascotas.isIcon())
        {
            try {
                jifListarMascotas.setIcon(false);
            } catch (PropertyVetoException ex) {
                System.out.println("No se encontraba minimizado");
            }
        }
        jifListarMascotas.show();
    }//GEN-LAST:event_menuListarMascotasActionPerformed

    private void menuAgregarMascotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAgregarMascotaActionPerformed
        // TODO add your handling code here:
        //abrirlo despues de estar minimizado
        if(jifRegistrarMasota.isIcon())
        {
            try {
                jifRegistrarMasota.setIcon(false);
            } catch (PropertyVetoException ex) {
                System.out.println("No se encontraba minimizado");
            }
        }
        //nos aplica la funcion
        jifRegistrarMasota.show();
    }//GEN-LAST:event_menuAgregarMascotaActionPerformed

    private void cmbEspecieItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbEspecieItemStateChanged
        // TODO add your handling code here:
        llenarCombosRazas();
    }//GEN-LAST:event_cmbEspecieItemStateChanged

    private void btmRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btmRegistrarActionPerformed
        // TODO add your handling code here:

        RegistroServicio adicionarMascota;
        int pos= cmbSexoMascota.getSelectedIndex();
        int pos1= cmbEspecie.getSelectedIndex();
        int pos2= cmbRaza.getSelectedIndex();
        int pos3= cmbServicio.getSelectedIndex();

        //        adicionarMascota=controlVeterinaria.verificarMascota(txtFecha.getText(), txtCodigoMascota.getText(), txtNombreMascota.getText(), controlVeterinaria.getSexos()[pos],
            //        controlVeterinaria.getEspecies()[pos1], controlVeterinaria.getRazas()[pos2], controlVeterinaria.getServicio()[pos3]);
        try
        {
            if (validarCodigo()==false)
            {
                adicionarMascota=controlVeterinaria.verificarMascota(txtFecha.getText(), txtCodigoMascota.getText(), txtNombreMascota.getText(), controlVeterinaria.getSexos()[pos],
                    controlVeterinaria.getEspecies()[pos1], controlVeterinaria.getRazas()[pos2], controlVeterinaria.getServicio()[pos3]);
                String mensaje=controlVeterinaria.adicionarMascota(adicionarMascota);
                llenarMascotas();
                JOptionPane.showMessageDialog(rootPane, mensaje);
            }
            else
            {
                JOptionPane.showMessageDialog(rootPane, "Codigo Repetido","Error",1);
            }
        } catch (VeterinariaExcepcion ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Error", 1);
        }

        txtCodigoMascota.setText("");
        txtFecha.setText("");
        txtNombreMascota.setText("");
        txtContrasenia.setText("");
        txtCorreo.setText("");

        ///        String mensaje= ControladorVeterinaria.(adicionarMascota);
    }//GEN-LAST:event_btmRegistrarActionPerformed

    private void btnGraficoServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGraficoServicioActionPerformed
        // TODO add your handling code here

        if (tblMascotas.getRowCount()!=0)
        {
            //        servicio[0] = new Servicio((byte)1, "Baño", 35000);
            //        servicio[1] = new Servicio((byte)2, "Consulta Medica", 40000);
            //        servicio[2] = new Servicio((byte)3, "Estetica", 50000);
            //        servicio[3] = new Servicio((byte)4, "Desparasitacion", 15000);
            int ser1 = 0;
            int ser2 = 0;
            int ser3 = 0;
            int ser4 = 0;

            int cols = tblMascotas.getColumnCount();
            int fils = tblMascotas.getRowCount();
            for(int i=0; i<fils; i++) {

                if(controlVeterinaria.getRegServicios()[i].getServicio().getNombreServicio().equals("Baño"))
                {
                    ser1++;
                }
                else if(controlVeterinaria.getRegServicios()[i].getServicio().getNombreServicio().equals("Consulta Medica"))
                {
                    ser2++;
                }
                else if(controlVeterinaria.getRegServicios()[i].getServicio().getNombreServicio().equals("Estetica"))
                {
                    ser3++;
                }
                else if(controlVeterinaria.getRegServicios()[i].getServicio().getNombreServicio().equals("Desparasitacion"))
                {
                    ser4++;
                }
                
                System.out.println(tblMascotas.getValueAt(i, 6));
            }

            if (controlVeterinaria.getRegServicios() != null) {

                DefaultCategoryDataset dataset = new DefaultCategoryDataset();

                dataset.setValue(ser1, "Servicio", controlVeterinaria.getServicio()[0].getNombreServicio());
                dataset.setValue(ser2, "Servicio", controlVeterinaria.getServicio()[1].getNombreServicio());
                dataset.setValue(ser3, "Servicio", controlVeterinaria.getServicio()[2].getNombreServicio());
                dataset.setValue(ser4, "Servicio", controlVeterinaria.getServicio()[3].getNombreServicio());
                JFreeChart diagramaServicio = ChartFactory.createBarChart("Gráfica Servicios", "Nombre Servicio", "Cantidad", dataset, PlotOrientation.VERTICAL, false, true, false);
                CategoryPlot p = diagramaServicio.getCategoryPlot();
                p.setRangeGridlinePaint(Color.BLUE);
                ChartFrame pantalla = new ChartFrame("Diagrama", diagramaServicio);
                pantalla.setVisible(true);
                pantalla.setSize(500, 500);
            } else {
                
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "No hay datos");
        }
        //        grafico1.show();
    }//GEN-LAST:event_btnGraficoServicioActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        DefaultTableModel miTableModel = (DefaultTableModel) tblMascotas.getModel();
        int eli = tblMascotas.getSelectedRow();
        if (eli >= 0)
        {
            controlVeterinaria.eliminarMascota(ERROR);
            miTableModel.removeRow(eli);
        }
        else
        {
            JOptionPane.showMessageDialog(null, "No tienes mas mascotas para eliminar");
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void tblMascotasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblMascotasKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tblMascotasKeyReleased

    private void tblMascotasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblMascotasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblMascotasKeyPressed

    private void btmIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btmIngresarActionPerformed
        try
        {
            usuarioAutenticado = controlUsuarios.verificarUsuario(txtCorreo.getText(), txtContrasenia.getText());
            controladorEstadoMenus();
            jifLogin.hide();
            JOptionPane.showMessageDialog(rootPane, " Bienvenido " + usuarioAutenticado.getNombre(),"Ingreso Correcto",1);
        }
        catch (VeterinariaExcepcion ex)
        {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Error", 0);
        }
        //        if(txtCorreo.getText().equals("")|| txtContrasenia.getPassword().length==0)
        //            {
            //            JOptionPane.showMessageDialog(rootPane, "Debe diligenciar los campos obligatorios", "Error", 1);
            //            }
        //            else
        //            {
            //            try {
                //                ///Si ingreso datos
                //                if(controlUsuarios.verificarUsuario(txtCorreo.getText(), txtContrasenia.getText()))
                //                {
                    //                    controladorEstadoMenus();
                    //                    jifLogin.hide();
                    //                }
                //                else
                //                {
                    //                    JOptionPane.showMessageDialog(rootPane, "Los datos ingresados son errados", "Error",0);
                    //                }
                //            } catch (VeterinariaExcepcion ex) {
                //                Logger.getLogger(MDIVeterinaria.class.getName()).log(Level.SEVERE, null, ex);
                //            }
            //
            //
            //            }

    }//GEN-LAST:event_btmIngresarActionPerformed

    private void txtCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCorreoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreoActionPerformed
        
    //Metodo para validar el codigo no se repita
    
    public boolean validarCodigo(){
        for(int i=0;  i<tblMascotas.getRowCount(); i++){
            if(tblMascotas.getValueAt(i, 1).equals(txtCodigoMascota.getText())){
                return true;
            }
    }
        return false;
    }
    
    public void llenarMascotas()
    {
        DefaultTableModel model= (DefaultTableModel) tblMascotas.getModel();
        model.getDataVector().removeAllElements();
        
        for(RegistroServicio regser: controlVeterinaria.getRegServicios())
        {
            if(regser!=null)
            {
            Object[] fila = {regser.getFecha(),regser.getCodigoMascota(),regser.getNombre(),regser.getSexo(),regser.getEspecie(),regser.getRaza(),regser.getServicio()};
            model.addRow(fila);
            }
        }   
        
        tblMascotas.repaint();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MDIVeterinaria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MDIVeterinaria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MDIVeterinaria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MDIVeterinaria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() 
        {
            public void run() 
            {
                new MDIVeterinaria().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btmIngresar;
    private javax.swing.JButton btmRegistrar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGraficoServicio;
    private javax.swing.JComboBox<String> cmbEspecie;
    private javax.swing.JComboBox<String> cmbRaza;
    private javax.swing.JComboBox<String> cmbServicio;
    private javax.swing.JComboBox<String> cmbSexoMascota;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JInternalFrame jifListarMascotas;
    private javax.swing.JInternalFrame jifLogin;
    private javax.swing.JInternalFrame jifRegistrarMasota;
    private javax.swing.JLabel jlbCodigoMascota;
    private javax.swing.JLabel jlbEspecie;
    private javax.swing.JLabel jlbFecha;
    private javax.swing.JLabel jlbNombreMascota;
    private javax.swing.JLabel jlbRaza;
    private javax.swing.JLabel jlbServicio;
    private javax.swing.JLabel jlbSexoMascota;
    private javax.swing.JLabel lblContrasenia;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JMenuItem menuAgregarMascota;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem menuGraficoServicio;
    private javax.swing.JMenu menuInicio;
    private javax.swing.JMenuItem menuListarMascotas;
    private javax.swing.JMenuItem menuSalir;
    private javax.swing.JTable tblMascotas;
    private javax.swing.JTextField txtCodigoMascota;
    private javax.swing.JPasswordField txtContrasenia;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtNombreMascota;
    // End of variables declaration//GEN-END:variables

}
