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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "artefacto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Artefacto.findAll", query = "SELECT a FROM Artefacto a"),
    @NamedQuery(name = "Artefacto.findByIdArtefacto", query = "SELECT a FROM Artefacto a WHERE a.idArtefacto = :idArtefacto"),
    @NamedQuery(name = "Artefacto.findBySerie", query = "SELECT a FROM Artefacto a WHERE a.serie = :serie"),
    @NamedQuery(name = "Artefacto.findByDetalle", query = "SELECT a FROM Artefacto a WHERE a.detalle = :detalle"),
    @NamedQuery(name = "Artefacto.findByEstado", query = "SELECT a FROM Artefacto a WHERE a.estado = :estado")})
public class Artefacto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idArtefacto")
    private Integer idArtefacto;
    @Column(name = "serie")
    private Integer serie;
    @Column(name = "detalle")
    private String detalle;
    @Column(name = "estado")
    private Integer estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idArtefacto")
    private Collection<Asignacion> asignacionCollection;

    public Artefacto() {
    }

    public Artefacto(Integer idArtefacto) {
        this.idArtefacto = idArtefacto;
    }

    public Integer getIdArtefacto() {
        return idArtefacto;
    }

    public void setIdArtefacto(Integer idArtefacto) {
        this.idArtefacto = idArtefacto;
    }

    public Integer getSerie() {
        return serie;
    }

    public void setSerie(Integer serie) {
        this.serie = serie;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    @XmlTransient
    public Collection<Asignacion> getAsignacionCollection() {
        return asignacionCollection;
    }

    public void setAsignacionCollection(Collection<Asignacion> asignacionCollection) {
        this.asignacionCollection = asignacionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idArtefacto != null ? idArtefacto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Artefacto)) {
            return false;
        }
        Artefacto other = (Artefacto) object;
        if ((this.idArtefacto == null && other.idArtefacto != null) || (this.idArtefacto != null && !this.idArtefacto.equals(other.idArtefacto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Artefacto[ idArtefacto=" + idArtefacto + " ]";
    }
    
}
