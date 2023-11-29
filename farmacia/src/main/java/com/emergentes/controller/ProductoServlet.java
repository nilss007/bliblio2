
package com.emergentes.controller;

import com.emergentes.Bean.BeanCategoria;
import com.emergentes.Bean.BeanProducto;
import com.emergentes.Bean.BeanProducto;
import com.emergentes.Bean.BeanProveedore;
import com.emergentes.entidades.Categoria;
import com.emergentes.entidades.Producto;
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
        int idCategoria = Integer.parseInt(request.getParameter("idCategoria"));
        Categoria cate = daoCategoria.buscar(idCategoria);
        int idProveedore = Integer.parseInt(request.getParameter("idProveedore"));
        Proveedore prov = daoProveedore.buscar(idProveedore);

        
        Producto pro = new Producto();
        pro.setIdProductos(id);
        pro.setDescripcion(descripcion);
        pro.setPrecio(precio);
        pro.setIdCategorias(cate);
        pro.setIdProveedores(prov);
        
        
        if (id > 0){
            daoProducto.editar(pro);
        }
        else{
            daoProducto.insertar(pro);
        }
        response.sendRedirect("ProductoServlet");
        
    }

}
