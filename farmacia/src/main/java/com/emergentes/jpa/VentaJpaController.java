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
            Factura idFacturas = venta.getIdFacturas();
            if (idFacturas != null) {
                idFacturas = em.getReference(idFacturas.getClass(), idFacturas.getIdFacturas());
                venta.setIdFacturas(idFacturas);
            }
            Producto idProductos = venta.getIdProductos();
            if (idProductos != null) {
                idProductos = em.getReference(idProductos.getClass(), idProductos.getIdProductos());
                venta.setIdProductos(idProductos);
            }
            em.persist(venta);
            if (idFacturas != null) {
                idFacturas.getVentaList().add(venta);
                idFacturas = em.merge(idFacturas);
            }
            if (idProductos != null) {
                idProductos.getVentaList().add(venta);
                idProductos = em.merge(idProductos);
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
            Venta persistentVenta = em.find(Venta.class, venta.getIdVentas());
            Factura idFacturasOld = persistentVenta.getIdFacturas();
            Factura idFacturasNew = venta.getIdFacturas();
            Producto idProductosOld = persistentVenta.getIdProductos();
            Producto idProductosNew = venta.getIdProductos();
            if (idFacturasNew != null) {
                idFacturasNew = em.getReference(idFacturasNew.getClass(), idFacturasNew.getIdFacturas());
                venta.setIdFacturas(idFacturasNew);
            }
            if (idProductosNew != null) {
                idProductosNew = em.getReference(idProductosNew.getClass(), idProductosNew.getIdProductos());
                venta.setIdProductos(idProductosNew);
            }
            venta = em.merge(venta);
            if (idFacturasOld != null && !idFacturasOld.equals(idFacturasNew)) {
                idFacturasOld.getVentaList().remove(venta);
                idFacturasOld = em.merge(idFacturasOld);
            }
            if (idFacturasNew != null && !idFacturasNew.equals(idFacturasOld)) {
                idFacturasNew.getVentaList().add(venta);
                idFacturasNew = em.merge(idFacturasNew);
            }
            if (idProductosOld != null && !idProductosOld.equals(idProductosNew)) {
                idProductosOld.getVentaList().remove(venta);
                idProductosOld = em.merge(idProductosOld);
            }
            if (idProductosNew != null && !idProductosNew.equals(idProductosOld)) {
                idProductosNew.getVentaList().add(venta);
                idProductosNew = em.merge(idProductosNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = venta.getIdVentas();
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
                venta.getIdVentas();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The venta with id " + id + " no longer exists.", enfe);
            }
            Factura idFacturas = venta.getIdFacturas();
            if (idFacturas != null) {
                idFacturas.getVentaList().remove(venta);
                idFacturas = em.merge(idFacturas);
            }
            Producto idProductos = venta.getIdProductos();
            if (idProductos != null) {
                idProductos.getVentaList().remove(venta);
                idProductos = em.merge(idProductos);
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
