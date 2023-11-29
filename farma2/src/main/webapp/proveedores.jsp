<%@page import="com.emergentes.entidades.Proveedore"%>
<%@page import="java.util.List"%>
<%
    List<Proveedore> proveedores = (List<Proveedore>)request.getAttribute("proveedores");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Listado de Proveedores</h1>
        <p><a href="ProveedoreServlet?action=add">Nuevo</a></p>
        <table border="1">
            <tr>
                <th>Id</th>
                <th>Nombre</th>
                <th>Direccion</th>
                <th>Telefono</th>
                <th></th>
                <th></th>
            </tr>
            <%
                for (Proveedore item : proveedores){
            %>

            <tr>
                <td><%= item.getId() %></td>
                <td><%= item.getNombre() %></td>
                <td><%= item.getDireccion() %></td>
                <td><%= item.getTelefono() %></td>
                <th><a href="ProveedoreServlet?action=edit&id=<%= item.getId() %>">Editar</a></th>
                <th><a href="ProveedoreServlet?action=dele&id=<%= item.getId() %>" onclick="return (confirm('Esta seguro de eliminar ?'))" >Eliminar</a></th>
            </tr>
            <%
                }
            %>
        </table>
        <p><a href="index.jsp">Volver al incio</a></p>
    </body>
</html>
