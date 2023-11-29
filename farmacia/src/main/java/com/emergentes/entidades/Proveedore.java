/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.emergentes.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author HOMELADER
 */
@Entity
@Table(name = "proveedores")
@NamedQueries({
    @NamedQuery(name = "Proveedore.findAll", query = "SELECT p FROM Proveedore p"),
    @NamedQuery(name = "Proveedore.findByIdProveedores", query = "SELECT p FROM Proveedore p WHERE p.idProveedores = :idProveedores"),
    @NamedQuery(name = "Proveedore.findByNombre", query = "SELECT p FROM Proveedore p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Proveedore.findByDireccion", query = "SELECT p FROM Proveedore p WHERE p.direccion = :direccion"),
    @NamedQuery(name = "Proveedore.findByTelefono", query = "SELECT p FROM Proveedore p WHERE p.telefono = :telefono")})
public class Proveedore implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_proveedores")
    private Integer idProveedores;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "direccion")
    private String direccion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "telefono")
    private int telefono;
    @OneToMany(mappedBy = "idProveedores")
    private List<Producto> productoList;

    public Proveedore() {
        this.idProveedores = 0;
        this.nombre = "";
        this.direccion = "";
        this.telefono = 0;
        productoList = new ArrayList<Producto>();
    }

    public Proveedore(Integer idProveedores) {
        this.idProveedores = idProveedores;
    }

    public Proveedore(Integer idProveedores, String nombre, String direccion, int telefono) {
        this.idProveedores = idProveedores;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public Integer getIdProveedores() {
        return idProveedores;
    }

    public void setIdProveedores(Integer idProveedores) {
        this.idProveedores = idProveedores;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public List<Producto> getProductoList() {
        return productoList;
    }

    public void setProductoList(List<Producto> productoList) {
        this.productoList = productoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProveedores != null ? idProveedores.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proveedore)) {
            return false;
        }
        Proveedore other = (Proveedore) object;
        if ((this.idProveedores == null && other.idProveedores != null) || (this.idProveedores != null && !this.idProveedores.equals(other.idProveedores))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Proveedore{" + "idProveedores=" + idProveedores + ", nombre=" + nombre + ", direccion=" + direccion + ", telefono=" + telefono + ", productoList=" + productoList + '}';
    }
 
}
