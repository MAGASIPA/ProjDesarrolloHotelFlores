/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "detallereposicion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Detallereposicion.findAll", query = "SELECT d FROM Detallereposicion d"),
    @NamedQuery(name = "Detallereposicion.findByIdDetalleReposicion", query = "SELECT d FROM Detallereposicion d WHERE d.idDetalleReposicion = :idDetalleReposicion"),
    @NamedQuery(name = "Detallereposicion.findByCantidad", query = "SELECT d FROM Detallereposicion d WHERE d.cantidad = :cantidad")})
public class Detallereposicion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDetalleReposicion")
    private Integer idDetalleReposicion;
    @Basic(optional = false)
    @Column(name = "cantidad")
    private int cantidad;
    @JoinColumn(name = "idProducto", referencedColumnName = "idProducto")
    @ManyToOne(optional = false)
    private Producto idProducto;
    @JoinColumn(name = "idOrdenReposicion", referencedColumnName = "idOrdenReposicion")
    @ManyToOne(optional = false)
    private Ordenreposicion idOrdenReposicion;

    public Detallereposicion() {
    }

    public Detallereposicion(Integer idDetalleReposicion) {
        this.idDetalleReposicion = idDetalleReposicion;
    }

    public Detallereposicion(Integer idDetalleReposicion, int cantidad) {
        this.idDetalleReposicion = idDetalleReposicion;
        this.cantidad = cantidad;
    }

    public Integer getIdDetalleReposicion() {
        return idDetalleReposicion;
    }

    public void setIdDetalleReposicion(Integer idDetalleReposicion) {
        this.idDetalleReposicion = idDetalleReposicion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

    public Ordenreposicion getIdOrdenReposicion() {
        return idOrdenReposicion;
    }

    public void setIdOrdenReposicion(Ordenreposicion idOrdenReposicion) {
        this.idOrdenReposicion = idOrdenReposicion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalleReposicion != null ? idDetalleReposicion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Detallereposicion)) {
            return false;
        }
        Detallereposicion other = (Detallereposicion) object;
        if ((this.idDetalleReposicion == null && other.idDetalleReposicion != null) || (this.idDetalleReposicion != null && !this.idDetalleReposicion.equals(other.idDetalleReposicion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Detallereposicion[ idDetalleReposicion=" + idDetalleReposicion + " ]";
    }
    
}
