/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Control.exceptions.IllegalOrphanException;
import Control.exceptions.NonexistentEntityException;
import Control.exceptions.PreexistingEntityException;
import Modelo.Empleado;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Tipoempleado;
import Modelo.Persona;
import Modelo.Usuario;
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
public class EmpleadoJpaController implements Serializable {

    public EmpleadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empleado empleado) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (empleado.getUsuarioCollection() == null) {
            empleado.setUsuarioCollection(new ArrayList<Usuario>());
        }
        if (empleado.getReservacionalquilerCollection() == null) {
            empleado.setReservacionalquilerCollection(new ArrayList<Reservacionalquiler>());
        }
        List<String> illegalOrphanMessages = null;
        Persona personaOrphanCheck = empleado.getPersona();
        if (personaOrphanCheck != null) {
            Empleado oldEmpleadoOfPersona = personaOrphanCheck.getEmpleado();
            if (oldEmpleadoOfPersona != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Persona " + personaOrphanCheck + " already has an item of type Empleado whose persona column cannot be null. Please make another selection for the persona field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipoempleado idTipoEmpleado = empleado.getIdTipoEmpleado();
            if (idTipoEmpleado != null) {
                idTipoEmpleado = em.getReference(idTipoEmpleado.getClass(), idTipoEmpleado.getIdTipoEmpleado());
                empleado.setIdTipoEmpleado(idTipoEmpleado);
            }
            Persona persona = empleado.getPersona();
            if (persona != null) {
                persona = em.getReference(persona.getClass(), persona.getIdPersona());
                empleado.setPersona(persona);
            }
            Collection<Usuario> attachedUsuarioCollection = new ArrayList<Usuario>();
            for (Usuario usuarioCollectionUsuarioToAttach : empleado.getUsuarioCollection()) {
                usuarioCollectionUsuarioToAttach = em.getReference(usuarioCollectionUsuarioToAttach.getClass(), usuarioCollectionUsuarioToAttach.getIdUsuario());
                attachedUsuarioCollection.add(usuarioCollectionUsuarioToAttach);
            }
            empleado.setUsuarioCollection(attachedUsuarioCollection);
            Collection<Reservacionalquiler> attachedReservacionalquilerCollection = new ArrayList<Reservacionalquiler>();
            for (Reservacionalquiler reservacionalquilerCollectionReservacionalquilerToAttach : empleado.getReservacionalquilerCollection()) {
                reservacionalquilerCollectionReservacionalquilerToAttach = em.getReference(reservacionalquilerCollectionReservacionalquilerToAttach.getClass(), reservacionalquilerCollectionReservacionalquilerToAttach.getIdReservacionAlquiler());
                attachedReservacionalquilerCollection.add(reservacionalquilerCollectionReservacionalquilerToAttach);
            }
            empleado.setReservacionalquilerCollection(attachedReservacionalquilerCollection);
            em.persist(empleado);
            if (idTipoEmpleado != null) {
                idTipoEmpleado.getEmpleadoCollection().add(empleado);
                idTipoEmpleado = em.merge(idTipoEmpleado);
            }
            if (persona != null) {
                persona.setEmpleado(empleado);
                persona = em.merge(persona);
            }
            for (Usuario usuarioCollectionUsuario : empleado.getUsuarioCollection()) {
                Empleado oldIdEmpleadoOfUsuarioCollectionUsuario = usuarioCollectionUsuario.getIdEmpleado();
                usuarioCollectionUsuario.setIdEmpleado(empleado);
                usuarioCollectionUsuario = em.merge(usuarioCollectionUsuario);
                if (oldIdEmpleadoOfUsuarioCollectionUsuario != null) {
                    oldIdEmpleadoOfUsuarioCollectionUsuario.getUsuarioCollection().remove(usuarioCollectionUsuario);
                    oldIdEmpleadoOfUsuarioCollectionUsuario = em.merge(oldIdEmpleadoOfUsuarioCollectionUsuario);
                }
            }
            for (Reservacionalquiler reservacionalquilerCollectionReservacionalquiler : empleado.getReservacionalquilerCollection()) {
                Empleado oldIdEmpleadoOfReservacionalquilerCollectionReservacionalquiler = reservacionalquilerCollectionReservacionalquiler.getIdEmpleado();
                reservacionalquilerCollectionReservacionalquiler.setIdEmpleado(empleado);
                reservacionalquilerCollectionReservacionalquiler = em.merge(reservacionalquilerCollectionReservacionalquiler);
                if (oldIdEmpleadoOfReservacionalquilerCollectionReservacionalquiler != null) {
                    oldIdEmpleadoOfReservacionalquilerCollectionReservacionalquiler.getReservacionalquilerCollection().remove(reservacionalquilerCollectionReservacionalquiler);
                    oldIdEmpleadoOfReservacionalquilerCollectionReservacionalquiler = em.merge(oldIdEmpleadoOfReservacionalquilerCollectionReservacionalquiler);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEmpleado(empleado.getIdPersona()) != null) {
                throw new PreexistingEntityException("Empleado " + empleado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empleado empleado) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado persistentEmpleado = em.find(Empleado.class, empleado.getIdPersona());
            Tipoempleado idTipoEmpleadoOld = persistentEmpleado.getIdTipoEmpleado();
            Tipoempleado idTipoEmpleadoNew = empleado.getIdTipoEmpleado();
            Persona personaOld = persistentEmpleado.getPersona();
            Persona personaNew = empleado.getPersona();
            Collection<Usuario> usuarioCollectionOld = persistentEmpleado.getUsuarioCollection();
            Collection<Usuario> usuarioCollectionNew = empleado.getUsuarioCollection();
            Collection<Reservacionalquiler> reservacionalquilerCollectionOld = persistentEmpleado.getReservacionalquilerCollection();
            Collection<Reservacionalquiler> reservacionalquilerCollectionNew = empleado.getReservacionalquilerCollection();
            List<String> illegalOrphanMessages = null;
            if (personaNew != null && !personaNew.equals(personaOld)) {
                Empleado oldEmpleadoOfPersona = personaNew.getEmpleado();
                if (oldEmpleadoOfPersona != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Persona " + personaNew + " already has an item of type Empleado whose persona column cannot be null. Please make another selection for the persona field.");
                }
            }
            for (Usuario usuarioCollectionOldUsuario : usuarioCollectionOld) {
                if (!usuarioCollectionNew.contains(usuarioCollectionOldUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuario " + usuarioCollectionOldUsuario + " since its idEmpleado field is not nullable.");
                }
            }
            for (Reservacionalquiler reservacionalquilerCollectionOldReservacionalquiler : reservacionalquilerCollectionOld) {
                if (!reservacionalquilerCollectionNew.contains(reservacionalquilerCollectionOldReservacionalquiler)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Reservacionalquiler " + reservacionalquilerCollectionOldReservacionalquiler + " since its idEmpleado field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idTipoEmpleadoNew != null) {
                idTipoEmpleadoNew = em.getReference(idTipoEmpleadoNew.getClass(), idTipoEmpleadoNew.getIdTipoEmpleado());
                empleado.setIdTipoEmpleado(idTipoEmpleadoNew);
            }
            if (personaNew != null) {
                personaNew = em.getReference(personaNew.getClass(), personaNew.getIdPersona());
                empleado.setPersona(personaNew);
            }
            Collection<Usuario> attachedUsuarioCollectionNew = new ArrayList<Usuario>();
            for (Usuario usuarioCollectionNewUsuarioToAttach : usuarioCollectionNew) {
                usuarioCollectionNewUsuarioToAttach = em.getReference(usuarioCollectionNewUsuarioToAttach.getClass(), usuarioCollectionNewUsuarioToAttach.getIdUsuario());
                attachedUsuarioCollectionNew.add(usuarioCollectionNewUsuarioToAttach);
            }
            usuarioCollectionNew = attachedUsuarioCollectionNew;
            empleado.setUsuarioCollection(usuarioCollectionNew);
            Collection<Reservacionalquiler> attachedReservacionalquilerCollectionNew = new ArrayList<Reservacionalquiler>();
            for (Reservacionalquiler reservacionalquilerCollectionNewReservacionalquilerToAttach : reservacionalquilerCollectionNew) {
                reservacionalquilerCollectionNewReservacionalquilerToAttach = em.getReference(reservacionalquilerCollectionNewReservacionalquilerToAttach.getClass(), reservacionalquilerCollectionNewReservacionalquilerToAttach.getIdReservacionAlquiler());
                attachedReservacionalquilerCollectionNew.add(reservacionalquilerCollectionNewReservacionalquilerToAttach);
            }
            reservacionalquilerCollectionNew = attachedReservacionalquilerCollectionNew;
            empleado.setReservacionalquilerCollection(reservacionalquilerCollectionNew);
            empleado = em.merge(empleado);
            if (idTipoEmpleadoOld != null && !idTipoEmpleadoOld.equals(idTipoEmpleadoNew)) {
                idTipoEmpleadoOld.getEmpleadoCollection().remove(empleado);
                idTipoEmpleadoOld = em.merge(idTipoEmpleadoOld);
            }
            if (idTipoEmpleadoNew != null && !idTipoEmpleadoNew.equals(idTipoEmpleadoOld)) {
                idTipoEmpleadoNew.getEmpleadoCollection().add(empleado);
                idTipoEmpleadoNew = em.merge(idTipoEmpleadoNew);
            }
            if (personaOld != null && !personaOld.equals(personaNew)) {
                personaOld.setEmpleado(null);
                personaOld = em.merge(personaOld);
            }
            if (personaNew != null && !personaNew.equals(personaOld)) {
                personaNew.setEmpleado(empleado);
                personaNew = em.merge(personaNew);
            }
            for (Usuario usuarioCollectionNewUsuario : usuarioCollectionNew) {
                if (!usuarioCollectionOld.contains(usuarioCollectionNewUsuario)) {
                    Empleado oldIdEmpleadoOfUsuarioCollectionNewUsuario = usuarioCollectionNewUsuario.getIdEmpleado();
                    usuarioCollectionNewUsuario.setIdEmpleado(empleado);
                    usuarioCollectionNewUsuario = em.merge(usuarioCollectionNewUsuario);
                    if (oldIdEmpleadoOfUsuarioCollectionNewUsuario != null && !oldIdEmpleadoOfUsuarioCollectionNewUsuario.equals(empleado)) {
                        oldIdEmpleadoOfUsuarioCollectionNewUsuario.getUsuarioCollection().remove(usuarioCollectionNewUsuario);
                        oldIdEmpleadoOfUsuarioCollectionNewUsuario = em.merge(oldIdEmpleadoOfUsuarioCollectionNewUsuario);
                    }
                }
            }
            for (Reservacionalquiler reservacionalquilerCollectionNewReservacionalquiler : reservacionalquilerCollectionNew) {
                if (!reservacionalquilerCollectionOld.contains(reservacionalquilerCollectionNewReservacionalquiler)) {
                    Empleado oldIdEmpleadoOfReservacionalquilerCollectionNewReservacionalquiler = reservacionalquilerCollectionNewReservacionalquiler.getIdEmpleado();
                    reservacionalquilerCollectionNewReservacionalquiler.setIdEmpleado(empleado);
                    reservacionalquilerCollectionNewReservacionalquiler = em.merge(reservacionalquilerCollectionNewReservacionalquiler);
                    if (oldIdEmpleadoOfReservacionalquilerCollectionNewReservacionalquiler != null && !oldIdEmpleadoOfReservacionalquilerCollectionNewReservacionalquiler.equals(empleado)) {
                        oldIdEmpleadoOfReservacionalquilerCollectionNewReservacionalquiler.getReservacionalquilerCollection().remove(reservacionalquilerCollectionNewReservacionalquiler);
                        oldIdEmpleadoOfReservacionalquilerCollectionNewReservacionalquiler = em.merge(oldIdEmpleadoOfReservacionalquilerCollectionNewReservacionalquiler);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = empleado.getIdPersona();
                if (findEmpleado(id) == null) {
                    throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.");
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
            Empleado empleado;
            try {
                empleado = em.getReference(Empleado.class, id);
                empleado.getIdPersona();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Usuario> usuarioCollectionOrphanCheck = empleado.getUsuarioCollection();
            for (Usuario usuarioCollectionOrphanCheckUsuario : usuarioCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empleado (" + empleado + ") cannot be destroyed since the Usuario " + usuarioCollectionOrphanCheckUsuario + " in its usuarioCollection field has a non-nullable idEmpleado field.");
            }
            Collection<Reservacionalquiler> reservacionalquilerCollectionOrphanCheck = empleado.getReservacionalquilerCollection();
            for (Reservacionalquiler reservacionalquilerCollectionOrphanCheckReservacionalquiler : reservacionalquilerCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empleado (" + empleado + ") cannot be destroyed since the Reservacionalquiler " + reservacionalquilerCollectionOrphanCheckReservacionalquiler + " in its reservacionalquilerCollection field has a non-nullable idEmpleado field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Tipoempleado idTipoEmpleado = empleado.getIdTipoEmpleado();
            if (idTipoEmpleado != null) {
                idTipoEmpleado.getEmpleadoCollection().remove(empleado);
                idTipoEmpleado = em.merge(idTipoEmpleado);
            }
            Persona persona = empleado.getPersona();
            if (persona != null) {
                persona.setEmpleado(null);
                persona = em.merge(persona);
            }
            em.remove(empleado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empleado> findEmpleadoEntities() {
        return findEmpleadoEntities(true, -1, -1);
    }

    public List<Empleado> findEmpleadoEntities(int maxResults, int firstResult) {
        return findEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<Empleado> findEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empleado.class));
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

    public Empleado findEmpleado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empleado> rt = cq.from(Empleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
