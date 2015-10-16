package Controlador;

import Logica.HabitacionLogica;
import Logica.TipoHabitacionLogica;
import Modelo.Controles.ModelComboTipoHabitacion;
import Modelo.Controles.ModeloTablaHabitaciones;
import Modelo.Controles.ModeloTablaTipoHabitacion;
import Modelo.Habitacion;
import Modelo.Tipohabitacion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import vista.JRegistroHabitacion;

public class CtrlRegistroHabitacion implements ActionListener, MouseListener{
    JRegistroHabitacion formRegHab;
    TipoHabitacionLogica tipHabLog;
    HabitacionLogica habLog;
    Habitacion hab;
    int idHab = 0;
            
    JDesktopPane panelMain = null;
    
    public CtrlRegistroHabitacion(JDesktopPane panel) {
        this.formRegHab = new JRegistroHabitacion();
        tipHabLog = new TipoHabitacionLogica();  
        habLog = new HabitacionLogica();
        this.panelMain = panel;
        listarCategorias();
        listarHabitaciones();
    }
    
    public void listarCategorias(){
        List<TipoHabitacion> listCombo = tipHabLog.listarTiposHab();
        Modelo.Controles.ModelComboTipoHabitacion ModelCombo = new ModelComboTipoHabitacion(listCombo);
        formRegHab.cbxTipoHab.setModel(ModelCombo);
        if(!listCombo.isEmpty()){
            formRegHab.cbxTipoHab.setSelectedIndex(0);
        }
        
        List<TipoHabitacion> listTable = tipHabLog.listarTiposHab();
        Modelo.Controles.ModeloTablaTipoHabitacion ModelTable = new ModeloTablaTipoHabitacion(listTable);
        formRegHab.tblTipHab.setModel(ModelTable);


    }
    
    public void listarHabitaciones(){
        List<Habitacion> listHab = habLog.ListarHabitaciones();
        Modelo.Controles.ModeloTablaHabitaciones modelTabla = new ModeloTablaHabitaciones(listHab);
        formRegHab.tblListHab.setModel(modelTabla);
    }
    
    
    public void showForm(){        
        formRegHab.btnAddTipoHab.addActionListener(this);
        formRegHab.btnGuardar.addActionListener(this);
        formRegHab.btnEdit.addActionListener(this);        
        formRegHab.btnLimpiar.addActionListener(this);
        formRegHab.btnEliminar.addActionListener(this);
        formRegHab.tblTipHab.addMouseListener(this);
        formRegHab.tblListHab.addMouseListener(this);
        
        panelMain.add(formRegHab);
        formRegHab.setVisible(false);
        formRegHab.show();
        //return formRegHab;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        
        if(e.getActionCommand().equals("addTipoHab")){
            CtrlRegitroTipoHabitacion ctrlTipoHab = new CtrlRegitroTipoHabitacion(this, panelMain);
            ctrlTipoHab.showForm();
        }
        
        if(e.getActionCommand().equals("Guardar")){
            
            hab = new Habitacion();
            TipoHabitacion tipHab = (TipoHabitacion)formRegHab.cbxTipoHab.getSelectedItem();
            
            hab.setTipHab(tipHab);
            hab.setNumero(formRegHab.txtnumHab.getText());
            hab.setPiso(Integer.parseInt(formRegHab.spnPiso.getValue().toString()));
            hab.setPrecio(Double.parseDouble(formRegHab.txtprecio.getText()));           
            hab.setBaño((formRegHab.ckbbaño.isSelected()) ? "Si" : "No");
            hab.setMedidas(formRegHab.txtancho.getText()+" X "+formRegHab.txtlargo.getText());
            hab.setEstado(formRegHab.cbxEstado.getSelectedIndex()+1);
            
            int  rspta;
            
            if((idHab > 0)){
                hab.setIdHabitacion(idHab);
                rspta = habLog.actualizarHabitacion(hab);
            }
            else {
                rspta = habLog.registroHabitacion(hab);
            }
            
                                    
            if(rspta == -1){
                JOptionPane.showMessageDialog(null, "Ya existe el Numero de Habitacion ","Registro Habitacion",JOptionPane.ERROR_MESSAGE);
            }else if(rspta == 0){
                JOptionPane.showMessageDialog(null, "El precio no puede ser Cero o menor","Registro Habitacion",JOptionPane.ERROR_MESSAGE);
            }else if(rspta == 1){
                JOptionPane.showMessageDialog(null, "Registro Realizado con Exito","Registro Habitacion",JOptionPane.INFORMATION_MESSAGE);
                //llamar a lista en tabla habitaciones
                listarHabitaciones();
                limpiarControls();
            }else{
                JOptionPane.showMessageDialog(null, "Error de Registro","Registro Habitacion",JOptionPane.ERROR_MESSAGE);
            }   
        }
        
        if(e.getActionCommand().equals("Editar")){
            int row = formRegHab.tblListHab.getSelectedRow();
            
            if(row != -1){
                
                idHab = Integer.parseInt(formRegHab.tblListHab.getValueAt(row, 0).toString());     
                
                TipoHabitacion idTipHab = (TipoHabitacion)formRegHab.tblListHab.getValueAt(row, 1);
                
                String num = formRegHab.tblListHab.getValueAt(row, 2).toString();
                int piso = Integer.parseInt(formRegHab.tblListHab.getValueAt(row, 3).toString());
                double precio = Double.parseDouble(formRegHab.tblListHab.getValueAt(row, 4).toString());
                String baño = formRegHab.tblListHab.getValueAt(row, 5).toString();
                String medidas[] = formRegHab.tblListHab.getValueAt(row, 6).toString().split(" X ");
                String estado = formRegHab.tblListHab.getValueAt(row, 7).toString();
                
                //capturamos el modelo del combo actual y lo volvemos a setear nuevamente al combo 
                //esto corrige el error de seteo de datos por accion de otros controles
                ModelComboTipoHabitacion ModelCombo = (ModelComboTipoHabitacion)formRegHab.cbxTipoHab.getModel();
                ModelCombo.setSelectedItem(idTipHab);                
                formRegHab.cbxTipoHab.setModel(ModelCombo);
                formRegHab.cbxTipoHab.updateUI();
                
                formRegHab.cbxEstado.setSelectedItem(estado);
                formRegHab.txtnumHab.setText(num);
                formRegHab.txtprecio.setText(precio+"");
                formRegHab.spnPiso.setValue(piso);
                formRegHab.txtancho.setText(medidas[0]);
                formRegHab.txtlargo.setText(medidas[1]);             
                formRegHab.ckbbaño.setSelected(baño.equals("Si"));
                
                formRegHab.btnEdit.setText("Cancel Edit");
                formRegHab.btnEdit.setActionCommand("Cancelar");
                formRegHab.tblListHab.setEnabled(false);
                formRegHab.tblTipHab.setEnabled(false);                      
                
            }
        }
        
        if(e.getActionCommand().equals("Cancelar")){
            
            formRegHab.btnEdit.setText("Editar");
            formRegHab.btnEdit.setActionCommand("Editar");
            formRegHab.tblListHab.setEnabled(true);
            formRegHab.tblTipHab.setEnabled(true);
            limpiarControls();
            idHab = 0;
        }
        
        if(e.getActionCommand().equals("Limpiar")){
            limpiarControls();
        }
        
        
        if(e.getActionCommand().equals("Eliminar") || e.getActionCommand().equals("Habilitar")){
            int row = formRegHab.tblListHab.getSelectedRow();
            
            if(row != -1){
                hab = new Habitacion();                
                hab.setIdHabitacion(Integer.parseInt(formRegHab.tblListHab.getValueAt(row, 0).toString()));
                habLog.eliminarHabitacion(hab);
                listarHabitaciones();
            }
        }
    }
    
