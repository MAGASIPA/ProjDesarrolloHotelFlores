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
@Table(name = "ordenreposicion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ordenreposicion.findAll", query = "SELECT o FROM Ordenreposicion o"),
    @NamedQuery(name = "Ordenreposicion.findByIdOrdenReposicion", query = "SELECT o FROM Ordenreposicion o WHERE o.idOrdenReposicion = :idOrdenReposicion"),
    @NamedQuery(name = "Ordenreposicion.findByFecha", query = "SELECT o FROM Ordenreposicion o WHERE o.fecha = :fecha")})
public class Ordenreposicion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idOrdenReposicion")
    private Integer idOrdenReposicion;
    @Basic(optional = false)
    @Column(name = "Fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idOrdenReposicion")
    private Collection<Detallereposicion> detallereposicionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idOrdenReposicion")
    private Collection<Ordencompra> ordencompraCollection;

    public Ordenreposicion() {
    }

    public Ordenreposicion(Integer idOrdenReposicion) {
        this.idOrdenReposicion = idOrdenReposicion;
    }

    public Ordenreposicion(Integer idOrdenReposicion, Date fecha) {
        this.idOrdenReposicion = idOrdenReposicion;
        this.fecha = fecha;
    }

    public Integer getIdOrdenReposicion() {
        return idOrdenReposicion;
    }

    public void setIdOrdenReposicion(Integer idOrdenReposicion) {
        this.idOrdenReposicion = idOrdenReposicion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @XmlTransient
    public Collection<Detallereposicion> getDetallereposicionCollection() {
        return detallereposicionCollection;
    }

    public void setDetallereposicionCollection(Collection<Detallereposicion> detallereposicionCollection) {
        this.detallereposicionCollection = detallereposicionCollection;
    }

    @XmlTransient
    public Collection<Ordencompra> getOrdencompraCollection() {
        return ordencompraCollection;
    }

    public void setOrdencompraCollection(Collection<Ordencompra> ordencompraCollection) {
        this.ordencompraCollection = ordencompraCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOrdenReposicion != null ? idOrdenReposicion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ordenreposicion)) {
            return false;
        }
        Ordenreposicion other = (Ordenreposicion) object;
        if ((this.idOrdenReposicion == null && other.idOrdenReposicion != null) || (this.idOrdenReposicion != null && !this.idOrdenReposicion.equals(other.idOrdenReposicion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Ordenreposicion[ idOrdenReposicion=" + idOrdenReposicion + " ]";
    }
    
}
