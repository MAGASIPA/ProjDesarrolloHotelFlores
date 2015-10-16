/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Control.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Habitacion;
import Modelo.Artefacto;
import Modelo.Asignacion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
 */
public class AsignacionJpaController implements Serializable {

    public AsignacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Asignacion asignacion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Habitacion idHabitacion = asignacion.getIdHabitacion();
            if (idHabitacion != null) {
                idHabitacion = em.getReference(idHabitacion.getClass(), idHabitacion.getIdHabitacion());
                asignacion.setIdHabitacion(idHabitacion);
            }
            Artefacto idArtefacto = asignacion.getIdArtefacto();
            if (idArtefacto != null) {
                idArtefacto = em.getReference(idArtefacto.getClass(), idArtefacto.getIdArtefacto());
                asignacion.setIdArtefacto(idArtefacto);
            }
            em.persist(asignacion);
            if (idHabitacion != null) {
                idHabitacion.getAsignacionCollection().add(asignacion);
                idHabitacion = em.merge(idHabitacion);
            }
            if (idArtefacto != null) {
                idArtefacto.getAsignacionCollection().add(asignacion);
                idArtefacto = em.merge(idArtefacto);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Asignacion asignacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Asignacion persistentAsignacion = em.find(Asignacion.class, asignacion.getIdAsignacion());
            Habitacion idHabitacionOld = persistentAsignacion.getIdHabitacion();
            Habitacion idHabitacionNew = asignacion.getIdHabitacion();
            Artefacto idArtefactoOld = persistentAsignacion.getIdArtefacto();
            Artefacto idArtefactoNew = asignacion.getIdArtefacto();
            if (idHabitacionNew != null) {
                idHabitacionNew = em.getReference(idHabitacionNew.getClass(), idHabitacionNew.getIdHabitacion());
                asignacion.setIdHabitacion(idHabitacionNew);
            }
            if (idArtefactoNew != null) {
                idArtefactoNew = em.getReference(idArtefactoNew.getClass(), idArtefactoNew.getIdArtefacto());
                asignacion.setIdArtefacto(idArtefactoNew);
            }
            asignacion = em.merge(asignacion);
            if (idHabitacionOld != null && !idHabitacionOld.equals(idHabitacionNew)) {
                idHabitacionOld.getAsignacionCollection().remove(asignacion);
                idHabitacionOld = em.merge(idHabitacionOld);
            }
            if (idHabitacionNew != null && !idHabitacionNew.equals(idHabitacionOld)) {
                idHabitacionNew.getAsignacionCollection().add(asignacion);
                idHabitacionNew = em.merge(idHabitacionNew);
            }
            if (idArtefactoOld != null && !idArtefactoOld.equals(idArtefactoNew)) {
                idArtefactoOld.getAsignacionCollection().remove(asignacion);
                idArtefactoOld = em.merge(idArtefactoOld);
            }
            if (idArtefactoNew != null && !idArtefactoNew.equals(idArtefactoOld)) {
                idArtefactoNew.getAsignacionCollection().add(asignacion);
                idArtefactoNew = em.merge(idArtefactoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = asignacion.getIdAsignacion();
                if (findAsignacion(id) == null) {
                    throw new NonexistentEntityException("The asignacion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Asignacion asignacion;
            try {
                asignacion = em.getReference(Asignacion.class, id);
                asignacion.getIdAsignacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The asignacion with id " + id + " no longer exists.", enfe);
            }
            Habitacion idHabitacion = asignacion.getIdHabitacion();
            if (idHabitacion != null) {
                idHabitacion.getAsignacionCollection().remove(asignacion);
                idHabitacion = em.merge(idHabitacion);
            }
            Artefacto idArtefacto = asignacion.getIdArtefacto();
            if (idArtefacto != null) {
                idArtefacto.getAsignacionCollection().remove(asignacion);
                idArtefacto = em.merge(idArtefacto);
            }
            em.remove(asignacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Asignacion> findAsignacionEntities() {
        return findAsignacionEntities(true, -1, -1);
    }

    public List<Asignacion> findAsignacionEntities(int maxResults, int firstResult) {
        return findAsignacionEntities(false, maxResults, firstResult);
    }

    private List<Asignacion> findAsignacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Asignacion.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Asignacion findAsignacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Asignacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getAsignacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Asignacion> rt = cq.from(Asignacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
