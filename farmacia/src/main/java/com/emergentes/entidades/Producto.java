/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.emergentes.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author HOMELADER
 */
@Entity
@Table(name = "productos")
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p"),
    @NamedQuery(name = "Producto.findByIdProductos", query = "SELECT p FROM Producto p WHERE p.idProductos = :idProductos"),
    @NamedQuery(name = "Producto.findByDescripcion", query = "SELECT p FROM Producto p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "Producto.findByPrecio", query = "SELECT p FROM Producto p WHERE p.precio = :precio")})
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_productos")
    private Integer idProductos;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "descripcion")
    private String descripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "precio")
    private BigDecimal precio;
    @OneToMany(mappedBy = "idProductos")
    private List<Venta> ventaList;
    @JoinColumn(name = "id_categorias", referencedColumnName = "id_categorias")
    @ManyToOne
    private Categoria idCategorias;
    @JoinColumn(name = "id_proveedores", referencedColumnName = "id_proveedores")
    @ManyToOne
    private Proveedore idProveedores;

    public Producto() {
        this.idProductos = 0;
        this.descripcion = "";
        this.precio = BigDecimal.ZERO;
        this.idCategorias = new Categoria();
        this.idProveedores = new Proveedore();
        ventaList = new ArrayList<Venta>();
    }

    public Producto(Integer idProductos) {
        this.idProductos = idProductos;
    }

    public Producto(Integer idProductos, String descripcion, BigDecimal precio) {
        this.idProductos = idProductos;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public Integer getIdProductos() {
        return idProductos;
    }

    public void setIdProductos(Integer idProductos) {
        this.idProductos = idProductos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public List<Venta> getVentaList() {
        return ventaList;
    }

    public void setVentaList(List<Venta> ventaList) {
        this.ventaList = ventaList;
    }

    public Categoria getIdCategorias() {
        return idCategorias;
    }

    public void setIdCategorias(Categoria idCategorias) {
        this.idCategorias = idCategorias;
    }

    public Proveedore getIdProveedores() {
        return idProveedores;
    }

    public void setIdProveedores(Proveedore idProveedores) {
        this.idProveedores = idProveedores;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProductos != null ? idProductos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.idProductos == null && other.idProductos != null) || (this.idProductos != null && !this.idProductos.equals(other.idProductos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Producto{" + "idProductos=" + idProductos + ", descripcion=" + descripcion + ", precio=" + precio + ", ventaList=" + ventaList + ", idCategorias=" + idCategorias + ", idProveedores=" + idProveedores + '}';
    }
    
}
