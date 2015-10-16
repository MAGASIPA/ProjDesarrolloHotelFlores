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
@Table(name = "detallereclamo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Detallereclamo.findAll", query = "SELECT d FROM Detallereclamo d"),
    @NamedQuery(name = "Detallereclamo.findByIdDetalleReclamo", query = "SELECT d FROM Detallereclamo d WHERE d.idDetalleReclamo = :idDetalleReclamo"),
    @NamedQuery(name = "Detallereclamo.findByCantidad", query = "SELECT d FROM Detallereclamo d WHERE d.cantidad = :cantidad")})
public class Detallereclamo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDetalleReclamo")
    private Integer idDetalleReclamo;
    @Basic(optional = false)
    @Column(name = "Cantidad")
    private int cantidad;
    @JoinColumn(name = "idProducto", referencedColumnName = "idProducto")
    @ManyToOne(optional = false)
    private Producto idProducto;
    @JoinColumn(name = "idOrdenReclamo", referencedColumnName = "idOrdenReclamo")
    @ManyToOne(optional = false)
    private Ordenreclamo idOrdenReclamo;

    public Detallereclamo() {
    }

    public Detallereclamo(Integer idDetalleReclamo) {
        this.idDetalleReclamo = idDetalleReclamo;
    }

    public Detallereclamo(Integer idDetalleReclamo, int cantidad) {
        this.idDetalleReclamo = idDetalleReclamo;
        this.cantidad = cantidad;
    }

    public Integer getIdDetalleReclamo() {
        return idDetalleReclamo;
    }

    public void setIdDetalleReclamo(Integer idDetalleReclamo) {
        this.idDetalleReclamo = idDetalleReclamo;
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

    public Ordenreclamo getIdOrdenReclamo() {
        return idOrdenReclamo;
    }

    public void setIdOrdenReclamo(Ordenreclamo idOrdenReclamo) {
        this.idOrdenReclamo = idOrdenReclamo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalleReclamo != null ? idDetalleReclamo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Detallereclamo)) {
            return false;
        }
        Detallereclamo other = (Detallereclamo) object;
        if ((this.idDetalleReclamo == null && other.idDetalleReclamo != null) || (this.idDetalleReclamo != null && !this.idDetalleReclamo.equals(other.idDetalleReclamo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Detallereclamo[ idDetalleReclamo=" + idDetalleReclamo + " ]";
    }
    
}
