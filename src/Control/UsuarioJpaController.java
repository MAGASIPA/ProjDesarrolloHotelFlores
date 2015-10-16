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
import Modelo.Tipousuario;
import Modelo.Empleado;
import Modelo.Usuario;
import Modelo.UsuarioPermiso;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipousuario idTipoUsuario = usuario.getIdTipoUsuario();
            if (idTipoUsuario != null) {
                idTipoUsuario = em.getReference(idTipoUsuario.getClass(), idTipoUsuario.getIdTipoUsuario());
                usuario.setIdTipoUsuario(idTipoUsuario);
            }
            Empleado idEmpleado = usuario.getIdEmpleado();
            if (idEmpleado != null) {
                idEmpleado = em.getReference(idEmpleado.getClass(), idEmpleado.getIdPersona());
                usuario.setIdEmpleado(idEmpleado);
            }
            UsuarioPermiso usuarioPermiso = usuario.getUsuarioPermiso();
            if (usuarioPermiso != null) {
                usuarioPermiso = em.getReference(usuarioPermiso.getClass(), usuarioPermiso.getIdUsuario());
                usuario.setUsuarioPermiso(usuarioPermiso);
            }
            em.persist(usuario);
            if (idTipoUsuario != null) {
                idTipoUsuario.getUsuarioCollection().add(usuario);
                idTipoUsuario = em.merge(idTipoUsuario);
            }
            if (idEmpleado != null) {
                idEmpleado.getUsuarioCollection().add(usuario);
                idEmpleado = em.merge(idEmpleado);
            }
            if (usuarioPermiso != null) {
                Usuario oldUsuarioOfUsuarioPermiso = usuarioPermiso.getUsuario();
                if (oldUsuarioOfUsuarioPermiso != null) {
                    oldUsuarioOfUsuarioPermiso.setUsuarioPermiso(null);
                    oldUsuarioOfUsuarioPermiso = em.merge(oldUsuarioOfUsuarioPermiso);
                }
                usuarioPermiso.setUsuario(usuario);
                usuarioPermiso = em.merge(usuarioPermiso);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getIdUsuario());
            Tipousuario idTipoUsuarioOld = persistentUsuario.getIdTipoUsuario();
            Tipousuario idTipoUsuarioNew = usuario.getIdTipoUsuario();
            Empleado idEmpleadoOld = persistentUsuario.getIdEmpleado();
            Empleado idEmpleadoNew = usuario.getIdEmpleado();
            UsuarioPermiso usuarioPermisoOld = persistentUsuario.getUsuarioPermiso();
            UsuarioPermiso usuarioPermisoNew = usuario.getUsuarioPermiso();
            List<String> illegalOrphanMessages = null;
            if (usuarioPermisoOld != null && !usuarioPermisoOld.equals(usuarioPermisoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain UsuarioPermiso " + usuarioPermisoOld + " since its usuario field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idTipoUsuarioNew != null) {
                idTipoUsuarioNew = em.getReference(idTipoUsuarioNew.getClass(), idTipoUsuarioNew.getIdTipoUsuario());
                usuario.setIdTipoUsuario(idTipoUsuarioNew);
            }
            if (idEmpleadoNew != null) {
                idEmpleadoNew = em.getReference(idEmpleadoNew.getClass(), idEmpleadoNew.getIdPersona());
                usuario.setIdEmpleado(idEmpleadoNew);
            }
            if (usuarioPermisoNew != null) {
                usuarioPermisoNew = em.getReference(usuarioPermisoNew.getClass(), usuarioPermisoNew.getIdUsuario());
                usuario.setUsuarioPermiso(usuarioPermisoNew);
            }
            usuario = em.merge(usuario);
            if (idTipoUsuarioOld != null && !idTipoUsuarioOld.equals(idTipoUsuarioNew)) {
                idTipoUsuarioOld.getUsuarioCollection().remove(usuario);
                idTipoUsuarioOld = em.merge(idTipoUsuarioOld);
            }
            if (idTipoUsuarioNew != null && !idTipoUsuarioNew.equals(idTipoUsuarioOld)) {
                idTipoUsuarioNew.getUsuarioCollection().add(usuario);
                idTipoUsuarioNew = em.merge(idTipoUsuarioNew);
            }
            if (idEmpleadoOld != null && !idEmpleadoOld.equals(idEmpleadoNew)) {
                idEmpleadoOld.getUsuarioCollection().remove(usuario);
                idEmpleadoOld = em.merge(idEmpleadoOld);
            }
            if (idEmpleadoNew != null && !idEmpleadoNew.equals(idEmpleadoOld)) {
                idEmpleadoNew.getUsuarioCollection().add(usuario);
                idEmpleadoNew = em.merge(idEmpleadoNew);
            }
            if (usuarioPermisoNew != null && !usuarioPermisoNew.equals(usuarioPermisoOld)) {
                Usuario oldUsuarioOfUsuarioPermiso = usuarioPermisoNew.getUsuario();
                if (oldUsuarioOfUsuarioPermiso != null) {
                    oldUsuarioOfUsuarioPermiso.setUsuarioPermiso(null);
                    oldUsuarioOfUsuarioPermiso = em.merge(oldUsuarioOfUsuarioPermiso);
                }
                usuarioPermisoNew.setUsuario(usuario);
                usuarioPermisoNew = em.merge(usuarioPermisoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getIdUsuario();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getIdUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            UsuarioPermiso usuarioPermisoOrphanCheck = usuario.getUsuarioPermiso();
            if (usuarioPermisoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the UsuarioPermiso " + usuarioPermisoOrphanCheck + " in its usuarioPermiso field has a non-nullable usuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Tipousuario idTipoUsuario = usuario.getIdTipoUsuario();
            if (idTipoUsuario != null) {
                idTipoUsuario.getUsuarioCollection().remove(usuario);
                idTipoUsuario = em.merge(idTipoUsuario);
            }
            Empleado idEmpleado = usuario.getIdEmpleado();
            if (idEmpleado != null) {
                idEmpleado.getUsuarioCollection().remove(usuario);
                idEmpleado = em.merge(idEmpleado);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
