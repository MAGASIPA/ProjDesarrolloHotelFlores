/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "habitacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Habitacion.findAll", query = "SELECT h FROM Habitacion h"),
    @NamedQuery(name = "Habitacion.findByIdHabitacion", query = "SELECT h FROM Habitacion h WHERE h.idHabitacion = :idHabitacion"),
    @NamedQuery(name = "Habitacion.findByNumero", query = "SELECT h FROM Habitacion h WHERE h.numero = :numero"),
    @NamedQuery(name = "Habitacion.findByPiso", query = "SELECT h FROM Habitacion h WHERE h.piso = :piso"),
    @NamedQuery(name = "Habitacion.findByPrecio", query = "SELECT h FROM Habitacion h WHERE h.precio = :precio"),
    @NamedQuery(name = "Habitacion.findByBa\u00f1o", query = "SELECT h FROM Habitacion h WHERE h.ba\u00f1o = :ba\u00f1o"),
    @NamedQuery(name = "Habitacion.findByMedidas", query = "SELECT h FROM Habitacion h WHERE h.medidas = :medidas"),
    @NamedQuery(name = "Habitacion.findByEstado", query = "SELECT h FROM Habitacion h WHERE h.estado = :estado")})
public class Habitacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idHabitacion")
    private Integer idHabitacion;
    @Basic(optional = false)
    @Column(name = "Numero")
    private String numero;
    @Basic(optional = false)
    @Column(name = "Piso")
    private int piso;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "Precio")
    private BigDecimal precio;
    @Column(name = "Ba\u00f1o")
    private String baño;
    @Column(name = "Medidas")
    private String medidas;
    @Basic(optional = false)
    @Column(name = "estado")
    private short estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idHabitacion")
    private Collection<Asignacion> asignacionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idHabitacion")
    private Collection<Reservacionalquiler> reservacionalquilerCollection;
    @JoinColumn(name = "idTipoHabitacion", referencedColumnName = "idTipoHabitacion")
    @ManyToOne(optional = false)
    private Tipohabitacion idTipoHabitacion;

    public Habitacion() {
    }

    public Habitacion(Integer idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    public Habitacion(Integer idHabitacion, String numero, int piso, BigDecimal precio, short estado) {
        this.idHabitacion = idHabitacion;
        this.numero = numero;
        this.piso = piso;
        this.precio = precio;
        this.estado = estado;
    }

    public Integer getIdHabitacion() {
        return idHabitacion;
    }

    public void setIdHabitacion(Integer idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getPiso() {
        return piso;
    }

    public void setPiso(int piso) {
        this.piso = piso;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getBaño() {
        return baño;
    }

    public void setBaño(String baño) {
        this.baño = baño;
    }

    public String getMedidas() {
        return medidas;
    }

    public void setMedidas(String medidas) {
        this.medidas = medidas;
    }

    public short getEstado() {
        return estado;
    }

    public void setEstado(short estado) {
        this.estado = estado;
    }

    @XmlTransient
    public Collection<Asignacion> getAsignacionCollection() {
        return asignacionCollection;
    }

    public void setAsignacionCollection(Collection<Asignacion> asignacionCollection) {
        this.asignacionCollection = asignacionCollection;
    }

    @XmlTransient
    public Collection<Reservacionalquiler> getReservacionalquilerCollection() {
        return reservacionalquilerCollection;
    }

    public void setReservacionalquilerCollection(Collection<Reservacionalquiler> reservacionalquilerCollection) {
        this.reservacionalquilerCollection = reservacionalquilerCollection;
    }

    public Tipohabitacion getIdTipoHabitacion() {
        return idTipoHabitacion;
    }

    public void setIdTipoHabitacion(Tipohabitacion idTipoHabitacion) {
        this.idTipoHabitacion = idTipoHabitacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHabitacion != null ? idHabitacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Habitacion)) {
            return false;
        }
        Habitacion other = (Habitacion) object;
        if ((this.idHabitacion == null && other.idHabitacion != null) || (this.idHabitacion != null && !this.idHabitacion.equals(other.idHabitacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Habitacion[ idHabitacion=" + idHabitacion + " ]";
    }
    
}
