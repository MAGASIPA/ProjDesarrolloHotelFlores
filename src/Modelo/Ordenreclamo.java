/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "ordenreclamo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ordenreclamo.findAll", query = "SELECT o FROM Ordenreclamo o"),
    @NamedQuery(name = "Ordenreclamo.findByIdOrdenReclamo", query = "SELECT o FROM Ordenreclamo o WHERE o.idOrdenReclamo = :idOrdenReclamo"),
    @NamedQuery(name = "Ordenreclamo.findByFechaReclamo", query = "SELECT o FROM Ordenreclamo o WHERE o.fechaReclamo = :fechaReclamo"),
    @NamedQuery(name = "Ordenreclamo.findByFechaDevolucion", query = "SELECT o FROM Ordenreclamo o WHERE o.fechaDevolucion = :fechaDevolucion")})
public class Ordenreclamo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idOrdenReclamo")
    private Integer idOrdenReclamo;
    @Basic(optional = false)
    @Column(name = "FechaReclamo")
    @Temporal(TemporalType.DATE)
    private Date fechaReclamo;
    @Basic(optional = false)
    @Column(name = "FechaDevolucion")
    @Temporal(TemporalType.DATE)
    private Date fechaDevolucion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idOrdenReclamo")
    private Collection<Detallereclamo> detallereclamoCollection;

    public Ordenreclamo() {
    }

    public Ordenreclamo(Integer idOrdenReclamo) {
        this.idOrdenReclamo = idOrdenReclamo;
    }

    public Ordenreclamo(Integer idOrdenReclamo, Date fechaReclamo, Date fechaDevolucion) {
        this.idOrdenReclamo = idOrdenReclamo;
        this.fechaReclamo = fechaReclamo;
        this.fechaDevolucion = fechaDevolucion;
    }

    public Integer getIdOrdenReclamo() {
        return idOrdenReclamo;
    }

    public void setIdOrdenReclamo(Integer idOrdenReclamo) {
        this.idOrdenReclamo = idOrdenReclamo;
    }

    public Date getFechaReclamo() {
        return fechaReclamo;
    }

    public void setFechaReclamo(Date fechaReclamo) {
        this.fechaReclamo = fechaReclamo;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    @XmlTransient
    public Collection<Detallereclamo> getDetallereclamoCollection() {
        return detallereclamoCollection;
    }

    public void setDetallereclamoCollection(Collection<Detallereclamo> detallereclamoCollection) {
        this.detallereclamoCollection = detallereclamoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOrdenReclamo != null ? idOrdenReclamo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ordenreclamo)) {
            return false;
        }
        Ordenreclamo other = (Ordenreclamo) object;
        if ((this.idOrdenReclamo == null && other.idOrdenReclamo != null) || (this.idOrdenReclamo != null && !this.idOrdenReclamo.equals(other.idOrdenReclamo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Ordenreclamo[ idOrdenReclamo=" + idOrdenReclamo + " ]";
    }
    
}
