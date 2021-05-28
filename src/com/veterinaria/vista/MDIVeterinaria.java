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
import com.veterinaria.modelo.TotalRaza;
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
    DefaultTableModel model1= new DefaultTableModel();
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
        
        llenarTotalRaza();
//        llenarTotalRazaPerro();
        
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
        jifTotalRaza = new javax.swing.JInternalFrame();
        jlbGatos = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        txtRaza1 = new javax.swing.JTextField();
        txtRaza2 = new javax.swing.JTextField();
        txtRaza3 = new javax.swing.JTextField();
        txtRaza4 = new javax.swing.JTextField();
        txtRaza5 = new javax.swing.JTextField();
        txtRaza6 = new javax.swing.JTextField();
        txtRaza7 = new javax.swing.JTextField();
        txtRaza8 = new javax.swing.JTextField();
        txtRaza9 = new javax.swing.JTextField();
        txtRaza10 = new javax.swing.JTextField();
        txtRaza11 = new javax.swing.JTextField();
        txtRaza12 = new javax.swing.JTextField();
        txtRaza13 = new javax.swing.JTextField();
        txtRaza14 = new javax.swing.JTextField();
        txtRaza15 = new javax.swing.JTextField();
        txtRaza16 = new javax.swing.JTextField();
        txtRaza17 = new javax.swing.JTextField();
        txtRaza18 = new javax.swing.JTextField();
        txtRaza19 = new javax.swing.JTextField();
        txtRaza20 = new javax.swing.JTextField();
        txtRaza22 = new javax.swing.JTextField();
        txtRaza23 = new javax.swing.JTextField();
        txtRaza24 = new javax.swing.JTextField();
        txtRaza25 = new javax.swing.JTextField();
        txtRaza21 = new javax.swing.JTextField();
        txtRaza26 = new javax.swing.JTextField();
        txtRaza27 = new javax.swing.JTextField();
        txtRaza28 = new javax.swing.JTextField();
        txtRaza29 = new javax.swing.JTextField();
        txtRaza30 = new javax.swing.JTextField();
        txtRaza31 = new javax.swing.JTextField();
        txtRaza32 = new javax.swing.JTextField();
        txtRaza33 = new javax.swing.JTextField();
        txtRaza34 = new javax.swing.JTextField();
        txtRaza35 = new javax.swing.JTextField();
        txtRaza36 = new javax.swing.JTextField();
        txtRaza37 = new javax.swing.JTextField();
        txtRaza38 = new javax.swing.JTextField();
        txtRaza39 = new javax.swing.JTextField();
        txtRaza40 = new javax.swing.JTextField();
        txtRaza41 = new javax.swing.JTextField();
        txtRaza42 = new javax.swing.JTextField();
        txtRaza43 = new javax.swing.JTextField();
        txtRaza44 = new javax.swing.JTextField();
        txtRaza45 = new javax.swing.JTextField();
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
        menuTotalRaza = new javax.swing.JMenuItem();
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
                .addContainerGap(45, Short.MAX_VALUE))
        );

        desktopPane.add(jifListarMascotas);
        jifListarMascotas.setBounds(200, 70, 1000, 520);

        jifTotalRaza.setClosable(true);
        jifTotalRaza.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        jifTotalRaza.setIconifiable(true);
        jifTotalRaza.setMaximizable(true);
        jifTotalRaza.setResizable(true);
        jifTotalRaza.setTitle("Total por raza");
        jifTotalRaza.setAutoscrolls(true);
        jifTotalRaza.setVisible(false);

        jlbGatos.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 24)); // NOI18N
        jlbGatos.setText("RAZAS");

        jLabel3.setText("Maine Coon");

        jLabel4.setText("Gato Persa");

        jLabel5.setText("Gato Esfinge");

        jLabel6.setText("Sphynx");

        jLabel7.setText("Gato Saimés");

        jLabel8.setText("Gato Bengalí");

        jLabel9.setText("Gato Exótico");

        jLabel11.setText("Bosque de Noruega");

        jLabel12.setText("Gato Siberiano");

        jLabel13.setText("Gato Birmano");

        jLabel14.setText("Angora Turco");

        jLabel15.setText("Van Turco");

        jLabel16.setText("Gato Himalayo");

        jLabel17.setText("Savannah");

        jLabel18.setText("Azul Ruso");

        jLabel19.setText("Gato Ragdoll");

        jLabel20.setText("Gato Oriental");

        jLabel21.setText("British Shorthair");

        jLabel22.setText("Nebelung");

        jLabel23.setText("Munchkin");

        jLabel24.setText("Lykoi");

        jLabel25.setText("PeterBald");

        jLabel26.setText("Burmés");

        jLabel27.setText("Tonkinés");

        jLabel28.setText("Scottish Fold");

        jLabel29.setText("Burmilla");

        jLabel30.setText("Abisinio");

        jLabel31.setText("Gato Bombay");

        jLabel32.setText("Korat");

        jLabel33.setText("Devon Rex");

        jLabel34.setText("Cornish Rex");

        jLabel35.setText("Selkirk Rex");

        jLabel36.setText("Gato Común Europeo");

        jLabel37.setText("Snowshoe");

        jLabel38.setText("Bobtail Japonés");

        jLabel39.setText("Gato Balinés");

        jLabel40.setText("Laperm");

        jLabel41.setText("Singapura");

        jLabel42.setText("Gato Cartujo");

        jLabel43.setText("Mau Egipcio");

        jLabel44.setText("Ocicat");

        jLabel45.setText("Toyger");

        jLabel46.setText("Gato Manx");

        jLabel47.setText("Gato Ragamuffin");

        jLabel48.setText("Curl Americano");

        txtRaza1.setEditable(false);

        txtRaza2.setEditable(false);

        txtRaza3.setEditable(false);

        txtRaza4.setEditable(false);

        txtRaza5.setEditable(false);

        txtRaza6.setEditable(false);

        txtRaza7.setEditable(false);

        txtRaza8.setEditable(false);

        txtRaza9.setEditable(false);

        txtRaza10.setEditable(false);

        txtRaza11.setEditable(false);

        txtRaza12.setEditable(false);

        txtRaza13.setEditable(false);

        txtRaza14.setEditable(false);

        txtRaza15.setEditable(false);

        txtRaza16.setEditable(false);

        txtRaza17.setEditable(false);

        txtRaza18.setEditable(false);

        txtRaza19.setEditable(false);

        txtRaza20.setEditable(false);

        txtRaza22.setEditable(false);

        txtRaza23.setEditable(false);

        txtRaza24.setEditable(false);

        txtRaza25.setEditable(false);

        txtRaza21.setEditable(false);

        txtRaza26.setEditable(false);

        txtRaza27.setEditable(false);

        txtRaza28.setEditable(false);

        txtRaza29.setEditable(false);

        txtRaza30.setEditable(false);

        txtRaza31.setEditable(false);

        txtRaza32.setEditable(false);

        txtRaza33.setEditable(false);

        txtRaza34.setEditable(false);

        txtRaza35.setEditable(false);

        txtRaza36.setEditable(false);

        txtRaza37.setEditable(false);

        txtRaza38.setEditable(false);

        txtRaza39.setEditable(false);

        txtRaza40.setEditable(false);

        txtRaza41.setEditable(false);

        txtRaza42.setEditable(false);

        txtRaza43.setEditable(false);

        txtRaza44.setEditable(false);

        txtRaza45.setEditable(false);

        javax.swing.GroupLayout jifTotalRazaLayout = new javax.swing.GroupLayout(jifTotalRaza.getContentPane());
        jifTotalRaza.getContentPane().setLayout(jifTotalRazaLayout);
        jifTotalRazaLayout.setHorizontalGroup(
            jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jifTotalRazaLayout.createSequentialGroup()
                .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jifTotalRazaLayout.createSequentialGroup()
                            .addGap(31, 31, 31)
                            .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel11)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING))
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel8))
                                .addGroup(jifTotalRazaLayout.createSequentialGroup()
                                    .addGap(35, 35, 35)
                                    .addComponent(jLabel5))))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jifTotalRazaLayout.createSequentialGroup()
                            .addGap(86, 86, 86)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtRaza1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRaza3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRaza2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRaza4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRaza6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRaza7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRaza8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRaza9, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRaza10, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRaza11, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRaza12, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRaza13, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRaza14, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRaza15, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRaza5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(65, 65, 65)
                .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jifTotalRazaLayout.createSequentialGroup()
                        .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jifTotalRazaLayout.createSequentialGroup()
                                .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtRaza16, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtRaza17, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtRaza18, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtRaza19, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtRaza20, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jifTotalRazaLayout.createSequentialGroup()
                                .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel26)
                                    .addComponent(jLabel27)
                                    .addComponent(jLabel23)
                                    .addComponent(jLabel24)
                                    .addComponent(jLabel30)
                                    .addComponent(jLabel48))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtRaza25, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtRaza24, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtRaza23, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtRaza22, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtRaza21, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtRaza28, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jifTotalRazaLayout.createSequentialGroup()
                                .addComponent(jLabel28)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtRaza26, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jifTotalRazaLayout.createSequentialGroup()
                                .addComponent(jLabel29)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtRaza27, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jifTotalRazaLayout.createSequentialGroup()
                                .addComponent(jLabel31)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtRaza29, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jifTotalRazaLayout.createSequentialGroup()
                                .addComponent(jLabel32)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtRaza30, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(48, 48, 48)
                        .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jifTotalRazaLayout.createSequentialGroup()
                                .addComponent(jLabel38)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtRaza31, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jifTotalRazaLayout.createSequentialGroup()
                                .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jifTotalRazaLayout.createSequentialGroup()
                                        .addComponent(jLabel39)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtRaza32, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jifTotalRazaLayout.createSequentialGroup()
                                        .addComponent(jLabel41)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtRaza33, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jifTotalRazaLayout.createSequentialGroup()
                                        .addComponent(jLabel40)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtRaza34, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jifTotalRazaLayout.createSequentialGroup()
                                        .addComponent(jLabel33)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtRaza35, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jifTotalRazaLayout.createSequentialGroup()
                                        .addComponent(jLabel34)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtRaza36, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jifTotalRazaLayout.createSequentialGroup()
                                        .addComponent(jLabel35)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtRaza37, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jifTotalRazaLayout.createSequentialGroup()
                                        .addComponent(jLabel36)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtRaza38, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jifTotalRazaLayout.createSequentialGroup()
                                        .addComponent(jLabel37)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtRaza39, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jifTotalRazaLayout.createSequentialGroup()
                                        .addComponent(jLabel42)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtRaza40, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jifTotalRazaLayout.createSequentialGroup()
                                        .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLabel46)
                                            .addComponent(jLabel47)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jifTotalRazaLayout.createSequentialGroup()
                                                .addGap(47, 47, 47)
                                                .addComponent(jLabel45))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jifTotalRazaLayout.createSequentialGroup()
                                                .addGap(51, 51, 51)
                                                .addComponent(jLabel44))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jifTotalRazaLayout.createSequentialGroup()
                                                .addGap(25, 25, 25)
                                                .addComponent(jLabel43)))
                                        .addGap(18, 18, 18)
                                        .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtRaza41, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtRaza42, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtRaza43, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtRaza44, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtRaza45, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(1, 1, 1))))
                    .addGroup(jifTotalRazaLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jlbGatos, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(82, Short.MAX_VALUE))
        );
        jifTotalRazaLayout.setVerticalGroup(
            jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jifTotalRazaLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jlbGatos, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jifTotalRazaLayout.createSequentialGroup()
                        .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtRaza1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtRaza2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtRaza3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtRaza4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtRaza5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jifTotalRazaLayout.createSequentialGroup()
                        .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(txtRaza16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel38)
                            .addComponent(txtRaza31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(txtRaza17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel39)
                            .addComponent(txtRaza32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(txtRaza18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel41)
                            .addComponent(txtRaza33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(txtRaza19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel40)
                            .addComponent(txtRaza34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(txtRaza20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel33)
                            .addComponent(txtRaza35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jifTotalRazaLayout.createSequentialGroup()
                        .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtRaza6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtRaza7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtRaza8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtRaza9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(txtRaza10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(txtRaza11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(txtRaza12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(txtRaza13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txtRaza14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(txtRaza15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(295, 295, 295))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jifTotalRazaLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(txtRaza21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel34)
                            .addComponent(txtRaza36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(txtRaza22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel35)
                            .addComponent(txtRaza37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26)
                            .addComponent(txtRaza23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel36)
                            .addComponent(txtRaza38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(txtRaza24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel37)
                            .addComponent(txtRaza39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel48)
                            .addComponent(txtRaza25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel42)
                            .addComponent(txtRaza40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jifTotalRazaLayout.createSequentialGroup()
                                .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel28)
                                    .addComponent(txtRaza26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel29)
                                    .addComponent(txtRaza27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel30)
                                    .addComponent(txtRaza28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel31)
                                    .addComponent(txtRaza29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel32)
                                    .addComponent(txtRaza30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jifTotalRazaLayout.createSequentialGroup()
                                .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel43)
                                    .addComponent(txtRaza41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel44)
                                    .addComponent(txtRaza42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel45)
                                    .addComponent(txtRaza43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel46)
                                    .addComponent(txtRaza44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jifTotalRazaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel47)
                                    .addComponent(txtRaza45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(296, 296, 296))))
        );

        desktopPane.add(jifTotalRaza);
        jifTotalRaza.setBounds(70, 10, 620, 590);

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

        cmbRaza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbRazaActionPerformed(evt);
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
                .addContainerGap(166, Short.MAX_VALUE))
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

        menuTotalRaza.setText("Total por Raza");
        menuTotalRaza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuTotalRazaActionPerformed(evt);
            }
        });
        menuInicio.add(menuTotalRaza);

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

                switch (controlVeterinaria.getRegServicios()[i].getServicio().getNombreServicio()) {
                    case "Baño":
                        ser1++;
                        break;
                    case "Consulta Medica":
                        ser2++;
                        break;
                    case "Estetica":
                        ser3++;
                        break;
                    case "Desparasitacion":
                        ser4++;
                        break;
                    default:
                        break;
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
                txtCodigoMascota.setText("");
                txtFecha.setText("");
                txtNombreMascota.setText("");
                txtContrasenia.setText("");
                txtCorreo.setText("");
            }
            else
            {
                JOptionPane.showMessageDialog(rootPane, "Codigo Repetido","Error",1);
            }
        } catch (VeterinariaExcepcion ex) 
        {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Error", 1);
        }

        

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
            int serBaño = 0;
            int serConsultaMedica = 0;
            int serEstetica = 0;
            int serDesparasitacion = 0;

            int cols = tblMascotas.getColumnCount();
            int fils = tblMascotas.getRowCount();
            for(int i=0; i<fils; i++) {

                switch (controlVeterinaria.getRegServicios()[i].getServicio().getNombreServicio()) {
                    case "Baño":
                        serBaño++;
                        break;
                    case "Consulta Medica":
                        serConsultaMedica++;
                        break;
                    case "Estetica":
                        serEstetica++;
                        break;
                    case "Desparasitacion":
                        serDesparasitacion++;
                        break;
                    default:
                        break;
                }
                
                System.out.println(tblMascotas.getValueAt(i, 6));
            }

            if (controlVeterinaria.getRegServicios() != null) {

                DefaultCategoryDataset dataset = new DefaultCategoryDataset();

                dataset.setValue(serBaño, "Servicio", controlVeterinaria.getServicio()[0].getNombreServicio());
                dataset.setValue(serConsultaMedica, "Servicio", controlVeterinaria.getServicio()[1].getNombreServicio());
                dataset.setValue(serEstetica, "Servicio", controlVeterinaria.getServicio()[2].getNombreServicio());
                dataset.setValue(serDesparasitacion, "Servicio", controlVeterinaria.getServicio()[3].getNombreServicio());
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

    private void menuTotalRazaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuTotalRazaActionPerformed
        // TODO add your handling code here:
        if(jifTotalRaza.isIcon())
        {
            try {
                jifTotalRaza.setIcon(false);
            } catch (PropertyVetoException ex) {
                System.out.println("No se encontraba minimizado");
            }
        }
        //nos aplica la funcion
        jifTotalRaza.show();
    }//GEN-LAST:event_menuTotalRazaActionPerformed

    private void cmbRazaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbRazaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbRazaActionPerformed
        
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
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
//    public void llenarTotalRazaPerro()
//    {
//        
//    }
    
    
    public void llenarTotalRaza()
    {
        if(tblMascotas.getRowCount()!=0)
        {    
            int raza1 = 0;
            int raza2 = 0;
            int raza3 = 0;
            int raza4 = 0;
            int raza5 = 0;
            int raza6 = 0;
            int raza7 = 0;
            int raza8 = 0;
            int raza9 = 0;
            int raza10 = 0;
            int raza11 = 0;
            int raza12 = 0;
            int raza13 = 0;
            int raza14 = 0;
            int raza15 = 0;
            int raza16 = 0;
            int raza17 = 0;
            int raza18 = 0;
            int raza19 = 0;
            int raza20 = 0;
            int raza21 = 0;
            int raza22 = 0;
            int raza23 = 0;
            int raza24 = 0;
            int raza25 = 0;
            int raza26 = 0;
            int raza27 = 0;
            int raza28 = 0;
            int raza29 = 0;
            int raza30= 0;
            int raza31 = 0;
            int raza32 = 0;
            int raza33 = 0;
            int raza34 = 0;
            int raza35 = 0;
            int raza36 = 0;
            int raza37 = 0;
            int raza38 = 0;
            int raza39 = 0;
            int raza40 = 0;
            int raza41 = 0;
            int raza42 = 0;
            int raza43 = 0;
            int raza44 = 0;
            int raza45 = 0;
            int raza46 = 0;
            int raza47 = 0;
            int raza48 = 0;
            int raza49 = 0;
            int raza50 = 0;
            int raza51 = 0;
            int raza52 = 0;
            int raza53 = 0;
            int raza54 = 0;
            int raza55 = 0;
            int raza56 = 0;
            int raza57 = 0;
            int raza58 = 0;
            int raza59 = 0;
            int raza60 = 0;
            int raza61 = 0;
            int raza62 = 0;
            int raza63 = 0;
            int raza64 = 0;
            int raza65 = 0;
            int raza66 = 0;
            int raza67 = 0;
            int raza68 = 0;
            int raza69 = 0;
            int raza70 = 0;

            int cols = tblMascotas.getColumnCount();
            int fils = tblMascotas.getRowCount();
            for(int i=0; i<fils; i++) 
            {
                switch (controlVeterinaria.getRazas()[i].getNombre()) 
                {
                    case "Maine Coon":
                        raza1++;
                       
                    case "Gato Persa":
                        raza2++;
                        break;
                    case "Sphynx":
                        raza3++;
                        break;
                    case "Gato Esfinge":
                        raza4++;
                        break;
                    case "Gato Saimés":
                        raza5++;
                        break;
                    case "Gato Bengalí":
                        raza6++;
                        break;
                    case "Gato Exótico":
                        raza7++;
                        break;
                    case "Bosque de Noruega":
                        raza8++;
                        break;
                    case "Gato Siberiano":
                        raza9++;
                        break;
                    case "Azul Ruso":
                        raza10++;
                        break;
                    case "Gato Ragdoll":
                        raza11++;
                        break;
                    case "British Shorthair":
                        raza12++;
                        break;
                    case "Gato Oriental":
                        raza13++;
                        break;
                    case "Gato Birmano":
                        raza14++;
                        break;
                    case "Angora Turco":
                        raza15++;
                        break;
                    case "Van Turco":
                        raza16++;
                        break;
                    case "Gato Himalayo":
                        raza17++;
                        break;
                    case "Savannah":
                        raza18++;
                        break;
                    case "Nebelung":
                        raza19++;
                        break;
                    case "PeterBald":
                        raza20++;
                        break;
                    case "Lykoi":
                        raza21++;
                        break;
                    case "Munchkin":
                        raza22++;
                        break;
                    case "Burmés":
                        raza23++;
                        break;
                    case "Tonkinés":
                        raza24++;
                        break;
                    case "Curl Americano":
                        raza25++;
                        break;
                    case "Scottish Fold":
                        raza26++;
                        break;
                    case "Burmilla":
                        raza27++;
                        break;
                    case "Abisinio":
                        raza28++;
                        break;
                    case "Gato Bombay":
                        raza29++;
                        break;
                    case "Korat":
                        raza30++;
                        break;
                    case "Bobtail Japonés":
                        raza31++;
                        break;
                    case "Gato Balinés":
                        raza32++;
                        break;
                    case "Singapura":
                        raza33++;
                        break;
                    case "Laperm":
                        raza34++;
                        break;
                    case "Devon Rex":
                        raza35++;
                        break;
                    case "Cornish Rex":
                        raza36++;
                        break;
                    case "Selkirk Rex":
                        raza37++;
                        break;
                    case "Gato Común Europeo":
                        raza38++;
                        break;
                    case "Snowshoe":
                        raza39++;
                        break;
                    case "Gato Cartujo":
                        raza40++;
                    case "Mau Egipcio":
                        raza41++;
                        break;
                    case "Ocicat":
                        raza42++;
                        break;
                    case "Toyger":
                        raza43++;
                        break;
                    case "Gato Manx":
                        raza44++;
                        break;
                    case "Gato Ragamuffin":
                        raza45++;
                        break;
                    case "Cocker spaniel":
                        raza46++;
                        break;
                    case "Pomerania":
                        raza47++;
                        break;
                    case "Bulldog Francés":
                        raza48++;
                        break;
                    case "Carlino":
                        raza49++;
                        break;
                    case "Pug":
                        raza50++;
                        break;
                    case "Chihuahua":
                        raza51++;
                        break;
                    case "Perro salchicha":
                        raza52++;
                        break;
                    case "Teckel":
                        raza53++;
                        break;
                    case "Pequinés":
                        raza54++;
                        break;
                    case "Pinscher mini":
                        raza55++;
                        break;
                    case "Boston Terrier":
                        raza56++;
                        break;
                    case "Scottish Terrier":
                        raza57++;
                        break;
                    case "Basenji":
                        raza58++;
                        break;
                    case "Corgi":
                        raza59++;
                        break;
                    case "Terrier Tibetano":
                        raza60++;
                        break;
                    case "Alabai":
                        raza61++;
                        break;
                    case "Crestado Chino":
                        raza62++;
                        break;
                    case "Chow Chow":
                        raza63++;
                        break;
                    case "Lhasa Apso":
                        raza64++;
                        break;
                    case "Shar Pei":
                        raza65++;
                        break;
                    case "Akita Inu japonés":
                        raza66++;
                        break;
                    case "Chongqing":
                        raza67++;
                        break;
                    case "Bichón Maltés":
                        raza68++;
                        break;
                    case "french poodle":
                        raza69++;
                        break;
                    case "Criollo":
                        raza70++;
                        break;
                    default:
                        break;
                } 
                System.out.println(tblMascotas.getValueAt(i, 5));
                this.txtRaza1.setText(String.valueOf(raza1));
//                this.jtxtBaños.setText(String.valueOf(b));
//                this.jtxtCirugia.setText(String.valueOf(c));
//                this.jtxtDestartaje.setText(String.valueOf(d));
//                this.jtxtEcografias.setText(String.valueOf(e));
//                this.jtxtRadiografias.setText(String.valueOf(r));
            tblMascotas.repaint();
            }
        tblMascotas.repaint();
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
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JInternalFrame jifListarMascotas;
    private javax.swing.JInternalFrame jifLogin;
    private javax.swing.JInternalFrame jifRegistrarMasota;
    private javax.swing.JInternalFrame jifTotalRaza;
    private javax.swing.JLabel jlbCodigoMascota;
    private javax.swing.JLabel jlbEspecie;
    private javax.swing.JLabel jlbFecha;
    private javax.swing.JLabel jlbGatos;
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
    private javax.swing.JMenuItem menuTotalRaza;
    private javax.swing.JTable tblMascotas;
    private javax.swing.JTextField txtCodigoMascota;
    private javax.swing.JPasswordField txtContrasenia;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtNombreMascota;
    private javax.swing.JTextField txtRaza1;
    private javax.swing.JTextField txtRaza10;
    private javax.swing.JTextField txtRaza11;
    private javax.swing.JTextField txtRaza12;
    private javax.swing.JTextField txtRaza13;
    private javax.swing.JTextField txtRaza14;
    private javax.swing.JTextField txtRaza15;
    private javax.swing.JTextField txtRaza16;
    private javax.swing.JTextField txtRaza17;
    private javax.swing.JTextField txtRaza18;
    private javax.swing.JTextField txtRaza19;
    private javax.swing.JTextField txtRaza2;
    private javax.swing.JTextField txtRaza20;
    private javax.swing.JTextField txtRaza21;
    private javax.swing.JTextField txtRaza22;
    private javax.swing.JTextField txtRaza23;
    private javax.swing.JTextField txtRaza24;
    private javax.swing.JTextField txtRaza25;
    private javax.swing.JTextField txtRaza26;
    private javax.swing.JTextField txtRaza27;
    private javax.swing.JTextField txtRaza28;
    private javax.swing.JTextField txtRaza29;
    private javax.swing.JTextField txtRaza3;
    private javax.swing.JTextField txtRaza30;
    private javax.swing.JTextField txtRaza31;
    private javax.swing.JTextField txtRaza32;
    private javax.swing.JTextField txtRaza33;
    private javax.swing.JTextField txtRaza34;
    private javax.swing.JTextField txtRaza35;
    private javax.swing.JTextField txtRaza36;
    private javax.swing.JTextField txtRaza37;
    private javax.swing.JTextField txtRaza38;
    private javax.swing.JTextField txtRaza39;
    private javax.swing.JTextField txtRaza4;
    private javax.swing.JTextField txtRaza40;
    private javax.swing.JTextField txtRaza41;
    private javax.swing.JTextField txtRaza42;
    private javax.swing.JTextField txtRaza43;
    private javax.swing.JTextField txtRaza44;
    private javax.swing.JTextField txtRaza45;
    private javax.swing.JTextField txtRaza5;
    private javax.swing.JTextField txtRaza6;
    private javax.swing.JTextField txtRaza7;
    private javax.swing.JTextField txtRaza8;
    private javax.swing.JTextField txtRaza9;
    // End of variables declaration//GEN-END:variables

}



//}
        
//        DefaultTableModel model= (DefaultTableModel) tblTotalRazaGatos.getModel();
//        model.getDataVector().removeAllElements();
//        
//        for(TotalRaza toraza: controlVeterinaria.getTotalRaza())
//        {
//            if(toraza!=null)
//            {
//                
//                for(int i=0; i<fils; i++)
//                {        
//                    switch (controlVeterinaria.getRazas()[i].getNombre()) 
//                    {
//                            case "Maine Coon":
//                                Object[] fila = {raza1,toraza.getNombre()};
//                                model.addRow(fila);
//                                break;
//                    }
//                }
//            }
//            
//        }   
//        tblTotalRazaGatos.repaint();