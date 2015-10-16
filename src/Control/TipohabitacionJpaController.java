/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Control.exceptions.IllegalOrphanException;
import Control.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Habitacion;
import Modelo.Tipohabitacion;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
 */
public class TipohabitacionJpaController implements Serializable {

    public TipohabitacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipohabitacion tipohabitacion) {
        if (tipohabitacion.getHabitacionCollection() == null) {
            tipohabitacion.setHabitacionCollection(new ArrayList<Habitacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Habitacion> attachedHabitacionCollection = new ArrayList<Habitacion>();
            for (Habitacion habitacionCollectionHabitacionToAttach : tipohabitacion.getHabitacionCollection()) {
                habitacionCollectionHabitacionToAttach = em.getReference(habitacionCollectionHabitacionToAttach.getClass(), habitacionCollectionHabitacionToAttach.getIdHabitacion());
                attachedHabitacionCollection.add(habitacionCollectionHabitacionToAttach);
            }
            tipohabitacion.setHabitacionCollection(attachedHabitacionCollection);
            em.persist(tipohabitacion);
            for (Habitacion habitacionCollectionHabitacion : tipohabitacion.getHabitacionCollection()) {
                Tipohabitacion oldIdTipoHabitacionOfHabitacionCollectionHabitacion = habitacionCollectionHabitacion.getIdTipoHabitacion();
                habitacionCollectionHabitacion.setIdTipoHabitacion(tipohabitacion);
                habitacionCollectionHabitacion = em.merge(habitacionCollectionHabitacion);
                if (oldIdTipoHabitacionOfHabitacionCollectionHabitacion != null) {
                    oldIdTipoHabitacionOfHabitacionCollectionHabitacion.getHabitacionCollection().remove(habitacionCollectionHabitacion);
                    oldIdTipoHabitacionOfHabitacionCollectionHabitacion = em.merge(oldIdTipoHabitacionOfHabitacionCollectionHabitacion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipohabitacion tipohabitacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipohabitacion persistentTipohabitacion = em.find(Tipohabitacion.class, tipohabitacion.getIdTipoHabitacion());
            Collection<Habitacion> habitacionCollectionOld = persistentTipohabitacion.getHabitacionCollection();
            Collection<Habitacion> habitacionCollectionNew = tipohabitacion.getHabitacionCollection();
            List<String> illegalOrphanMessages = null;
            for (Habitacion habitacionCollectionOldHabitacion : habitacionCollectionOld) {
                if (!habitacionCollectionNew.contains(habitacionCollectionOldHabitacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Habitacion " + habitacionCollectionOldHabitacion + " since its idTipoHabitacion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Habitacion> attachedHabitacionCollectionNew = new ArrayList<Habitacion>();
            for (Habitacion habitacionCollectionNewHabitacionToAttach : habitacionCollectionNew) {
                habitacionCollectionNewHabitacionToAttach = em.getReference(habitacionCollectionNewHabitacionToAttach.getClass(), habitacionCollectionNewHabitacionToAttach.getIdHabitacion());
                attachedHabitacionCollectionNew.add(habitacionCollectionNewHabitacionToAttach);
            }
            habitacionCollectionNew = attachedHabitacionCollectionNew;
            tipohabitacion.setHabitacionCollection(habitacionCollectionNew);
            tipohabitacion = em.merge(tipohabitacion);
            for (Habitacion habitacionCollectionNewHabitacion : habitacionCollectionNew) {
                if (!habitacionCollectionOld.contains(habitacionCollectionNewHabitacion)) {
                    Tipohabitacion oldIdTipoHabitacionOfHabitacionCollectionNewHabitacion = habitacionCollectionNewHabitacion.getIdTipoHabitacion();
                    habitacionCollectionNewHabitacion.setIdTipoHabitacion(tipohabitacion);
                    habitacionCollectionNewHabitacion = em.merge(habitacionCollectionNewHabitacion);
                    if (oldIdTipoHabitacionOfHabitacionCollectionNewHabitacion != null && !oldIdTipoHabitacionOfHabitacionCollectionNewHabitacion.equals(tipohabitacion)) {
                        oldIdTipoHabitacionOfHabitacionCollectionNewHabitacion.getHabitacionCollection().remove(habitacionCollectionNewHabitacion);
                        oldIdTipoHabitacionOfHabitacionCollectionNewHabitacion = em.merge(oldIdTipoHabitacionOfHabitacionCollectionNewHabitacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipohabitacion.getIdTipoHabitacion();
                if (findTipohabitacion(id) == null) {
                    throw new NonexistentEntityException("The tipohabitacion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipohabitacion tipohabitacion;
            try {
                tipohabitacion = em.getReference(Tipohabitacion.class, id);
                tipohabitacion.getIdTipoHabitacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipohabitacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Habitacion> habitacionCollectionOrphanCheck = tipohabitacion.getHabitacionCollection();
            for (Habitacion habitacionCollectionOrphanCheckHabitacion : habitacionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tipohabitacion (" + tipohabitacion + ") cannot be destroyed since the Habitacion " + habitacionCollectionOrphanCheckHabitacion + " in its habitacionCollection field has a non-nullable idTipoHabitacion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipohabitacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipohabitacion> findTipohabitacionEntities() {
        return findTipohabitacionEntities(true, -1, -1);
    }

    public List<Tipohabitacion> findTipohabitacionEntities(int maxResults, int firstResult) {
        return findTipohabitacionEntities(false, maxResults, firstResult);
    }

    private List<Tipohabitacion> findTipohabitacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipohabitacion.class));
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

    public Tipohabitacion findTipohabitacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipohabitacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipohabitacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipohabitacion> rt = cq.from(Tipohabitacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
