
package Controlador;

import Logica.AlquilerReservaLogica;
import Logica.DocumentoAlquilerLogica;
import Modelo.DocumentoAlquiler;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import vista.JRegistroFacturacion;

public class CtrlRegistroFacturacion implements ActionListener{

    JRegistroFacturacion formFact;
    JDesktopPane DeskMain;
    DocumentoAlquiler docAl;
    DocumentoAlquilerLogica docAlLog;
    int idHab = 0;
    int idAlq = 0;
    
    public CtrlRegistroFacturacion(JDesktopPane panel, int idHab) {
        formFact = new JRegistroFacturacion();
        DeskMain = panel;
        this.idHab = idHab;
        docAlLog = new DocumentoAlquilerLogica();
        
        docAl = docAlLog.devolverDocAlquiler(idHab);
        
        formFact.lblMontoAlquiler.setText(docAl.getMontoAlquiler()+"");
        formFact.lblMontoTotal.setText(docAl.getMontoAlquiler()+"");
        idAlq = docAl.getIdDocumentoAlquiler();
    }
    
    public void showForm(){
        this.DeskMain.add(formFact);
        formFact.btnGuardar.addActionListener(this);
        formFact.btnCancelar.addActionListener(this);
        formFact.show();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Guardar")){
            
            DocumentoAlquiler docAl = new DocumentoAlquiler();
            docAl.setIdDocumentoAlquiler(idAlq);
            docAl.setSerie(Integer.parseInt(formFact.txtSerie.getText()));
            docAl.setNumeroDocumento(Integer.parseInt(formFact.txtNumDoc.getText()));
            docAl.setMontoTotal(Double.parseDouble(formFact.lblMontoTotal.getText()));
            
            String msj = docAlLog.registroDocumentoAlquiler(docAl, idHab);
            
            JOptionPane.showMessageDialog(null, msj,"Registro Documento Alquiler",JOptionPane.INFORMATION_MESSAGE);
        }
        if(e.getActionCommand().equals("Cancelar")){
            formFact.dispose();
        }
    }
    
}
