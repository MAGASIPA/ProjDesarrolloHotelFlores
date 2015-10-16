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
@Table(name = "tipoempleado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipoempleado.findAll", query = "SELECT t FROM Tipoempleado t"),
    @NamedQuery(name = "Tipoempleado.findByIdTipoEmpleado", query = "SELECT t FROM Tipoempleado t WHERE t.idTipoEmpleado = :idTipoEmpleado"),
    @NamedQuery(name = "Tipoempleado.findByCategoria", query = "SELECT t FROM Tipoempleado t WHERE t.categoria = :categoria"),
    @NamedQuery(name = "Tipoempleado.findByDescripcion", query = "SELECT t FROM Tipoempleado t WHERE t.descripcion = :descripcion")})
public class Tipoempleado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTipoEmpleado")
    private Integer idTipoEmpleado;
    @Basic(optional = false)
    @Column(name = "Categoria")
    private String categoria;
    @Column(name = "Descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoEmpleado")
    private Collection<Empleado> empleadoCollection;

    public Tipoempleado() {
    }

    public Tipoempleado(Integer idTipoEmpleado) {
        this.idTipoEmpleado = idTipoEmpleado;
    }

    public Tipoempleado(Integer idTipoEmpleado, String categoria) {
        this.idTipoEmpleado = idTipoEmpleado;
        this.categoria = categoria;
    }

    public Integer getIdTipoEmpleado() {
        return idTipoEmpleado;
    }

    public void setIdTipoEmpleado(Integer idTipoEmpleado) {
        this.idTipoEmpleado = idTipoEmpleado;
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
    public Collection<Empleado> getEmpleadoCollection() {
        return empleadoCollection;
    }

    public void setEmpleadoCollection(Collection<Empleado> empleadoCollection) {
        this.empleadoCollection = empleadoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoEmpleado != null ? idTipoEmpleado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipoempleado)) {
            return false;
        }
        Tipoempleado other = (Tipoempleado) object;
        if ((this.idTipoEmpleado == null && other.idTipoEmpleado != null) || (this.idTipoEmpleado != null && !this.idTipoEmpleado.equals(other.idTipoEmpleado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Tipoempleado[ idTipoEmpleado=" + idTipoEmpleado + " ]";
    }
    
}
