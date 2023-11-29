<%@page import="com.emergentes.entidades.Producto"%>
<%@page import="java.util.List"%>
<%
    List<Producto> productos =(List<Producto>) request.getAttribute("productos");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Listado de Productos</h1>
        <p><a href="ProductoServlet?action=add">Nuevo</a></p>
        <table border="1">
            <tr>
                <th>Id</th>
                <th>Descripcion</th>
                <th>Precio</th>
                <th>Categoria</th>
                <th>Proveedor</th>
                <th></th>
                <th></th>
            </tr>
            <%
                for (Producto item : productos){
            %>
            <tr>
                <td><%= item.getIdProductos() %></td>
                <td><%= item.getDescripcion() %></td>
                <td><%= item.getPrecio() %></td>
                <td><%= item.getIdCategorias().getDescripcion() %></td>
                <td><%= item.getIdProveedores().getNombre() %></td>
                <td><a href="ProductoServlet?action=edit&id=<%= item.getIdProductos() %>">Editar</a></td>
                <td><a href="ProductoServlet?action=dele&id=<%= item.getIdProductos() %>" onclick="return(confirm('Estas seguro de eliminar ?'))" >Eliminar</a></td>   
            </tr>
            <%
                }
            %>
        </table>
    </body>
</html>
