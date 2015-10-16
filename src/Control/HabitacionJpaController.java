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
import Modelo.Tipohabitacion;
import Modelo.Asignacion;
import Modelo.Habitacion;
import java.util.ArrayList;
import java.util.Collection;
import Modelo.Reservacionalquiler;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
 */
public class HabitacionJpaController implements Serializable {

    public HabitacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Habitacion habitacion) {
        if (habitacion.getAsignacionCollection() == null) {
            habitacion.setAsignacionCollection(new ArrayList<Asignacion>());
        }
        if (habitacion.getReservacionalquilerCollection() == null) {
            habitacion.setReservacionalquilerCollection(new ArrayList<Reservacionalquiler>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipohabitacion idTipoHabitacion = habitacion.getIdTipoHabitacion();
            if (idTipoHabitacion != null) {
                idTipoHabitacion = em.getReference(idTipoHabitacion.getClass(), idTipoHabitacion.getIdTipoHabitacion());
                habitacion.setIdTipoHabitacion(idTipoHabitacion);
            }
            Collection<Asignacion> attachedAsignacionCollection = new ArrayList<Asignacion>();
            for (Asignacion asignacionCollectionAsignacionToAttach : habitacion.getAsignacionCollection()) {
                asignacionCollectionAsignacionToAttach = em.getReference(asignacionCollectionAsignacionToAttach.getClass(), asignacionCollectionAsignacionToAttach.getIdAsignacion());
                attachedAsignacionCollection.add(asignacionCollectionAsignacionToAttach);
            }
            habitacion.setAsignacionCollection(attachedAsignacionCollection);
            Collection<Reservacionalquiler> attachedReservacionalquilerCollection = new ArrayList<Reservacionalquiler>();
            for (Reservacionalquiler reservacionalquilerCollectionReservacionalquilerToAttach : habitacion.getReservacionalquilerCollection()) {
                reservacionalquilerCollectionReservacionalquilerToAttach = em.getReference(reservacionalquilerCollectionReservacionalquilerToAttach.getClass(), reservacionalquilerCollectionReservacionalquilerToAttach.getIdReservacionAlquiler());
                attachedReservacionalquilerCollection.add(reservacionalquilerCollectionReservacionalquilerToAttach);
            }
            habitacion.setReservacionalquilerCollection(attachedReservacionalquilerCollection);
            em.persist(habitacion);
            if (idTipoHabitacion != null) {
                idTipoHabitacion.getHabitacionCollection().add(habitacion);
                idTipoHabitacion = em.merge(idTipoHabitacion);
            }
            for (Asignacion asignacionCollectionAsignacion : habitacion.getAsignacionCollection()) {
                Habitacion oldIdHabitacionOfAsignacionCollectionAsignacion = asignacionCollectionAsignacion.getIdHabitacion();
                asignacionCollectionAsignacion.setIdHabitacion(habitacion);
                asignacionCollectionAsignacion = em.merge(asignacionCollectionAsignacion);
                if (oldIdHabitacionOfAsignacionCollectionAsignacion != null) {
                    oldIdHabitacionOfAsignacionCollectionAsignacion.getAsignacionCollection().remove(asignacionCollectionAsignacion);
                    oldIdHabitacionOfAsignacionCollectionAsignacion = em.merge(oldIdHabitacionOfAsignacionCollectionAsignacion);
                }
            }
            for (Reservacionalquiler reservacionalquilerCollectionReservacionalquiler : habitacion.getReservacionalquilerCollection()) {
                Habitacion oldIdHabitacionOfReservacionalquilerCollectionReservacionalquiler = reservacionalquilerCollectionReservacionalquiler.getIdHabitacion();
                reservacionalquilerCollectionReservacionalquiler.setIdHabitacion(habitacion);
                reservacionalquilerCollectionReservacionalquiler = em.merge(reservacionalquilerCollectionReservacionalquiler);
                if (oldIdHabitacionOfReservacionalquilerCollectionReservacionalquiler != null) {
                    oldIdHabitacionOfReservacionalquilerCollectionReservacionalquiler.getReservacionalquilerCollection().remove(reservacionalquilerCollectionReservacionalquiler);
                    oldIdHabitacionOfReservacionalquilerCollectionReservacionalquiler = em.merge(oldIdHabitacionOfReservacionalquilerCollectionReservacionalquiler);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Habitacion habitacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Habitacion persistentHabitacion = em.find(Habitacion.class, habitacion.getIdHabitacion());
            Tipohabitacion idTipoHabitacionOld = persistentHabitacion.getIdTipoHabitacion();
            Tipohabitacion idTipoHabitacionNew = habitacion.getIdTipoHabitacion();
            Collection<Asignacion> asignacionCollectionOld = persistentHabitacion.getAsignacionCollection();
            Collection<Asignacion> asignacionCollectionNew = habitacion.getAsignacionCollection();
            Collection<Reservacionalquiler> reservacionalquilerCollectionOld = persistentHabitacion.getReservacionalquilerCollection();
            Collection<Reservacionalquiler> reservacionalquilerCollectionNew = habitacion.getReservacionalquilerCollection();
            List<String> illegalOrphanMessages = null;
            for (Asignacion asignacionCollectionOldAsignacion : asignacionCollectionOld) {
                if (!asignacionCollectionNew.contains(asignacionCollectionOldAsignacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Asignacion " + asignacionCollectionOldAsignacion + " since its idHabitacion field is not nullable.");
                }
            }
            for (Reservacionalquiler reservacionalquilerCollectionOldReservacionalquiler : reservacionalquilerCollectionOld) {
                if (!reservacionalquilerCollectionNew.contains(reservacionalquilerCollectionOldReservacionalquiler)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Reservacionalquiler " + reservacionalquilerCollectionOldReservacionalquiler + " since its idHabitacion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idTipoHabitacionNew != null) {
                idTipoHabitacionNew = em.getReference(idTipoHabitacionNew.getClass(), idTipoHabitacionNew.getIdTipoHabitacion());
                habitacion.setIdTipoHabitacion(idTipoHabitacionNew);
            }
            Collection<Asignacion> attachedAsignacionCollectionNew = new ArrayList<Asignacion>();
            for (Asignacion asignacionCollectionNewAsignacionToAttach : asignacionCollectionNew) {
                asignacionCollectionNewAsignacionToAttach = em.getReference(asignacionCollectionNewAsignacionToAttach.getClass(), asignacionCollectionNewAsignacionToAttach.getIdAsignacion());
                attachedAsignacionCollectionNew.add(asignacionCollectionNewAsignacionToAttach);
            }
            asignacionCollectionNew = attachedAsignacionCollectionNew;
            habitacion.setAsignacionCollection(asignacionCollectionNew);
            Collection<Reservacionalquiler> attachedReservacionalquilerCollectionNew = new ArrayList<Reservacionalquiler>();
            for (Reservacionalquiler reservacionalquilerCollectionNewReservacionalquilerToAttach : reservacionalquilerCollectionNew) {
                reservacionalquilerCollectionNewReservacionalquilerToAttach = em.getReference(reservacionalquilerCollectionNewReservacionalquilerToAttach.getClass(), reservacionalquilerCollectionNewReservacionalquilerToAttach.getIdReservacionAlquiler());
                attachedReservacionalquilerCollectionNew.add(reservacionalquilerCollectionNewReservacionalquilerToAttach);
            }
            reservacionalquilerCollectionNew = attachedReservacionalquilerCollectionNew;
            habitacion.setReservacionalquilerCollection(reservacionalquilerCollectionNew);
            habitacion = em.merge(habitacion);
            if (idTipoHabitacionOld != null && !idTipoHabitacionOld.equals(idTipoHabitacionNew)) {
                idTipoHabitacionOld.getHabitacionCollection().remove(habitacion);
                idTipoHabitacionOld = em.merge(idTipoHabitacionOld);
            }
            if (idTipoHabitacionNew != null && !idTipoHabitacionNew.equals(idTipoHabitacionOld)) {
                idTipoHabitacionNew.getHabitacionCollection().add(habitacion);
                idTipoHabitacionNew = em.merge(idTipoHabitacionNew);
            }
            for (Asignacion asignacionCollectionNewAsignacion : asignacionCollectionNew) {
                if (!asignacionCollectionOld.contains(asignacionCollectionNewAsignacion)) {
                    Habitacion oldIdHabitacionOfAsignacionCollectionNewAsignacion = asignacionCollectionNewAsignacion.getIdHabitacion();
                    asignacionCollectionNewAsignacion.setIdHabitacion(habitacion);
                    asignacionCollectionNewAsignacion = em.merge(asignacionCollectionNewAsignacion);
                    if (oldIdHabitacionOfAsignacionCollectionNewAsignacion != null && !oldIdHabitacionOfAsignacionCollectionNewAsignacion.equals(habitacion)) {
                        oldIdHabitacionOfAsignacionCollectionNewAsignacion.getAsignacionCollection().remove(asignacionCollectionNewAsignacion);
                        oldIdHabitacionOfAsignacionCollectionNewAsignacion = em.merge(oldIdHabitacionOfAsignacionCollectionNewAsignacion);
                    }
                }
            }
            for (Reservacionalquiler reservacionalquilerCollectionNewReservacionalquiler : reservacionalquilerCollectionNew) {
                if (!reservacionalquilerCollectionOld.contains(reservacionalquilerCollectionNewReservacionalquiler)) {
                    Habitacion oldIdHabitacionOfReservacionalquilerCollectionNewReservacionalquiler = reservacionalquilerCollectionNewReservacionalquiler.getIdHabitacion();
                    reservacionalquilerCollectionNewReservacionalquiler.setIdHabitacion(habitacion);
                    reservacionalquilerCollectionNewReservacionalquiler = em.merge(reservacionalquilerCollectionNewReservacionalquiler);
                    if (oldIdHabitacionOfReservacionalquilerCollectionNewReservacionalquiler != null && !oldIdHabitacionOfReservacionalquilerCollectionNewReservacionalquiler.equals(habitacion)) {
                        oldIdHabitacionOfReservacionalquilerCollectionNewReservacionalquiler.getReservacionalquilerCollection().remove(reservacionalquilerCollectionNewReservacionalquiler);
                        oldIdHabitacionOfReservacionalquilerCollectionNewReservacionalquiler = em.merge(oldIdHabitacionOfReservacionalquilerCollectionNewReservacionalquiler);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = habitacion.getIdHabitacion();
                if (findHabitacion(id) == null) {
                    throw new NonexistentEntityException("The habitacion with id " + id + " no longer exists.");
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
            Habitacion habitacion;
            try {
                habitacion = em.getReference(Habitacion.class, id);
                habitacion.getIdHabitacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The habitacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Asignacion> asignacionCollectionOrphanCheck = habitacion.getAsignacionCollection();
            for (Asignacion asignacionCollectionOrphanCheckAsignacion : asignacionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Habitacion (" + habitacion + ") cannot be destroyed since the Asignacion " + asignacionCollectionOrphanCheckAsignacion + " in its asignacionCollection field has a non-nullable idHabitacion field.");
            }
            Collection<Reservacionalquiler> reservacionalquilerCollectionOrphanCheck = habitacion.getReservacionalquilerCollection();
            for (Reservacionalquiler reservacionalquilerCollectionOrphanCheckReservacionalquiler : reservacionalquilerCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Habitacion (" + habitacion + ") cannot be destroyed since the Reservacionalquiler " + reservacionalquilerCollectionOrphanCheckReservacionalquiler + " in its reservacionalquilerCollection field has a non-nullable idHabitacion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Tipohabitacion idTipoHabitacion = habitacion.getIdTipoHabitacion();
            if (idTipoHabitacion != null) {
                idTipoHabitacion.getHabitacionCollection().remove(habitacion);
                idTipoHabitacion = em.merge(idTipoHabitacion);
            }
            em.remove(habitacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Habitacion> findHabitacionEntities() {
        return findHabitacionEntities(true, -1, -1);
    }

    public List<Habitacion> findHabitacionEntities(int maxResults, int firstResult) {
        return findHabitacionEntities(false, maxResults, firstResult);
    }

    private List<Habitacion> findHabitacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Habitacion.class));
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

    public Habitacion findHabitacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Habitacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getHabitacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Habitacion> rt = cq.from(Habitacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
