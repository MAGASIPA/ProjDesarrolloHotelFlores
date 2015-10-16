
package LOGICA;


import Control.TipodocumentoJpaController;
import Modelo.Tipodocumento;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TipoDocLogica {
    public List<Tipodocumento> listarTDocumentos() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PROYECTO_FINAL_DESARROLLOPU");
        EntityManager em = emf.createEntityManager();
        TipodocumentoJpaController servicio = new TipodocumentoJpaController(emf);
        List<Tipodocumento> lista = null;

        em.getTransaction().begin();
        try {
          lista = servicio.findTipodocumentoEntities();
        } catch (Exception e) {
          em.getTransaction().rollback();
          throw e;
        }
        em.close();
        emf.close();
        return lista;
    }
    
    
//    public int actualizarTipoDocumento(TipoDocumento tipod) {
//        TipoDocumentoDAO td = new TipoDocumentoDAO();
//        return td.actualizarTipoDocumento(tipod);
//    }
//    public boolean guardarTipoDocumento(TipoDocumento tipod) {
//        TipoDocumentoDAO td = new TipoDocumentoDAO();
//        return td.guardarTipoDocumento(tipod);
//    }
//
//    public int eliminarTipoDocumento(TipoDocumento id) {
//        TipoDocumentoDAO td = new TipoDocumentoDAO();
//        return td.eliminarTipoDocumento(id);
//    }
//    
//     public List<TipoDocumento> buscarTipoDocumento(String nom) {
//        TipoDocumentoDAO td = new TipoDocumentoDAO();
//        return td.buscarTipoDocumento(nom);
//    }
}
