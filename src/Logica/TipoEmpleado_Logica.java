package Logica;

import Control.TipoempleadoJpaController;
import Modelo.Tipoempleado;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;

/**
 *
 * @author ChristianM
 */
public class TipoEmpleado_Logica {

    public static boolean agregar(Tipoempleado obj) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PROYECTO_FINAL_DESARROLLOPU");
        EntityManager em = emf.createEntityManager();
        TipoempleadoJpaController servicio = new TipoempleadoJpaController(emf);

        em.getTransaction().begin();
        try {
            servicio.create(obj);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
        em.close();
        emf.close();
        return true;
    }

    public static List<Tipoempleado> listado() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PROYECTO_FINAL_DESARROLLOPU");
        EntityManager em = emf.createEntityManager();
        TipoempleadoJpaController servicio = new TipoempleadoJpaController(emf);
        List<Tipoempleado> lista = null;

        em.getTransaction().begin();
        try {
            lista = servicio.findTipoempleadoEntities();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
        em.close();
        emf.close();
        return lista;
    }

    public static boolean modificar(Tipoempleado obj) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PROYECTO_FINAL_DESARROLLOPU");
        EntityManager em = emf.createEntityManager();
    //DepartamentoJpaController servicio = new DepartamentoJpaController(emf);

        em.getTransaction().begin();
        try {
            StoredProcedureQuery query = em.createStoredProcedureQuery("sp_Actualizar_Tipo_Empleado");
            //Registrar parametros y su tipo: entrada o salida
            query.registerStoredProcedureParameter("id", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("Cat", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("Des", String.class, ParameterMode.IN);
            //Registrar datos en los parametros
            query.setParameter("id", obj.getIdTipoEmpleado());
            query.setParameter("Cat", obj.getCategoria());
            query.setParameter("Des", obj.getDescripcion());
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
}
