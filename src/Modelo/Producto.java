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
@Table(name = "producto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p"),
    @NamedQuery(name = "Producto.findByIdProducto", query = "SELECT p FROM Producto p WHERE p.idProducto = :idProducto"),
    @NamedQuery(name = "Producto.findByNombreProducto", query = "SELECT p FROM Producto p WHERE p.nombreProducto = :nombreProducto"),
    @NamedQuery(name = "Producto.findByPrecio", query = "SELECT p FROM Producto p WHERE p.precio = :precio"),
    @NamedQuery(name = "Producto.findByCantidad", query = "SELECT p FROM Producto p WHERE p.cantidad = :cantidad"),
    @NamedQuery(name = "Producto.findByVigencia", query = "SELECT p FROM Producto p WHERE p.vigencia = :vigencia")})
public class Producto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idProducto")
    private Integer idProducto;
    @Basic(optional = false)
    @Column(name = "NombreProducto")
    private String nombreProducto;
    @Basic(optional = false)
    @Column(name = "Precio")
    private long precio;
    @Basic(optional = false)
    @Column(name = "Cantidad")
    private int cantidad;
    @Column(name = "Vigencia")
    @Temporal(TemporalType.DATE)
    private Date vigencia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProducto")
    private Collection<Kardex> kardexCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProducto")
    private Collection<Hojaconsumo> hojaconsumoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProducto")
    private Collection<Detallereclamo> detallereclamoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProducto")
    private Collection<Detallereposicion> detallereposicionCollection;

    public Producto() {
    }

    public Producto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public Producto(Integer idProducto, String nombreProducto, long precio, int cantidad) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public long getPrecio() {
        return precio;
    }

    public void setPrecio(long precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Date getVigencia() {
        return vigencia;
    }

    public void setVigencia(Date vigencia) {
        this.vigencia = vigencia;
    }

    @XmlTransient
    public Collection<Kardex> getKardexCollection() {
        return kardexCollection;
    }

    public void setKardexCollection(Collection<Kardex> kardexCollection) {
        this.kardexCollection = kardexCollection;
    }

    @XmlTransient
    public Collection<Hojaconsumo> getHojaconsumoCollection() {
        return hojaconsumoCollection;
    }

    public void setHojaconsumoCollection(Collection<Hojaconsumo> hojaconsumoCollection) {
        this.hojaconsumoCollection = hojaconsumoCollection;
    }

    @XmlTransient
    public Collection<Detallereclamo> getDetallereclamoCollection() {
        return detallereclamoCollection;
    }

    public void setDetallereclamoCollection(Collection<Detallereclamo> detallereclamoCollection) {
        this.detallereclamoCollection = detallereclamoCollection;
    }

    @XmlTransient
    public Collection<Detallereposicion> getDetallereposicionCollection() {
        return detallereposicionCollection;
    }

    public void setDetallereposicionCollection(Collection<Detallereposicion> detallereposicionCollection) {
        this.detallereposicionCollection = detallereposicionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProducto != null ? idProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.idProducto == null && other.idProducto != null) || (this.idProducto != null && !this.idProducto.equals(other.idProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Producto[ idProducto=" + idProducto + " ]";
    }
    
}
