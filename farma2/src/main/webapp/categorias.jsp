<%@page import="com.emergentes.entidades.Categoria"%>
<%@page import="java.util.List"%>
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
                <th></th>
                <th></th>
            </tr>
            <%
                for (Categoria item : categorias){
            %>
            <tr>
                <td><%= item.getId() %></td>
                <td><%= item.getDescripcion() %></td>
                <th><a href="CategoriaServlet?action=edit&id=<%= item.getId() %>">Editar</a></th>
                <th><a href="CategoriaServlet?action=dele&id=<%= item.getId() %>" onclick="return (confirm('Esta seguro de eliminar ?'))" >Eliminar</a></th>
            </tr>
            <%
                }
            %>
        </table>
        <p><a href="index.jsp">Volver al incio</a></p>
    </body>
</html>
