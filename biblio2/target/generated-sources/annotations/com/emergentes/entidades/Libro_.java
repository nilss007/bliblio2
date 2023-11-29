package com.emergentes.entidades;

import com.emergentes.entidades.Categoria;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2023-11-19T15:00:59")
@StaticMetamodel(Libro.class)
public class Libro_ { 

    public static volatile SingularAttribute<Libro, String> titulo;
    public static volatile SingularAttribute<Libro, Integer> id;
    public static volatile SingularAttribute<Libro, String> autor;
    public static volatile SingularAttribute<Libro, Integer> disponible;
    public static volatile SingularAttribute<Libro, Categoria> categoriaId;

}