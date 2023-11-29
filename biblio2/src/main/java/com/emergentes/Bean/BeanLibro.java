/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.emergentes.Bean;

import com.emergentes.entidades.Libro;
import com.emergentes.jpa.LibroJpaController;
import com.emergentes.jpa.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author HOMELADER
 */
public class BeanLibro {
    private EntityManagerFactory emf;
    private LibroJpaController jpaLibro;
    
    public BeanLibro() {
        emf = Persistence.createEntityManagerFactory("biblio20PU");
        jpaLibro = new LibroJpaController(emf);
    }
    
    
    public List<Libro> listarTodos(){
        return jpaLibro.findLibroEntities();
    }
    
    
    public void insertar(Libro libro){
        jpaLibro.create(libro);
    }
    
    
    public void editar(Libro libro){
        try {
            jpaLibro.edit(libro);
        } catch (Exception ex) {
            Logger.getLogger(BeanLibro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void eliminar(Integer id)
    {
        try {
            jpaLibro.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(BeanLibro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public Libro buscar (Integer id){
        return jpaLibro.findLibro(id);
    }
}
