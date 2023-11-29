package com.emergentes.entidades;

import com.emergentes.entidades.Cliente;
import com.emergentes.entidades.Venta;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2023-11-21T15:50:45")
@StaticMetamodel(Factura.class)
public class Factura_ { 

    public static volatile SingularAttribute<Factura, Date> fecha;
    public static volatile ListAttribute<Factura, Venta> ventaList;
    public static volatile SingularAttribute<Factura, Cliente> clientesId;
    public static volatile SingularAttribute<Factura, Integer> id;

}