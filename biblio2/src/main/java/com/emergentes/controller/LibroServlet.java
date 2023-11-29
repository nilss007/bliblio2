/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.emergentes.controller;

import com.emergentes.Bean.BeanCategoria;
import com.emergentes.Bean.BeanLibro;
import com.emergentes.Bean.BeanLibro;
import com.emergentes.entidades.Categoria;
import com.emergentes.entidades.Libro;
import com.emergentes.entidades.Libro;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "LibroServlet", urlPatterns = {"/LibroServlet"})
public class LibroServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int id;
        BeanLibro daoLibro =new BeanLibro();
        BeanCategoria daoCategoria = new BeanCategoria();
        
        Libro libro = new Libro();
        List<Categoria> lista;
        
        
        String action = (request.getParameter("action") !=null) ?request.getParameter("action"):"view";
        
        switch(action){
            case "add":
                lista = daoCategoria.listarTodos();
                request.setAttribute("categorias", lista);
                request.setAttribute("libro", libro);
                request.getRequestDispatcher("libro-edit.jsp").forward(request, response);
                break;
                
            
            case "edit":
                id = Integer.parseInt(request.getParameter("id"));
                libro = daoLibro.buscar(id);
                
                lista = daoCategoria.listarTodos();
                request.setAttribute("categorias", lista);
                
                request.setAttribute("libro", libro);
                request.getRequestDispatcher("libro-edit.jsp").forward(request, response);
                 break;
                 
            
            case "dele":
                id = Integer.parseInt(request.getParameter("id"));
                daoLibro.eliminar(id);
                response.sendRedirect("LibroServlet");
                break;
            
                
            case "view":
                List<Libro> libros = daoLibro.listarTodos();
                request.setAttribute("libros", libros);
                request.getRequestDispatcher("libros.jsp").forward(request, response);
                break;
        }
        
    }

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        BeanLibro daoLibro = new BeanLibro();
        BeanCategoria daoCategoria = new BeanCategoria(); 
        
        int id = Integer.parseInt(request.getParameter("id"));
        String titulo = request.getParameter("titulo");
        String autor = request.getParameter("autor");
        int disponible = Integer.parseInt(request.getParameter("disponible"));
        int categoria_id = Integer.parseInt(request.getParameter("categoria_id"));
        
        Categoria cate = daoCategoria.buscar(categoria_id);
        
        Libro libro = new Libro();
        libro.setId(id);
        libro.setTitulo(titulo);
        libro.setAutor(autor);
        libro.setDisponible(disponible);
        libro.setCategoriaId(cate);
        
        if (id > 0){
            daoLibro.editar(libro);
        }
        else{
            daoLibro.insertar(libro);
        }
        response.sendRedirect("LibroServlet");
        
    }
}
