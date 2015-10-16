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
import Modelo.Cliente;
import Modelo.Persona;
import Modelo.Tipodocumento;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
 */
public class PersonaJpaController implements Serializable {

    public PersonaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Persona persona) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado empleado = persona.getEmpleado();
            if (empleado != null) {
                empleado = em.getReference(empleado.getClass(), empleado.getIdPersona());
                persona.setEmpleado(empleado);
            }
            Cliente cliente = persona.getCliente();
            if (cliente != null) {
                cliente = em.getReference(cliente.getClass(), cliente.getIdPersona());
                persona.setCliente(cliente);
            }
            Tipodocumento idTipoDoc = persona.getIdTipoDoc();
            if (idTipoDoc != null) {
                idTipoDoc = em.getReference(idTipoDoc.getClass(), idTipoDoc.getIdTipoDoc());
                persona.setIdTipoDoc(idTipoDoc);
            }
            em.persist(persona);
            if (empleado != null) {
                Persona oldPersonaOfEmpleado = empleado.getPersona();
                if (oldPersonaOfEmpleado != null) {
                    oldPersonaOfEmpleado.setEmpleado(null);
                    oldPersonaOfEmpleado = em.merge(oldPersonaOfEmpleado);
                }
                empleado.setPersona(persona);
                empleado = em.merge(empleado);
            }
            if (cliente != null) {
                Persona oldPersonaOfCliente = cliente.getPersona();
                if (oldPersonaOfCliente != null) {
                    oldPersonaOfCliente.setCliente(null);
                    oldPersonaOfCliente = em.merge(oldPersonaOfCliente);
                }
                cliente.setPersona(persona);
                cliente = em.merge(cliente);
            }
            if (idTipoDoc != null) {
                idTipoDoc.getPersonaCollection().add(persona);
                idTipoDoc = em.merge(idTipoDoc);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Persona persona) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persistentPersona = em.find(Persona.class, persona.getIdPersona());
            Empleado empleadoOld = persistentPersona.getEmpleado();
            Empleado empleadoNew = persona.getEmpleado();
            Cliente clienteOld = persistentPersona.getCliente();
            Cliente clienteNew = persona.getCliente();
            Tipodocumento idTipoDocOld = persistentPersona.getIdTipoDoc();
            Tipodocumento idTipoDocNew = persona.getIdTipoDoc();
            List<String> illegalOrphanMessages = null;
            if (empleadoOld != null && !empleadoOld.equals(empleadoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Empleado " + empleadoOld + " since its persona field is not nullable.");
            }
            if (clienteOld != null && !clienteOld.equals(clienteNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Cliente " + clienteOld + " since its persona field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (empleadoNew != null) {
                empleadoNew = em.getReference(empleadoNew.getClass(), empleadoNew.getIdPersona());
                persona.setEmpleado(empleadoNew);
            }
            if (clienteNew != null) {
                clienteNew = em.getReference(clienteNew.getClass(), clienteNew.getIdPersona());
                persona.setCliente(clienteNew);
            }
            if (idTipoDocNew != null) {
                idTipoDocNew = em.getReference(idTipoDocNew.getClass(), idTipoDocNew.getIdTipoDoc());
                persona.setIdTipoDoc(idTipoDocNew);
            }
            persona = em.merge(persona);
            if (empleadoNew != null && !empleadoNew.equals(empleadoOld)) {
                Persona oldPersonaOfEmpleado = empleadoNew.getPersona();
                if (oldPersonaOfEmpleado != null) {
                    oldPersonaOfEmpleado.setEmpleado(null);
                    oldPersonaOfEmpleado = em.merge(oldPersonaOfEmpleado);
                }
                empleadoNew.setPersona(persona);
                empleadoNew = em.merge(empleadoNew);
            }
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                Persona oldPersonaOfCliente = clienteNew.getPersona();
                if (oldPersonaOfCliente != null) {
                    oldPersonaOfCliente.setCliente(null);
                    oldPersonaOfCliente = em.merge(oldPersonaOfCliente);
                }
                clienteNew.setPersona(persona);
                clienteNew = em.merge(clienteNew);
            }
            if (idTipoDocOld != null && !idTipoDocOld.equals(idTipoDocNew)) {
                idTipoDocOld.getPersonaCollection().remove(persona);
                idTipoDocOld = em.merge(idTipoDocOld);
            }
            if (idTipoDocNew != null && !idTipoDocNew.equals(idTipoDocOld)) {
                idTipoDocNew.getPersonaCollection().add(persona);
                idTipoDocNew = em.merge(idTipoDocNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = persona.getIdPersona();
                if (findPersona(id) == null) {
                    throw new NonexistentEntityException("The persona with id " + id + " no longer exists.");
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
            Persona persona;
            try {
                persona = em.getReference(Persona.class, id);
                persona.getIdPersona();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The persona with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Empleado empleadoOrphanCheck = persona.getEmpleado();
            if (empleadoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the Empleado " + empleadoOrphanCheck + " in its empleado field has a non-nullable persona field.");
            }
            Cliente clienteOrphanCheck = persona.getCliente();
            if (clienteOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the Cliente " + clienteOrphanCheck + " in its cliente field has a non-nullable persona field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Tipodocumento idTipoDoc = persona.getIdTipoDoc();
            if (idTipoDoc != null) {
                idTipoDoc.getPersonaCollection().remove(persona);
                idTipoDoc = em.merge(idTipoDoc);
            }
            em.remove(persona);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Persona> findPersonaEntities() {
        return findPersonaEntities(true, -1, -1);
    }

    public List<Persona> findPersonaEntities(int maxResults, int firstResult) {
        return findPersonaEntities(false, maxResults, firstResult);
    }

    private List<Persona> findPersonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Persona.class));
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

    public Persona findPersona(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Persona.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Persona> rt = cq.from(Persona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
