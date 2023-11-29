/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.emergentes.entidades;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;

/**
 *
 * @author HOMELADER
 */
@Entity
@Table(name = "ventas")
@NamedQueries({
    @NamedQuery(name = "Venta.findAll", query = "SELECT v FROM Venta v"),
    @NamedQuery(name = "Venta.findByIdVentas", query = "SELECT v FROM Venta v WHERE v.idVentas = :idVentas"),
    @NamedQuery(name = "Venta.findByCantidad", query = "SELECT v FROM Venta v WHERE v.cantidad = :cantidad")})
public class Venta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_ventas")
    private Integer idVentas;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad")
    private int cantidad;
    @JoinColumn(name = "id_facturas", referencedColumnName = "id_facturas")
    @ManyToOne
    private Factura idFacturas;
    @JoinColumn(name = "id_productos", referencedColumnName = "id_productos")
    @ManyToOne
    private Producto idProductos;

    public Venta() {
        this.idVentas = 0;
        this.cantidad = 0;
        this.idFacturas = new Factura();
        this.idProductos = new Producto();
    }

    public Venta(Integer idVentas) {
        this.idVentas = idVentas;
    }

    public Venta(Integer idVentas, int cantidad) {
        this.idVentas = idVentas;
        this.cantidad = cantidad;
    }

    public Integer getIdVentas() {
        return idVentas;
    }

    public void setIdVentas(Integer idVentas) {
        this.idVentas = idVentas;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Factura getIdFacturas() {
        return idFacturas;
    }

    public void setIdFacturas(Factura idFacturas) {
        this.idFacturas = idFacturas;
    }

    public Producto getIdProductos() {
        return idProductos;
    }

    public void setIdProductos(Producto idProductos) {
        this.idProductos = idProductos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVentas != null ? idVentas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Venta)) {
            return false;
        }
        Venta other = (Venta) object;
        if ((this.idVentas == null && other.idVentas != null) || (this.idVentas != null && !this.idVentas.equals(other.idVentas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.emergentes.entidades.Venta[ idVentas=" + idVentas + " ]";
    }
    
}
