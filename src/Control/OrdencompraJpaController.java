/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Control.exceptions.NonexistentEntityException;
import Modelo.Ordencompra;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Ordenreposicion;
import Modelo.Proveedor;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
 */
public class OrdencompraJpaController implements Serializable {

    public OrdencompraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ordencompra ordencompra) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ordenreposicion idOrdenReposicion = ordencompra.getIdOrdenReposicion();
            if (idOrdenReposicion != null) {
                idOrdenReposicion = em.getReference(idOrdenReposicion.getClass(), idOrdenReposicion.getIdOrdenReposicion());
                ordencompra.setIdOrdenReposicion(idOrdenReposicion);
            }
            Proveedor idProveedor = ordencompra.getIdProveedor();
            if (idProveedor != null) {
                idProveedor = em.getReference(idProveedor.getClass(), idProveedor.getIdProveedor());
                ordencompra.setIdProveedor(idProveedor);
            }
            em.persist(ordencompra);
            if (idOrdenReposicion != null) {
                idOrdenReposicion.getOrdencompraCollection().add(ordencompra);
                idOrdenReposicion = em.merge(idOrdenReposicion);
            }
            if (idProveedor != null) {
                idProveedor.getOrdencompraCollection().add(ordencompra);
                idProveedor = em.merge(idProveedor);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ordencompra ordencompra) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ordencompra persistentOrdencompra = em.find(Ordencompra.class, ordencompra.getIdOrdenCompra());
            Ordenreposicion idOrdenReposicionOld = persistentOrdencompra.getIdOrdenReposicion();
            Ordenreposicion idOrdenReposicionNew = ordencompra.getIdOrdenReposicion();
            Proveedor idProveedorOld = persistentOrdencompra.getIdProveedor();
            Proveedor idProveedorNew = ordencompra.getIdProveedor();
            if (idOrdenReposicionNew != null) {
                idOrdenReposicionNew = em.getReference(idOrdenReposicionNew.getClass(), idOrdenReposicionNew.getIdOrdenReposicion());
                ordencompra.setIdOrdenReposicion(idOrdenReposicionNew);
            }
            if (idProveedorNew != null) {
                idProveedorNew = em.getReference(idProveedorNew.getClass(), idProveedorNew.getIdProveedor());
                ordencompra.setIdProveedor(idProveedorNew);
            }
            ordencompra = em.merge(ordencompra);
            if (idOrdenReposicionOld != null && !idOrdenReposicionOld.equals(idOrdenReposicionNew)) {
                idOrdenReposicionOld.getOrdencompraCollection().remove(ordencompra);
                idOrdenReposicionOld = em.merge(idOrdenReposicionOld);
            }
            if (idOrdenReposicionNew != null && !idOrdenReposicionNew.equals(idOrdenReposicionOld)) {
                idOrdenReposicionNew.getOrdencompraCollection().add(ordencompra);
                idOrdenReposicionNew = em.merge(idOrdenReposicionNew);
            }
            if (idProveedorOld != null && !idProveedorOld.equals(idProveedorNew)) {
                idProveedorOld.getOrdencompraCollection().remove(ordencompra);
                idProveedorOld = em.merge(idProveedorOld);
            }
            if (idProveedorNew != null && !idProveedorNew.equals(idProveedorOld)) {
                idProveedorNew.getOrdencompraCollection().add(ordencompra);
                idProveedorNew = em.merge(idProveedorNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ordencompra.getIdOrdenCompra();
                if (findOrdencompra(id) == null) {
                    throw new NonexistentEntityException("The ordencompra with id " + id + " no longer exists.");
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
            Ordencompra ordencompra;
            try {
                ordencompra = em.getReference(Ordencompra.class, id);
                ordencompra.getIdOrdenCompra();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ordencompra with id " + id + " no longer exists.", enfe);
            }
            Ordenreposicion idOrdenReposicion = ordencompra.getIdOrdenReposicion();
            if (idOrdenReposicion != null) {
                idOrdenReposicion.getOrdencompraCollection().remove(ordencompra);
                idOrdenReposicion = em.merge(idOrdenReposicion);
            }
            Proveedor idProveedor = ordencompra.getIdProveedor();
            if (idProveedor != null) {
                idProveedor.getOrdencompraCollection().remove(ordencompra);
                idProveedor = em.merge(idProveedor);
            }
            em.remove(ordencompra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ordencompra> findOrdencompraEntities() {
        return findOrdencompraEntities(true, -1, -1);
    }

    public List<Ordencompra> findOrdencompraEntities(int maxResults, int firstResult) {
        return findOrdencompraEntities(false, maxResults, firstResult);
    }

    private List<Ordencompra> findOrdencompraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ordencompra.class));
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

    public Ordencompra findOrdencompra(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ordencompra.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrdencompraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ordencompra> rt = cq.from(Ordencompra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
