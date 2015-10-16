/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "empleado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empleado.findAll", query = "SELECT e FROM Empleado e"),
    @NamedQuery(name = "Empleado.findByIdPersona", query = "SELECT e FROM Empleado e WHERE e.idPersona = :idPersona"),
    @NamedQuery(name = "Empleado.findBySueldo", query = "SELECT e FROM Empleado e WHERE e.sueldo = :sueldo")})
public class Empleado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idPersona")
    private Integer idPersona;
    @Column(name = "sueldo")
    private Long sueldo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEmpleado")
    private Collection<Usuario> usuarioCollection;
    @JoinColumn(name = "idTipoEmpleado", referencedColumnName = "idTipoEmpleado")
    @ManyToOne(optional = false)
    private Tipoempleado idTipoEmpleado;
    @JoinColumn(name = "idPersona", referencedColumnName = "idPersona", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Persona persona;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEmpleado")
    private Collection<Reservacionalquiler> reservacionalquilerCollection;

    public Empleado() {
    }

    public Empleado(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public Long getSueldo() {
        return sueldo;
    }

    public void setSueldo(Long sueldo) {
        this.sueldo = sueldo;
    }

    @XmlTransient
    public Collection<Usuario> getUsuarioCollection() {
        return usuarioCollection;
    }

    public void setUsuarioCollection(Collection<Usuario> usuarioCollection) {
        this.usuarioCollection = usuarioCollection;
    }

    public Tipoempleado getIdTipoEmpleado() {
        return idTipoEmpleado;
    }

    public void setIdTipoEmpleado(Tipoempleado idTipoEmpleado) {
        this.idTipoEmpleado = idTipoEmpleado;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    @XmlTransient
    public Collection<Reservacionalquiler> getReservacionalquilerCollection() {
        return reservacionalquilerCollection;
    }

    public void setReservacionalquilerCollection(Collection<Reservacionalquiler> reservacionalquilerCollection) {
        this.reservacionalquilerCollection = reservacionalquilerCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPersona != null ? idPersona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empleado)) {
            return false;
        }
        Empleado other = (Empleado) object;
        if ((this.idPersona == null && other.idPersona != null) || (this.idPersona != null && !this.idPersona.equals(other.idPersona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Empleado[ idPersona=" + idPersona + " ]";
    }
    
}
