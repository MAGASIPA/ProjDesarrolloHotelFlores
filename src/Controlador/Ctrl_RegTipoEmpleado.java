package Controlador;

import Logica.TipoEmpleado_Logica;
import Modelo.MODELO_TABLA_TIPO_EMPLEADO;
import Modelo.Tipoempleado;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import vista.Reg_TipoEmpleado;

/**
 *
 * @author ChristianM
 */
public class Ctrl_RegTipoEmpleado implements ActionListener, MouseListener {

    Reg_TipoEmpleado formRTE = new Reg_TipoEmpleado();
    JDesktopPane pan = null;
    TipoEmpleado_Logica tel;
    MODELO_TABLA_TIPO_EMPLEADO modelo;
    Tipoempleado tipoempleado;

    public Ctrl_RegTipoEmpleado(Reg_TipoEmpleado form, JDesktopPane panel) {

        tel = new TipoEmpleado_Logica();
        this.pan = panel;
        this.formRTE = form;

//        this.formRTE.setBounds(0, 0, 348, 207);
        this.formRTE.setBounds(0, 0, 700, 350);

        formRTE.btn_guardar.addActionListener(this);
        formRTE.btn_cancelar.addActionListener(this);
        formRTE.btn_Modificar.addActionListener(this);
        formRTE.Tbl_TipoEmpleado.addMouseListener(this);
    }

    public void cargarTipoEmpleado() {
        List<Tipoempleado> lista;
        lista = tel.listado();
        modelo = new MODELO_TABLA_TIPO_EMPLEADO(lista);
        formRTE.Tbl_TipoEmpleado.setModel(modelo);
    }

    public void limpiar() {
        formRTE.txt_categoria.setText(null);
        formRTE.txt_descripcion.setText(null);
    }

    public void runner() {
        this.pan.add(formRTE);
        formRTE.show();
        cargarTipoEmpleado();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("btn_cancelar")) {
            formRTE.dispose();
        }
        if (e.getActionCommand().equals("btn_guardar")) {
            Tipoempleado te = new Tipoempleado();

            if (formRTE.txt_categoria.getText().isEmpty()
                    && formRTE.txt_descripcion.getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Por favor ingrese datos en los campos vacíos");

            } else {
                te.setIdTipoEmpleado(Integer.SIZE);
                if (formRTE.txt_categoria.getText().isEmpty()
                        || formRTE.txt_descripcion.getText().isEmpty()) {

                    JOptionPane.showMessageDialog(null, "Por favor ingrese datos en los campos vacíos");
                    limpiar();
                } else {
                    te.setCategoria(formRTE.txt_categoria.getText().toString().trim());
                    te.setDescripcion(formRTE.txt_descripcion.getText().toString().trim());
                }

                if (tel.agregar(te)) {
                    JOptionPane.showMessageDialog(null, "Se guardo");
                    cargarTipoEmpleado();
                    limpiar();

                } else {
                    JOptionPane.showMessageDialog(null, "No se guardo");
                    cargarTipoEmpleado();
                    limpiar();
                }
                cargarTipoEmpleado();
            }
        }

        if (e.getActionCommand().equals("btn_Modificar")) {
            int numero1;
            numero1 = Integer.parseInt(formRTE.txt_Codigo.getText());  
            
            Tipoempleado obj = new Tipoempleado(numero1, formRTE.txt_categoria.getText().trim(),formRTE.txt_descripcion.getText().trim());

            if (tel.modificar(obj)) {
                JOptionPane.showMessageDialog(null, "Tipo de Empleado modificado con exito");
                cargarTipoEmpleado();
            } else {
                JOptionPane.showMessageDialog(null, "ERROR en modificación de datos");
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int Pos = formRTE.Tbl_TipoEmpleado.getSelectedRow();
        Tipoempleado obj;
        int a;
        if (Pos >= 0) {
            obj = modelo.getTipoempleado(Pos);
            formRTE.txt_Codigo.setText(obj.getIdTipoEmpleado().toString());
            formRTE.txt_categoria.setText(obj.getCategoria());
            formRTE.txt_descripcion.setText(obj.getDescripcion());
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