    public void limpiarControls(){
        
        formRegHab.cbxEstado.setSelectedIndex(0);
        formRegHab.txtnumHab.setText("");
        formRegHab.txtprecio.setText("");
        formRegHab.spnPiso.setValue(1);
        formRegHab.txtancho.setText("");
        formRegHab.txtlargo.setText("");             
        formRegHab.ckbbaño.setSelected(false);

        formRegHab.btnEdit.setText("Editar");
        formRegHab.btnEdit.setActionCommand("Editar");
        formRegHab.tblListHab.setEnabled(true);
        formRegHab.tblTipHab.setEnabled(true);
        
        double num = .1;
        System.out.println(num+"");
    }
    
    @Override
    public void mouseClicked(MouseEvent me) {
        
        if(me.getSource().equals(formRegHab.tblTipHab)){
           
            int fila = formRegHab.tblTipHab.getSelectedRow();
            TipoHabitacion idTipHab = new TipoHabitacion();
            idTipHab.setIdTipoHabitacion(Integer.parseInt(formRegHab.tblTipHab.getValueAt(fila,0).toString()));
            
            formRegHab.tblListHab.removeAll();
            formRegHab.tblListHab.setModel(new DefaultTableModel());
            List<Habitacion> listHab = habLog.ListarHabitacion_xTipo(idTipHab);
            Modelo.Controles.ModeloTablaHabitaciones modelTabla = new ModeloTablaHabitaciones(listHab);            
            formRegHab.tblListHab.setModel(modelTabla);
        }
        
        if(me.getSource().equals(formRegHab.tblListHab)){
            int fila = formRegHab.tblListHab.getSelectedRow();
            String est = formRegHab.tblListHab.getValueAt(fila, 7).toString();
            
            if(est.equals("Ocupado")){
                formRegHab.btnEliminar.setEnabled(false);
                formRegHab.btnEdit.setEnabled(false);
            }else {
                formRegHab.btnEliminar.setEnabled(true);
                formRegHab.btnEdit.setEnabled(true);
            }
            
            if(est.equals("Deshabilitado")){
                formRegHab.btnEliminar.setText("Habilitar");
                formRegHab.btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("../Imagenes/thumbs-up.png")));
            } else {
                formRegHab.btnEliminar.setText("Eliminar");
                formRegHab.btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("../Imagenes/trash.png")));
                
            }
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
    
}
