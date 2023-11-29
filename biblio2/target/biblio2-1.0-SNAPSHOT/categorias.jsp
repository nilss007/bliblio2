<%-- 
    Document   : categorias
    Created on : 19 nov de 2023, 15:37:01
    Author     : HOMELADER
--%>

<%@page import="java.util.List"%>
<%@page import="com.emergentes.entidades.Categoria"%>
<%
    List<Categoria> categorias = (List<Categoria>)request.getAttribute("categorias");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Listado de Categorias</h1>
        <p><a href="CategoriaServlet?action=add">Nuevo</a></p>
        <table border="1">
            <tr>
                <th>Id</th>
                <th>Descripcion</th>
                <td></td>
                <td></td>
            </tr>
            <%
                for(Categoria item : categorias){
            %>
            <tr>
                <td><%= item.getId() %></td>
                <td><%= item.getDescripcion() %></td>
                <td><a href="CategoriaServlet?action=edit&id=<%= item.getId() %>">Editar</a></td>
                <td><a href="CategoriaServlet?action=dele&id=<%= item.getId() %>" onclick="return(confirm('Esta seguro de eliminar ?'))" >Eliminar</a></td>
                <td></td>
            </tr>
            <%
                }
            %>
        </table>
    </body>
</html>
