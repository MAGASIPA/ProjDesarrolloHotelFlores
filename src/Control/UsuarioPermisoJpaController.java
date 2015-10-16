/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Control.exceptions.IllegalOrphanException;
import Control.exceptions.NonexistentEntityException;
import Control.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Usuario;
import Modelo.Permiso;
import Modelo.UsuarioPermiso;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
 */
public class UsuarioPermisoJpaController implements Serializable {

    public UsuarioPermisoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UsuarioPermiso usuarioPermiso) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Usuario usuarioOrphanCheck = usuarioPermiso.getUsuario();
        if (usuarioOrphanCheck != null) {
            UsuarioPermiso oldUsuarioPermisoOfUsuario = usuarioOrphanCheck.getUsuarioPermiso();
            if (oldUsuarioPermisoOfUsuario != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Usuario " + usuarioOrphanCheck + " already has an item of type UsuarioPermiso whose usuario column cannot be null. Please make another selection for the usuario field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario = usuarioPermiso.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getIdUsuario());
                usuarioPermiso.setUsuario(usuario);
            }
            Permiso idPermiso = usuarioPermiso.getIdPermiso();
            if (idPermiso != null) {
                idPermiso = em.getReference(idPermiso.getClass(), idPermiso.getIdPermiso());
                usuarioPermiso.setIdPermiso(idPermiso);
            }
            em.persist(usuarioPermiso);
            if (usuario != null) {
                usuario.setUsuarioPermiso(usuarioPermiso);
                usuario = em.merge(usuario);
            }
            if (idPermiso != null) {
                idPermiso.getUsuarioPermisoCollection().add(usuarioPermiso);
                idPermiso = em.merge(idPermiso);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuarioPermiso(usuarioPermiso.getIdUsuario()) != null) {
                throw new PreexistingEntityException("UsuarioPermiso " + usuarioPermiso + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UsuarioPermiso usuarioPermiso) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UsuarioPermiso persistentUsuarioPermiso = em.find(UsuarioPermiso.class, usuarioPermiso.getIdUsuario());
            Usuario usuarioOld = persistentUsuarioPermiso.getUsuario();
            Usuario usuarioNew = usuarioPermiso.getUsuario();
            Permiso idPermisoOld = persistentUsuarioPermiso.getIdPermiso();
            Permiso idPermisoNew = usuarioPermiso.getIdPermiso();
            List<String> illegalOrphanMessages = null;
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                UsuarioPermiso oldUsuarioPermisoOfUsuario = usuarioNew.getUsuarioPermiso();
                if (oldUsuarioPermisoOfUsuario != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Usuario " + usuarioNew + " already has an item of type UsuarioPermiso whose usuario column cannot be null. Please make another selection for the usuario field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getIdUsuario());
                usuarioPermiso.setUsuario(usuarioNew);
            }
            if (idPermisoNew != null) {
                idPermisoNew = em.getReference(idPermisoNew.getClass(), idPermisoNew.getIdPermiso());
                usuarioPermiso.setIdPermiso(idPermisoNew);
            }
            usuarioPermiso = em.merge(usuarioPermiso);
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.setUsuarioPermiso(null);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.setUsuarioPermiso(usuarioPermiso);
                usuarioNew = em.merge(usuarioNew);
            }
            if (idPermisoOld != null && !idPermisoOld.equals(idPermisoNew)) {
                idPermisoOld.getUsuarioPermisoCollection().remove(usuarioPermiso);
                idPermisoOld = em.merge(idPermisoOld);
            }
            if (idPermisoNew != null && !idPermisoNew.equals(idPermisoOld)) {
                idPermisoNew.getUsuarioPermisoCollection().add(usuarioPermiso);
                idPermisoNew = em.merge(idPermisoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuarioPermiso.getIdUsuario();
                if (findUsuarioPermiso(id) == null) {
                    throw new NonexistentEntityException("The usuarioPermiso with id " + id + " no longer exists.");
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
            UsuarioPermiso usuarioPermiso;
            try {
                usuarioPermiso = em.getReference(UsuarioPermiso.class, id);
                usuarioPermiso.getIdUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarioPermiso with id " + id + " no longer exists.", enfe);
            }
            Usuario usuario = usuarioPermiso.getUsuario();
            if (usuario != null) {
                usuario.setUsuarioPermiso(null);
                usuario = em.merge(usuario);
            }
            Permiso idPermiso = usuarioPermiso.getIdPermiso();
            if (idPermiso != null) {
                idPermiso.getUsuarioPermisoCollection().remove(usuarioPermiso);
                idPermiso = em.merge(idPermiso);
            }
            em.remove(usuarioPermiso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UsuarioPermiso> findUsuarioPermisoEntities() {
        return findUsuarioPermisoEntities(true, -1, -1);
    }

    public List<UsuarioPermiso> findUsuarioPermisoEntities(int maxResults, int firstResult) {
        return findUsuarioPermisoEntities(false, maxResults, firstResult);
    }

    private List<UsuarioPermiso> findUsuarioPermisoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UsuarioPermiso.class));
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

    public UsuarioPermiso findUsuarioPermiso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UsuarioPermiso.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioPermisoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UsuarioPermiso> rt = cq.from(UsuarioPermiso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
