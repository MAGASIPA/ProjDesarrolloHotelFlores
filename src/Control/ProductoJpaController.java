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
import Modelo.Kardex;
import java.util.ArrayList;
import java.util.Collection;
import Modelo.Hojaconsumo;
import Modelo.Detallereclamo;
import Modelo.Detallereposicion;
import Modelo.Producto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
 */
public class ProductoJpaController implements Serializable {

    public ProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producto producto) {
        if (producto.getKardexCollection() == null) {
            producto.setKardexCollection(new ArrayList<Kardex>());
        }
        if (producto.getHojaconsumoCollection() == null) {
            producto.setHojaconsumoCollection(new ArrayList<Hojaconsumo>());
        }
        if (producto.getDetallereclamoCollection() == null) {
            producto.setDetallereclamoCollection(new ArrayList<Detallereclamo>());
        }
        if (producto.getDetallereposicionCollection() == null) {
            producto.setDetallereposicionCollection(new ArrayList<Detallereposicion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Kardex> attachedKardexCollection = new ArrayList<Kardex>();
            for (Kardex kardexCollectionKardexToAttach : producto.getKardexCollection()) {
                kardexCollectionKardexToAttach = em.getReference(kardexCollectionKardexToAttach.getClass(), kardexCollectionKardexToAttach.getIdKardex());
                attachedKardexCollection.add(kardexCollectionKardexToAttach);
            }
            producto.setKardexCollection(attachedKardexCollection);
            Collection<Hojaconsumo> attachedHojaconsumoCollection = new ArrayList<Hojaconsumo>();
            for (Hojaconsumo hojaconsumoCollectionHojaconsumoToAttach : producto.getHojaconsumoCollection()) {
                hojaconsumoCollectionHojaconsumoToAttach = em.getReference(hojaconsumoCollectionHojaconsumoToAttach.getClass(), hojaconsumoCollectionHojaconsumoToAttach.getIdHojaConsumo());
                attachedHojaconsumoCollection.add(hojaconsumoCollectionHojaconsumoToAttach);
            }
            producto.setHojaconsumoCollection(attachedHojaconsumoCollection);
            Collection<Detallereclamo> attachedDetallereclamoCollection = new ArrayList<Detallereclamo>();
            for (Detallereclamo detallereclamoCollectionDetallereclamoToAttach : producto.getDetallereclamoCollection()) {
                detallereclamoCollectionDetallereclamoToAttach = em.getReference(detallereclamoCollectionDetallereclamoToAttach.getClass(), detallereclamoCollectionDetallereclamoToAttach.getIdDetalleReclamo());
                attachedDetallereclamoCollection.add(detallereclamoCollectionDetallereclamoToAttach);
            }
            producto.setDetallereclamoCollection(attachedDetallereclamoCollection);
            Collection<Detallereposicion> attachedDetallereposicionCollection = new ArrayList<Detallereposicion>();
            for (Detallereposicion detallereposicionCollectionDetallereposicionToAttach : producto.getDetallereposicionCollection()) {
                detallereposicionCollectionDetallereposicionToAttach = em.getReference(detallereposicionCollectionDetallereposicionToAttach.getClass(), detallereposicionCollectionDetallereposicionToAttach.getIdDetalleReposicion());
                attachedDetallereposicionCollection.add(detallereposicionCollectionDetallereposicionToAttach);
            }
            producto.setDetallereposicionCollection(attachedDetallereposicionCollection);
            em.persist(producto);
            for (Kardex kardexCollectionKardex : producto.getKardexCollection()) {
                Producto oldIdProductoOfKardexCollectionKardex = kardexCollectionKardex.getIdProducto();
                kardexCollectionKardex.setIdProducto(producto);
                kardexCollectionKardex = em.merge(kardexCollectionKardex);
                if (oldIdProductoOfKardexCollectionKardex != null) {
                    oldIdProductoOfKardexCollectionKardex.getKardexCollection().remove(kardexCollectionKardex);
                    oldIdProductoOfKardexCollectionKardex = em.merge(oldIdProductoOfKardexCollectionKardex);
                }
            }
            for (Hojaconsumo hojaconsumoCollectionHojaconsumo : producto.getHojaconsumoCollection()) {
                Producto oldIdProductoOfHojaconsumoCollectionHojaconsumo = hojaconsumoCollectionHojaconsumo.getIdProducto();
                hojaconsumoCollectionHojaconsumo.setIdProducto(producto);
                hojaconsumoCollectionHojaconsumo = em.merge(hojaconsumoCollectionHojaconsumo);
                if (oldIdProductoOfHojaconsumoCollectionHojaconsumo != null) {
                    oldIdProductoOfHojaconsumoCollectionHojaconsumo.getHojaconsumoCollection().remove(hojaconsumoCollectionHojaconsumo);
                    oldIdProductoOfHojaconsumoCollectionHojaconsumo = em.merge(oldIdProductoOfHojaconsumoCollectionHojaconsumo);
                }
            }
            for (Detallereclamo detallereclamoCollectionDetallereclamo : producto.getDetallereclamoCollection()) {
                Producto oldIdProductoOfDetallereclamoCollectionDetallereclamo = detallereclamoCollectionDetallereclamo.getIdProducto();
                detallereclamoCollectionDetallereclamo.setIdProducto(producto);
                detallereclamoCollectionDetallereclamo = em.merge(detallereclamoCollectionDetallereclamo);
                if (oldIdProductoOfDetallereclamoCollectionDetallereclamo != null) {
                    oldIdProductoOfDetallereclamoCollectionDetallereclamo.getDetallereclamoCollection().remove(detallereclamoCollectionDetallereclamo);
                    oldIdProductoOfDetallereclamoCollectionDetallereclamo = em.merge(oldIdProductoOfDetallereclamoCollectionDetallereclamo);
                }
            }
            for (Detallereposicion detallereposicionCollectionDetallereposicion : producto.getDetallereposicionCollection()) {
                Producto oldIdProductoOfDetallereposicionCollectionDetallereposicion = detallereposicionCollectionDetallereposicion.getIdProducto();
                detallereposicionCollectionDetallereposicion.setIdProducto(producto);
                detallereposicionCollectionDetallereposicion = em.merge(detallereposicionCollectionDetallereposicion);
                if (oldIdProductoOfDetallereposicionCollectionDetallereposicion != null) {
                    oldIdProductoOfDetallereposicionCollectionDetallereposicion.getDetallereposicionCollection().remove(detallereposicionCollectionDetallereposicion);
                    oldIdProductoOfDetallereposicionCollectionDetallereposicion = em.merge(oldIdProductoOfDetallereposicionCollectionDetallereposicion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Producto producto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto persistentProducto = em.find(Producto.class, producto.getIdProducto());
            Collection<Kardex> kardexCollectionOld = persistentProducto.getKardexCollection();
            Collection<Kardex> kardexCollectionNew = producto.getKardexCollection();
            Collection<Hojaconsumo> hojaconsumoCollectionOld = persistentProducto.getHojaconsumoCollection();
            Collection<Hojaconsumo> hojaconsumoCollectionNew = producto.getHojaconsumoCollection();
            Collection<Detallereclamo> detallereclamoCollectionOld = persistentProducto.getDetallereclamoCollection();
            Collection<Detallereclamo> detallereclamoCollectionNew = producto.getDetallereclamoCollection();
            Collection<Detallereposicion> detallereposicionCollectionOld = persistentProducto.getDetallereposicionCollection();
            Collection<Detallereposicion> detallereposicionCollectionNew = producto.getDetallereposicionCollection();
            List<String> illegalOrphanMessages = null;
            for (Kardex kardexCollectionOldKardex : kardexCollectionOld) {
                if (!kardexCollectionNew.contains(kardexCollectionOldKardex)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Kardex " + kardexCollectionOldKardex + " since its idProducto field is not nullable.");
                }
            }
            for (Hojaconsumo hojaconsumoCollectionOldHojaconsumo : hojaconsumoCollectionOld) {
                if (!hojaconsumoCollectionNew.contains(hojaconsumoCollectionOldHojaconsumo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Hojaconsumo " + hojaconsumoCollectionOldHojaconsumo + " since its idProducto field is not nullable.");
                }
            }
            for (Detallereclamo detallereclamoCollectionOldDetallereclamo : detallereclamoCollectionOld) {
                if (!detallereclamoCollectionNew.contains(detallereclamoCollectionOldDetallereclamo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Detallereclamo " + detallereclamoCollectionOldDetallereclamo + " since its idProducto field is not nullable.");
                }
            }
            for (Detallereposicion detallereposicionCollectionOldDetallereposicion : detallereposicionCollectionOld) {
                if (!detallereposicionCollectionNew.contains(detallereposicionCollectionOldDetallereposicion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Detallereposicion " + detallereposicionCollectionOldDetallereposicion + " since its idProducto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Kardex> attachedKardexCollectionNew = new ArrayList<Kardex>();
            for (Kardex kardexCollectionNewKardexToAttach : kardexCollectionNew) {
                kardexCollectionNewKardexToAttach = em.getReference(kardexCollectionNewKardexToAttach.getClass(), kardexCollectionNewKardexToAttach.getIdKardex());
                attachedKardexCollectionNew.add(kardexCollectionNewKardexToAttach);
            }
            kardexCollectionNew = attachedKardexCollectionNew;
            producto.setKardexCollection(kardexCollectionNew);
            Collection<Hojaconsumo> attachedHojaconsumoCollectionNew = new ArrayList<Hojaconsumo>();
            for (Hojaconsumo hojaconsumoCollectionNewHojaconsumoToAttach : hojaconsumoCollectionNew) {
                hojaconsumoCollectionNewHojaconsumoToAttach = em.getReference(hojaconsumoCollectionNewHojaconsumoToAttach.getClass(), hojaconsumoCollectionNewHojaconsumoToAttach.getIdHojaConsumo());
                attachedHojaconsumoCollectionNew.add(hojaconsumoCollectionNewHojaconsumoToAttach);
            }
            hojaconsumoCollectionNew = attachedHojaconsumoCollectionNew;
            producto.setHojaconsumoCollection(hojaconsumoCollectionNew);
            Collection<Detallereclamo> attachedDetallereclamoCollectionNew = new ArrayList<Detallereclamo>();
            for (Detallereclamo detallereclamoCollectionNewDetallereclamoToAttach : detallereclamoCollectionNew) {
                detallereclamoCollectionNewDetallereclamoToAttach = em.getReference(detallereclamoCollectionNewDetallereclamoToAttach.getClass(), detallereclamoCollectionNewDetallereclamoToAttach.getIdDetalleReclamo());
                attachedDetallereclamoCollectionNew.add(detallereclamoCollectionNewDetallereclamoToAttach);
            }
            detallereclamoCollectionNew = attachedDetallereclamoCollectionNew;
            producto.setDetallereclamoCollection(detallereclamoCollectionNew);
            Collection<Detallereposicion> attachedDetallereposicionCollectionNew = new ArrayList<Detallereposicion>();
            for (Detallereposicion detallereposicionCollectionNewDetallereposicionToAttach : detallereposicionCollectionNew) {
                detallereposicionCollectionNewDetallereposicionToAttach = em.getReference(detallereposicionCollectionNewDetallereposicionToAttach.getClass(), detallereposicionCollectionNewDetallereposicionToAttach.getIdDetalleReposicion());
                attachedDetallereposicionCollectionNew.add(detallereposicionCollectionNewDetallereposicionToAttach);
            }
            detallereposicionCollectionNew = attachedDetallereposicionCollectionNew;
            producto.setDetallereposicionCollection(detallereposicionCollectionNew);
            producto = em.merge(producto);
            for (Kardex kardexCollectionNewKardex : kardexCollectionNew) {
                if (!kardexCollectionOld.contains(kardexCollectionNewKardex)) {
                    Producto oldIdProductoOfKardexCollectionNewKardex = kardexCollectionNewKardex.getIdProducto();
                    kardexCollectionNewKardex.setIdProducto(producto);
                    kardexCollectionNewKardex = em.merge(kardexCollectionNewKardex);
                    if (oldIdProductoOfKardexCollectionNewKardex != null && !oldIdProductoOfKardexCollectionNewKardex.equals(producto)) {
                        oldIdProductoOfKardexCollectionNewKardex.getKardexCollection().remove(kardexCollectionNewKardex);
                        oldIdProductoOfKardexCollectionNewKardex = em.merge(oldIdProductoOfKardexCollectionNewKardex);
                    }
                }
            }
            for (Hojaconsumo hojaconsumoCollectionNewHojaconsumo : hojaconsumoCollectionNew) {
                if (!hojaconsumoCollectionOld.contains(hojaconsumoCollectionNewHojaconsumo)) {
                    Producto oldIdProductoOfHojaconsumoCollectionNewHojaconsumo = hojaconsumoCollectionNewHojaconsumo.getIdProducto();
                    hojaconsumoCollectionNewHojaconsumo.setIdProducto(producto);
                    hojaconsumoCollectionNewHojaconsumo = em.merge(hojaconsumoCollectionNewHojaconsumo);
                    if (oldIdProductoOfHojaconsumoCollectionNewHojaconsumo != null && !oldIdProductoOfHojaconsumoCollectionNewHojaconsumo.equals(producto)) {
                        oldIdProductoOfHojaconsumoCollectionNewHojaconsumo.getHojaconsumoCollection().remove(hojaconsumoCollectionNewHojaconsumo);
                        oldIdProductoOfHojaconsumoCollectionNewHojaconsumo = em.merge(oldIdProductoOfHojaconsumoCollectionNewHojaconsumo);
                    }
                }
            }
            for (Detallereclamo detallereclamoCollectionNewDetallereclamo : detallereclamoCollectionNew) {
                if (!detallereclamoCollectionOld.contains(detallereclamoCollectionNewDetallereclamo)) {
                    Producto oldIdProductoOfDetallereclamoCollectionNewDetallereclamo = detallereclamoCollectionNewDetallereclamo.getIdProducto();
                    detallereclamoCollectionNewDetallereclamo.setIdProducto(producto);
                    detallereclamoCollectionNewDetallereclamo = em.merge(detallereclamoCollectionNewDetallereclamo);
                    if (oldIdProductoOfDetallereclamoCollectionNewDetallereclamo != null && !oldIdProductoOfDetallereclamoCollectionNewDetallereclamo.equals(producto)) {
                        oldIdProductoOfDetallereclamoCollectionNewDetallereclamo.getDetallereclamoCollection().remove(detallereclamoCollectionNewDetallereclamo);
                        oldIdProductoOfDetallereclamoCollectionNewDetallereclamo = em.merge(oldIdProductoOfDetallereclamoCollectionNewDetallereclamo);
                    }
                }
            }
            for (Detallereposicion detallereposicionCollectionNewDetallereposicion : detallereposicionCollectionNew) {
                if (!detallereposicionCollectionOld.contains(detallereposicionCollectionNewDetallereposicion)) {
                    Producto oldIdProductoOfDetallereposicionCollectionNewDetallereposicion = detallereposicionCollectionNewDetallereposicion.getIdProducto();
                    detallereposicionCollectionNewDetallereposicion.setIdProducto(producto);
                    detallereposicionCollectionNewDetallereposicion = em.merge(detallereposicionCollectionNewDetallereposicion);
                    if (oldIdProductoOfDetallereposicionCollectionNewDetallereposicion != null && !oldIdProductoOfDetallereposicionCollectionNewDetallereposicion.equals(producto)) {
                        oldIdProductoOfDetallereposicionCollectionNewDetallereposicion.getDetallereposicionCollection().remove(detallereposicionCollectionNewDetallereposicion);
                        oldIdProductoOfDetallereposicionCollectionNewDetallereposicion = em.merge(oldIdProductoOfDetallereposicionCollectionNewDetallereposicion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = producto.getIdProducto();
                if (findProducto(id) == null) {
                    throw new NonexistentEntityException("The producto with id " + id + " no longer exists.");
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
            Producto producto;
            try {
                producto = em.getReference(Producto.class, id);
                producto.getIdProducto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Kardex> kardexCollectionOrphanCheck = producto.getKardexCollection();
            for (Kardex kardexCollectionOrphanCheckKardex : kardexCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the Kardex " + kardexCollectionOrphanCheckKardex + " in its kardexCollection field has a non-nullable idProducto field.");
            }
            Collection<Hojaconsumo> hojaconsumoCollectionOrphanCheck = producto.getHojaconsumoCollection();
            for (Hojaconsumo hojaconsumoCollectionOrphanCheckHojaconsumo : hojaconsumoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the Hojaconsumo " + hojaconsumoCollectionOrphanCheckHojaconsumo + " in its hojaconsumoCollection field has a non-nullable idProducto field.");
            }
            Collection<Detallereclamo> detallereclamoCollectionOrphanCheck = producto.getDetallereclamoCollection();
            for (Detallereclamo detallereclamoCollectionOrphanCheckDetallereclamo : detallereclamoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the Detallereclamo " + detallereclamoCollectionOrphanCheckDetallereclamo + " in its detallereclamoCollection field has a non-nullable idProducto field.");
            }
            Collection<Detallereposicion> detallereposicionCollectionOrphanCheck = producto.getDetallereposicionCollection();
            for (Detallereposicion detallereposicionCollectionOrphanCheckDetallereposicion : detallereposicionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the Detallereposicion " + detallereposicionCollectionOrphanCheckDetallereposicion + " in its detallereposicionCollection field has a non-nullable idProducto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(producto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Producto.class));
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

    public Producto findProducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Producto> rt = cq.from(Producto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
