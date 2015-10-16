/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Control.exceptions.IllegalOrphanException;
import Control.exceptions.NonexistentEntityException;
import Modelo.Tipousuario;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Usuario;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
 */
public class TipousuarioJpaController implements Serializable {

    public TipousuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipousuario tipousuario) {
        if (tipousuario.getUsuarioCollection() == null) {
            tipousuario.setUsuarioCollection(new ArrayList<Usuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Usuario> attachedUsuarioCollection = new ArrayList<Usuario>();
            for (Usuario usuarioCollectionUsuarioToAttach : tipousuario.getUsuarioCollection()) {
                usuarioCollectionUsuarioToAttach = em.getReference(usuarioCollectionUsuarioToAttach.getClass(), usuarioCollectionUsuarioToAttach.getIdUsuario());
                attachedUsuarioCollection.add(usuarioCollectionUsuarioToAttach);
            }
            tipousuario.setUsuarioCollection(attachedUsuarioCollection);
            em.persist(tipousuario);
            for (Usuario usuarioCollectionUsuario : tipousuario.getUsuarioCollection()) {
                Tipousuario oldIdTipoUsuarioOfUsuarioCollectionUsuario = usuarioCollectionUsuario.getIdTipoUsuario();
                usuarioCollectionUsuario.setIdTipoUsuario(tipousuario);
                usuarioCollectionUsuario = em.merge(usuarioCollectionUsuario);
                if (oldIdTipoUsuarioOfUsuarioCollectionUsuario != null) {
                    oldIdTipoUsuarioOfUsuarioCollectionUsuario.getUsuarioCollection().remove(usuarioCollectionUsuario);
                    oldIdTipoUsuarioOfUsuarioCollectionUsuario = em.merge(oldIdTipoUsuarioOfUsuarioCollectionUsuario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipousuario tipousuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipousuario persistentTipousuario = em.find(Tipousuario.class, tipousuario.getIdTipoUsuario());
            Collection<Usuario> usuarioCollectionOld = persistentTipousuario.getUsuarioCollection();
            Collection<Usuario> usuarioCollectionNew = tipousuario.getUsuarioCollection();
            List<String> illegalOrphanMessages = null;
            for (Usuario usuarioCollectionOldUsuario : usuarioCollectionOld) {
                if (!usuarioCollectionNew.contains(usuarioCollectionOldUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuario " + usuarioCollectionOldUsuario + " since its idTipoUsuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Usuario> attachedUsuarioCollectionNew = new ArrayList<Usuario>();
            for (Usuario usuarioCollectionNewUsuarioToAttach : usuarioCollectionNew) {
                usuarioCollectionNewUsuarioToAttach = em.getReference(usuarioCollectionNewUsuarioToAttach.getClass(), usuarioCollectionNewUsuarioToAttach.getIdUsuario());
                attachedUsuarioCollectionNew.add(usuarioCollectionNewUsuarioToAttach);
            }
            usuarioCollectionNew = attachedUsuarioCollectionNew;
            tipousuario.setUsuarioCollection(usuarioCollectionNew);
            tipousuario = em.merge(tipousuario);
            for (Usuario usuarioCollectionNewUsuario : usuarioCollectionNew) {
                if (!usuarioCollectionOld.contains(usuarioCollectionNewUsuario)) {
                    Tipousuario oldIdTipoUsuarioOfUsuarioCollectionNewUsuario = usuarioCollectionNewUsuario.getIdTipoUsuario();
                    usuarioCollectionNewUsuario.setIdTipoUsuario(tipousuario);
                    usuarioCollectionNewUsuario = em.merge(usuarioCollectionNewUsuario);
                    if (oldIdTipoUsuarioOfUsuarioCollectionNewUsuario != null && !oldIdTipoUsuarioOfUsuarioCollectionNewUsuario.equals(tipousuario)) {
                        oldIdTipoUsuarioOfUsuarioCollectionNewUsuario.getUsuarioCollection().remove(usuarioCollectionNewUsuario);
                        oldIdTipoUsuarioOfUsuarioCollectionNewUsuario = em.merge(oldIdTipoUsuarioOfUsuarioCollectionNewUsuario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipousuario.getIdTipoUsuario();
                if (findTipousuario(id) == null) {
                    throw new NonexistentEntityException("The tipousuario with id " + id + " no longer exists.");
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
            Tipousuario tipousuario;
            try {
                tipousuario = em.getReference(Tipousuario.class, id);
                tipousuario.getIdTipoUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipousuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Usuario> usuarioCollectionOrphanCheck = tipousuario.getUsuarioCollection();
            for (Usuario usuarioCollectionOrphanCheckUsuario : usuarioCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tipousuario (" + tipousuario + ") cannot be destroyed since the Usuario " + usuarioCollectionOrphanCheckUsuario + " in its usuarioCollection field has a non-nullable idTipoUsuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipousuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipousuario> findTipousuarioEntities() {
        return findTipousuarioEntities(true, -1, -1);
    }

    public List<Tipousuario> findTipousuarioEntities(int maxResults, int firstResult) {
        return findTipousuarioEntities(false, maxResults, firstResult);
    }

    private List<Tipousuario> findTipousuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipousuario.class));
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

    public Tipousuario findTipousuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipousuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipousuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipousuario> rt = cq.from(Tipousuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
