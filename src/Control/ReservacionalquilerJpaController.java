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
import Modelo.Empleado;
import Modelo.Cliente;
import Modelo.Documentoalquiler;
import Modelo.Reservacionalquiler;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
 */
public class ReservacionalquilerJpaController implements Serializable {

    public ReservacionalquilerJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Reservacionalquiler reservacionalquiler) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Habitacion idHabitacion = reservacionalquiler.getIdHabitacion();
            if (idHabitacion != null) {
                idHabitacion = em.getReference(idHabitacion.getClass(), idHabitacion.getIdHabitacion());
                reservacionalquiler.setIdHabitacion(idHabitacion);
            }
            Empleado idEmpleado = reservacionalquiler.getIdEmpleado();
            if (idEmpleado != null) {
                idEmpleado = em.getReference(idEmpleado.getClass(), idEmpleado.getIdPersona());
                reservacionalquiler.setIdEmpleado(idEmpleado);
            }
            Cliente idCliente = reservacionalquiler.getIdCliente();
            if (idCliente != null) {
                idCliente = em.getReference(idCliente.getClass(), idCliente.getIdPersona());
                reservacionalquiler.setIdCliente(idCliente);
            }
            Documentoalquiler documentoalquiler = reservacionalquiler.getDocumentoalquiler();
            if (documentoalquiler != null) {
                documentoalquiler = em.getReference(documentoalquiler.getClass(), documentoalquiler.getIdDocumentoAlquiler());
                reservacionalquiler.setDocumentoalquiler(documentoalquiler);
            }
            em.persist(reservacionalquiler);
            if (idHabitacion != null) {
                idHabitacion.getReservacionalquilerCollection().add(reservacionalquiler);
                idHabitacion = em.merge(idHabitacion);
            }
            if (idEmpleado != null) {
                idEmpleado.getReservacionalquilerCollection().add(reservacionalquiler);
                idEmpleado = em.merge(idEmpleado);
            }
            if (idCliente != null) {
                idCliente.getReservacionalquilerCollection().add(reservacionalquiler);
                idCliente = em.merge(idCliente);
            }
            if (documentoalquiler != null) {
                Reservacionalquiler oldReservacionalquilerOfDocumentoalquiler = documentoalquiler.getReservacionalquiler();
                if (oldReservacionalquilerOfDocumentoalquiler != null) {
                    oldReservacionalquilerOfDocumentoalquiler.setDocumentoalquiler(null);
                    oldReservacionalquilerOfDocumentoalquiler = em.merge(oldReservacionalquilerOfDocumentoalquiler);
                }
                documentoalquiler.setReservacionalquiler(reservacionalquiler);
                documentoalquiler = em.merge(documentoalquiler);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Reservacionalquiler reservacionalquiler) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reservacionalquiler persistentReservacionalquiler = em.find(Reservacionalquiler.class, reservacionalquiler.getIdReservacionAlquiler());
            Habitacion idHabitacionOld = persistentReservacionalquiler.getIdHabitacion();
            Habitacion idHabitacionNew = reservacionalquiler.getIdHabitacion();
            Empleado idEmpleadoOld = persistentReservacionalquiler.getIdEmpleado();
            Empleado idEmpleadoNew = reservacionalquiler.getIdEmpleado();
            Cliente idClienteOld = persistentReservacionalquiler.getIdCliente();
            Cliente idClienteNew = reservacionalquiler.getIdCliente();
            Documentoalquiler documentoalquilerOld = persistentReservacionalquiler.getDocumentoalquiler();
            Documentoalquiler documentoalquilerNew = reservacionalquiler.getDocumentoalquiler();
            List<String> illegalOrphanMessages = null;
            if (documentoalquilerOld != null && !documentoalquilerOld.equals(documentoalquilerNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Documentoalquiler " + documentoalquilerOld + " since its reservacionalquiler field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idHabitacionNew != null) {
                idHabitacionNew = em.getReference(idHabitacionNew.getClass(), idHabitacionNew.getIdHabitacion());
                reservacionalquiler.setIdHabitacion(idHabitacionNew);
            }
            if (idEmpleadoNew != null) {
                idEmpleadoNew = em.getReference(idEmpleadoNew.getClass(), idEmpleadoNew.getIdPersona());
                reservacionalquiler.setIdEmpleado(idEmpleadoNew);
            }
            if (idClienteNew != null) {
                idClienteNew = em.getReference(idClienteNew.getClass(), idClienteNew.getIdPersona());
                reservacionalquiler.setIdCliente(idClienteNew);
            }
            if (documentoalquilerNew != null) {
                documentoalquilerNew = em.getReference(documentoalquilerNew.getClass(), documentoalquilerNew.getIdDocumentoAlquiler());
                reservacionalquiler.setDocumentoalquiler(documentoalquilerNew);
            }
            reservacionalquiler = em.merge(reservacionalquiler);
            if (idHabitacionOld != null && !idHabitacionOld.equals(idHabitacionNew)) {
                idHabitacionOld.getReservacionalquilerCollection().remove(reservacionalquiler);
                idHabitacionOld = em.merge(idHabitacionOld);
            }
            if (idHabitacionNew != null && !idHabitacionNew.equals(idHabitacionOld)) {
                idHabitacionNew.getReservacionalquilerCollection().add(reservacionalquiler);
                idHabitacionNew = em.merge(idHabitacionNew);
            }
            if (idEmpleadoOld != null && !idEmpleadoOld.equals(idEmpleadoNew)) {
                idEmpleadoOld.getReservacionalquilerCollection().remove(reservacionalquiler);
                idEmpleadoOld = em.merge(idEmpleadoOld);
            }
            if (idEmpleadoNew != null && !idEmpleadoNew.equals(idEmpleadoOld)) {
                idEmpleadoNew.getReservacionalquilerCollection().add(reservacionalquiler);
                idEmpleadoNew = em.merge(idEmpleadoNew);
            }
            if (idClienteOld != null && !idClienteOld.equals(idClienteNew)) {
                idClienteOld.getReservacionalquilerCollection().remove(reservacionalquiler);
                idClienteOld = em.merge(idClienteOld);
            }
            if (idClienteNew != null && !idClienteNew.equals(idClienteOld)) {
                idClienteNew.getReservacionalquilerCollection().add(reservacionalquiler);
                idClienteNew = em.merge(idClienteNew);
            }
            if (documentoalquilerNew != null && !documentoalquilerNew.equals(documentoalquilerOld)) {
                Reservacionalquiler oldReservacionalquilerOfDocumentoalquiler = documentoalquilerNew.getReservacionalquiler();
                if (oldReservacionalquilerOfDocumentoalquiler != null) {
                    oldReservacionalquilerOfDocumentoalquiler.setDocumentoalquiler(null);
                    oldReservacionalquilerOfDocumentoalquiler = em.merge(oldReservacionalquilerOfDocumentoalquiler);
                }
                documentoalquilerNew.setReservacionalquiler(reservacionalquiler);
                documentoalquilerNew = em.merge(documentoalquilerNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = reservacionalquiler.getIdReservacionAlquiler();
                if (findReservacionalquiler(id) == null) {
                    throw new NonexistentEntityException("The reservacionalquiler with id " + id + " no longer exists.");
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
            Reservacionalquiler reservacionalquiler;
            try {
                reservacionalquiler = em.getReference(Reservacionalquiler.class, id);
                reservacionalquiler.getIdReservacionAlquiler();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The reservacionalquiler with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Documentoalquiler documentoalquilerOrphanCheck = reservacionalquiler.getDocumentoalquiler();
            if (documentoalquilerOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Reservacionalquiler (" + reservacionalquiler + ") cannot be destroyed since the Documentoalquiler " + documentoalquilerOrphanCheck + " in its documentoalquiler field has a non-nullable reservacionalquiler field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Habitacion idHabitacion = reservacionalquiler.getIdHabitacion();
            if (idHabitacion != null) {
                idHabitacion.getReservacionalquilerCollection().remove(reservacionalquiler);
                idHabitacion = em.merge(idHabitacion);
            }
            Empleado idEmpleado = reservacionalquiler.getIdEmpleado();
            if (idEmpleado != null) {
                idEmpleado.getReservacionalquilerCollection().remove(reservacionalquiler);
                idEmpleado = em.merge(idEmpleado);
            }
            Cliente idCliente = reservacionalquiler.getIdCliente();
            if (idCliente != null) {
                idCliente.getReservacionalquilerCollection().remove(reservacionalquiler);
                idCliente = em.merge(idCliente);
            }
            em.remove(reservacionalquiler);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Reservacionalquiler> findReservacionalquilerEntities() {
        return findReservacionalquilerEntities(true, -1, -1);
    }

    public List<Reservacionalquiler> findReservacionalquilerEntities(int maxResults, int firstResult) {
        return findReservacionalquilerEntities(false, maxResults, firstResult);
    }

    private List<Reservacionalquiler> findReservacionalquilerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Reservacionalquiler.class));
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

    public Reservacionalquiler findReservacionalquiler(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Reservacionalquiler.class, id);
        } finally {
            em.close();
        }
    }

    public int getReservacionalquilerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Reservacionalquiler> rt = cq.from(Reservacionalquiler.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
