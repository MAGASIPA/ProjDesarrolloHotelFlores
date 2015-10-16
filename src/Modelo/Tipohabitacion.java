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
@Table(name = "tipohabitacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipohabitacion.findAll", query = "SELECT t FROM Tipohabitacion t"),
    @NamedQuery(name = "Tipohabitacion.findByIdTipoHabitacion", query = "SELECT t FROM Tipohabitacion t WHERE t.idTipoHabitacion = :idTipoHabitacion"),
    @NamedQuery(name = "Tipohabitacion.findByCategoria", query = "SELECT t FROM Tipohabitacion t WHERE t.categoria = :categoria"),
    @NamedQuery(name = "Tipohabitacion.findByDescripcion", query = "SELECT t FROM Tipohabitacion t WHERE t.descripcion = :descripcion")})
public class Tipohabitacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTipoHabitacion")
    private Integer idTipoHabitacion;
    @Basic(optional = false)
    @Column(name = "Categoria")
    private String categoria;
    @Column(name = "Descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoHabitacion")
    private Collection<Habitacion> habitacionCollection;

    public Tipohabitacion() {
    }

    public Tipohabitacion(Integer idTipoHabitacion) {
        this.idTipoHabitacion = idTipoHabitacion;
    }

    public Tipohabitacion(Integer idTipoHabitacion, String categoria) {
        this.idTipoHabitacion = idTipoHabitacion;
        this.categoria = categoria;
    }

    public Integer getIdTipoHabitacion() {
        return idTipoHabitacion;
    }

    public void setIdTipoHabitacion(Integer idTipoHabitacion) {
        this.idTipoHabitacion = idTipoHabitacion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public Collection<Habitacion> getHabitacionCollection() {
        return habitacionCollection;
    }

    public void setHabitacionCollection(Collection<Habitacion> habitacionCollection) {
        this.habitacionCollection = habitacionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoHabitacion != null ? idTipoHabitacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipohabitacion)) {
            return false;
        }
        Tipohabitacion other = (Tipohabitacion) object;
        if ((this.idTipoHabitacion == null && other.idTipoHabitacion != null) || (this.idTipoHabitacion != null && !this.idTipoHabitacion.equals(other.idTipoHabitacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Tipohabitacion[ idTipoHabitacion=" + idTipoHabitacion + " ]";
    }
    
}
