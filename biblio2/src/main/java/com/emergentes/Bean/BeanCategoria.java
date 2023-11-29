/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.emergentes.Bean;

import com.emergentes.entidades.Categoria;
import com.emergentes.jpa.CategoriaJpaController;
import com.emergentes.jpa.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author HOMELADER
 */
public class BeanCategoria {
    
    private EntityManagerFactory emf;
    private CategoriaJpaController jpaCategoria;
    
    public BeanCategoria() {
        emf = Persistence.createEntityManagerFactory("biblio20PU");
        jpaCategoria = new CategoriaJpaController(emf);
    }
    
    
    public List<Categoria> listarTodos(){
        return jpaCategoria.findCategoriaEntities();
    }
    
    
    public void insertar(Categoria c){
        jpaCategoria.create(c);
    }
    
    
    public void editar(Categoria c){
        try {
            jpaCategoria.edit(c);
        } catch (Exception ex) {
            Logger.getLogger(BeanCategoria.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void eliminar(Integer id)
    {
        try {
            jpaCategoria.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(BeanCategoria.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public Categoria buscar (Integer id){
        return jpaCategoria.findCategoria(id);
    }
}
