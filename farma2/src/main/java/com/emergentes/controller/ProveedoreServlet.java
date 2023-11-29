
package com.emergentes.controller;

import com.emergentes.bean.BeanProveedore;
import com.emergentes.entidades.Proveedore;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "ProveedoreServlet", urlPatterns = {"/ProveedoreServlet"})
public class ProveedoreServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int id;
        BeanProveedore daoProveedore = new BeanProveedore();
        Proveedore prov = new Proveedore();
        String action =(request.getParameter("action") != null) ? request.getParameter("action") : "view";
        
        switch(action){
            
            case "add":
                request.setAttribute("proveedore", prov);
                request.getRequestDispatcher("proveedore-edit.jsp").forward(request, response);
                break;
            
                
            case "edit":
                id= Integer.parseInt(request.getParameter("id"));
                prov = daoProveedore.buscar(id);
                request.setAttribute("proveedore", prov);
                request.getRequestDispatcher("proveedore-edit.jsp").forward(request, response);
                break;
            
            
            case "dele":
                id= Integer.parseInt(request.getParameter("id"));
                daoProveedore.eliminar(id);
                response.sendRedirect("ProveedoreServlet");
                break;
            
                
            case "view":
                List<Proveedore> lista = daoProveedore.listarTodos();
                request.setAttribute("proveedores", lista);
                request.getRequestDispatcher("proveedores.jsp").forward(request, response);
                break;
        }
        
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        BeanProveedore daoProveedore = new BeanProveedore();
        
        int id= Integer.parseInt(request.getParameter("id"));
        String nombre = request.getParameter("nombre");
        String direccion = request.getParameter("direccion");
        int telefono = Integer.parseInt(request.getParameter("telefono"));
        
        Proveedore prov = new Proveedore();
        prov.setId(id);
        prov.setNombre(nombre);
        prov.setDireccion(direccion);
        prov.setTelefono(telefono);
        
        if (id > 0){
            daoProveedore.editar(prov);
        }
        else{
            daoProveedore.insertar(prov);
        }
        response.sendRedirect("ProveedoreServlet");
        
    }

}
