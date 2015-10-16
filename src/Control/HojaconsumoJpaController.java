/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Control.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Producto;
import Modelo.Cliente;
import Modelo.Documentoalquiler;
import Modelo.Hojaconsumo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
 */
public class HojaconsumoJpaController implements Serializable {

    public HojaconsumoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Hojaconsumo hojaconsumo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto idProducto = hojaconsumo.getIdProducto();
            if (idProducto != null) {
                idProducto = em.getReference(idProducto.getClass(), idProducto.getIdProducto());
                hojaconsumo.setIdProducto(idProducto);
            }
            Cliente idCliente = hojaconsumo.getIdCliente();
            if (idCliente != null) {
                idCliente = em.getReference(idCliente.getClass(), idCliente.getIdPersona());
                hojaconsumo.setIdCliente(idCliente);
            }
            Documentoalquiler idDocumentoAlquiler = hojaconsumo.getIdDocumentoAlquiler();
            if (idDocumentoAlquiler != null) {
                idDocumentoAlquiler = em.getReference(idDocumentoAlquiler.getClass(), idDocumentoAlquiler.getIdDocumentoAlquiler());
                hojaconsumo.setIdDocumentoAlquiler(idDocumentoAlquiler);
            }
            em.persist(hojaconsumo);
            if (idProducto != null) {
                idProducto.getHojaconsumoCollection().add(hojaconsumo);
                idProducto = em.merge(idProducto);
            }
            if (idCliente != null) {
                idCliente.getHojaconsumoCollection().add(hojaconsumo);
                idCliente = em.merge(idCliente);
            }
            if (idDocumentoAlquiler != null) {
                idDocumentoAlquiler.getHojaconsumoCollection().add(hojaconsumo);
                idDocumentoAlquiler = em.merge(idDocumentoAlquiler);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Hojaconsumo hojaconsumo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Hojaconsumo persistentHojaconsumo = em.find(Hojaconsumo.class, hojaconsumo.getIdHojaConsumo());
            Producto idProductoOld = persistentHojaconsumo.getIdProducto();
            Producto idProductoNew = hojaconsumo.getIdProducto();
            Cliente idClienteOld = persistentHojaconsumo.getIdCliente();
            Cliente idClienteNew = hojaconsumo.getIdCliente();
            Documentoalquiler idDocumentoAlquilerOld = persistentHojaconsumo.getIdDocumentoAlquiler();
            Documentoalquiler idDocumentoAlquilerNew = hojaconsumo.getIdDocumentoAlquiler();
            if (idProductoNew != null) {
                idProductoNew = em.getReference(idProductoNew.getClass(), idProductoNew.getIdProducto());
                hojaconsumo.setIdProducto(idProductoNew);
            }
            if (idClienteNew != null) {
                idClienteNew = em.getReference(idClienteNew.getClass(), idClienteNew.getIdPersona());
                hojaconsumo.setIdCliente(idClienteNew);
            }
            if (idDocumentoAlquilerNew != null) {
                idDocumentoAlquilerNew = em.getReference(idDocumentoAlquilerNew.getClass(), idDocumentoAlquilerNew.getIdDocumentoAlquiler());
                hojaconsumo.setIdDocumentoAlquiler(idDocumentoAlquilerNew);
            }
            hojaconsumo = em.merge(hojaconsumo);
            if (idProductoOld != null && !idProductoOld.equals(idProductoNew)) {
                idProductoOld.getHojaconsumoCollection().remove(hojaconsumo);
                idProductoOld = em.merge(idProductoOld);
            }
            if (idProductoNew != null && !idProductoNew.equals(idProductoOld)) {
                idProductoNew.getHojaconsumoCollection().add(hojaconsumo);
                idProductoNew = em.merge(idProductoNew);
            }
            if (idClienteOld != null && !idClienteOld.equals(idClienteNew)) {
                idClienteOld.getHojaconsumoCollection().remove(hojaconsumo);
                idClienteOld = em.merge(idClienteOld);
            }
            if (idClienteNew != null && !idClienteNew.equals(idClienteOld)) {
                idClienteNew.getHojaconsumoCollection().add(hojaconsumo);
                idClienteNew = em.merge(idClienteNew);
            }
            if (idDocumentoAlquilerOld != null && !idDocumentoAlquilerOld.equals(idDocumentoAlquilerNew)) {
                idDocumentoAlquilerOld.getHojaconsumoCollection().remove(hojaconsumo);
                idDocumentoAlquilerOld = em.merge(idDocumentoAlquilerOld);
            }
            if (idDocumentoAlquilerNew != null && !idDocumentoAlquilerNew.equals(idDocumentoAlquilerOld)) {
                idDocumentoAlquilerNew.getHojaconsumoCollection().add(hojaconsumo);
                idDocumentoAlquilerNew = em.merge(idDocumentoAlquilerNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = hojaconsumo.getIdHojaConsumo();
                if (findHojaconsumo(id) == null) {
                    throw new NonexistentEntityException("The hojaconsumo with id " + id + " no longer exists.");
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
            Hojaconsumo hojaconsumo;
            try {
                hojaconsumo = em.getReference(Hojaconsumo.class, id);
                hojaconsumo.getIdHojaConsumo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The hojaconsumo with id " + id + " no longer exists.", enfe);
            }
            Producto idProducto = hojaconsumo.getIdProducto();
            if (idProducto != null) {
                idProducto.getHojaconsumoCollection().remove(hojaconsumo);
                idProducto = em.merge(idProducto);
            }
            Cliente idCliente = hojaconsumo.getIdCliente();
            if (idCliente != null) {
                idCliente.getHojaconsumoCollection().remove(hojaconsumo);
                idCliente = em.merge(idCliente);
            }
            Documentoalquiler idDocumentoAlquiler = hojaconsumo.getIdDocumentoAlquiler();
            if (idDocumentoAlquiler != null) {
                idDocumentoAlquiler.getHojaconsumoCollection().remove(hojaconsumo);
                idDocumentoAlquiler = em.merge(idDocumentoAlquiler);
            }
            em.remove(hojaconsumo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Hojaconsumo> findHojaconsumoEntities() {
        return findHojaconsumoEntities(true, -1, -1);
    }

    public List<Hojaconsumo> findHojaconsumoEntities(int maxResults, int firstResult) {
        return findHojaconsumoEntities(false, maxResults, firstResult);
    }

    private List<Hojaconsumo> findHojaconsumoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Hojaconsumo.class));
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

    public Hojaconsumo findHojaconsumo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Hojaconsumo.class, id);
        } finally {
            em.close();
        }
    }

    public int getHojaconsumoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Hojaconsumo> rt = cq.from(Hojaconsumo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
