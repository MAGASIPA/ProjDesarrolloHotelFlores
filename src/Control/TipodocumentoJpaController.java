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
import Modelo.Persona;
import Modelo.Tipodocumento;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
 */
public class TipodocumentoJpaController implements Serializable {

    public TipodocumentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipodocumento tipodocumento) {
        if (tipodocumento.getPersonaCollection() == null) {
            tipodocumento.setPersonaCollection(new ArrayList<Persona>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Persona> attachedPersonaCollection = new ArrayList<Persona>();
            for (Persona personaCollectionPersonaToAttach : tipodocumento.getPersonaCollection()) {
                personaCollectionPersonaToAttach = em.getReference(personaCollectionPersonaToAttach.getClass(), personaCollectionPersonaToAttach.getIdPersona());
                attachedPersonaCollection.add(personaCollectionPersonaToAttach);
            }
            tipodocumento.setPersonaCollection(attachedPersonaCollection);
            em.persist(tipodocumento);
            for (Persona personaCollectionPersona : tipodocumento.getPersonaCollection()) {
                Tipodocumento oldIdTipoDocOfPersonaCollectionPersona = personaCollectionPersona.getIdTipoDoc();
                personaCollectionPersona.setIdTipoDoc(tipodocumento);
                personaCollectionPersona = em.merge(personaCollectionPersona);
                if (oldIdTipoDocOfPersonaCollectionPersona != null) {
                    oldIdTipoDocOfPersonaCollectionPersona.getPersonaCollection().remove(personaCollectionPersona);
                    oldIdTipoDocOfPersonaCollectionPersona = em.merge(oldIdTipoDocOfPersonaCollectionPersona);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipodocumento tipodocumento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipodocumento persistentTipodocumento = em.find(Tipodocumento.class, tipodocumento.getIdTipoDoc());
            Collection<Persona> personaCollectionOld = persistentTipodocumento.getPersonaCollection();
            Collection<Persona> personaCollectionNew = tipodocumento.getPersonaCollection();
            List<String> illegalOrphanMessages = null;
            for (Persona personaCollectionOldPersona : personaCollectionOld) {
                if (!personaCollectionNew.contains(personaCollectionOldPersona)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Persona " + personaCollectionOldPersona + " since its idTipoDoc field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Persona> attachedPersonaCollectionNew = new ArrayList<Persona>();
            for (Persona personaCollectionNewPersonaToAttach : personaCollectionNew) {
                personaCollectionNewPersonaToAttach = em.getReference(personaCollectionNewPersonaToAttach.getClass(), personaCollectionNewPersonaToAttach.getIdPersona());
                attachedPersonaCollectionNew.add(personaCollectionNewPersonaToAttach);
            }
            personaCollectionNew = attachedPersonaCollectionNew;
            tipodocumento.setPersonaCollection(personaCollectionNew);
            tipodocumento = em.merge(tipodocumento);
            for (Persona personaCollectionNewPersona : personaCollectionNew) {
                if (!personaCollectionOld.contains(personaCollectionNewPersona)) {
                    Tipodocumento oldIdTipoDocOfPersonaCollectionNewPersona = personaCollectionNewPersona.getIdTipoDoc();
                    personaCollectionNewPersona.setIdTipoDoc(tipodocumento);
                    personaCollectionNewPersona = em.merge(personaCollectionNewPersona);
                    if (oldIdTipoDocOfPersonaCollectionNewPersona != null && !oldIdTipoDocOfPersonaCollectionNewPersona.equals(tipodocumento)) {
                        oldIdTipoDocOfPersonaCollectionNewPersona.getPersonaCollection().remove(personaCollectionNewPersona);
                        oldIdTipoDocOfPersonaCollectionNewPersona = em.merge(oldIdTipoDocOfPersonaCollectionNewPersona);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipodocumento.getIdTipoDoc();
                if (findTipodocumento(id) == null) {
                    throw new NonexistentEntityException("The tipodocumento with id " + id + " no longer exists.");
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
            Tipodocumento tipodocumento;
            try {
                tipodocumento = em.getReference(Tipodocumento.class, id);
                tipodocumento.getIdTipoDoc();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipodocumento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Persona> personaCollectionOrphanCheck = tipodocumento.getPersonaCollection();
            for (Persona personaCollectionOrphanCheckPersona : personaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tipodocumento (" + tipodocumento + ") cannot be destroyed since the Persona " + personaCollectionOrphanCheckPersona + " in its personaCollection field has a non-nullable idTipoDoc field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipodocumento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipodocumento> findTipodocumentoEntities() {
        return findTipodocumentoEntities(true, -1, -1);
    }

    public List<Tipodocumento> findTipodocumentoEntities(int maxResults, int firstResult) {
        return findTipodocumentoEntities(false, maxResults, firstResult);
    }

    private List<Tipodocumento> findTipodocumentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipodocumento.class));
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

    public Tipodocumento findTipodocumento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipodocumento.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipodocumentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipodocumento> rt = cq.from(Tipodocumento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
