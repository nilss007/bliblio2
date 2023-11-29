<%@page import="com.emergentes.entidades.Proveedore"%>
<%
    Proveedore pro = (Proveedore)request.getAttribute("proveedore");
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
            <input type="hidden" name="id" value="<%= pro.getIdProveedores() %>">
            <input type="text" name="nombre" value="<%= pro.getNombre() %>">
            <input type="text" name="direccion" value="<%= pro.getDireccion() %>">
            <input type="number" name="telefono" value="<%= pro.getTelefono() %>">
            <input type="submit">
        </form>
    </body>
</html>
