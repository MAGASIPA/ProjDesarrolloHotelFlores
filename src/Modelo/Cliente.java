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
@Table(name = "cliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Cliente.findByIdPersona", query = "SELECT c FROM Cliente c WHERE c.idPersona = :idPersona"),
    @NamedQuery(name = "Cliente.findByNacionalidad", query = "SELECT c FROM Cliente c WHERE c.nacionalidad = :nacionalidad"),
    @NamedQuery(name = "Cliente.findByReferencia", query = "SELECT c FROM Cliente c WHERE c.referencia = :referencia")})
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idPersona")
    private Integer idPersona;
    @Column(name = "nacionalidad")
    private String nacionalidad;
    @Column(name = "referencia")
    private String referencia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCliente")
    private Collection<Hojaconsumo> hojaconsumoCollection;
    @JoinColumn(name = "idPersona", referencedColumnName = "idPersona", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Persona persona;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCliente")
    private Collection<Reservacionalquiler> reservacionalquilerCollection;

    public Cliente() {
    }

    public Cliente(String nacionalidad, String referencia) {
        this.nacionalidad = nacionalidad;
        this.referencia = referencia;
    }

    public Cliente(Integer idPersona, String nacionalidad, String referencia, Collection<Hojaconsumo> hojaconsumoCollection, Persona persona, Collection<Reservacionalquiler> reservacionalquilerCollection) {
        this.idPersona = idPersona;
        this.nacionalidad = nacionalidad;
        this.referencia = referencia;
        this.hojaconsumoCollection = hojaconsumoCollection;
        this.persona = persona;
        this.reservacionalquilerCollection = reservacionalquilerCollection;
    }

    public Cliente(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    @XmlTransient
    public Collection<Hojaconsumo> getHojaconsumoCollection() {
        return hojaconsumoCollection;
    }

    public void setHojaconsumoCollection(Collection<Hojaconsumo> hojaconsumoCollection) {
        this.hojaconsumoCollection = hojaconsumoCollection;
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
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.idPersona == null && other.idPersona != null) || (this.idPersona != null && !this.idPersona.equals(other.idPersona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Cliente[ idPersona=" + idPersona + " ]";
    }
    
}
