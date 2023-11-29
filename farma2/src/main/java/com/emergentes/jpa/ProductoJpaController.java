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
import com.emergentes.entidades.Categoria;
import com.emergentes.entidades.Producto;
import com.emergentes.entidades.Proveedore;
import com.emergentes.entidades.Venta;
import com.emergentes.jpa.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author HOMELADER
 */
public class ProductoJpaController implements Serializable {

    public ProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producto producto) {
        if (producto.getVentaList() == null) {
            producto.setVentaList(new ArrayList<Venta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria categoriasId = producto.getCategoriasId();
            if (categoriasId != null) {
                categoriasId = em.getReference(categoriasId.getClass(), categoriasId.getId());
                producto.setCategoriasId(categoriasId);
            }
            Proveedore proveedoresId = producto.getProveedoresId();
            if (proveedoresId != null) {
                proveedoresId = em.getReference(proveedoresId.getClass(), proveedoresId.getId());
                producto.setProveedoresId(proveedoresId);
            }
            List<Venta> attachedVentaList = new ArrayList<Venta>();
            for (Venta ventaListVentaToAttach : producto.getVentaList()) {
                ventaListVentaToAttach = em.getReference(ventaListVentaToAttach.getClass(), ventaListVentaToAttach.getId());
                attachedVentaList.add(ventaListVentaToAttach);
            }
            producto.setVentaList(attachedVentaList);
            em.persist(producto);
            if (categoriasId != null) {
                categoriasId.getProductoList().add(producto);
                categoriasId = em.merge(categoriasId);
            }
            if (proveedoresId != null) {
                proveedoresId.getProductoList().add(producto);
                proveedoresId = em.merge(proveedoresId);
            }
            for (Venta ventaListVenta : producto.getVentaList()) {
                Producto oldProductosIdOfVentaListVenta = ventaListVenta.getProductosId();
                ventaListVenta.setProductosId(producto);
                ventaListVenta = em.merge(ventaListVenta);
                if (oldProductosIdOfVentaListVenta != null) {
                    oldProductosIdOfVentaListVenta.getVentaList().remove(ventaListVenta);
                    oldProductosIdOfVentaListVenta = em.merge(oldProductosIdOfVentaListVenta);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Producto producto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto persistentProducto = em.find(Producto.class, producto.getId());
            Categoria categoriasIdOld = persistentProducto.getCategoriasId();
            Categoria categoriasIdNew = producto.getCategoriasId();
            Proveedore proveedoresIdOld = persistentProducto.getProveedoresId();
            Proveedore proveedoresIdNew = producto.getProveedoresId();
            List<Venta> ventaListOld = persistentProducto.getVentaList();
            List<Venta> ventaListNew = producto.getVentaList();
            if (categoriasIdNew != null) {
                categoriasIdNew = em.getReference(categoriasIdNew.getClass(), categoriasIdNew.getId());
                producto.setCategoriasId(categoriasIdNew);
            }
            if (proveedoresIdNew != null) {
                proveedoresIdNew = em.getReference(proveedoresIdNew.getClass(), proveedoresIdNew.getId());
                producto.setProveedoresId(proveedoresIdNew);
            }
            List<Venta> attachedVentaListNew = new ArrayList<Venta>();
            for (Venta ventaListNewVentaToAttach : ventaListNew) {
                ventaListNewVentaToAttach = em.getReference(ventaListNewVentaToAttach.getClass(), ventaListNewVentaToAttach.getId());
                attachedVentaListNew.add(ventaListNewVentaToAttach);
            }
            ventaListNew = attachedVentaListNew;
            producto.setVentaList(ventaListNew);
            producto = em.merge(producto);
            if (categoriasIdOld != null && !categoriasIdOld.equals(categoriasIdNew)) {
                categoriasIdOld.getProductoList().remove(producto);
                categoriasIdOld = em.merge(categoriasIdOld);
            }
            if (categoriasIdNew != null && !categoriasIdNew.equals(categoriasIdOld)) {
                categoriasIdNew.getProductoList().add(producto);
                categoriasIdNew = em.merge(categoriasIdNew);
            }
            if (proveedoresIdOld != null && !proveedoresIdOld.equals(proveedoresIdNew)) {
                proveedoresIdOld.getProductoList().remove(producto);
                proveedoresIdOld = em.merge(proveedoresIdOld);
            }
            if (proveedoresIdNew != null && !proveedoresIdNew.equals(proveedoresIdOld)) {
                proveedoresIdNew.getProductoList().add(producto);
                proveedoresIdNew = em.merge(proveedoresIdNew);
            }
            for (Venta ventaListOldVenta : ventaListOld) {
                if (!ventaListNew.contains(ventaListOldVenta)) {
                    ventaListOldVenta.setProductosId(null);
                    ventaListOldVenta = em.merge(ventaListOldVenta);
                }
            }
            for (Venta ventaListNewVenta : ventaListNew) {
                if (!ventaListOld.contains(ventaListNewVenta)) {
                    Producto oldProductosIdOfVentaListNewVenta = ventaListNewVenta.getProductosId();
                    ventaListNewVenta.setProductosId(producto);
                    ventaListNewVenta = em.merge(ventaListNewVenta);
                    if (oldProductosIdOfVentaListNewVenta != null && !oldProductosIdOfVentaListNewVenta.equals(producto)) {
                        oldProductosIdOfVentaListNewVenta.getVentaList().remove(ventaListNewVenta);
                        oldProductosIdOfVentaListNewVenta = em.merge(oldProductosIdOfVentaListNewVenta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = producto.getId();
                if (findProducto(id) == null) {
                    throw new NonexistentEntityException("The producto with id " + id + " no longer exists.");
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
            Producto producto;
            try {
                producto = em.getReference(Producto.class, id);
                producto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producto with id " + id + " no longer exists.", enfe);
            }
            Categoria categoriasId = producto.getCategoriasId();
            if (categoriasId != null) {
                categoriasId.getProductoList().remove(producto);
                categoriasId = em.merge(categoriasId);
            }
            Proveedore proveedoresId = producto.getProveedoresId();
            if (proveedoresId != null) {
                proveedoresId.getProductoList().remove(producto);
                proveedoresId = em.merge(proveedoresId);
            }
            List<Venta> ventaList = producto.getVentaList();
            for (Venta ventaListVenta : ventaList) {
                ventaListVenta.setProductosId(null);
                ventaListVenta = em.merge(ventaListVenta);
            }
            em.remove(producto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Producto.class));
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

    public Producto findProducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Producto> rt = cq.from(Producto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
