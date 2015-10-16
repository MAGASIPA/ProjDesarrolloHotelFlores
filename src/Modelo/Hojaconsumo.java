/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "hojaconsumo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Hojaconsumo.findAll", query = "SELECT h FROM Hojaconsumo h"),
    @NamedQuery(name = "Hojaconsumo.findByIdHojaConsumo", query = "SELECT h FROM Hojaconsumo h WHERE h.idHojaConsumo = :idHojaConsumo"),
    @NamedQuery(name = "Hojaconsumo.findByFecha", query = "SELECT h FROM Hojaconsumo h WHERE h.fecha = :fecha"),
    @NamedQuery(name = "Hojaconsumo.findByCantidad", query = "SELECT h FROM Hojaconsumo h WHERE h.cantidad = :cantidad"),
    @NamedQuery(name = "Hojaconsumo.findByMonto", query = "SELECT h FROM Hojaconsumo h WHERE h.monto = :monto")})
public class Hojaconsumo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idHojaConsumo")
    private Integer idHojaConsumo;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "cantidad")
    private int cantidad;
    @Basic(optional = false)
    @Column(name = "monto")
    private long monto;
    @JoinColumn(name = "idProducto", referencedColumnName = "idProducto")
    @ManyToOne(optional = false)
    private Producto idProducto;
    @JoinColumn(name = "idCliente", referencedColumnName = "idPersona")
    @ManyToOne(optional = false)
    private Cliente idCliente;
    @JoinColumn(name = "idDocumentoAlquiler", referencedColumnName = "idDocumentoAlquiler")
    @ManyToOne(optional = false)
    private Documentoalquiler idDocumentoAlquiler;

    public Hojaconsumo() {
    }

    public Hojaconsumo(Integer idHojaConsumo) {
        this.idHojaConsumo = idHojaConsumo;
    }

    public Hojaconsumo(Integer idHojaConsumo, Date fecha, int cantidad, long monto) {
        this.idHojaConsumo = idHojaConsumo;
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.monto = monto;
    }

    public Integer getIdHojaConsumo() {
        return idHojaConsumo;
    }

    public void setIdHojaConsumo(Integer idHojaConsumo) {
        this.idHojaConsumo = idHojaConsumo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public long getMonto() {
        return monto;
    }

    public void setMonto(long monto) {
        this.monto = monto;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    public Documentoalquiler getIdDocumentoAlquiler() {
        return idDocumentoAlquiler;
    }

    public void setIdDocumentoAlquiler(Documentoalquiler idDocumentoAlquiler) {
        this.idDocumentoAlquiler = idDocumentoAlquiler;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHojaConsumo != null ? idHojaConsumo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Hojaconsumo)) {
            return false;
        }
        Hojaconsumo other = (Hojaconsumo) object;
        if ((this.idHojaConsumo == null && other.idHojaConsumo != null) || (this.idHojaConsumo != null && !this.idHojaConsumo.equals(other.idHojaConsumo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Hojaconsumo[ idHojaConsumo=" + idHojaConsumo + " ]";
    }
    
}
