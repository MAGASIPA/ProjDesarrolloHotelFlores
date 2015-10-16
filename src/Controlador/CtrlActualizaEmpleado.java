/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Logica.Empleado_Logica;
import Logica.Persona_Logica;
import Logica.TipoDocumento_Logica;
import Logica.TipoEmpleado_Logica;
import Modelo.Controles.ModeloCombo_TipoDocumento;
import Modelo.Controles.ModeloCombo_TipoEmp;
import Modelo.Empleado;
import Modelo.Empleados_Vista;
import Modelo.Personaa;
import Modelo.TipoDocumento;
import Modelo.TipoEmpleado;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JDesktopPane;
import vista.ActualizaEmpleado;

/**
 *
 * @author ChristianM
 */
public class CtrlActualizaEmpleado implements ActionListener {
    JDesktopPane pan;
    ActualizaEmpleado formAE;
    
    TipoEmpleado_Logica tel;
    TipoDocumento_Logica tdl;
    Persona_Logica pl;
    Empleado_Logica el;

    public CtrlActualizaEmpleado(JDesktopPane panel) {
        this.formAE = new ActualizaEmpleado();
        this.pan = panel;
    }
    
    public void runner(Empleados_Vista ev) {
        llenaform(ev);
        this.pan.add(formAE);
        this.formAE.show();
    }

    private void llenaform(Empleados_Vista ev) {
        formAE.txt_nombre.setText(ev.getNombres().toString());
        formAE.txt_appat.setText(ev.getApellidoPaterno().toString());
        formAE.txt_apmat.setText(ev.getApellidoMaterno().toString());
        formAE.txt_direccion.setText(ev.getDireccion().toString());
        formAE.txt_correo.setText(ev.getCorreo().toString());
        formAE.txt_ndoc.setText(ev.getNdocumento().toString());
        formAE.txt_sueldo.setText(String.valueOf(ev.getSueldo()));
        
        tel = new TipoEmpleado_Logica();
        List<TipoEmpleado> list = tel.leerTiposE();
        ModeloCombo_TipoEmp modeloTE = new ModeloCombo_TipoEmp(list);
        formAE.cb_tipoemp.setModel(modeloTE);
        
        tdl = new TipoDocumento_Logica();
        List<TipoDocumento> list2 = tdl.leerTiposDoc();
        ModeloCombo_TipoDocumento modeloTD = new ModeloCombo_TipoDocumento(list2);
        formAE.cb_tipodoc.setModel(modeloTD);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("btn_cancelar")){
            formAE.dispose();
        }
        if(e.getActionCommand().equals("btn_guardar")){
            Personaa p = new Personaa();
            pl = new Persona_Logica();
            el = new Empleado_Logica();
            p.setNombres(formAE.txt_nombre.getText().toString());
            p.setApellidoPaterno(formAE.txt_appat.getText().toString());
            p.setApellidoMaterno(formAE.txt_apmat.getText().toString());
            p.setDireccion(formAE.txt_direccion.getText().toString());
            p.setCorreo(formAE.txt_correo.getText().toString());
            TipoDocumento td = (TipoDocumento)formAE.cb_tipodoc.getSelectedItem();
            p.setIdTipoDoc(td.getIdTipoDocumento());
            p.setNumerodoc(formAE.txt_ndoc.getText());
            int idp = pl.registrarPersona(p);
            Empleado em = new Empleado();
            em.setIdEmpleado(idp);
            TipoEmpleado te = (TipoEmpleado)formAE.cb_tipoemp.getSelectedItem();
            em.setIdTipoEmpleado(te.getIdTipoEmpleado());
            em.setSueldo(Double.parseDouble(formAE.txt_sueldo.getText().toString()));
            el.regEmpleado(em);
            formAE.dispose();
        }
    }
}
