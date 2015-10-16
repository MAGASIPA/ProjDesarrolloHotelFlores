package Controlador;

import Logica.HabitacionLogica;
import Modelo.Habitacion;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import vista.ScreenHabitaciones;
import vista.itemHabitacion;

public class CtrlScreenHabitaciones implements ActionListener{

    ScreenHabitaciones formScreen = new ScreenHabitaciones();
    JDesktopPane deskMain;
    List<Habitacion> listHab = null;
    HabitacionLogica habLog = null;
    itemHabitacion item_1 = null;
    JPanel content = new JPanel();
    int idEmpleado = 0;
    
    //creacion de controles para panel de filtro
    JLabel lblbuscar;
    JCheckBox ckbLibre;
    JCheckBox ckbocupado;
    JCheckBox ckbMantenimiento;
    JCheckBox ckbLimpieza;
    JButton btnFiltrar;
    JButton btnConfirmarReserv;
    
    
    public CtrlScreenHabitaciones(ScreenHabitaciones form, JDesktopPane panel, int idEmp) {
        this.formScreen = form;  
        this.deskMain = panel;
        this.idEmpleado = idEmp;
        habLog = new HabitacionLogica();
                
        this.formScreen.setLayout(new BorderLayout());
        this.formScreen.setBounds(90,90, 850, 500);
                
        content.setLayout(new ModifiedFlowLayout(ModifiedFlowLayout.LEFT, 20, 20));
        
        //agregar items habitacion
        generarItemsHabitacion(item_1, content,1,null);
        //end
        
        JScrollPane scroll = new JScrollPane(content);        
        scroll.getVerticalScrollBar().setUnitIncrement(10);
        scroll.setBorder(null);

        
        JPanel pnlOptions = new JPanel();
        pnlOptions.setLayout(new FlowLayout(FlowLayout.LEFT));
        pnlOptions.setBounds(0, 0, 500, 120);
        pnlOptions.setBackground(Color.WHITE);
        
        
        lblbuscar = new JLabel("Filtrar Habitacion:");
        ckbLibre = new JCheckBox("Libres");
        ckbocupado = new JCheckBox("Ocupadas");
        ckbMantenimiento = new JCheckBox("Mantenimiento");
        ckbLimpieza = new JCheckBox("Limpieza");
        
        btnFiltrar = new JButton("Filtrar");
        btnConfirmarReserv = new JButton("Confirmar Reservas");
        btnConfirmarReserv.setActionCommand("Confirmar");
        btnFiltrar.setActionCommand("Filtrar");
        
        
        pnlOptions.add(lblbuscar);
        pnlOptions.add(ckbLibre);
        pnlOptions.add(ckbocupado);
        pnlOptions.add(ckbMantenimiento);
        pnlOptions.add(ckbLimpieza);
        pnlOptions.add(btnFiltrar);    
        pnlOptions.add(btnConfirmarReserv);
        btnConfirmarReserv.addActionListener(this);
        btnFiltrar.addActionListener(this);
        
        
        
        this.formScreen.add(scroll,BorderLayout.CENTER);
        this.formScreen.add(pnlOptions,BorderLayout.SOUTH);
       
    }
    //metodo para crear los items dinamicos en formScreenHabitaciones, se le pasan parametros como la clase que crea el item form,
    //el panel donde se van a agregar, made que quiere decir el origen del metodo de consulta y attrib que son los atributos para el 
    //medotdo de consulta 2
    private void generarItemsHabitacion(itemHabitacion item_1, JPanel content, int made, String attrib){
        
        if(made == 1)
            listHab = habLog.ListarHabitaciones_Item();
        else
            listHab = habLog.List_Habitacion_estado(attrib);
        
            
        for(Habitacion hab : listHab){
            item_1 = new itemHabitacion();
            item_1.jButton2.setActionCommand(hab.getIdHabitacion()+"");
            item_1.jLabel5.setText(hab.getIdTipoHabitacion().getCategoria()+" - "+hab.getNumero());//nombre habitacion
            item_1.jButton2.setEnabled(false);///desabilito todos los botones
            item_1.jButton2.addActionListener(this);
            
            String estado = "";
            String rutaImage = "";
            switch(hab.getEstado()){
                case 1:
                    estado = "LIBRE";
                    rutaImage = "/Imagenes/hab-libre.png";
                    item_1.setBackground(new java.awt.Color(137, 183, 59));
                    break;
                case 2:
                    estado = "OCUPADO";   
                    rutaImage = "/Imagenes/hab-ocupada.png";
                    item_1.setBackground(new java.awt.Color(204,51,0));
                    break;
                case 3:
                    estado = "MANTENIMIENTO";
                    rutaImage = "/Imagenes/hab-mantenimiento.png";
                    item_1.setBackground(new java.awt.Color(0,102,153));
                    break;
                case 4:
                    estado = "LIMPIEZA";
                    rutaImage = "/Imagenes/hab-limpieza.png";
                    item_1.setBackground(new java.awt.Color(255,204,0));
                    break;
                case 5:
                    estado = "DESHABILITADO";
                    rutaImage = "/Imagenes/hab-deshabilitado.png";
                    item_1.setBackground(new java.awt.Color(153,153,153));
                    break;
            }            
            item_1.jLabel2.setText(estado);//estado
            item_1.jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource(rutaImage)));
            if(hab.getEstado()== 2){
                item_1.jButton2.setText("DESOCUPAR");
            }
            if(hab.getEstado()== 1){
                item_1.jButton2.setEnabled(true);
            }else if(hab.getEstado()== 2){
                item_1.jButton2.setEnabled(true);
            }
            
