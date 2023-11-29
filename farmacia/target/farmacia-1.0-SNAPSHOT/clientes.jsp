<%@page import="com.emergentes.entidades.Cliente"%>
<%@page import="java.util.List"%>
<%
    List<Cliente> clientes = (List<Cliente>)request.getAttribute("clientes");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Listado de Clientes</h1>
        <p><a href="ClienteServlet?action=add">Nuevo</a></p>
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
                for (Cliente item : clientes){
            %>

            <tr>
                <td><%= item.getIdClientes() %></td>
                <td><%= item.getNombre() %></td>
                <td><%= item.getDireccion() %></td>
                <td><%= item.getTelefono() %></td>
                <th><a href="ClienteServlet?action=edit&id=<%= item.getIdClientes() %>">Editar</a></th>
                <th><a href="ClienteServlet?action=dele&id=<%= item.getIdClientes() %>" onclick="return (confirm('Esta seguro de eliminar ?'))" >Eliminar</a></th>
            </tr>
            <%
                }
            %>
        </table>
        <p><a href="index.jsp">Volver al incio</a></p>
    </body>
</html>
