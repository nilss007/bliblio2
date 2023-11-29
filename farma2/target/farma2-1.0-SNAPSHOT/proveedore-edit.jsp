<%@page import="com.emergentes.entidades.Proveedore"%>
<%
    Proveedore prov = (Proveedore)request.getAttribute("proveedore");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Registrar Proveedor</h1>
        <form action="ProveedoreServlet" method="post">
            <label>Descripcion</label>
            <input type="hidden" name="id" value="<%= prov.getId() %>">
            <input type="text" name="nombre" value="<%= prov.getNombre() %>">
            <input type="text" name="direccion" value="<%= prov.getDireccion() %>">
            <input type="number" name="telefono" value="<%= prov.getTelefono() %>">
            <input type="submit">
        </form>
    </body>
</html>
