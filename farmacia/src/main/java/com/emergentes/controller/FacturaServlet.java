
package com.emergentes.controller;

import com.emergentes.Bean.BeanCliente;
import com.emergentes.Bean.BeanFactura;
import com.emergentes.Bean.BeanFactura;
import com.emergentes.entidades.Cliente;
import com.emergentes.entidades.Factura;
import com.emergentes.entidades.Factura;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "FacturaServlet", urlPatterns = {"/FacturaServlet"})
public class FacturaServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int id;
        BeanFactura daoFactura = new BeanFactura();
        BeanCliente daoCliente = new BeanCliente();
        
        Factura fac = new Factura();
        List<Cliente> lista;
        
        String action =(request.getParameter("action") != null) ? request.getParameter("action") : "view";
        
        switch(action){
            
            case "add":
                lista = daoCliente.listarTodos();
                request.setAttribute("clientes", lista);
                request.setAttribute("factura", fac);
                request.getRequestDispatcher("factura-edit.jsp").forward(request, response);
                break;
            
                
            case "edit":
                id= Integer.parseInt(request.getParameter("id"));
                fac = daoFactura.buscar(id);
                
                lista = daoCliente.listarTodos();
                request.setAttribute("clientes", lista);
                
                request.setAttribute("factura", fac);
                request.getRequestDispatcher("factura-edit.jsp").forward(request, response);
                break;
            
            
            case "dele":
                id= Integer.parseInt(request.getParameter("id"));
                daoFactura.eliminar(id);
                response.sendRedirect("FacturaServlet");
                break;
            
                
            case "view":
                List<Factura> facturas = daoFactura.listarTodos();
                request.setAttribute("facturas", facturas);
                request.getRequestDispatcher("facturas.jsp").forward(request, response);
                break;
        } 
        
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        BeanFactura daoFactura = new BeanFactura();
        BeanCliente daoCliente = new BeanCliente();
        
        int id= Integer.parseInt(request.getParameter("id"));
        
        //String fecha = request.getParameter("fecha");
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        
        Cliente cli = daoCliente.buscar(idCliente);
        
        Factura fac = new Factura();
        fac.setIdFacturas(id);
        //fac.setFecha(fecha);
        fac.setIdClientes(cli);
        
        
        if (id > 0){
            daoFactura.editar(fac);
        }
        else{
            daoFactura.insertar(fac);
        }
        response.sendRedirect("FacturaServlet");
    }


}
