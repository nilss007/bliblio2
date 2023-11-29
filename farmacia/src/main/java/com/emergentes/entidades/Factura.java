/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.emergentes.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author HOMELADER
 */
@Entity
@Table(name = "facturas")
@NamedQueries({
    @NamedQuery(name = "Factura.findAll", query = "SELECT f FROM Factura f"),
    @NamedQuery(name = "Factura.findByIdFacturas", query = "SELECT f FROM Factura f WHERE f.idFacturas = :idFacturas"),
    @NamedQuery(name = "Factura.findByFecha", query = "SELECT f FROM Factura f WHERE f.fecha = :fecha")})
public class Factura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_facturas")
    private Integer idFacturas;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @JoinColumn(name = "id_clientes", referencedColumnName = "id_clientes")
    @ManyToOne
    private Cliente idClientes;
    @OneToMany(mappedBy = "idFacturas")
    private List<Venta> ventaList;

    public Factura() {
        this.idFacturas = 0;
        this.fecha = new Date();
        this.idClientes = new Cliente();
        ventaList = new ArrayList<Venta>();        
    }

    public Factura(Integer idFacturas) {
        this.idFacturas = idFacturas;
    }

    public Integer getIdFacturas() {
        return idFacturas;
    }

    public void setIdFacturas(Integer idFacturas) {
        this.idFacturas = idFacturas;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Cliente getIdClientes() {
        return idClientes;
    }

    public void setIdClientes(Cliente idClientes) {
        this.idClientes = idClientes;
    }

    public List<Venta> getVentaList() {
        return ventaList;
    }

    public void setVentaList(List<Venta> ventaList) {
        this.ventaList = ventaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFacturas != null ? idFacturas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Factura)) {
            return false;
        }
        Factura other = (Factura) object;
        if ((this.idFacturas == null && other.idFacturas != null) || (this.idFacturas != null && !this.idFacturas.equals(other.idFacturas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.emergentes.entidades.Factura[ idFacturas=" + idFacturas + " ]";
    }
    
}
