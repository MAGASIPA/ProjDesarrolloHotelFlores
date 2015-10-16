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
import Modelo.Detallereclamo;
import Modelo.Ordenreclamo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
 */
public class OrdenreclamoJpaController implements Serializable {

    public OrdenreclamoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ordenreclamo ordenreclamo) {
        if (ordenreclamo.getDetallereclamoCollection() == null) {
            ordenreclamo.setDetallereclamoCollection(new ArrayList<Detallereclamo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Detallereclamo> attachedDetallereclamoCollection = new ArrayList<Detallereclamo>();
            for (Detallereclamo detallereclamoCollectionDetallereclamoToAttach : ordenreclamo.getDetallereclamoCollection()) {
                detallereclamoCollectionDetallereclamoToAttach = em.getReference(detallereclamoCollectionDetallereclamoToAttach.getClass(), detallereclamoCollectionDetallereclamoToAttach.getIdDetalleReclamo());
                attachedDetallereclamoCollection.add(detallereclamoCollectionDetallereclamoToAttach);
            }
            ordenreclamo.setDetallereclamoCollection(attachedDetallereclamoCollection);
            em.persist(ordenreclamo);
            for (Detallereclamo detallereclamoCollectionDetallereclamo : ordenreclamo.getDetallereclamoCollection()) {
                Ordenreclamo oldIdOrdenReclamoOfDetallereclamoCollectionDetallereclamo = detallereclamoCollectionDetallereclamo.getIdOrdenReclamo();
                detallereclamoCollectionDetallereclamo.setIdOrdenReclamo(ordenreclamo);
                detallereclamoCollectionDetallereclamo = em.merge(detallereclamoCollectionDetallereclamo);
                if (oldIdOrdenReclamoOfDetallereclamoCollectionDetallereclamo != null) {
                    oldIdOrdenReclamoOfDetallereclamoCollectionDetallereclamo.getDetallereclamoCollection().remove(detallereclamoCollectionDetallereclamo);
                    oldIdOrdenReclamoOfDetallereclamoCollectionDetallereclamo = em.merge(oldIdOrdenReclamoOfDetallereclamoCollectionDetallereclamo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ordenreclamo ordenreclamo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ordenreclamo persistentOrdenreclamo = em.find(Ordenreclamo.class, ordenreclamo.getIdOrdenReclamo());
            Collection<Detallereclamo> detallereclamoCollectionOld = persistentOrdenreclamo.getDetallereclamoCollection();
            Collection<Detallereclamo> detallereclamoCollectionNew = ordenreclamo.getDetallereclamoCollection();
            List<String> illegalOrphanMessages = null;
            for (Detallereclamo detallereclamoCollectionOldDetallereclamo : detallereclamoCollectionOld) {
                if (!detallereclamoCollectionNew.contains(detallereclamoCollectionOldDetallereclamo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Detallereclamo " + detallereclamoCollectionOldDetallereclamo + " since its idOrdenReclamo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Detallereclamo> attachedDetallereclamoCollectionNew = new ArrayList<Detallereclamo>();
            for (Detallereclamo detallereclamoCollectionNewDetallereclamoToAttach : detallereclamoCollectionNew) {
                detallereclamoCollectionNewDetallereclamoToAttach = em.getReference(detallereclamoCollectionNewDetallereclamoToAttach.getClass(), detallereclamoCollectionNewDetallereclamoToAttach.getIdDetalleReclamo());
                attachedDetallereclamoCollectionNew.add(detallereclamoCollectionNewDetallereclamoToAttach);
            }
            detallereclamoCollectionNew = attachedDetallereclamoCollectionNew;
            ordenreclamo.setDetallereclamoCollection(detallereclamoCollectionNew);
            ordenreclamo = em.merge(ordenreclamo);
            for (Detallereclamo detallereclamoCollectionNewDetallereclamo : detallereclamoCollectionNew) {
                if (!detallereclamoCollectionOld.contains(detallereclamoCollectionNewDetallereclamo)) {
                    Ordenreclamo oldIdOrdenReclamoOfDetallereclamoCollectionNewDetallereclamo = detallereclamoCollectionNewDetallereclamo.getIdOrdenReclamo();
                    detallereclamoCollectionNewDetallereclamo.setIdOrdenReclamo(ordenreclamo);
                    detallereclamoCollectionNewDetallereclamo = em.merge(detallereclamoCollectionNewDetallereclamo);
                    if (oldIdOrdenReclamoOfDetallereclamoCollectionNewDetallereclamo != null && !oldIdOrdenReclamoOfDetallereclamoCollectionNewDetallereclamo.equals(ordenreclamo)) {
                        oldIdOrdenReclamoOfDetallereclamoCollectionNewDetallereclamo.getDetallereclamoCollection().remove(detallereclamoCollectionNewDetallereclamo);
                        oldIdOrdenReclamoOfDetallereclamoCollectionNewDetallereclamo = em.merge(oldIdOrdenReclamoOfDetallereclamoCollectionNewDetallereclamo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ordenreclamo.getIdOrdenReclamo();
                if (findOrdenreclamo(id) == null) {
                    throw new NonexistentEntityException("The ordenreclamo with id " + id + " no longer exists.");
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
            Ordenreclamo ordenreclamo;
            try {
                ordenreclamo = em.getReference(Ordenreclamo.class, id);
                ordenreclamo.getIdOrdenReclamo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ordenreclamo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Detallereclamo> detallereclamoCollectionOrphanCheck = ordenreclamo.getDetallereclamoCollection();
            for (Detallereclamo detallereclamoCollectionOrphanCheckDetallereclamo : detallereclamoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ordenreclamo (" + ordenreclamo + ") cannot be destroyed since the Detallereclamo " + detallereclamoCollectionOrphanCheckDetallereclamo + " in its detallereclamoCollection field has a non-nullable idOrdenReclamo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(ordenreclamo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ordenreclamo> findOrdenreclamoEntities() {
        return findOrdenreclamoEntities(true, -1, -1);
    }

    public List<Ordenreclamo> findOrdenreclamoEntities(int maxResults, int firstResult) {
        return findOrdenreclamoEntities(false, maxResults, firstResult);
    }

    private List<Ordenreclamo> findOrdenreclamoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ordenreclamo.class));
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

    public Ordenreclamo findOrdenreclamo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ordenreclamo.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrdenreclamoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ordenreclamo> rt = cq.from(Ordenreclamo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
