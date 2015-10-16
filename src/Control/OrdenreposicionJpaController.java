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
import Modelo.Detallereposicion;
import java.util.ArrayList;
import java.util.Collection;
import Modelo.Ordencompra;
import Modelo.Ordenreposicion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
 */
public class OrdenreposicionJpaController implements Serializable {

    public OrdenreposicionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ordenreposicion ordenreposicion) {
        if (ordenreposicion.getDetallereposicionCollection() == null) {
            ordenreposicion.setDetallereposicionCollection(new ArrayList<Detallereposicion>());
        }
        if (ordenreposicion.getOrdencompraCollection() == null) {
            ordenreposicion.setOrdencompraCollection(new ArrayList<Ordencompra>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Detallereposicion> attachedDetallereposicionCollection = new ArrayList<Detallereposicion>();
            for (Detallereposicion detallereposicionCollectionDetallereposicionToAttach : ordenreposicion.getDetallereposicionCollection()) {
                detallereposicionCollectionDetallereposicionToAttach = em.getReference(detallereposicionCollectionDetallereposicionToAttach.getClass(), detallereposicionCollectionDetallereposicionToAttach.getIdDetalleReposicion());
                attachedDetallereposicionCollection.add(detallereposicionCollectionDetallereposicionToAttach);
            }
            ordenreposicion.setDetallereposicionCollection(attachedDetallereposicionCollection);
            Collection<Ordencompra> attachedOrdencompraCollection = new ArrayList<Ordencompra>();
            for (Ordencompra ordencompraCollectionOrdencompraToAttach : ordenreposicion.getOrdencompraCollection()) {
                ordencompraCollectionOrdencompraToAttach = em.getReference(ordencompraCollectionOrdencompraToAttach.getClass(), ordencompraCollectionOrdencompraToAttach.getIdOrdenCompra());
                attachedOrdencompraCollection.add(ordencompraCollectionOrdencompraToAttach);
            }
            ordenreposicion.setOrdencompraCollection(attachedOrdencompraCollection);
            em.persist(ordenreposicion);
            for (Detallereposicion detallereposicionCollectionDetallereposicion : ordenreposicion.getDetallereposicionCollection()) {
                Ordenreposicion oldIdOrdenReposicionOfDetallereposicionCollectionDetallereposicion = detallereposicionCollectionDetallereposicion.getIdOrdenReposicion();
                detallereposicionCollectionDetallereposicion.setIdOrdenReposicion(ordenreposicion);
                detallereposicionCollectionDetallereposicion = em.merge(detallereposicionCollectionDetallereposicion);
                if (oldIdOrdenReposicionOfDetallereposicionCollectionDetallereposicion != null) {
                    oldIdOrdenReposicionOfDetallereposicionCollectionDetallereposicion.getDetallereposicionCollection().remove(detallereposicionCollectionDetallereposicion);
                    oldIdOrdenReposicionOfDetallereposicionCollectionDetallereposicion = em.merge(oldIdOrdenReposicionOfDetallereposicionCollectionDetallereposicion);
                }
            }
            for (Ordencompra ordencompraCollectionOrdencompra : ordenreposicion.getOrdencompraCollection()) {
                Ordenreposicion oldIdOrdenReposicionOfOrdencompraCollectionOrdencompra = ordencompraCollectionOrdencompra.getIdOrdenReposicion();
                ordencompraCollectionOrdencompra.setIdOrdenReposicion(ordenreposicion);
                ordencompraCollectionOrdencompra = em.merge(ordencompraCollectionOrdencompra);
                if (oldIdOrdenReposicionOfOrdencompraCollectionOrdencompra != null) {
                    oldIdOrdenReposicionOfOrdencompraCollectionOrdencompra.getOrdencompraCollection().remove(ordencompraCollectionOrdencompra);
                    oldIdOrdenReposicionOfOrdencompraCollectionOrdencompra = em.merge(oldIdOrdenReposicionOfOrdencompraCollectionOrdencompra);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ordenreposicion ordenreposicion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ordenreposicion persistentOrdenreposicion = em.find(Ordenreposicion.class, ordenreposicion.getIdOrdenReposicion());
            Collection<Detallereposicion> detallereposicionCollectionOld = persistentOrdenreposicion.getDetallereposicionCollection();
            Collection<Detallereposicion> detallereposicionCollectionNew = ordenreposicion.getDetallereposicionCollection();
            Collection<Ordencompra> ordencompraCollectionOld = persistentOrdenreposicion.getOrdencompraCollection();
            Collection<Ordencompra> ordencompraCollectionNew = ordenreposicion.getOrdencompraCollection();
            List<String> illegalOrphanMessages = null;
            for (Detallereposicion detallereposicionCollectionOldDetallereposicion : detallereposicionCollectionOld) {
                if (!detallereposicionCollectionNew.contains(detallereposicionCollectionOldDetallereposicion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Detallereposicion " + detallereposicionCollectionOldDetallereposicion + " since its idOrdenReposicion field is not nullable.");
                }
            }
            for (Ordencompra ordencompraCollectionOldOrdencompra : ordencompraCollectionOld) {
                if (!ordencompraCollectionNew.contains(ordencompraCollectionOldOrdencompra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ordencompra " + ordencompraCollectionOldOrdencompra + " since its idOrdenReposicion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Detallereposicion> attachedDetallereposicionCollectionNew = new ArrayList<Detallereposicion>();
            for (Detallereposicion detallereposicionCollectionNewDetallereposicionToAttach : detallereposicionCollectionNew) {
                detallereposicionCollectionNewDetallereposicionToAttach = em.getReference(detallereposicionCollectionNewDetallereposicionToAttach.getClass(), detallereposicionCollectionNewDetallereposicionToAttach.getIdDetalleReposicion());
                attachedDetallereposicionCollectionNew.add(detallereposicionCollectionNewDetallereposicionToAttach);
            }
            detallereposicionCollectionNew = attachedDetallereposicionCollectionNew;
            ordenreposicion.setDetallereposicionCollection(detallereposicionCollectionNew);
            Collection<Ordencompra> attachedOrdencompraCollectionNew = new ArrayList<Ordencompra>();
            for (Ordencompra ordencompraCollectionNewOrdencompraToAttach : ordencompraCollectionNew) {
                ordencompraCollectionNewOrdencompraToAttach = em.getReference(ordencompraCollectionNewOrdencompraToAttach.getClass(), ordencompraCollectionNewOrdencompraToAttach.getIdOrdenCompra());
                attachedOrdencompraCollectionNew.add(ordencompraCollectionNewOrdencompraToAttach);
            }
            ordencompraCollectionNew = attachedOrdencompraCollectionNew;
            ordenreposicion.setOrdencompraCollection(ordencompraCollectionNew);
            ordenreposicion = em.merge(ordenreposicion);
            for (Detallereposicion detallereposicionCollectionNewDetallereposicion : detallereposicionCollectionNew) {
                if (!detallereposicionCollectionOld.contains(detallereposicionCollectionNewDetallereposicion)) {
                    Ordenreposicion oldIdOrdenReposicionOfDetallereposicionCollectionNewDetallereposicion = detallereposicionCollectionNewDetallereposicion.getIdOrdenReposicion();
                    detallereposicionCollectionNewDetallereposicion.setIdOrdenReposicion(ordenreposicion);
                    detallereposicionCollectionNewDetallereposicion = em.merge(detallereposicionCollectionNewDetallereposicion);
                    if (oldIdOrdenReposicionOfDetallereposicionCollectionNewDetallereposicion != null && !oldIdOrdenReposicionOfDetallereposicionCollectionNewDetallereposicion.equals(ordenreposicion)) {
                        oldIdOrdenReposicionOfDetallereposicionCollectionNewDetallereposicion.getDetallereposicionCollection().remove(detallereposicionCollectionNewDetallereposicion);
                        oldIdOrdenReposicionOfDetallereposicionCollectionNewDetallereposicion = em.merge(oldIdOrdenReposicionOfDetallereposicionCollectionNewDetallereposicion);
                    }
                }
            }
            for (Ordencompra ordencompraCollectionNewOrdencompra : ordencompraCollectionNew) {
                if (!ordencompraCollectionOld.contains(ordencompraCollectionNewOrdencompra)) {
                    Ordenreposicion oldIdOrdenReposicionOfOrdencompraCollectionNewOrdencompra = ordencompraCollectionNewOrdencompra.getIdOrdenReposicion();
                    ordencompraCollectionNewOrdencompra.setIdOrdenReposicion(ordenreposicion);
                    ordencompraCollectionNewOrdencompra = em.merge(ordencompraCollectionNewOrdencompra);
                    if (oldIdOrdenReposicionOfOrdencompraCollectionNewOrdencompra != null && !oldIdOrdenReposicionOfOrdencompraCollectionNewOrdencompra.equals(ordenreposicion)) {
                        oldIdOrdenReposicionOfOrdencompraCollectionNewOrdencompra.getOrdencompraCollection().remove(ordencompraCollectionNewOrdencompra);
                        oldIdOrdenReposicionOfOrdencompraCollectionNewOrdencompra = em.merge(oldIdOrdenReposicionOfOrdencompraCollectionNewOrdencompra);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ordenreposicion.getIdOrdenReposicion();
                if (findOrdenreposicion(id) == null) {
                    throw new NonexistentEntityException("The ordenreposicion with id " + id + " no longer exists.");
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
            Ordenreposicion ordenreposicion;
            try {
                ordenreposicion = em.getReference(Ordenreposicion.class, id);
                ordenreposicion.getIdOrdenReposicion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ordenreposicion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Detallereposicion> detallereposicionCollectionOrphanCheck = ordenreposicion.getDetallereposicionCollection();
            for (Detallereposicion detallereposicionCollectionOrphanCheckDetallereposicion : detallereposicionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ordenreposicion (" + ordenreposicion + ") cannot be destroyed since the Detallereposicion " + detallereposicionCollectionOrphanCheckDetallereposicion + " in its detallereposicionCollection field has a non-nullable idOrdenReposicion field.");
            }
            Collection<Ordencompra> ordencompraCollectionOrphanCheck = ordenreposicion.getOrdencompraCollection();
            for (Ordencompra ordencompraCollectionOrphanCheckOrdencompra : ordencompraCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ordenreposicion (" + ordenreposicion + ") cannot be destroyed since the Ordencompra " + ordencompraCollectionOrphanCheckOrdencompra + " in its ordencompraCollection field has a non-nullable idOrdenReposicion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(ordenreposicion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ordenreposicion> findOrdenreposicionEntities() {
        return findOrdenreposicionEntities(true, -1, -1);
    }

    public List<Ordenreposicion> findOrdenreposicionEntities(int maxResults, int firstResult) {
        return findOrdenreposicionEntities(false, maxResults, firstResult);
    }

    private List<Ordenreposicion> findOrdenreposicionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ordenreposicion.class));
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

    public Ordenreposicion findOrdenreposicion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ordenreposicion.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrdenreposicionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ordenreposicion> rt = cq.from(Ordenreposicion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
