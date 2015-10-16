/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Control.exceptions.IllegalOrphanException;
import Control.exceptions.NonexistentEntityException;
import Modelo.Artefacto;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Asignacion;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
 */
public class ArtefactoJpaController implements Serializable {

    public ArtefactoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Artefacto artefacto) {
        if (artefacto.getAsignacionCollection() == null) {
            artefacto.setAsignacionCollection(new ArrayList<Asignacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Asignacion> attachedAsignacionCollection = new ArrayList<Asignacion>();
            for (Asignacion asignacionCollectionAsignacionToAttach : artefacto.getAsignacionCollection()) {
                asignacionCollectionAsignacionToAttach = em.getReference(asignacionCollectionAsignacionToAttach.getClass(), asignacionCollectionAsignacionToAttach.getIdAsignacion());
                attachedAsignacionCollection.add(asignacionCollectionAsignacionToAttach);
            }
            artefacto.setAsignacionCollection(attachedAsignacionCollection);
            em.persist(artefacto);
            for (Asignacion asignacionCollectionAsignacion : artefacto.getAsignacionCollection()) {
                Artefacto oldIdArtefactoOfAsignacionCollectionAsignacion = asignacionCollectionAsignacion.getIdArtefacto();
                asignacionCollectionAsignacion.setIdArtefacto(artefacto);
                asignacionCollectionAsignacion = em.merge(asignacionCollectionAsignacion);
                if (oldIdArtefactoOfAsignacionCollectionAsignacion != null) {
                    oldIdArtefactoOfAsignacionCollectionAsignacion.getAsignacionCollection().remove(asignacionCollectionAsignacion);
                    oldIdArtefactoOfAsignacionCollectionAsignacion = em.merge(oldIdArtefactoOfAsignacionCollectionAsignacion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Artefacto artefacto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Artefacto persistentArtefacto = em.find(Artefacto.class, artefacto.getIdArtefacto());
            Collection<Asignacion> asignacionCollectionOld = persistentArtefacto.getAsignacionCollection();
            Collection<Asignacion> asignacionCollectionNew = artefacto.getAsignacionCollection();
            List<String> illegalOrphanMessages = null;
            for (Asignacion asignacionCollectionOldAsignacion : asignacionCollectionOld) {
                if (!asignacionCollectionNew.contains(asignacionCollectionOldAsignacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Asignacion " + asignacionCollectionOldAsignacion + " since its idArtefacto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Asignacion> attachedAsignacionCollectionNew = new ArrayList<Asignacion>();
            for (Asignacion asignacionCollectionNewAsignacionToAttach : asignacionCollectionNew) {
                asignacionCollectionNewAsignacionToAttach = em.getReference(asignacionCollectionNewAsignacionToAttach.getClass(), asignacionCollectionNewAsignacionToAttach.getIdAsignacion());
                attachedAsignacionCollectionNew.add(asignacionCollectionNewAsignacionToAttach);
            }
            asignacionCollectionNew = attachedAsignacionCollectionNew;
            artefacto.setAsignacionCollection(asignacionCollectionNew);
            artefacto = em.merge(artefacto);
            for (Asignacion asignacionCollectionNewAsignacion : asignacionCollectionNew) {
                if (!asignacionCollectionOld.contains(asignacionCollectionNewAsignacion)) {
                    Artefacto oldIdArtefactoOfAsignacionCollectionNewAsignacion = asignacionCollectionNewAsignacion.getIdArtefacto();
                    asignacionCollectionNewAsignacion.setIdArtefacto(artefacto);
                    asignacionCollectionNewAsignacion = em.merge(asignacionCollectionNewAsignacion);
                    if (oldIdArtefactoOfAsignacionCollectionNewAsignacion != null && !oldIdArtefactoOfAsignacionCollectionNewAsignacion.equals(artefacto)) {
                        oldIdArtefactoOfAsignacionCollectionNewAsignacion.getAsignacionCollection().remove(asignacionCollectionNewAsignacion);
                        oldIdArtefactoOfAsignacionCollectionNewAsignacion = em.merge(oldIdArtefactoOfAsignacionCollectionNewAsignacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = artefacto.getIdArtefacto();
                if (findArtefacto(id) == null) {
                    throw new NonexistentEntityException("The artefacto with id " + id + " no longer exists.");
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
            Artefacto artefacto;
            try {
                artefacto = em.getReference(Artefacto.class, id);
                artefacto.getIdArtefacto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The artefacto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Asignacion> asignacionCollectionOrphanCheck = artefacto.getAsignacionCollection();
            for (Asignacion asignacionCollectionOrphanCheckAsignacion : asignacionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Artefacto (" + artefacto + ") cannot be destroyed since the Asignacion " + asignacionCollectionOrphanCheckAsignacion + " in its asignacionCollection field has a non-nullable idArtefacto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(artefacto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Artefacto> findArtefactoEntities() {
        return findArtefactoEntities(true, -1, -1);
    }

    public List<Artefacto> findArtefactoEntities(int maxResults, int firstResult) {
        return findArtefactoEntities(false, maxResults, firstResult);
    }

    private List<Artefacto> findArtefactoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Artefacto.class));
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

    public Artefacto findArtefacto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Artefacto.class, id);
        } finally {
            em.close();
        }
    }

    public int getArtefactoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Artefacto> rt = cq.from(Artefacto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
