/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.emergentes.jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.emergentes.entidades.Producto;
import com.emergentes.entidades.Proveedore;
import com.emergentes.jpa.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author HOMELADER
 */
public class ProveedoreJpaController implements Serializable {

    public ProveedoreJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proveedore proveedore) {
        if (proveedore.getProductoList() == null) {
            proveedore.setProductoList(new ArrayList<Producto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Producto> attachedProductoList = new ArrayList<Producto>();
            for (Producto productoListProductoToAttach : proveedore.getProductoList()) {
                productoListProductoToAttach = em.getReference(productoListProductoToAttach.getClass(), productoListProductoToAttach.getIdProductos());
                attachedProductoList.add(productoListProductoToAttach);
            }
            proveedore.setProductoList(attachedProductoList);
            em.persist(proveedore);
            for (Producto productoListProducto : proveedore.getProductoList()) {
                Proveedore oldIdProveedoresOfProductoListProducto = productoListProducto.getIdProveedores();
                productoListProducto.setIdProveedores(proveedore);
                productoListProducto = em.merge(productoListProducto);
                if (oldIdProveedoresOfProductoListProducto != null) {
                    oldIdProveedoresOfProductoListProducto.getProductoList().remove(productoListProducto);
                    oldIdProveedoresOfProductoListProducto = em.merge(oldIdProveedoresOfProductoListProducto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Proveedore proveedore) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proveedore persistentProveedore = em.find(Proveedore.class, proveedore.getIdProveedores());
            List<Producto> productoListOld = persistentProveedore.getProductoList();
            List<Producto> productoListNew = proveedore.getProductoList();
            List<Producto> attachedProductoListNew = new ArrayList<Producto>();
            for (Producto productoListNewProductoToAttach : productoListNew) {
                productoListNewProductoToAttach = em.getReference(productoListNewProductoToAttach.getClass(), productoListNewProductoToAttach.getIdProductos());
                attachedProductoListNew.add(productoListNewProductoToAttach);
            }
            productoListNew = attachedProductoListNew;
            proveedore.setProductoList(productoListNew);
            proveedore = em.merge(proveedore);
            for (Producto productoListOldProducto : productoListOld) {
                if (!productoListNew.contains(productoListOldProducto)) {
                    productoListOldProducto.setIdProveedores(null);
                    productoListOldProducto = em.merge(productoListOldProducto);
                }
            }
            for (Producto productoListNewProducto : productoListNew) {
                if (!productoListOld.contains(productoListNewProducto)) {
                    Proveedore oldIdProveedoresOfProductoListNewProducto = productoListNewProducto.getIdProveedores();
                    productoListNewProducto.setIdProveedores(proveedore);
                    productoListNewProducto = em.merge(productoListNewProducto);
                    if (oldIdProveedoresOfProductoListNewProducto != null && !oldIdProveedoresOfProductoListNewProducto.equals(proveedore)) {
                        oldIdProveedoresOfProductoListNewProducto.getProductoList().remove(productoListNewProducto);
                        oldIdProveedoresOfProductoListNewProducto = em.merge(oldIdProveedoresOfProductoListNewProducto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = proveedore.getIdProveedores();
                if (findProveedore(id) == null) {
                    throw new NonexistentEntityException("The proveedore with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proveedore proveedore;
            try {
                proveedore = em.getReference(Proveedore.class, id);
                proveedore.getIdProveedores();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proveedore with id " + id + " no longer exists.", enfe);
            }
            List<Producto> productoList = proveedore.getProductoList();
            for (Producto productoListProducto : productoList) {
                productoListProducto.setIdProveedores(null);
                productoListProducto = em.merge(productoListProducto);
            }
            em.remove(proveedore);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Proveedore> findProveedoreEntities() {
        return findProveedoreEntities(true, -1, -1);
    }

    public List<Proveedore> findProveedoreEntities(int maxResults, int firstResult) {
        return findProveedoreEntities(false, maxResults, firstResult);
    }

    private List<Proveedore> findProveedoreEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Proveedore.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Proveedore findProveedore(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Proveedore.class, id);
        } finally {
            em.close();
        }
    }

    public int getProveedoreCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Proveedore> rt = cq.from(Proveedore.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
