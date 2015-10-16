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
@Table(name = "ordencompra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ordencompra.findAll", query = "SELECT o FROM Ordencompra o"),
    @NamedQuery(name = "Ordencompra.findByIdOrdenCompra", query = "SELECT o FROM Ordencompra o WHERE o.idOrdenCompra = :idOrdenCompra"),
    @NamedQuery(name = "Ordencompra.findByNumeroDocuCompra", query = "SELECT o FROM Ordencompra o WHERE o.numeroDocuCompra = :numeroDocuCompra"),
    @NamedQuery(name = "Ordencompra.findByFechaIngreso", query = "SELECT o FROM Ordencompra o WHERE o.fechaIngreso = :fechaIngreso"),
    @NamedQuery(name = "Ordencompra.findByFechaCompra", query = "SELECT o FROM Ordencompra o WHERE o.fechaCompra = :fechaCompra"),
    @NamedQuery(name = "Ordencompra.findByPrecioUnitario", query = "SELECT o FROM Ordencompra o WHERE o.precioUnitario = :precioUnitario"),
    @NamedQuery(name = "Ordencompra.findByCantidad", query = "SELECT o FROM Ordencompra o WHERE o.cantidad = :cantidad"),
    @NamedQuery(name = "Ordencompra.findByDescuento", query = "SELECT o FROM Ordencompra o WHERE o.descuento = :descuento"),
    @NamedQuery(name = "Ordencompra.findBySubtotal", query = "SELECT o FROM Ordencompra o WHERE o.subtotal = :subtotal"),
    @NamedQuery(name = "Ordencompra.findByTotal", query = "SELECT o FROM Ordencompra o WHERE o.total = :total"),
    @NamedQuery(name = "Ordencompra.findByFormaPago", query = "SELECT o FROM Ordencompra o WHERE o.formaPago = :formaPago"),
    @NamedQuery(name = "Ordencompra.findByMoneda", query = "SELECT o FROM Ordencompra o WHERE o.moneda = :moneda")})
public class Ordencompra implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idOrdenCompra")
    private Integer idOrdenCompra;
    @Basic(optional = false)
    @Column(name = "numeroDocuCompra")
    private String numeroDocuCompra;
    @Basic(optional = false)
    @Column(name = "FechaIngreso")
    @Temporal(TemporalType.DATE)
    private Date fechaIngreso;
    @Basic(optional = false)
    @Column(name = "FechaCompra")
    @Temporal(TemporalType.DATE)
    private Date fechaCompra;
    @Basic(optional = false)
    @Column(name = "PrecioUnitario")
    private long precioUnitario;
    @Basic(optional = false)
    @Column(name = "cantidad")
    private int cantidad;
    @Column(name = "Descuento")
    private Long descuento;
    @Basic(optional = false)
    @Column(name = "Subtotal")
    private long subtotal;
    @Basic(optional = false)
    @Column(name = "Total")
    private long total;
    @Basic(optional = false)
    @Column(name = "formaPago")
    private String formaPago;
    @Basic(optional = false)
    @Column(name = "moneda")
    private String moneda;
    @JoinColumn(name = "idOrdenReposicion", referencedColumnName = "idOrdenReposicion")
    @ManyToOne(optional = false)
    private Ordenreposicion idOrdenReposicion;
    @JoinColumn(name = "idProveedor", referencedColumnName = "idProveedor")
    @ManyToOne(optional = false)
    private Proveedor idProveedor;

    public Ordencompra() {
    }

    public Ordencompra(Integer idOrdenCompra) {
        this.idOrdenCompra = idOrdenCompra;
    }

    public Ordencompra(Integer idOrdenCompra, String numeroDocuCompra, Date fechaIngreso, Date fechaCompra, long precioUnitario, int cantidad, long subtotal, long total, String formaPago, String moneda) {
        this.idOrdenCompra = idOrdenCompra;
        this.numeroDocuCompra = numeroDocuCompra;
        this.fechaIngreso = fechaIngreso;
        this.fechaCompra = fechaCompra;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.total = total;
        this.formaPago = formaPago;
        this.moneda = moneda;
    }

    public Integer getIdOrdenCompra() {
        return idOrdenCompra;
    }

    public void setIdOrdenCompra(Integer idOrdenCompra) {
        this.idOrdenCompra = idOrdenCompra;
    }

    public String getNumeroDocuCompra() {
        return numeroDocuCompra;
    }

    public void setNumeroDocuCompra(String numeroDocuCompra) {
        this.numeroDocuCompra = numeroDocuCompra;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public long getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(long precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Long getDescuento() {
        return descuento;
    }

    public void setDescuento(Long descuento) {
        this.descuento = descuento;
    }

    public long getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(long subtotal) {
        this.subtotal = subtotal;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public Ordenreposicion getIdOrdenReposicion() {
        return idOrdenReposicion;
    }

    public void setIdOrdenReposicion(Ordenreposicion idOrdenReposicion) {
        this.idOrdenReposicion = idOrdenReposicion;
    }

    public Proveedor getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Proveedor idProveedor) {
        this.idProveedor = idProveedor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOrdenCompra != null ? idOrdenCompra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ordencompra)) {
            return false;
        }
        Ordencompra other = (Ordencompra) object;
        if ((this.idOrdenCompra == null && other.idOrdenCompra != null) || (this.idOrdenCompra != null && !this.idOrdenCompra.equals(other.idOrdenCompra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Ordencompra[ idOrdenCompra=" + idOrdenCompra + " ]";
    }
    
}
