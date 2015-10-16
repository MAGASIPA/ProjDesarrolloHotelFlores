
package LOGICA;

//import DAO.PersonaDAO;
import Modelo.Persona;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;


public class PersonaLogica {
   public boolean actualizarPersona(Persona per) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA_EmpleadosPU");
        EntityManager em = emf.createEntityManager();
        //DepartamentoJpaController servicio = new DepartamentoJpaController(emf);

        em.getTransaction().begin();
        try {
          StoredProcedureQuery query = em.createStoredProcedureQuery("pa_modificaDpto");
          //Registrar parametros y su tipo: entrada o salida
          query.registerStoredProcedureParameter("id", Integer.class, ParameterMode.IN);
          query.registerStoredProcedureParameter("nom", String.class, ParameterMode.IN);
          query.registerStoredProcedureParameter("ap_p", String.class, ParameterMode.IN);
          query.registerStoredProcedureParameter("ap_m", String.class, ParameterMode.IN);
          query.registerStoredProcedureParameter("dir", String.class, ParameterMode.IN);
          query.registerStoredProcedureParameter("correo", String.class, ParameterMode.IN);
          query.registerStoredProcedureParameter("numdoc", String.class, ParameterMode.IN);
          query.registerStoredProcedureParameter("tel", String.class, ParameterMode.IN);
          query.registerStoredProcedureParameter("Idtipo", Integer.class, ParameterMode.IN);
          query.registerStoredProcedureParameter("nac", String.class, ParameterMode.IN);
          query.registerStoredProcedureParameter("ref", String.class, ParameterMode.IN);
          
          //Registrar datos en los parametros
          query.setParameter("id", per.getIdPersona());
          query.setParameter("nom", per.getNombres());
          query.setParameter("ap_p", per.getApellidoPaterno());
          query.setParameter("ap_m", per.getApellidoMaterno());
          query.setParameter("dir", per.getDireccion());
          query.setParameter("correo", per.getCorreo());
          query.setParameter("numdoc", per.getNumerodoc());
          query.setParameter("tel", per.getTelefono());
          query.setParameter("Idtipo", per.getIdTipoDoc());
          query.setParameter("nac", per.getCliente().getNacionalidad());
          query.setParameter("ref", per.getCliente().getReferencia());
          query.execute();
          em.getTransaction().commit();
        } catch (Exception e) {
          em.getTransaction().rollback();
          throw e;
        }
        em.close();
        emf.close();
        return true;
    }
    public boolean guardarPersona(Persona per) {
//        PersonaDAO pers = new PersonaDAO();
//        return pers.guardarPersonaCliente(per);
        return true;
    }
//       public List<Persona> listarPersonas() {
////        PersonaDAO per = new PersonaDAO();
////        return per.listarPersonasCliente();
//    }
//
//    public int eliminarPersonas(Persona id) {
//        PersonaDAO per = new PersonaDAO();
//        return per.eliminarPersonaCliente(id);
//    }
//    
//     public List<Persona> buscarPersonas(String nom) {
//        PersonaDAO per = new PersonaDAO();
//        return per.buscarPersonaCliente(nom);
//    }
//     
//    //AGREGADO OSCAR PARA EL MODULO PERSONA
//    public Persona buscarPersonaClienteXDoc(String numDoc) {
//        PersonaDAO per = new PersonaDAO();
//        return per.buscarPersonaClienteXDoc(numDoc);
//    }
//    
}
