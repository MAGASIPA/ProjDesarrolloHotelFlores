/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Control.exceptions.NonexistentEntityException;
import Modelo.Detallereclamo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Producto;
import Modelo.Ordenreclamo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
 */
public class DetallereclamoJpaController implements Serializable {

    public DetallereclamoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Detallereclamo detallereclamo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto idProducto = detallereclamo.getIdProducto();
            if (idProducto != null) {
                idProducto = em.getReference(idProducto.getClass(), idProducto.getIdProducto());
                detallereclamo.setIdProducto(idProducto);
            }
            Ordenreclamo idOrdenReclamo = detallereclamo.getIdOrdenReclamo();
            if (idOrdenReclamo != null) {
                idOrdenReclamo = em.getReference(idOrdenReclamo.getClass(), idOrdenReclamo.getIdOrdenReclamo());
                detallereclamo.setIdOrdenReclamo(idOrdenReclamo);
            }
            em.persist(detallereclamo);
            if (idProducto != null) {
                idProducto.getDetallereclamoCollection().add(detallereclamo);
                idProducto = em.merge(idProducto);
            }
            if (idOrdenReclamo != null) {
                idOrdenReclamo.getDetallereclamoCollection().add(detallereclamo);
                idOrdenReclamo = em.merge(idOrdenReclamo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Detallereclamo detallereclamo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Detallereclamo persistentDetallereclamo = em.find(Detallereclamo.class, detallereclamo.getIdDetalleReclamo());
            Producto idProductoOld = persistentDetallereclamo.getIdProducto();
            Producto idProductoNew = detallereclamo.getIdProducto();
            Ordenreclamo idOrdenReclamoOld = persistentDetallereclamo.getIdOrdenReclamo();
            Ordenreclamo idOrdenReclamoNew = detallereclamo.getIdOrdenReclamo();
            if (idProductoNew != null) {
                idProductoNew = em.getReference(idProductoNew.getClass(), idProductoNew.getIdProducto());
                detallereclamo.setIdProducto(idProductoNew);
            }
            if (idOrdenReclamoNew != null) {
                idOrdenReclamoNew = em.getReference(idOrdenReclamoNew.getClass(), idOrdenReclamoNew.getIdOrdenReclamo());
                detallereclamo.setIdOrdenReclamo(idOrdenReclamoNew);
            }
            detallereclamo = em.merge(detallereclamo);
            if (idProductoOld != null && !idProductoOld.equals(idProductoNew)) {
                idProductoOld.getDetallereclamoCollection().remove(detallereclamo);
                idProductoOld = em.merge(idProductoOld);
            }
            if (idProductoNew != null && !idProductoNew.equals(idProductoOld)) {
                idProductoNew.getDetallereclamoCollection().add(detallereclamo);
                idProductoNew = em.merge(idProductoNew);
            }
            if (idOrdenReclamoOld != null && !idOrdenReclamoOld.equals(idOrdenReclamoNew)) {
                idOrdenReclamoOld.getDetallereclamoCollection().remove(detallereclamo);
                idOrdenReclamoOld = em.merge(idOrdenReclamoOld);
            }
            if (idOrdenReclamoNew != null && !idOrdenReclamoNew.equals(idOrdenReclamoOld)) {
                idOrdenReclamoNew.getDetallereclamoCollection().add(detallereclamo);
                idOrdenReclamoNew = em.merge(idOrdenReclamoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detallereclamo.getIdDetalleReclamo();
                if (findDetallereclamo(id) == null) {
                    throw new NonexistentEntityException("The detallereclamo with id " + id + " no longer exists.");
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
            Detallereclamo detallereclamo;
            try {
                detallereclamo = em.getReference(Detallereclamo.class, id);
                detallereclamo.getIdDetalleReclamo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detallereclamo with id " + id + " no longer exists.", enfe);
            }
            Producto idProducto = detallereclamo.getIdProducto();
            if (idProducto != null) {
                idProducto.getDetallereclamoCollection().remove(detallereclamo);
                idProducto = em.merge(idProducto);
            }
            Ordenreclamo idOrdenReclamo = detallereclamo.getIdOrdenReclamo();
            if (idOrdenReclamo != null) {
                idOrdenReclamo.getDetallereclamoCollection().remove(detallereclamo);
                idOrdenReclamo = em.merge(idOrdenReclamo);
            }
            em.remove(detallereclamo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Detallereclamo> findDetallereclamoEntities() {
        return findDetallereclamoEntities(true, -1, -1);
    }

    public List<Detallereclamo> findDetallereclamoEntities(int maxResults, int firstResult) {
        return findDetallereclamoEntities(false, maxResults, firstResult);
    }

    private List<Detallereclamo> findDetallereclamoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Detallereclamo.class));
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

    public Detallereclamo findDetallereclamo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Detallereclamo.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetallereclamoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Detallereclamo> rt = cq.from(Detallereclamo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