            content.add(item_1);
        }        
        
        JButton btnRefresh = new JButton();
        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/refresh2.png")));
        btnRefresh.setActionCommand("btnRefresh");
        
        content.add(btnRefresh);
        btnRefresh.addActionListener(this);
        
    }
    public void showForm(){       
        deskMain.add(formScreen);
        this.formScreen.show();        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("btnRefresh")){
            content.removeAll();
            generarItemsHabitacion(item_1, content,1,null);
            ckbLibre.setSelected(false);
            ckbocupado.setSelected(false);
            ckbMantenimiento.setSelected(false);
            ckbLimpieza.setSelected(false);
            content.updateUI();
        }
        
        if(e.getActionCommand().equals("Filtrar")){
            
            String est = "";
            String estados="";
            //concatenamos para formar una consulta ya que luego enviaremos una consulta elavorada a la base de datos 
            //para que nos retorne lo solicitado segun se marquen los checkbox que identifican a los estados de la habitacion.
            if(ckbLibre.isSelected()){
                est += "1,";
            }
            if(ckbocupado.isSelected()){
                est += "2,";
            }
            if(ckbMantenimiento.isSelected()){
                est += "3,";
            }                        
            if(ckbLimpieza.isSelected()){
                est += "4,";
            }
            
            if(!est.equals("")){
                estados = est.substring(0, est.length()-1);
                content.removeAll();  //limpia el panel de todos los controles          
                generarItemsHabitacion(item_1, content,2,estados);//enviamos los parametros solicitados, el 2 indica que es un listar por estados
                content.updateUI();//refrescamos el frame. UI = User Interface.
            }
            
            
            
        }
        
        
        JButton btn = (JButton)e.getSource();
        if(btn.getText().equals("ALQUILAR")){
            CtrlRegistroAlquilerReserva CtrlRegAlq = new CtrlRegistroAlquilerReserva(deskMain, Integer.parseInt(btn.getActionCommand()),idEmpleado);
            CtrlRegAlq.showForm();
        }
        
        if(btn.getText().equals("DESOCUPAR")){
            CtrlRegistroFacturacion CtrlRegFac = new CtrlRegistroFacturacion(deskMain, Integer.parseInt(btn.getActionCommand()));
            CtrlRegFac.showForm();
        }
        
        if(e.getActionCommand().equals("Confirmar")){
            CtrlRegistroAlquilerReserva CtrlRegAlq = new CtrlRegistroAlquilerReserva(deskMain, 0,idEmpleado);
            CtrlRegAlq.showForm();
        }
        
    }
   
    
}
