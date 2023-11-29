
package com.emergentes.bean;

import com.emergentes.entidades.Cliente;
import com.emergentes.jpa.ClienteJpaController;
import com.emergentes.jpa.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class BeanCliente {
    private EntityManagerFactory emf;
    private ClienteJpaController jpaCliente;

    public BeanCliente() {
        emf = Persistence.createEntityManagerFactory("farma2PU");
        jpaCliente = new ClienteJpaController(emf);
    }
    
    public List<Cliente> listarTodos(){
        return jpaCliente.findClienteEntities();
    }
    
    
    public void insertar(Cliente clie){
        jpaCliente.create(clie);
    }
    
    
    public void editar(Cliente clie){
        try {
            jpaCliente.edit(clie);
        } catch (Exception ex) {
            Logger.getLogger(BeanCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void eliminar(Integer id)
    {
        try {
            jpaCliente.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(BeanCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public Cliente buscar (Integer id){
        return jpaCliente.findCliente(id);
    }
}
