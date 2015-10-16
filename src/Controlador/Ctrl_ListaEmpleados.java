/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Logica.EmpleadoVista_Logica;
import Modelo.Controles.ModeloTablaEmpleados;
import Modelo.Empleados_Vista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JDesktopPane;
import vista.ActualizaEmpleado;
import vista.ListarEmpleados;
import vista.Main;

/**
 *
 * @author ChristianM
 */
public class Ctrl_ListaEmpleados implements ActionListener, MouseListener {
    
    ListarEmpleados formListE;
    Empleados_Vista ev;
    EmpleadoVista_Logica evl;

    JDesktopPane pan = null; 

    public Ctrl_ListaEmpleados(ListarEmpleados form,JDesktopPane panel) {
        this.formListE = form;
        this.pan = panel;
        this.formListE.setBounds(0, 0, 600, 339);
        this.evl = new EmpleadoVista_Logica();
        this.ev = new Empleados_Vista();
        listarEmpleados();
        
//        formListE.btn_editar.addActionListener(this);
        formListE.tbl_empleados.addMouseListener(this);
        
        formListE.btn_eliminar.setVisible(false);
        formListE.btn_nuevo.setVisible(false);
    }

    public void runner(){
        this.pan.add(formListE);
        formListE.show();
    }

    public void listarEmpleados() {
        List<Empleados_Vista> lista = evl.listarEmpleados();
        Modelo.Controles.ModeloTablaEmpleados modeloT = new ModeloTablaEmpleados(lista);
        formListE.tbl_empleados.setModel(modeloT);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("btn_editar")){
//            ActualizaEmpleado formAE = new ActualizaEmpleado();
            CtrlActualizaEmpleado ctrlform =  new CtrlActualizaEmpleado(pan);
            ctrlform.runner(ev);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(formListE.tbl_empleados)) {
            int fila = formListE.tbl_empleados.getSelectedRow();
            
            ev.setIdPersona(Integer.parseInt(formListE.tbl_empleados.getValueAt(fila, 0).toString()));
            ev.setNombres(formListE.tbl_empleados.getValueAt(fila, 1).toString());
            ev.setApellidoPaterno(formListE.tbl_empleados.getValueAt(fila, 2).toString());
            ev.setApellidoMaterno(formListE.tbl_empleados.getValueAt(fila, 3).toString());
            ev.setDireccion(formListE.tbl_empleados.getValueAt(fila, 4).toString());
            ev.setCorreo(formListE.tbl_empleados.getValueAt(fila, 5).toString());
            ev.setDocumento(formListE.tbl_empleados.getValueAt(fila, 6).toString());
            ev.setNdocumento(formListE.tbl_empleados.getValueAt(fila, 7).toString());
            ev.setCategoria(formListE.tbl_empleados.getValueAt(fila, 8).toString());
            ev.setSueldo(Double.parseDouble(formListE.tbl_empleados.getValueAt(fila, 9).toString()));
            
            
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
