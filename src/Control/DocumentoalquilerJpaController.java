/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Control.exceptions.IllegalOrphanException;
import Control.exceptions.NonexistentEntityException;
import Control.exceptions.PreexistingEntityException;
import Modelo.Documentoalquiler;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Reservacionalquiler;
import Modelo.Hojaconsumo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
 */
public class DocumentoalquilerJpaController implements Serializable {

    public DocumentoalquilerJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Documentoalquiler documentoalquiler) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (documentoalquiler.getHojaconsumoCollection() == null) {
            documentoalquiler.setHojaconsumoCollection(new ArrayList<Hojaconsumo>());
        }
        List<String> illegalOrphanMessages = null;
        Reservacionalquiler reservacionalquilerOrphanCheck = documentoalquiler.getReservacionalquiler();
        if (reservacionalquilerOrphanCheck != null) {
            Documentoalquiler oldDocumentoalquilerOfReservacionalquiler = reservacionalquilerOrphanCheck.getDocumentoalquiler();
            if (oldDocumentoalquilerOfReservacionalquiler != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Reservacionalquiler " + reservacionalquilerOrphanCheck + " already has an item of type Documentoalquiler whose reservacionalquiler column cannot be null. Please make another selection for the reservacionalquiler field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reservacionalquiler reservacionalquiler = documentoalquiler.getReservacionalquiler();
            if (reservacionalquiler != null) {
                reservacionalquiler = em.getReference(reservacionalquiler.getClass(), reservacionalquiler.getIdReservacionAlquiler());
                documentoalquiler.setReservacionalquiler(reservacionalquiler);
            }
            Collection<Hojaconsumo> attachedHojaconsumoCollection = new ArrayList<Hojaconsumo>();
            for (Hojaconsumo hojaconsumoCollectionHojaconsumoToAttach : documentoalquiler.getHojaconsumoCollection()) {
                hojaconsumoCollectionHojaconsumoToAttach = em.getReference(hojaconsumoCollectionHojaconsumoToAttach.getClass(), hojaconsumoCollectionHojaconsumoToAttach.getIdHojaConsumo());
                attachedHojaconsumoCollection.add(hojaconsumoCollectionHojaconsumoToAttach);
            }
            documentoalquiler.setHojaconsumoCollection(attachedHojaconsumoCollection);
            em.persist(documentoalquiler);
            if (reservacionalquiler != null) {
                reservacionalquiler.setDocumentoalquiler(documentoalquiler);
                reservacionalquiler = em.merge(reservacionalquiler);
            }
            for (Hojaconsumo hojaconsumoCollectionHojaconsumo : documentoalquiler.getHojaconsumoCollection()) {
                Documentoalquiler oldIdDocumentoAlquilerOfHojaconsumoCollectionHojaconsumo = hojaconsumoCollectionHojaconsumo.getIdDocumentoAlquiler();
                hojaconsumoCollectionHojaconsumo.setIdDocumentoAlquiler(documentoalquiler);
                hojaconsumoCollectionHojaconsumo = em.merge(hojaconsumoCollectionHojaconsumo);
                if (oldIdDocumentoAlquilerOfHojaconsumoCollectionHojaconsumo != null) {
                    oldIdDocumentoAlquilerOfHojaconsumoCollectionHojaconsumo.getHojaconsumoCollection().remove(hojaconsumoCollectionHojaconsumo);
                    oldIdDocumentoAlquilerOfHojaconsumoCollectionHojaconsumo = em.merge(oldIdDocumentoAlquilerOfHojaconsumoCollectionHojaconsumo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDocumentoalquiler(documentoalquiler.getIdDocumentoAlquiler()) != null) {
                throw new PreexistingEntityException("Documentoalquiler " + documentoalquiler + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Documentoalquiler documentoalquiler) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Documentoalquiler persistentDocumentoalquiler = em.find(Documentoalquiler.class, documentoalquiler.getIdDocumentoAlquiler());
            Reservacionalquiler reservacionalquilerOld = persistentDocumentoalquiler.getReservacionalquiler();
            Reservacionalquiler reservacionalquilerNew = documentoalquiler.getReservacionalquiler();
            Collection<Hojaconsumo> hojaconsumoCollectionOld = persistentDocumentoalquiler.getHojaconsumoCollection();
            Collection<Hojaconsumo> hojaconsumoCollectionNew = documentoalquiler.getHojaconsumoCollection();
            List<String> illegalOrphanMessages = null;
            if (reservacionalquilerNew != null && !reservacionalquilerNew.equals(reservacionalquilerOld)) {
                Documentoalquiler oldDocumentoalquilerOfReservacionalquiler = reservacionalquilerNew.getDocumentoalquiler();
                if (oldDocumentoalquilerOfReservacionalquiler != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Reservacionalquiler " + reservacionalquilerNew + " already has an item of type Documentoalquiler whose reservacionalquiler column cannot be null. Please make another selection for the reservacionalquiler field.");
                }
            }
            for (Hojaconsumo hojaconsumoCollectionOldHojaconsumo : hojaconsumoCollectionOld) {
                if (!hojaconsumoCollectionNew.contains(hojaconsumoCollectionOldHojaconsumo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Hojaconsumo " + hojaconsumoCollectionOldHojaconsumo + " since its idDocumentoAlquiler field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (reservacionalquilerNew != null) {
                reservacionalquilerNew = em.getReference(reservacionalquilerNew.getClass(), reservacionalquilerNew.getIdReservacionAlquiler());
                documentoalquiler.setReservacionalquiler(reservacionalquilerNew);
            }
            Collection<Hojaconsumo> attachedHojaconsumoCollectionNew = new ArrayList<Hojaconsumo>();
            for (Hojaconsumo hojaconsumoCollectionNewHojaconsumoToAttach : hojaconsumoCollectionNew) {
                hojaconsumoCollectionNewHojaconsumoToAttach = em.getReference(hojaconsumoCollectionNewHojaconsumoToAttach.getClass(), hojaconsumoCollectionNewHojaconsumoToAttach.getIdHojaConsumo());
                attachedHojaconsumoCollectionNew.add(hojaconsumoCollectionNewHojaconsumoToAttach);
            }
            hojaconsumoCollectionNew = attachedHojaconsumoCollectionNew;
            documentoalquiler.setHojaconsumoCollection(hojaconsumoCollectionNew);
            documentoalquiler = em.merge(documentoalquiler);
            if (reservacionalquilerOld != null && !reservacionalquilerOld.equals(reservacionalquilerNew)) {
                reservacionalquilerOld.setDocumentoalquiler(null);
                reservacionalquilerOld = em.merge(reservacionalquilerOld);
            }
            if (reservacionalquilerNew != null && !reservacionalquilerNew.equals(reservacionalquilerOld)) {
                reservacionalquilerNew.setDocumentoalquiler(documentoalquiler);
                reservacionalquilerNew = em.merge(reservacionalquilerNew);
            }
            for (Hojaconsumo hojaconsumoCollectionNewHojaconsumo : hojaconsumoCollectionNew) {
                if (!hojaconsumoCollectionOld.contains(hojaconsumoCollectionNewHojaconsumo)) {
                    Documentoalquiler oldIdDocumentoAlquilerOfHojaconsumoCollectionNewHojaconsumo = hojaconsumoCollectionNewHojaconsumo.getIdDocumentoAlquiler();
                    hojaconsumoCollectionNewHojaconsumo.setIdDocumentoAlquiler(documentoalquiler);
                    hojaconsumoCollectionNewHojaconsumo = em.merge(hojaconsumoCollectionNewHojaconsumo);
                    if (oldIdDocumentoAlquilerOfHojaconsumoCollectionNewHojaconsumo != null && !oldIdDocumentoAlquilerOfHojaconsumoCollectionNewHojaconsumo.equals(documentoalquiler)) {
                        oldIdDocumentoAlquilerOfHojaconsumoCollectionNewHojaconsumo.getHojaconsumoCollection().remove(hojaconsumoCollectionNewHojaconsumo);
                        oldIdDocumentoAlquilerOfHojaconsumoCollectionNewHojaconsumo = em.merge(oldIdDocumentoAlquilerOfHojaconsumoCollectionNewHojaconsumo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = documentoalquiler.getIdDocumentoAlquiler();
                if (findDocumentoalquiler(id) == null) {
                    throw new NonexistentEntityException("The documentoalquiler with id " + id + " no longer exists.");
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
            Documentoalquiler documentoalquiler;
            try {
                documentoalquiler = em.getReference(Documentoalquiler.class, id);
                documentoalquiler.getIdDocumentoAlquiler();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The documentoalquiler with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Hojaconsumo> hojaconsumoCollectionOrphanCheck = documentoalquiler.getHojaconsumoCollection();
            for (Hojaconsumo hojaconsumoCollectionOrphanCheckHojaconsumo : hojaconsumoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Documentoalquiler (" + documentoalquiler + ") cannot be destroyed since the Hojaconsumo " + hojaconsumoCollectionOrphanCheckHojaconsumo + " in its hojaconsumoCollection field has a non-nullable idDocumentoAlquiler field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Reservacionalquiler reservacionalquiler = documentoalquiler.getReservacionalquiler();
            if (reservacionalquiler != null) {
                reservacionalquiler.setDocumentoalquiler(null);
                reservacionalquiler = em.merge(reservacionalquiler);
            }
            em.remove(documentoalquiler);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Documentoalquiler> findDocumentoalquilerEntities() {
        return findDocumentoalquilerEntities(true, -1, -1);
    }

    public List<Documentoalquiler> findDocumentoalquilerEntities(int maxResults, int firstResult) {
        return findDocumentoalquilerEntities(false, maxResults, firstResult);
    }

    private List<Documentoalquiler> findDocumentoalquilerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Documentoalquiler.class));
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

    public Documentoalquiler findDocumentoalquiler(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Documentoalquiler.class, id);
        } finally {
            em.close();
        }
    }

    public int getDocumentoalquilerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Documentoalquiler> rt = cq.from(Documentoalquiler.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
