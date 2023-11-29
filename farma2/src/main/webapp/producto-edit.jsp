<%@page import="com.emergentes.entidades.Proveedore"%>
<%@page import="com.emergentes.entidades.Categoria"%>
<%@page import="java.util.List"%>
<%@page import="com.emergentes.entidades.Producto"%>
<%
    Producto prod = (Producto) request.getAttribute("producto");
    List<Categoria> categorias = (List<Categoria>) request.getAttribute("categorias");
    List<Proveedore> proveedores = (List<Proveedore>) request.getAttribute("proveedores");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Registro de Producto</h1>
        <form action="ProductoServlet" method="post">
            <input type="hidden" name="id" value="<%= prod.getId() %>">
            <table>
                <tr>
                    <td>Descripcion:</td>
                    <td><input type="text" name="descripcion" value="<%= prod.getDescripcion()%>"></td>
                </tr>
                <tr>
                    <td>Precio:</td>
                    <td><input type="number" name="precio" value="<%= prod.getPrecio()%>" min="0"></td>
                </tr>
                <tr>
                    <td>Disponible:</td>
                    <td>
                        <input type="radio" name="disponible" value="0"
                               <%= (prod.getDisponible() == 0) ? "chequed":"" %>> No disponible
                        <input type="radio" name="disponible" value="1"
                               <%= (prod.getDisponible() == 1) ? "chequed":"" %>> Disponible
                    </td>
                </tr>
                <tr>
                    <td>Categoria:</td>
                    <td>
                        <select name="Id">
                            <%
                                if (categorias != null) {
                                    for (Categoria item : categorias) {
                            %>
                            <option value="<%= item.getId()%>" 
                                    <%= (item.getId() == prod.getCategoriasId().getId()) ? "selected":""%>
                                    ><%= item.getDescripcion()%></option>
                            <%
                                    }
                                }

                            %>
                    </td>
                </tr>
                <tr>
                    <td>Proveedor:</td>
                    <td>
                        <select name="Id">
                            <%
                                if (proveedores != null) {
                                    for (Proveedore item : proveedores) {
                            %>
                            <option value="<%= item.getId()%>" 
                                    <%= (item.getId() == prod.getProveedoresId().getId()) ? "selected" : ""%>
                                    ><%= item.getNombre()%></option>
                            <%
                                    }
                                }

                            %>
                    </td>
                </tr>
            </table>
            <input type="submit">
        </form>
    </body>
</html>
