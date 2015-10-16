
package Logica;

import DAO.DocumentoAlquilerDAO;
import Modelo.DocumentoAlquiler;

public class DocumentoAlquilerLogica {
    public DocumentoAlquiler devolverDocAlquiler(int idHab){
        DocumentoAlquilerDAO docal = new DocumentoAlquilerDAO();
        return docal.devolverDocAlquiler(idHab);
    }
    
    public String registroDocumentoAlquiler(DocumentoAlquiler docAlq, int idHab){
        DocumentoAlquilerDAO docal = new DocumentoAlquilerDAO();
        return docal.registroDocumentoAlquiler(docAlq, idHab);
    }
}
