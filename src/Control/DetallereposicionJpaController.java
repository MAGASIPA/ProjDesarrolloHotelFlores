/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Control.exceptions.NonexistentEntityException;
import Modelo.Detallereposicion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Producto;
import Modelo.Ordenreposicion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
 */
public class DetallereposicionJpaController implements Serializable {

    public DetallereposicionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Detallereposicion detallereposicion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto idProducto = detallereposicion.getIdProducto();
            if (idProducto != null) {
                idProducto = em.getReference(idProducto.getClass(), idProducto.getIdProducto());
                detallereposicion.setIdProducto(idProducto);
            }
            Ordenreposicion idOrdenReposicion = detallereposicion.getIdOrdenReposicion();
            if (idOrdenReposicion != null) {
                idOrdenReposicion = em.getReference(idOrdenReposicion.getClass(), idOrdenReposicion.getIdOrdenReposicion());
                detallereposicion.setIdOrdenReposicion(idOrdenReposicion);
            }
            em.persist(detallereposicion);
            if (idProducto != null) {
                idProducto.getDetallereposicionCollection().add(detallereposicion);
                idProducto = em.merge(idProducto);
            }
            if (idOrdenReposicion != null) {
                idOrdenReposicion.getDetallereposicionCollection().add(detallereposicion);
                idOrdenReposicion = em.merge(idOrdenReposicion);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Detallereposicion detallereposicion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Detallereposicion persistentDetallereposicion = em.find(Detallereposicion.class, detallereposicion.getIdDetalleReposicion());
            Producto idProductoOld = persistentDetallereposicion.getIdProducto();
            Producto idProductoNew = detallereposicion.getIdProducto();
            Ordenreposicion idOrdenReposicionOld = persistentDetallereposicion.getIdOrdenReposicion();
            Ordenreposicion idOrdenReposicionNew = detallereposicion.getIdOrdenReposicion();
            if (idProductoNew != null) {
                idProductoNew = em.getReference(idProductoNew.getClass(), idProductoNew.getIdProducto());
                detallereposicion.setIdProducto(idProductoNew);
            }
            if (idOrdenReposicionNew != null) {
                idOrdenReposicionNew = em.getReference(idOrdenReposicionNew.getClass(), idOrdenReposicionNew.getIdOrdenReposicion());
                detallereposicion.setIdOrdenReposicion(idOrdenReposicionNew);
            }
            detallereposicion = em.merge(detallereposicion);
            if (idProductoOld != null && !idProductoOld.equals(idProductoNew)) {
                idProductoOld.getDetallereposicionCollection().remove(detallereposicion);
                idProductoOld = em.merge(idProductoOld);
            }
            if (idProductoNew != null && !idProductoNew.equals(idProductoOld)) {
                idProductoNew.getDetallereposicionCollection().add(detallereposicion);
                idProductoNew = em.merge(idProductoNew);
            }
            if (idOrdenReposicionOld != null && !idOrdenReposicionOld.equals(idOrdenReposicionNew)) {
                idOrdenReposicionOld.getDetallereposicionCollection().remove(detallereposicion);
                idOrdenReposicionOld = em.merge(idOrdenReposicionOld);
            }
            if (idOrdenReposicionNew != null && !idOrdenReposicionNew.equals(idOrdenReposicionOld)) {
                idOrdenReposicionNew.getDetallereposicionCollection().add(detallereposicion);
                idOrdenReposicionNew = em.merge(idOrdenReposicionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detallereposicion.getIdDetalleReposicion();
                if (findDetallereposicion(id) == null) {
                    throw new NonexistentEntityException("The detallereposicion with id " + id + " no longer exists.");
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
            Detallereposicion detallereposicion;
            try {
                detallereposicion = em.getReference(Detallereposicion.class, id);
                detallereposicion.getIdDetalleReposicion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detallereposicion with id " + id + " no longer exists.", enfe);
            }
            Producto idProducto = detallereposicion.getIdProducto();
            if (idProducto != null) {
                idProducto.getDetallereposicionCollection().remove(detallereposicion);
                idProducto = em.merge(idProducto);
            }
            Ordenreposicion idOrdenReposicion = detallereposicion.getIdOrdenReposicion();
            if (idOrdenReposicion != null) {
                idOrdenReposicion.getDetallereposicionCollection().remove(detallereposicion);
                idOrdenReposicion = em.merge(idOrdenReposicion);
            }
            em.remove(detallereposicion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Detallereposicion> findDetallereposicionEntities() {
        return findDetallereposicionEntities(true, -1, -1);
    }

    public List<Detallereposicion> findDetallereposicionEntities(int maxResults, int firstResult) {
        return findDetallereposicionEntities(false, maxResults, firstResult);
    }

    private List<Detallereposicion> findDetallereposicionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Detallereposicion.class));
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

    public Detallereposicion findDetallereposicion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Detallereposicion.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetallereposicionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Detallereposicion> rt = cq.from(Detallereposicion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
