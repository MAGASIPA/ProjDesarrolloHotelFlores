/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Control.exceptions.IllegalOrphanException;
import Control.exceptions.NonexistentEntityException;
import Modelo.Permiso;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.UsuarioPermiso;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
 */
public class PermisoJpaController implements Serializable {

    public PermisoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Permiso permiso) {
        if (permiso.getUsuarioPermisoCollection() == null) {
            permiso.setUsuarioPermisoCollection(new ArrayList<UsuarioPermiso>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<UsuarioPermiso> attachedUsuarioPermisoCollection = new ArrayList<UsuarioPermiso>();
            for (UsuarioPermiso usuarioPermisoCollectionUsuarioPermisoToAttach : permiso.getUsuarioPermisoCollection()) {
                usuarioPermisoCollectionUsuarioPermisoToAttach = em.getReference(usuarioPermisoCollectionUsuarioPermisoToAttach.getClass(), usuarioPermisoCollectionUsuarioPermisoToAttach.getIdUsuario());
                attachedUsuarioPermisoCollection.add(usuarioPermisoCollectionUsuarioPermisoToAttach);
            }
            permiso.setUsuarioPermisoCollection(attachedUsuarioPermisoCollection);
            em.persist(permiso);
            for (UsuarioPermiso usuarioPermisoCollectionUsuarioPermiso : permiso.getUsuarioPermisoCollection()) {
                Permiso oldIdPermisoOfUsuarioPermisoCollectionUsuarioPermiso = usuarioPermisoCollectionUsuarioPermiso.getIdPermiso();
                usuarioPermisoCollectionUsuarioPermiso.setIdPermiso(permiso);
                usuarioPermisoCollectionUsuarioPermiso = em.merge(usuarioPermisoCollectionUsuarioPermiso);
                if (oldIdPermisoOfUsuarioPermisoCollectionUsuarioPermiso != null) {
                    oldIdPermisoOfUsuarioPermisoCollectionUsuarioPermiso.getUsuarioPermisoCollection().remove(usuarioPermisoCollectionUsuarioPermiso);
                    oldIdPermisoOfUsuarioPermisoCollectionUsuarioPermiso = em.merge(oldIdPermisoOfUsuarioPermisoCollectionUsuarioPermiso);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Permiso permiso) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Permiso persistentPermiso = em.find(Permiso.class, permiso.getIdPermiso());
            Collection<UsuarioPermiso> usuarioPermisoCollectionOld = persistentPermiso.getUsuarioPermisoCollection();
            Collection<UsuarioPermiso> usuarioPermisoCollectionNew = permiso.getUsuarioPermisoCollection();
            List<String> illegalOrphanMessages = null;
            for (UsuarioPermiso usuarioPermisoCollectionOldUsuarioPermiso : usuarioPermisoCollectionOld) {
                if (!usuarioPermisoCollectionNew.contains(usuarioPermisoCollectionOldUsuarioPermiso)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UsuarioPermiso " + usuarioPermisoCollectionOldUsuarioPermiso + " since its idPermiso field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<UsuarioPermiso> attachedUsuarioPermisoCollectionNew = new ArrayList<UsuarioPermiso>();
            for (UsuarioPermiso usuarioPermisoCollectionNewUsuarioPermisoToAttach : usuarioPermisoCollectionNew) {
                usuarioPermisoCollectionNewUsuarioPermisoToAttach = em.getReference(usuarioPermisoCollectionNewUsuarioPermisoToAttach.getClass(), usuarioPermisoCollectionNewUsuarioPermisoToAttach.getIdUsuario());
                attachedUsuarioPermisoCollectionNew.add(usuarioPermisoCollectionNewUsuarioPermisoToAttach);
            }
            usuarioPermisoCollectionNew = attachedUsuarioPermisoCollectionNew;
            permiso.setUsuarioPermisoCollection(usuarioPermisoCollectionNew);
            permiso = em.merge(permiso);
            for (UsuarioPermiso usuarioPermisoCollectionNewUsuarioPermiso : usuarioPermisoCollectionNew) {
                if (!usuarioPermisoCollectionOld.contains(usuarioPermisoCollectionNewUsuarioPermiso)) {
                    Permiso oldIdPermisoOfUsuarioPermisoCollectionNewUsuarioPermiso = usuarioPermisoCollectionNewUsuarioPermiso.getIdPermiso();
                    usuarioPermisoCollectionNewUsuarioPermiso.setIdPermiso(permiso);
                    usuarioPermisoCollectionNewUsuarioPermiso = em.merge(usuarioPermisoCollectionNewUsuarioPermiso);
                    if (oldIdPermisoOfUsuarioPermisoCollectionNewUsuarioPermiso != null && !oldIdPermisoOfUsuarioPermisoCollectionNewUsuarioPermiso.equals(permiso)) {
                        oldIdPermisoOfUsuarioPermisoCollectionNewUsuarioPermiso.getUsuarioPermisoCollection().remove(usuarioPermisoCollectionNewUsuarioPermiso);
                        oldIdPermisoOfUsuarioPermisoCollectionNewUsuarioPermiso = em.merge(oldIdPermisoOfUsuarioPermisoCollectionNewUsuarioPermiso);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = permiso.getIdPermiso();
                if (findPermiso(id) == null) {
                    throw new NonexistentEntityException("The permiso with id " + id + " no longer exists.");
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
            Permiso permiso;
            try {
                permiso = em.getReference(Permiso.class, id);
                permiso.getIdPermiso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The permiso with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<UsuarioPermiso> usuarioPermisoCollectionOrphanCheck = permiso.getUsuarioPermisoCollection();
            for (UsuarioPermiso usuarioPermisoCollectionOrphanCheckUsuarioPermiso : usuarioPermisoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Permiso (" + permiso + ") cannot be destroyed since the UsuarioPermiso " + usuarioPermisoCollectionOrphanCheckUsuarioPermiso + " in its usuarioPermisoCollection field has a non-nullable idPermiso field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(permiso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Permiso> findPermisoEntities() {
        return findPermisoEntities(true, -1, -1);
    }

    public List<Permiso> findPermisoEntities(int maxResults, int firstResult) {
        return findPermisoEntities(false, maxResults, firstResult);
    }

    private List<Permiso> findPermisoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Permiso.class));
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

    public Permiso findPermiso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Permiso.class, id);
        } finally {
            em.close();
        }
    }

    public int getPermisoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Permiso> rt = cq.from(Permiso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
