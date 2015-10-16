/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "reservacionalquiler")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reservacionalquiler.findAll", query = "SELECT r FROM Reservacionalquiler r"),
    @NamedQuery(name = "Reservacionalquiler.findByIdReservacionAlquiler", query = "SELECT r FROM Reservacionalquiler r WHERE r.idReservacionAlquiler = :idReservacionAlquiler"),
    @NamedQuery(name = "Reservacionalquiler.findByFechaRegistro", query = "SELECT r FROM Reservacionalquiler r WHERE r.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Reservacionalquiler.findByFechadeSalida", query = "SELECT r FROM Reservacionalquiler r WHERE r.fechadeSalida = :fechadeSalida"),
    @NamedQuery(name = "Reservacionalquiler.findByHoraReg", query = "SELECT r FROM Reservacionalquiler r WHERE r.horaReg = :horaReg"),
    @NamedQuery(name = "Reservacionalquiler.findByHoraSalida", query = "SELECT r FROM Reservacionalquiler r WHERE r.horaSalida = :horaSalida"),
    @NamedQuery(name = "Reservacionalquiler.findByAcompa\u00f1antes", query = "SELECT r FROM Reservacionalquiler r WHERE r.acompa\u00f1antes = :acompa\u00f1antes"),
    @NamedQuery(name = "Reservacionalquiler.findByMonto", query = "SELECT r FROM Reservacionalquiler r WHERE r.monto = :monto"),
    @NamedQuery(name = "Reservacionalquiler.findByEstado", query = "SELECT r FROM Reservacionalquiler r WHERE r.estado = :estado")})
public class Reservacionalquiler implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idReservacionAlquiler")
    private Integer idReservacionAlquiler;
    @Basic(optional = false)
    @Column(name = "FechaRegistro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "FechadeSalida")
    @Temporal(TemporalType.DATE)
    private Date fechadeSalida;
    @Basic(optional = false)
    @Column(name = "HoraReg")
    @Temporal(TemporalType.TIME)
    private Date horaReg;
    @Column(name = "HoraSalida")
    @Temporal(TemporalType.TIME)
    private Date horaSalida;
    @Column(name = "Acompa\u00f1antes")
    private String acompañantes;
    @Column(name = "Monto")
    private Long monto;
    @Basic(optional = false)
    @Column(name = "estado")
    private short estado;
    @JoinColumn(name = "idHabitacion", referencedColumnName = "idHabitacion")
    @ManyToOne(optional = false)
    private Habitacion idHabitacion;
    @JoinColumn(name = "IdEmpleado", referencedColumnName = "idPersona")
    @ManyToOne(optional = false)
    private Empleado idEmpleado;
    @JoinColumn(name = "IdCliente", referencedColumnName = "idPersona")
    @ManyToOne(optional = false)
    private Cliente idCliente;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "reservacionalquiler")
    private Documentoalquiler documentoalquiler;

    public Reservacionalquiler() {
    }

    public Reservacionalquiler(Integer idReservacionAlquiler) {
        this.idReservacionAlquiler = idReservacionAlquiler;
    }

    public Reservacionalquiler(Integer idReservacionAlquiler, Date fechaRegistro, Date horaReg, short estado) {
        this.idReservacionAlquiler = idReservacionAlquiler;
        this.fechaRegistro = fechaRegistro;
        this.horaReg = horaReg;
        this.estado = estado;
    }

    public Integer getIdReservacionAlquiler() {
        return idReservacionAlquiler;
    }

    public void setIdReservacionAlquiler(Integer idReservacionAlquiler) {
        this.idReservacionAlquiler = idReservacionAlquiler;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechadeSalida() {
        return fechadeSalida;
    }

    public void setFechadeSalida(Date fechadeSalida) {
        this.fechadeSalida = fechadeSalida;
    }

    public Date getHoraReg() {
        return horaReg;
    }

    public void setHoraReg(Date horaReg) {
        this.horaReg = horaReg;
    }

    public Date getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(Date horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getAcompañantes() {
        return acompañantes;
    }

    public void setAcompañantes(String acompañantes) {
        this.acompañantes = acompañantes;
    }

    public Long getMonto() {
        return monto;
    }

    public void setMonto(Long monto) {
        this.monto = monto;
    }

    public short getEstado() {
        return estado;
    }

    public void setEstado(short estado) {
        this.estado = estado;
    }

    public Habitacion getIdHabitacion() {
        return idHabitacion;
    }

    public void setIdHabitacion(Habitacion idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    public Documentoalquiler getDocumentoalquiler() {
        return documentoalquiler;
    }

    public void setDocumentoalquiler(Documentoalquiler documentoalquiler) {
        this.documentoalquiler = documentoalquiler;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReservacionAlquiler != null ? idReservacionAlquiler.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reservacionalquiler)) {
            return false;
        }
        Reservacionalquiler other = (Reservacionalquiler) object;
        if ((this.idReservacionAlquiler == null && other.idReservacionAlquiler != null) || (this.idReservacionAlquiler != null && !this.idReservacionAlquiler.equals(other.idReservacionAlquiler))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Reservacionalquiler[ idReservacionAlquiler=" + idReservacionAlquiler + " ]";
    }
    
}
