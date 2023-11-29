<%@page import="com.emergentes.entidades.Cliente"%>
<%
    Cliente clie = (Cliente)request.getAttribute("cliente");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Registrar Cliente</h1>
        <form action="ClienteServlet" method="post">
            <label>Descripcion</label>
            <input type="hidden" name="id" value="<%= clie.getIdClientes() %>">
            <input type="text" name="nombre" value="<%= clie.getNombre()%>">
            <input type="text" name="direccion" value="<%= clie.getDireccion()%>">
            <input type="number" name="telefono" value="<%= clie.getTelefono()%>">
            <input type="submit">
        </form>
        
    </body>
</html>
