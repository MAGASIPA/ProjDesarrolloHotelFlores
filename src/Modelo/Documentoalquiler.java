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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "documentoalquiler")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Documentoalquiler.findAll", query = "SELECT d FROM Documentoalquiler d"),
    @NamedQuery(name = "Documentoalquiler.findByIdDocumentoAlquiler", query = "SELECT d FROM Documentoalquiler d WHERE d.idDocumentoAlquiler = :idDocumentoAlquiler"),
    @NamedQuery(name = "Documentoalquiler.findBySerie", query = "SELECT d FROM Documentoalquiler d WHERE d.serie = :serie"),
    @NamedQuery(name = "Documentoalquiler.findByNumeroDocu", query = "SELECT d FROM Documentoalquiler d WHERE d.numeroDocu = :numeroDocu"),
    @NamedQuery(name = "Documentoalquiler.findByMontoTotal", query = "SELECT d FROM Documentoalquiler d WHERE d.montoTotal = :montoTotal")})
public class Documentoalquiler implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idDocumentoAlquiler")
    private Integer idDocumentoAlquiler;
    @Basic(optional = false)
    @Column(name = "serie")
    private int serie;
    @Basic(optional = false)
    @Column(name = "numeroDocu")
    private int numeroDocu;
    @Basic(optional = false)
    @Column(name = "montoTotal")
    private long montoTotal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDocumentoAlquiler")
    private Collection<Hojaconsumo> hojaconsumoCollection;
    @JoinColumn(name = "idDocumentoAlquiler", referencedColumnName = "idReservacionAlquiler", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Reservacionalquiler reservacionalquiler;

    public Documentoalquiler() {
    }

    public Documentoalquiler(Integer idDocumentoAlquiler) {
        this.idDocumentoAlquiler = idDocumentoAlquiler;
    }

    public Documentoalquiler(Integer idDocumentoAlquiler, int serie, int numeroDocu, long montoTotal) {
        this.idDocumentoAlquiler = idDocumentoAlquiler;
        this.serie = serie;
        this.numeroDocu = numeroDocu;
        this.montoTotal = montoTotal;
    }

    public Integer getIdDocumentoAlquiler() {
        return idDocumentoAlquiler;
    }

    public void setIdDocumentoAlquiler(Integer idDocumentoAlquiler) {
        this.idDocumentoAlquiler = idDocumentoAlquiler;
    }

    public int getSerie() {
        return serie;
    }

    public void setSerie(int serie) {
        this.serie = serie;
    }

    public int getNumeroDocu() {
        return numeroDocu;
    }

    public void setNumeroDocu(int numeroDocu) {
        this.numeroDocu = numeroDocu;
    }

    public long getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(long montoTotal) {
        this.montoTotal = montoTotal;
    }

    @XmlTransient
    public Collection<Hojaconsumo> getHojaconsumoCollection() {
        return hojaconsumoCollection;
    }

    public void setHojaconsumoCollection(Collection<Hojaconsumo> hojaconsumoCollection) {
        this.hojaconsumoCollection = hojaconsumoCollection;
    }

    public Reservacionalquiler getReservacionalquiler() {
        return reservacionalquiler;
    }

    public void setReservacionalquiler(Reservacionalquiler reservacionalquiler) {
        this.reservacionalquiler = reservacionalquiler;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDocumentoAlquiler != null ? idDocumentoAlquiler.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Documentoalquiler)) {
            return false;
        }
        Documentoalquiler other = (Documentoalquiler) object;
        if ((this.idDocumentoAlquiler == null && other.idDocumentoAlquiler != null) || (this.idDocumentoAlquiler != null && !this.idDocumentoAlquiler.equals(other.idDocumentoAlquiler))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Documentoalquiler[ idDocumentoAlquiler=" + idDocumentoAlquiler + " ]";
    }
    
}
