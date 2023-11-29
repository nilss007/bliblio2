package com.emergentes.entidades;

import com.emergentes.entidades.Factura;
import com.emergentes.entidades.Producto;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2023-11-20T00:10:46")
@StaticMetamodel(Venta.class)
public class Venta_ { 

    public static volatile SingularAttribute<Venta, Factura> idFacturas;
    public static volatile SingularAttribute<Venta, Producto> idProductos;
    public static volatile SingularAttribute<Venta, Integer> cantidad;
    public static volatile SingularAttribute<Venta, Integer> idVentas;

}