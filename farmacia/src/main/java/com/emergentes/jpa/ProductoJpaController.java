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
            Categoria idCategorias = producto.getIdCategorias();
            if (idCategorias != null) {
                idCategorias = em.getReference(idCategorias.getClass(), idCategorias.getIdCategorias());
                producto.setIdCategorias(idCategorias);
            }
            Proveedore idProveedores = producto.getIdProveedores();
            if (idProveedores != null) {
                idProveedores = em.getReference(idProveedores.getClass(), idProveedores.getIdProveedores());
                producto.setIdProveedores(idProveedores);
            }
            List<Venta> attachedVentaList = new ArrayList<Venta>();
            for (Venta ventaListVentaToAttach : producto.getVentaList()) {
                ventaListVentaToAttach = em.getReference(ventaListVentaToAttach.getClass(), ventaListVentaToAttach.getIdVentas());
                attachedVentaList.add(ventaListVentaToAttach);
            }
            producto.setVentaList(attachedVentaList);
            em.persist(producto);
            if (idCategorias != null) {
                idCategorias.getProductoList().add(producto);
                idCategorias = em.merge(idCategorias);
            }
            if (idProveedores != null) {
                idProveedores.getProductoList().add(producto);
                idProveedores = em.merge(idProveedores);
            }
            for (Venta ventaListVenta : producto.getVentaList()) {
                Producto oldIdProductosOfVentaListVenta = ventaListVenta.getIdProductos();
                ventaListVenta.setIdProductos(producto);
                ventaListVenta = em.merge(ventaListVenta);
                if (oldIdProductosOfVentaListVenta != null) {
                    oldIdProductosOfVentaListVenta.getVentaList().remove(ventaListVenta);
                    oldIdProductosOfVentaListVenta = em.merge(oldIdProductosOfVentaListVenta);
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
            Producto persistentProducto = em.find(Producto.class, producto.getIdProductos());
            Categoria idCategoriasOld = persistentProducto.getIdCategorias();
            Categoria idCategoriasNew = producto.getIdCategorias();
            Proveedore idProveedoresOld = persistentProducto.getIdProveedores();
            Proveedore idProveedoresNew = producto.getIdProveedores();
            List<Venta> ventaListOld = persistentProducto.getVentaList();
            List<Venta> ventaListNew = producto.getVentaList();
            if (idCategoriasNew != null) {
                idCategoriasNew = em.getReference(idCategoriasNew.getClass(), idCategoriasNew.getIdCategorias());
                producto.setIdCategorias(idCategoriasNew);
            }
            if (idProveedoresNew != null) {
                idProveedoresNew = em.getReference(idProveedoresNew.getClass(), idProveedoresNew.getIdProveedores());
                producto.setIdProveedores(idProveedoresNew);
            }
            List<Venta> attachedVentaListNew = new ArrayList<Venta>();
            for (Venta ventaListNewVentaToAttach : ventaListNew) {
                ventaListNewVentaToAttach = em.getReference(ventaListNewVentaToAttach.getClass(), ventaListNewVentaToAttach.getIdVentas());
                attachedVentaListNew.add(ventaListNewVentaToAttach);
            }
            ventaListNew = attachedVentaListNew;
            producto.setVentaList(ventaListNew);
            producto = em.merge(producto);
            if (idCategoriasOld != null && !idCategoriasOld.equals(idCategoriasNew)) {
                idCategoriasOld.getProductoList().remove(producto);
                idCategoriasOld = em.merge(idCategoriasOld);
            }
            if (idCategoriasNew != null && !idCategoriasNew.equals(idCategoriasOld)) {
                idCategoriasNew.getProductoList().add(producto);
                idCategoriasNew = em.merge(idCategoriasNew);
            }
            if (idProveedoresOld != null && !idProveedoresOld.equals(idProveedoresNew)) {
                idProveedoresOld.getProductoList().remove(producto);
                idProveedoresOld = em.merge(idProveedoresOld);
            }
            if (idProveedoresNew != null && !idProveedoresNew.equals(idProveedoresOld)) {
                idProveedoresNew.getProductoList().add(producto);
                idProveedoresNew = em.merge(idProveedoresNew);
            }
            for (Venta ventaListOldVenta : ventaListOld) {
                if (!ventaListNew.contains(ventaListOldVenta)) {
                    ventaListOldVenta.setIdProductos(null);
                    ventaListOldVenta = em.merge(ventaListOldVenta);
                }
            }
            for (Venta ventaListNewVenta : ventaListNew) {
                if (!ventaListOld.contains(ventaListNewVenta)) {
                    Producto oldIdProductosOfVentaListNewVenta = ventaListNewVenta.getIdProductos();
                    ventaListNewVenta.setIdProductos(producto);
                    ventaListNewVenta = em.merge(ventaListNewVenta);
                    if (oldIdProductosOfVentaListNewVenta != null && !oldIdProductosOfVentaListNewVenta.equals(producto)) {
                        oldIdProductosOfVentaListNewVenta.getVentaList().remove(ventaListNewVenta);
                        oldIdProductosOfVentaListNewVenta = em.merge(oldIdProductosOfVentaListNewVenta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = producto.getIdProductos();
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
                producto.getIdProductos();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producto with id " + id + " no longer exists.", enfe);
            }
            Categoria idCategorias = producto.getIdCategorias();
            if (idCategorias != null) {
                idCategorias.getProductoList().remove(producto);
                idCategorias = em.merge(idCategorias);
            }
            Proveedore idProveedores = producto.getIdProveedores();
            if (idProveedores != null) {
                idProveedores.getProductoList().remove(producto);
                idProveedores = em.merge(idProveedores);
            }
            List<Venta> ventaList = producto.getVentaList();
            for (Venta ventaListVenta : ventaList) {
                ventaListVenta.setIdProductos(null);
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
