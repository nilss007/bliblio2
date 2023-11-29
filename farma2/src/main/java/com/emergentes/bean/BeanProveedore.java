
package com.emergentes.bean;

import com.emergentes.entidades.Proveedore;
import com.emergentes.jpa.ProveedoreJpaController;
import com.emergentes.jpa.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class BeanProveedore {
    private EntityManagerFactory emf;
    private ProveedoreJpaController jpaProveedore;

    public BeanProveedore() {
        emf = Persistence.createEntityManagerFactory("farma2PU");
        jpaProveedore = new ProveedoreJpaController(emf);
    }
    
    public List<Proveedore> listarTodos(){
        return jpaProveedore.findProveedoreEntities();
    }
    
    
    public void insertar(Proveedore prov){
        jpaProveedore.create(prov);
    }
    
    
    public void editar(Proveedore prov){
        try {
            jpaProveedore.edit(prov);
        } catch (Exception ex) {
            Logger.getLogger(BeanProveedore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void eliminar(Integer id)
    {
        try {
            jpaProveedore.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(BeanProveedore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public Proveedore buscar (Integer id){
        return jpaProveedore.findProveedore(id);
    }
}
