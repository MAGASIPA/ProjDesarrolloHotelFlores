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
import Modelo.Empleado;
import Modelo.Tipoempleado;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
 */
public class TipoempleadoJpaController implements Serializable {

    public TipoempleadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipoempleado tipoempleado) {
        if (tipoempleado.getEmpleadoCollection() == null) {
            tipoempleado.setEmpleadoCollection(new ArrayList<Empleado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Empleado> attachedEmpleadoCollection = new ArrayList<Empleado>();
            for (Empleado empleadoCollectionEmpleadoToAttach : tipoempleado.getEmpleadoCollection()) {
                empleadoCollectionEmpleadoToAttach = em.getReference(empleadoCollectionEmpleadoToAttach.getClass(), empleadoCollectionEmpleadoToAttach.getIdPersona());
                attachedEmpleadoCollection.add(empleadoCollectionEmpleadoToAttach);
            }
            tipoempleado.setEmpleadoCollection(attachedEmpleadoCollection);
            em.persist(tipoempleado);
            for (Empleado empleadoCollectionEmpleado : tipoempleado.getEmpleadoCollection()) {
                Tipoempleado oldIdTipoEmpleadoOfEmpleadoCollectionEmpleado = empleadoCollectionEmpleado.getIdTipoEmpleado();
                empleadoCollectionEmpleado.setIdTipoEmpleado(tipoempleado);
                empleadoCollectionEmpleado = em.merge(empleadoCollectionEmpleado);
                if (oldIdTipoEmpleadoOfEmpleadoCollectionEmpleado != null) {
                    oldIdTipoEmpleadoOfEmpleadoCollectionEmpleado.getEmpleadoCollection().remove(empleadoCollectionEmpleado);
                    oldIdTipoEmpleadoOfEmpleadoCollectionEmpleado = em.merge(oldIdTipoEmpleadoOfEmpleadoCollectionEmpleado);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipoempleado tipoempleado) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipoempleado persistentTipoempleado = em.find(Tipoempleado.class, tipoempleado.getIdTipoEmpleado());
            Collection<Empleado> empleadoCollectionOld = persistentTipoempleado.getEmpleadoCollection();
            Collection<Empleado> empleadoCollectionNew = tipoempleado.getEmpleadoCollection();
            List<String> illegalOrphanMessages = null;
            for (Empleado empleadoCollectionOldEmpleado : empleadoCollectionOld) {
                if (!empleadoCollectionNew.contains(empleadoCollectionOldEmpleado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Empleado " + empleadoCollectionOldEmpleado + " since its idTipoEmpleado field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Empleado> attachedEmpleadoCollectionNew = new ArrayList<Empleado>();
            for (Empleado empleadoCollectionNewEmpleadoToAttach : empleadoCollectionNew) {
                empleadoCollectionNewEmpleadoToAttach = em.getReference(empleadoCollectionNewEmpleadoToAttach.getClass(), empleadoCollectionNewEmpleadoToAttach.getIdPersona());
                attachedEmpleadoCollectionNew.add(empleadoCollectionNewEmpleadoToAttach);
            }
            empleadoCollectionNew = attachedEmpleadoCollectionNew;
            tipoempleado.setEmpleadoCollection(empleadoCollectionNew);
            tipoempleado = em.merge(tipoempleado);
            for (Empleado empleadoCollectionNewEmpleado : empleadoCollectionNew) {
                if (!empleadoCollectionOld.contains(empleadoCollectionNewEmpleado)) {
                    Tipoempleado oldIdTipoEmpleadoOfEmpleadoCollectionNewEmpleado = empleadoCollectionNewEmpleado.getIdTipoEmpleado();
                    empleadoCollectionNewEmpleado.setIdTipoEmpleado(tipoempleado);
                    empleadoCollectionNewEmpleado = em.merge(empleadoCollectionNewEmpleado);
                    if (oldIdTipoEmpleadoOfEmpleadoCollectionNewEmpleado != null && !oldIdTipoEmpleadoOfEmpleadoCollectionNewEmpleado.equals(tipoempleado)) {
                        oldIdTipoEmpleadoOfEmpleadoCollectionNewEmpleado.getEmpleadoCollection().remove(empleadoCollectionNewEmpleado);
                        oldIdTipoEmpleadoOfEmpleadoCollectionNewEmpleado = em.merge(oldIdTipoEmpleadoOfEmpleadoCollectionNewEmpleado);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoempleado.getIdTipoEmpleado();
                if (findTipoempleado(id) == null) {
                    throw new NonexistentEntityException("The tipoempleado with id " + id + " no longer exists.");
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
            Tipoempleado tipoempleado;
            try {
                tipoempleado = em.getReference(Tipoempleado.class, id);
                tipoempleado.getIdTipoEmpleado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoempleado with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Empleado> empleadoCollectionOrphanCheck = tipoempleado.getEmpleadoCollection();
            for (Empleado empleadoCollectionOrphanCheckEmpleado : empleadoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tipoempleado (" + tipoempleado + ") cannot be destroyed since the Empleado " + empleadoCollectionOrphanCheckEmpleado + " in its empleadoCollection field has a non-nullable idTipoEmpleado field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoempleado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipoempleado> findTipoempleadoEntities() {
        return findTipoempleadoEntities(true, -1, -1);
    }

    public List<Tipoempleado> findTipoempleadoEntities(int maxResults, int firstResult) {
        return findTipoempleadoEntities(false, maxResults, firstResult);
    }

    private List<Tipoempleado> findTipoempleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipoempleado.class));
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

    public Tipoempleado findTipoempleado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipoempleado.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoempleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipoempleado> rt = cq.from(Tipoempleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
