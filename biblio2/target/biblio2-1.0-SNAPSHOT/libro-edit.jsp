<%-- 
    Document   : libro-edit
    Created on : 19 nov de 2023, 17:05:08
    Author     : HOMELADER
--%>

<%@page import="java.util.List"%>
<%@page import="com.emergentes.entidades.Categoria"%>
<%@page import="com.emergentes.entidades.Libro"%>
<%
    Libro libro = (Libro)request.getAttribute("libro");
    List<Categoria> categorias = (List<Categoria>) request.getAttribute("categorias");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Registro de Libro</h1>
        <form action="LibroServlet" method="post">
            <input type="hidden" name="id" value="<%= libro.getId()%>">
            <table>
                <tr>
                    <td>Titulo:</td>
                    <td><input type="text" name="titulo" value="<%= libro.getTitulo()%>" ></td>
                </tr>
                <tr>
                    <td>Autor:</td>
                    <td><input type="text" name="autor" value="<%= libro.getAutor()%>" ></td>
                </tr>
                <tr>
                    <td>Disponible:</td>
                    <td>    
                        <input type="radio" name="disponible" value="0"
                               <%= (libro.getDisponible() == 0) ? "checked" : ""%>> No disponible
                        <input type="radio" name="disponible" value="1"
                               <%= (libro.getDisponible() >= 1) ? "checked" : ""%>> Disponible
                    </td>
                </tr>
                <tr>
                    <td>Categoria:</td>
                    <td>
                        <select name="categoria_id">

                            <%
                                for (Categoria item : categorias) {
                            %>
                            <option value="<%= item.getId()%>" 
                                    <%= (item.getId() == libro.getCategoriaId().getId()) ? "selected" : ""%>
                                    ><%= item.getDescripcion()%></option>
                            <%
                                }
                            %>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit"></td>
                </tr>
            </table>
        </form>        
    </body>
</html>
