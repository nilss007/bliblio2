package com.emergentes.Bean;

import com.emergentes.entidades.Producto;
import com.emergentes.jpa.ProductoJpaController;
import com.emergentes.jpa.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class BeanProducto {
    private EntityManagerFactory emf;
    private ProductoJpaController jpaProducto;

    public BeanProducto() {
        emf = Persistence.createEntityManagerFactory("farmaciaPU");
        jpaProducto = new ProductoJpaController(emf);
    }
    
    public List<Producto> listarTodos(){
        return jpaProducto.findProductoEntities();
    }
    
    
    public void insertar(Producto pro){
        jpaProducto.create(pro);
    }
    
    
    public void editar(Producto pro){
        try {
            jpaProducto.edit(pro);
        } catch (Exception ex) {
            Logger.getLogger(BeanProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void eliminar(Integer id)
    {
        try {
            jpaProducto.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(BeanProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public Producto buscar (Integer id){
        return jpaProducto.findProducto(id);
    }
}
