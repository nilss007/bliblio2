/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.emergentes.controller;

import com.emergentes.bean.BeanCategoria;
import com.emergentes.bean.BeanProducto;
import com.emergentes.bean.BeanProveedore;
import com.emergentes.entidades.Categoria;
import com.emergentes.entidades.Producto;
import com.emergentes.entidades.Proveedore;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author HOMELADER
 */
@WebServlet(name = "ProductoServlet", urlPatterns = {"/ProductoServlet"})
public class ProductoServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int id;
        BeanProducto daoProducto = new BeanProducto();
        BeanCategoria daoCategoria =new BeanCategoria();
        BeanProveedore daoProveedore =new BeanProveedore();
        
        Producto pro = new Producto();
        List<Categoria> lista1;
        List<Proveedore> lista2;
        
        String action =(request.getParameter("action") != null) ? request.getParameter("action") : "view";
        
        switch(action){
            
            case "add":
                lista1 = daoCategoria.listarTodos();
                request.setAttribute("categoria", lista1);
                
                lista2 = daoProveedore.listarTodos();
                request.setAttribute("proveedore", lista2);
                
                request.setAttribute("producto", pro);
                request.getRequestDispatcher("producto-edit.jsp").forward(request, response);
                break;
            
                
            case "edit":
                id= Integer.parseInt(request.getParameter("id"));
                pro = daoProducto.buscar(id);
                
                lista1 = daoCategoria.listarTodos();
                request.setAttribute("categoria", lista1);
                
                lista2 = daoProveedore.listarTodos();
                request.setAttribute("proveedore", lista2);
                
                request.setAttribute("producto", pro);
                request.getRequestDispatcher("producto-edit.jsp").forward(request, response);
                break;
            
            
            case "dele":
                id= Integer.parseInt(request.getParameter("id"));
                daoProducto.eliminar(id);
                response.sendRedirect("ProductoServlet");
                break;
            
                
            case "view":
                List<Producto> lista = daoProducto.listarTodos();
                request.setAttribute("productos", lista);
                request.getRequestDispatcher("productos.jsp").forward(request, response);
                break;
        }
        
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        BeanProducto daoProducto = new BeanProducto();
        BeanCategoria daoCategoria =new BeanCategoria();
        BeanProveedore daoProveedore =new BeanProveedore();
        
        int id= Integer.parseInt(request.getParameter("id"));
        String descripcion = request.getParameter("descripcion");
        BigDecimal precio = new BigDecimal(request.getParameter("precio"));
        int disponible = Integer.parseInt(request.getParameter("disponible"));
        int idCategoria = Integer.parseInt(request.getParameter("idCategoria"));
        Categoria cate = daoCategoria.buscar(idCategoria);
        int idProveedore = Integer.parseInt(request.getParameter("idProveedore"));
        Proveedore prove = daoProveedore.buscar(idProveedore);

        
        Producto prov = new Producto();
        prov.setId(id);
        prov.setDescripcion(descripcion);
        prov.setPrecio(precio);
        prov.setDisponible(disponible);
        prov.setCategoriasId(cate);
        prov.setProveedoresId(prove);
        
        
        if (id > 0){
            daoProducto.editar(prov);
        }
        else{
            daoProducto.insertar(prov);
        }
        response.sendRedirect("ProductoServlet");
        
    }

}
