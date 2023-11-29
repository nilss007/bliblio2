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
import com.emergentes.entidades.Factura;
import com.emergentes.entidades.Producto;
import com.emergentes.entidades.Venta;
import com.emergentes.jpa.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author HOMELADER
 */
public class VentaJpaController implements Serializable {

    public VentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Venta venta) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Factura facturasId = venta.getFacturasId();
            if (facturasId != null) {
                facturasId = em.getReference(facturasId.getClass(), facturasId.getId());
                venta.setFacturasId(facturasId);
            }
            Producto productosId = venta.getProductosId();
            if (productosId != null) {
                productosId = em.getReference(productosId.getClass(), productosId.getId());
                venta.setProductosId(productosId);
            }
            em.persist(venta);
            if (facturasId != null) {
                facturasId.getVentaList().add(venta);
                facturasId = em.merge(facturasId);
            }
            if (productosId != null) {
                productosId.getVentaList().add(venta);
                productosId = em.merge(productosId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Venta venta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Venta persistentVenta = em.find(Venta.class, venta.getId());
            Factura facturasIdOld = persistentVenta.getFacturasId();
            Factura facturasIdNew = venta.getFacturasId();
            Producto productosIdOld = persistentVenta.getProductosId();
            Producto productosIdNew = venta.getProductosId();
            if (facturasIdNew != null) {
                facturasIdNew = em.getReference(facturasIdNew.getClass(), facturasIdNew.getId());
                venta.setFacturasId(facturasIdNew);
            }
            if (productosIdNew != null) {
                productosIdNew = em.getReference(productosIdNew.getClass(), productosIdNew.getId());
                venta.setProductosId(productosIdNew);
            }
            venta = em.merge(venta);
            if (facturasIdOld != null && !facturasIdOld.equals(facturasIdNew)) {
                facturasIdOld.getVentaList().remove(venta);
                facturasIdOld = em.merge(facturasIdOld);
            }
            if (facturasIdNew != null && !facturasIdNew.equals(facturasIdOld)) {
                facturasIdNew.getVentaList().add(venta);
                facturasIdNew = em.merge(facturasIdNew);
            }
            if (productosIdOld != null && !productosIdOld.equals(productosIdNew)) {
                productosIdOld.getVentaList().remove(venta);
                productosIdOld = em.merge(productosIdOld);
            }
            if (productosIdNew != null && !productosIdNew.equals(productosIdOld)) {
                productosIdNew.getVentaList().add(venta);
                productosIdNew = em.merge(productosIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = venta.getId();
                if (findVenta(id) == null) {
                    throw new NonexistentEntityException("The venta with id " + id + " no longer exists.");
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
            Venta venta;
            try {
                venta = em.getReference(Venta.class, id);
                venta.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The venta with id " + id + " no longer exists.", enfe);
            }
            Factura facturasId = venta.getFacturasId();
            if (facturasId != null) {
                facturasId.getVentaList().remove(venta);
                facturasId = em.merge(facturasId);
            }
            Producto productosId = venta.getProductosId();
            if (productosId != null) {
                productosId.getVentaList().remove(venta);
                productosId = em.merge(productosId);
            }
            em.remove(venta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Venta> findVentaEntities() {
        return findVentaEntities(true, -1, -1);
    }

    public List<Venta> findVentaEntities(int maxResults, int firstResult) {
        return findVentaEntities(false, maxResults, firstResult);
    }

    private List<Venta> findVentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Venta.class));
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

    public Venta findVenta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Venta.class, id);
        } finally {
            em.close();
        }
    }

    public int getVentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Venta> rt = cq.from(Venta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
