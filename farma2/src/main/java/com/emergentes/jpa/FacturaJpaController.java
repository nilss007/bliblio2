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
import com.emergentes.entidades.Cliente;
import com.emergentes.entidades.Factura;
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
public class FacturaJpaController implements Serializable {

    public FacturaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Factura factura) {
        if (factura.getVentaList() == null) {
            factura.setVentaList(new ArrayList<Venta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente clientesId = factura.getClientesId();
            if (clientesId != null) {
                clientesId = em.getReference(clientesId.getClass(), clientesId.getId());
                factura.setClientesId(clientesId);
            }
            List<Venta> attachedVentaList = new ArrayList<Venta>();
            for (Venta ventaListVentaToAttach : factura.getVentaList()) {
                ventaListVentaToAttach = em.getReference(ventaListVentaToAttach.getClass(), ventaListVentaToAttach.getId());
                attachedVentaList.add(ventaListVentaToAttach);
            }
            factura.setVentaList(attachedVentaList);
            em.persist(factura);
            if (clientesId != null) {
                clientesId.getFacturaList().add(factura);
                clientesId = em.merge(clientesId);
            }
            for (Venta ventaListVenta : factura.getVentaList()) {
                Factura oldFacturasIdOfVentaListVenta = ventaListVenta.getFacturasId();
                ventaListVenta.setFacturasId(factura);
                ventaListVenta = em.merge(ventaListVenta);
                if (oldFacturasIdOfVentaListVenta != null) {
                    oldFacturasIdOfVentaListVenta.getVentaList().remove(ventaListVenta);
                    oldFacturasIdOfVentaListVenta = em.merge(oldFacturasIdOfVentaListVenta);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Factura factura) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Factura persistentFactura = em.find(Factura.class, factura.getId());
            Cliente clientesIdOld = persistentFactura.getClientesId();
            Cliente clientesIdNew = factura.getClientesId();
            List<Venta> ventaListOld = persistentFactura.getVentaList();
            List<Venta> ventaListNew = factura.getVentaList();
            if (clientesIdNew != null) {
                clientesIdNew = em.getReference(clientesIdNew.getClass(), clientesIdNew.getId());
                factura.setClientesId(clientesIdNew);
            }
            List<Venta> attachedVentaListNew = new ArrayList<Venta>();
            for (Venta ventaListNewVentaToAttach : ventaListNew) {
                ventaListNewVentaToAttach = em.getReference(ventaListNewVentaToAttach.getClass(), ventaListNewVentaToAttach.getId());
                attachedVentaListNew.add(ventaListNewVentaToAttach);
            }
            ventaListNew = attachedVentaListNew;
            factura.setVentaList(ventaListNew);
            factura = em.merge(factura);
            if (clientesIdOld != null && !clientesIdOld.equals(clientesIdNew)) {
                clientesIdOld.getFacturaList().remove(factura);
                clientesIdOld = em.merge(clientesIdOld);
            }
            if (clientesIdNew != null && !clientesIdNew.equals(clientesIdOld)) {
                clientesIdNew.getFacturaList().add(factura);
                clientesIdNew = em.merge(clientesIdNew);
            }
            for (Venta ventaListOldVenta : ventaListOld) {
                if (!ventaListNew.contains(ventaListOldVenta)) {
                    ventaListOldVenta.setFacturasId(null);
                    ventaListOldVenta = em.merge(ventaListOldVenta);
                }
            }
            for (Venta ventaListNewVenta : ventaListNew) {
                if (!ventaListOld.contains(ventaListNewVenta)) {
                    Factura oldFacturasIdOfVentaListNewVenta = ventaListNewVenta.getFacturasId();
                    ventaListNewVenta.setFacturasId(factura);
                    ventaListNewVenta = em.merge(ventaListNewVenta);
                    if (oldFacturasIdOfVentaListNewVenta != null && !oldFacturasIdOfVentaListNewVenta.equals(factura)) {
                        oldFacturasIdOfVentaListNewVenta.getVentaList().remove(ventaListNewVenta);
                        oldFacturasIdOfVentaListNewVenta = em.merge(oldFacturasIdOfVentaListNewVenta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = factura.getId();
                if (findFactura(id) == null) {
                    throw new NonexistentEntityException("The factura with id " + id + " no longer exists.");
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
            Factura factura;
            try {
                factura = em.getReference(Factura.class, id);
                factura.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The factura with id " + id + " no longer exists.", enfe);
            }
            Cliente clientesId = factura.getClientesId();
            if (clientesId != null) {
                clientesId.getFacturaList().remove(factura);
                clientesId = em.merge(clientesId);
            }
            List<Venta> ventaList = factura.getVentaList();
            for (Venta ventaListVenta : ventaList) {
                ventaListVenta.setFacturasId(null);
                ventaListVenta = em.merge(ventaListVenta);
            }
            em.remove(factura);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Factura> findFacturaEntities() {
        return findFacturaEntities(true, -1, -1);
    }

    public List<Factura> findFacturaEntities(int maxResults, int firstResult) {
        return findFacturaEntities(false, maxResults, firstResult);
    }

    private List<Factura> findFacturaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Factura.class));
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

    public Factura findFactura(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Factura.class, id);
        } finally {
            em.close();
        }
    }

    public int getFacturaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Factura> rt = cq.from(Factura.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
