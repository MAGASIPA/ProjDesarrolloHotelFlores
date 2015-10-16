/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Control.exceptions.IllegalOrphanException;
import Control.exceptions.NonexistentEntityException;
import Control.exceptions.PreexistingEntityException;
import Modelo.Cliente;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Persona;
import Modelo.Hojaconsumo;
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
public class ClienteJpaController implements Serializable {

    public ClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (cliente.getHojaconsumoCollection() == null) {
            cliente.setHojaconsumoCollection(new ArrayList<Hojaconsumo>());
        }
        if (cliente.getReservacionalquilerCollection() == null) {
            cliente.setReservacionalquilerCollection(new ArrayList<Reservacionalquiler>());
        }
        List<String> illegalOrphanMessages = null;
        Persona personaOrphanCheck = cliente.getPersona();
        if (personaOrphanCheck != null) {
            Cliente oldClienteOfPersona = personaOrphanCheck.getCliente();
            if (oldClienteOfPersona != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Persona " + personaOrphanCheck + " already has an item of type Cliente whose persona column cannot be null. Please make another selection for the persona field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persona = cliente.getPersona();
            if (persona != null) {
                persona = em.getReference(persona.getClass(), persona.getIdPersona());
                cliente.setPersona(persona);
            }
            Collection<Hojaconsumo> attachedHojaconsumoCollection = new ArrayList<Hojaconsumo>();
            for (Hojaconsumo hojaconsumoCollectionHojaconsumoToAttach : cliente.getHojaconsumoCollection()) {
                hojaconsumoCollectionHojaconsumoToAttach = em.getReference(hojaconsumoCollectionHojaconsumoToAttach.getClass(), hojaconsumoCollectionHojaconsumoToAttach.getIdHojaConsumo());
                attachedHojaconsumoCollection.add(hojaconsumoCollectionHojaconsumoToAttach);
            }
            cliente.setHojaconsumoCollection(attachedHojaconsumoCollection);
            Collection<Reservacionalquiler> attachedReservacionalquilerCollection = new ArrayList<Reservacionalquiler>();
            for (Reservacionalquiler reservacionalquilerCollectionReservacionalquilerToAttach : cliente.getReservacionalquilerCollection()) {
                reservacionalquilerCollectionReservacionalquilerToAttach = em.getReference(reservacionalquilerCollectionReservacionalquilerToAttach.getClass(), reservacionalquilerCollectionReservacionalquilerToAttach.getIdReservacionAlquiler());
                attachedReservacionalquilerCollection.add(reservacionalquilerCollectionReservacionalquilerToAttach);
            }
            cliente.setReservacionalquilerCollection(attachedReservacionalquilerCollection);
            em.persist(cliente);
            if (persona != null) {
                persona.setCliente(cliente);
                persona = em.merge(persona);
            }
            for (Hojaconsumo hojaconsumoCollectionHojaconsumo : cliente.getHojaconsumoCollection()) {
                Cliente oldIdClienteOfHojaconsumoCollectionHojaconsumo = hojaconsumoCollectionHojaconsumo.getIdCliente();
                hojaconsumoCollectionHojaconsumo.setIdCliente(cliente);
                hojaconsumoCollectionHojaconsumo = em.merge(hojaconsumoCollectionHojaconsumo);
                if (oldIdClienteOfHojaconsumoCollectionHojaconsumo != null) {
                    oldIdClienteOfHojaconsumoCollectionHojaconsumo.getHojaconsumoCollection().remove(hojaconsumoCollectionHojaconsumo);
                    oldIdClienteOfHojaconsumoCollectionHojaconsumo = em.merge(oldIdClienteOfHojaconsumoCollectionHojaconsumo);
                }
            }
            for (Reservacionalquiler reservacionalquilerCollectionReservacionalquiler : cliente.getReservacionalquilerCollection()) {
                Cliente oldIdClienteOfReservacionalquilerCollectionReservacionalquiler = reservacionalquilerCollectionReservacionalquiler.getIdCliente();
                reservacionalquilerCollectionReservacionalquiler.setIdCliente(cliente);
                reservacionalquilerCollectionReservacionalquiler = em.merge(reservacionalquilerCollectionReservacionalquiler);
                if (oldIdClienteOfReservacionalquilerCollectionReservacionalquiler != null) {
                    oldIdClienteOfReservacionalquilerCollectionReservacionalquiler.getReservacionalquilerCollection().remove(reservacionalquilerCollectionReservacionalquiler);
                    oldIdClienteOfReservacionalquilerCollectionReservacionalquiler = em.merge(oldIdClienteOfReservacionalquilerCollectionReservacionalquiler);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCliente(cliente.getIdPersona()) != null) {
                throw new PreexistingEntityException("Cliente " + cliente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getIdPersona());
            Persona personaOld = persistentCliente.getPersona();
            Persona personaNew = cliente.getPersona();
            Collection<Hojaconsumo> hojaconsumoCollectionOld = persistentCliente.getHojaconsumoCollection();
            Collection<Hojaconsumo> hojaconsumoCollectionNew = cliente.getHojaconsumoCollection();
            Collection<Reservacionalquiler> reservacionalquilerCollectionOld = persistentCliente.getReservacionalquilerCollection();
            Collection<Reservacionalquiler> reservacionalquilerCollectionNew = cliente.getReservacionalquilerCollection();
            List<String> illegalOrphanMessages = null;
            if (personaNew != null && !personaNew.equals(personaOld)) {
                Cliente oldClienteOfPersona = personaNew.getCliente();
                if (oldClienteOfPersona != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Persona " + personaNew + " already has an item of type Cliente whose persona column cannot be null. Please make another selection for the persona field.");
                }
            }
            for (Hojaconsumo hojaconsumoCollectionOldHojaconsumo : hojaconsumoCollectionOld) {
                if (!hojaconsumoCollectionNew.contains(hojaconsumoCollectionOldHojaconsumo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Hojaconsumo " + hojaconsumoCollectionOldHojaconsumo + " since its idCliente field is not nullable.");
                }
            }
            for (Reservacionalquiler reservacionalquilerCollectionOldReservacionalquiler : reservacionalquilerCollectionOld) {
                if (!reservacionalquilerCollectionNew.contains(reservacionalquilerCollectionOldReservacionalquiler)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Reservacionalquiler " + reservacionalquilerCollectionOldReservacionalquiler + " since its idCliente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (personaNew != null) {
                personaNew = em.getReference(personaNew.getClass(), personaNew.getIdPersona());
                cliente.setPersona(personaNew);
            }
            Collection<Hojaconsumo> attachedHojaconsumoCollectionNew = new ArrayList<Hojaconsumo>();
            for (Hojaconsumo hojaconsumoCollectionNewHojaconsumoToAttach : hojaconsumoCollectionNew) {
                hojaconsumoCollectionNewHojaconsumoToAttach = em.getReference(hojaconsumoCollectionNewHojaconsumoToAttach.getClass(), hojaconsumoCollectionNewHojaconsumoToAttach.getIdHojaConsumo());
                attachedHojaconsumoCollectionNew.add(hojaconsumoCollectionNewHojaconsumoToAttach);
            }
            hojaconsumoCollectionNew = attachedHojaconsumoCollectionNew;
            cliente.setHojaconsumoCollection(hojaconsumoCollectionNew);
            Collection<Reservacionalquiler> attachedReservacionalquilerCollectionNew = new ArrayList<Reservacionalquiler>();
            for (Reservacionalquiler reservacionalquilerCollectionNewReservacionalquilerToAttach : reservacionalquilerCollectionNew) {
                reservacionalquilerCollectionNewReservacionalquilerToAttach = em.getReference(reservacionalquilerCollectionNewReservacionalquilerToAttach.getClass(), reservacionalquilerCollectionNewReservacionalquilerToAttach.getIdReservacionAlquiler());
                attachedReservacionalquilerCollectionNew.add(reservacionalquilerCollectionNewReservacionalquilerToAttach);
            }
            reservacionalquilerCollectionNew = attachedReservacionalquilerCollectionNew;
            cliente.setReservacionalquilerCollection(reservacionalquilerCollectionNew);
            cliente = em.merge(cliente);
            if (personaOld != null && !personaOld.equals(personaNew)) {
                personaOld.setCliente(null);
                personaOld = em.merge(personaOld);
            }
            if (personaNew != null && !personaNew.equals(personaOld)) {
                personaNew.setCliente(cliente);
                personaNew = em.merge(personaNew);
            }
            for (Hojaconsumo hojaconsumoCollectionNewHojaconsumo : hojaconsumoCollectionNew) {
                if (!hojaconsumoCollectionOld.contains(hojaconsumoCollectionNewHojaconsumo)) {
                    Cliente oldIdClienteOfHojaconsumoCollectionNewHojaconsumo = hojaconsumoCollectionNewHojaconsumo.getIdCliente();
                    hojaconsumoCollectionNewHojaconsumo.setIdCliente(cliente);
                    hojaconsumoCollectionNewHojaconsumo = em.merge(hojaconsumoCollectionNewHojaconsumo);
                    if (oldIdClienteOfHojaconsumoCollectionNewHojaconsumo != null && !oldIdClienteOfHojaconsumoCollectionNewHojaconsumo.equals(cliente)) {
                        oldIdClienteOfHojaconsumoCollectionNewHojaconsumo.getHojaconsumoCollection().remove(hojaconsumoCollectionNewHojaconsumo);
                        oldIdClienteOfHojaconsumoCollectionNewHojaconsumo = em.merge(oldIdClienteOfHojaconsumoCollectionNewHojaconsumo);
                    }
                }
            }
            for (Reservacionalquiler reservacionalquilerCollectionNewReservacionalquiler : reservacionalquilerCollectionNew) {
                if (!reservacionalquilerCollectionOld.contains(reservacionalquilerCollectionNewReservacionalquiler)) {
                    Cliente oldIdClienteOfReservacionalquilerCollectionNewReservacionalquiler = reservacionalquilerCollectionNewReservacionalquiler.getIdCliente();
                    reservacionalquilerCollectionNewReservacionalquiler.setIdCliente(cliente);
                    reservacionalquilerCollectionNewReservacionalquiler = em.merge(reservacionalquilerCollectionNewReservacionalquiler);
                    if (oldIdClienteOfReservacionalquilerCollectionNewReservacionalquiler != null && !oldIdClienteOfReservacionalquilerCollectionNewReservacionalquiler.equals(cliente)) {
                        oldIdClienteOfReservacionalquilerCollectionNewReservacionalquiler.getReservacionalquilerCollection().remove(reservacionalquilerCollectionNewReservacionalquiler);
                        oldIdClienteOfReservacionalquilerCollectionNewReservacionalquiler = em.merge(oldIdClienteOfReservacionalquilerCollectionNewReservacionalquiler);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliente.getIdPersona();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
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
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getIdPersona();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Hojaconsumo> hojaconsumoCollectionOrphanCheck = cliente.getHojaconsumoCollection();
            for (Hojaconsumo hojaconsumoCollectionOrphanCheckHojaconsumo : hojaconsumoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the Hojaconsumo " + hojaconsumoCollectionOrphanCheckHojaconsumo + " in its hojaconsumoCollection field has a non-nullable idCliente field.");
            }
            Collection<Reservacionalquiler> reservacionalquilerCollectionOrphanCheck = cliente.getReservacionalquilerCollection();
            for (Reservacionalquiler reservacionalquilerCollectionOrphanCheckReservacionalquiler : reservacionalquilerCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the Reservacionalquiler " + reservacionalquilerCollectionOrphanCheckReservacionalquiler + " in its reservacionalquilerCollection field has a non-nullable idCliente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Persona persona = cliente.getPersona();
            if (persona != null) {
                persona.setCliente(null);
                persona = em.merge(persona);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
