/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.allan.comisaria.models;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author allan
 */
@Entity
@Table(name = "comisaria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comisaria.findAll", query = "SELECT c FROM Comisaria c"),
    @NamedQuery(name = "Comisaria.findByIdComisaria", query = "SELECT c FROM Comisaria c WHERE c.idComisaria = :idComisaria"),
    @NamedQuery(name = "Comisaria.findByNombre", query = "SELECT c FROM Comisaria c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Comisaria.findByTelefono", query = "SELECT c FROM Comisaria c WHERE c.telefono = :telefono"),
    @NamedQuery(name = "Comisaria.findByDepartamento", query = "SELECT c FROM Comisaria c WHERE c.departamento = :departamento"),
    @NamedQuery(name = "Comisaria.findByUrl", query = "SELECT c FROM Comisaria c WHERE c.url = :url"),
    @NamedQuery(name = "Comisaria.findByDireccion", query = "SELECT c FROM Comisaria c WHERE c.direccion = :direccion")})
public class Comisaria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_comisaria")
    private Integer idComisaria;
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 8)
    @Column(name = "telefono")
    private String telefono;
    @Size(max = 45)
    @Column(name = "departamento")
    private String departamento;
    @Size(max = 500)
    @Column(name = "url")
    private String url;
    @Size(max = 45)
    @Column(name = "direccion")
    private String direccion;

    public Comisaria() {
    }

    public Comisaria(Integer idComisaria) {
        this.idComisaria = idComisaria;
    }

    public Integer getIdComisaria() {
        return idComisaria;
    }

    public void setIdComisaria(Integer idComisaria) {
        this.idComisaria = idComisaria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComisaria != null ? idComisaria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comisaria)) {
            return false;
        }
        Comisaria other = (Comisaria) object;
        if ((this.idComisaria == null && other.idComisaria != null) || (this.idComisaria != null && !this.idComisaria.equals(other.idComisaria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.allan.comisaria.models.Comisaria[ idComisaria=" + idComisaria + " ]";
    }
    
}
