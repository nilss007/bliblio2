
package com.emergentes.bean;

import com.emergentes.entidades.Venta;
import com.emergentes.jpa.VentaJpaController;
import com.emergentes.jpa.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class BeanVenta {
    private EntityManagerFactory emf;
    private VentaJpaController jpaVenta;

    public BeanVenta() {
        emf = Persistence.createEntityManagerFactory("farma2PU");
        jpaVenta = new VentaJpaController(emf);
    }
    
    public List<Venta> listarTodos(){
        return jpaVenta.findVentaEntities();
    }
    
    
    public void insertar(Venta ven){
        jpaVenta.create(ven);
    }
    
    
    public void editar(Venta ven){
        try {
            jpaVenta.edit(ven);
        } catch (Exception ex) {
            Logger.getLogger(BeanVenta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void eliminar(Integer id)
    {
        try {
            jpaVenta.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(BeanVenta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public Venta buscar (Integer id){
        return jpaVenta.findVenta(id);
    }
}
