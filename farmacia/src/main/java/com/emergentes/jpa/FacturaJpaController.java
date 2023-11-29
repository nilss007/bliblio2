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
            Cliente idClientes = factura.getIdClientes();
            if (idClientes != null) {
                idClientes = em.getReference(idClientes.getClass(), idClientes.getIdClientes());
                factura.setIdClientes(idClientes);
            }
            List<Venta> attachedVentaList = new ArrayList<Venta>();
            for (Venta ventaListVentaToAttach : factura.getVentaList()) {
                ventaListVentaToAttach = em.getReference(ventaListVentaToAttach.getClass(), ventaListVentaToAttach.getIdVentas());
                attachedVentaList.add(ventaListVentaToAttach);
            }
            factura.setVentaList(attachedVentaList);
            em.persist(factura);
            if (idClientes != null) {
                idClientes.getFacturaList().add(factura);
                idClientes = em.merge(idClientes);
            }
            for (Venta ventaListVenta : factura.getVentaList()) {
                Factura oldIdFacturasOfVentaListVenta = ventaListVenta.getIdFacturas();
                ventaListVenta.setIdFacturas(factura);
                ventaListVenta = em.merge(ventaListVenta);
                if (oldIdFacturasOfVentaListVenta != null) {
                    oldIdFacturasOfVentaListVenta.getVentaList().remove(ventaListVenta);
                    oldIdFacturasOfVentaListVenta = em.merge(oldIdFacturasOfVentaListVenta);
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
            Factura persistentFactura = em.find(Factura.class, factura.getIdFacturas());
            Cliente idClientesOld = persistentFactura.getIdClientes();
            Cliente idClientesNew = factura.getIdClientes();
            List<Venta> ventaListOld = persistentFactura.getVentaList();
            List<Venta> ventaListNew = factura.getVentaList();
            if (idClientesNew != null) {
                idClientesNew = em.getReference(idClientesNew.getClass(), idClientesNew.getIdClientes());
                factura.setIdClientes(idClientesNew);
            }
            List<Venta> attachedVentaListNew = new ArrayList<Venta>();
            for (Venta ventaListNewVentaToAttach : ventaListNew) {
                ventaListNewVentaToAttach = em.getReference(ventaListNewVentaToAttach.getClass(), ventaListNewVentaToAttach.getIdVentas());
                attachedVentaListNew.add(ventaListNewVentaToAttach);
            }
            ventaListNew = attachedVentaListNew;
            factura.setVentaList(ventaListNew);
            factura = em.merge(factura);
            if (idClientesOld != null && !idClientesOld.equals(idClientesNew)) {
                idClientesOld.getFacturaList().remove(factura);
                idClientesOld = em.merge(idClientesOld);
            }
            if (idClientesNew != null && !idClientesNew.equals(idClientesOld)) {
                idClientesNew.getFacturaList().add(factura);
                idClientesNew = em.merge(idClientesNew);
            }
            for (Venta ventaListOldVenta : ventaListOld) {
                if (!ventaListNew.contains(ventaListOldVenta)) {
                    ventaListOldVenta.setIdFacturas(null);
                    ventaListOldVenta = em.merge(ventaListOldVenta);
                }
            }
            for (Venta ventaListNewVenta : ventaListNew) {
                if (!ventaListOld.contains(ventaListNewVenta)) {
                    Factura oldIdFacturasOfVentaListNewVenta = ventaListNewVenta.getIdFacturas();
                    ventaListNewVenta.setIdFacturas(factura);
                    ventaListNewVenta = em.merge(ventaListNewVenta);
                    if (oldIdFacturasOfVentaListNewVenta != null && !oldIdFacturasOfVentaListNewVenta.equals(factura)) {
                        oldIdFacturasOfVentaListNewVenta.getVentaList().remove(ventaListNewVenta);
                        oldIdFacturasOfVentaListNewVenta = em.merge(oldIdFacturasOfVentaListNewVenta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = factura.getIdFacturas();
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
                factura.getIdFacturas();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The factura with id " + id + " no longer exists.", enfe);
            }
            Cliente idClientes = factura.getIdClientes();
            if (idClientes != null) {
                idClientes.getFacturaList().remove(factura);
                idClientes = em.merge(idClientes);
            }
            List<Venta> ventaList = factura.getVentaList();
            for (Venta ventaListVenta : ventaList) {
                ventaListVenta.setIdFacturas(null);
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
