package Controlador;

import LOGICA.TipoDocLogica;
import Modelo.Controles.ModeloTablaTipoDocumento;
import Modelo.TipoDocumento;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;
import vista.RegTipoDocumento;
import vista.Reg_TipoDocumento;

/**
 *
 * @author hneriam
 */
public class TipoDocumentoControlador implements ActionListener, MouseListener, KeyListener {
    private TipoDocLogica tipoDocumentoLogica;
    public RegTipoDocumento formularioTipoDocumento;
    JDesktopPane pan = null;
    private int IdTipo;
    
    
     public TipoDocumentoControlador(RegTipoDocumento form, JDesktopPane panel) {
        tipoDocumentoLogica = new TipoDocLogica();
        formularioTipoDocumento = new RegTipoDocumento();
        this.pan= panel;
        this.formularioTipoDocumento = form;
        this.formularioTipoDocumento.setBounds(0, 0, 425, 425);
        this.Escuchadores();

    }

    public void Escuchadores() {
  

    //        Escuchadores
        formularioTipoDocumento.btnGuardar.addActionListener(this);
        formularioTipoDocumento.btnSalir.addActionListener(this);
        formularioTipoDocumento.btnEliminar.addActionListener(this);
        formularioTipoDocumento.btnRefrescar.addActionListener(this);
        formularioTipoDocumento.btnModificar.addActionListener(this);
        formularioTipoDocumento.txtDescripcion.addKeyListener(this);
    }

    public void runner(){
        
        this.pan.add(formularioTipoDocumento);
        formularioTipoDocumento.setTitle("Gestion de Tipo Documentos");
        formularioTipoDocumento.setVisible(true);
        formularioTipoDocumento.txtCodigo.enable(false);
        listarTipoDocumentos();
        formularioTipoDocumento.show();
    }

    private void listarTipoDocumentos() {
        
        List<TipoDocumento> listita = tipoDocumentoLogica.listarTDocumentos();
        TableModel modelito = new ModeloTablaTipoDocumento(listita);
        formularioTipoDocumento.tblListarTipoDocumentos.setModel(modelito);
        formularioTipoDocumento.tblListarTipoDocumentos.getRowSorter();
        formularioTipoDocumento.tblListarTipoDocumentos.getColumnModel().getColumn(0).setPreferredWidth(1);
        formularioTipoDocumento.tblListarTipoDocumentos.getColumnModel().getColumn(0).setResizable(false);
        
    }

    private void limpiar() {
        formularioTipoDocumento.txtCodigo.setText("");
        formularioTipoDocumento.txtDescripcion.setText("");
        this.IdTipo = 0;
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
         if (ae.getActionCommand().equals("Guardar")) {
            TipoDocumento td = new TipoDocumento();
             if (formularioTipoDocumento.txtDescripcion.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor ingrese datos en el campo DESCRIPCIÓN");
            }else{
            td.setNombreDoc(formularioTipoDocumento.txtDescripcion.getText().trim());
            if (tipoDocumentoLogica.guardarTipoDocumento(td)) {
                JOptionPane.showMessageDialog(null, "No se guardo");
            } else {
                JOptionPane.showMessageDialog(null, "se guardo");
                listarTipoDocumentos();
                limpiar();
            }
            listarTipoDocumentos();
             }
        }
        if (ae.getActionCommand().equals("Actualizar")) {
            TipoDocumento td = new TipoDocumento();
            td.setIdTipoDocumento(Integer.parseInt(formularioTipoDocumento.txtCodigo.getText().trim()));
            td.setNombreDoc(formularioTipoDocumento.txtDescripcion.getText().trim());
            int r = tipoDocumentoLogica.actualizarTipoDocumento(td);
            System.out.println(r);
            if (r != 0) {
                JOptionPane.showMessageDialog(null, "Actualizando...");
            } else {
                JOptionPane.showMessageDialog(null, "No se Actualizó");
            }
            limpiar();
            listarTipoDocumentos();
            formularioTipoDocumento.btnGuardar.setText("Guardar");
        }

        if (ae.getActionCommand().equals("Salir")) {
            formularioTipoDocumento.dispose();
        }

        if (ae.getActionCommand().equals("Eliminar")) {
             TipoDocumento td = new TipoDocumento();
            int p = Integer.parseInt(
                    formularioTipoDocumento.tblListarTipoDocumentos.getValueAt(formularioTipoDocumento.tblListarTipoDocumentos.getSelectedRow(), 0).toString());
            td.setIdTipoDocumento(p);
            int r = tipoDocumentoLogica.eliminarTipoDocumento(td);
            if (r >= 1) {
                JOptionPane.showMessageDialog(formularioTipoDocumento, "Eliminado");
                listarTipoDocumentos();
            } else {
                JOptionPane.showMessageDialog(formularioTipoDocumento, "No se Eliminó");
            }
            limpiar();

        }
        if (ae.getActionCommand().equals("Modificar")) {
            formularioTipoDocumento.btnGuardar.setText("Actualizar");
            if (formularioTipoDocumento.tblListarTipoDocumentos.getSelectedRow() >= 0) {

                formularioTipoDocumento.txtCodigo.setText(
                        formularioTipoDocumento.tblListarTipoDocumentos.getValueAt(formularioTipoDocumento.tblListarTipoDocumentos.getSelectedRow(), 0).toString());

                formularioTipoDocumento.txtDescripcion.setText(
                        formularioTipoDocumento.tblListarTipoDocumentos.getValueAt(formularioTipoDocumento.tblListarTipoDocumentos.getSelectedRow(), 1).toString());

            } else {
                JOptionPane.showMessageDialog(formularioTipoDocumento, "Seleccione un tipo documento");
            }
            listarTipoDocumentos();
        }
        if (ae.getActionCommand().equals("Refrescar")) {
            limpiar();
            listarTipoDocumentos();
            formularioTipoDocumento.btnGuardar.setText("Guardar");
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        
    }

    @Override
    public void mousePressed(MouseEvent me) {
        
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        
    }

    @Override
    public void mouseExited(MouseEvent me) {
        
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        
    }

    @Override
    public void keyReleased(KeyEvent ke) {
         String des = "";
        des = formularioTipoDocumento.txtDescripcion.getText();
        if (!des.matches("[a-zA-Z]*")) {
            JOptionPane.showMessageDialog(null, "En el campo DESCRIPCION solo se admiten letras", "Advertencia", JOptionPane.ERROR_MESSAGE);
        }
      
    }

    
}
