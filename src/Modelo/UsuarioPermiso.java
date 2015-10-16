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
@Table(name = "usuario_permiso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuarioPermiso.findAll", query = "SELECT u FROM UsuarioPermiso u"),
    @NamedQuery(name = "UsuarioPermiso.findByIdUsuario", query = "SELECT u FROM UsuarioPermiso u WHERE u.idUsuario = :idUsuario"),
    @NamedQuery(name = "UsuarioPermiso.findByEstado", query = "SELECT u FROM UsuarioPermiso u WHERE u.estado = :estado"),
    @NamedQuery(name = "UsuarioPermiso.findByFechaAsignacion", query = "SELECT u FROM UsuarioPermiso u WHERE u.fechaAsignacion = :fechaAsignacion")})
public class UsuarioPermiso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idUsuario")
    private Integer idUsuario;
    @Basic(optional = false)
    @Column(name = "Estado")
    private boolean estado;
    @Column(name = "fechaAsignacion")
    @Temporal(TemporalType.DATE)
    private Date fechaAsignacion;
    @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Usuario usuario;
    @JoinColumn(name = "idPermiso", referencedColumnName = "idPermiso")
    @ManyToOne(optional = false)
    private Permiso idPermiso;

    public UsuarioPermiso() {
    }

    public UsuarioPermiso(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public UsuarioPermiso(Integer idUsuario, boolean estado) {
        this.idUsuario = idUsuario;
        this.estado = estado;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Permiso getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(Permiso idPermiso) {
        this.idPermiso = idPermiso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioPermiso)) {
            return false;
        }
        UsuarioPermiso other = (UsuarioPermiso) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.UsuarioPermiso[ idUsuario=" + idUsuario + " ]";
    }
    
}
