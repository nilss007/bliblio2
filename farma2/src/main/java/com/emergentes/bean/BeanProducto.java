
package com.emergentes.bean;

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
        emf = Persistence.createEntityManagerFactory("farma2PU");
        jpaProducto = new ProductoJpaController(emf);
    }
    
    public List<Producto> listarTodos(){
        return jpaProducto.findProductoEntities();
    }
    
    
    public void insertar(Producto prod){
        jpaProducto.create(prod);
    }
    
    
    public void editar(Producto prod){
        try {
            jpaProducto.edit(prod);
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
