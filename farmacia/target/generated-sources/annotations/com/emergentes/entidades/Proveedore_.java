package com.emergentes.entidades;

import com.emergentes.entidades.Producto;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2023-11-20T00:10:46")
@StaticMetamodel(Proveedore.class)
public class Proveedore_ { 

    public static volatile ListAttribute<Proveedore, Producto> productoList;
    public static volatile SingularAttribute<Proveedore, Integer> idProveedores;
    public static volatile SingularAttribute<Proveedore, String> direccion;
    public static volatile SingularAttribute<Proveedore, Integer> telefono;
    public static volatile SingularAttribute<Proveedore, String> nombre;

}