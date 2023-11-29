<%@page import="com.emergentes.entidades.Proveedore"%>
<%@page import="java.util.List"%>
<%@page import="com.emergentes.entidades.Categoria"%>
<%@page import="com.emergentes.entidades.Producto"%>
<%
    Producto produ = (Producto) request.getAttribute("producto");
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
            <input type="hidden" name="id" value="<%= produ.getIdProductos() %>">
            <table>
                <tr>
                    <td>Descripcion:</td>
                    <td><input type="text" name="descripcion" value="<%= produ.getDescripcion()%>"></td>
                </tr>
                <tr>
                    <td>Precio:</td>
                    <td><input type="number" name="precio" value="<%= produ.getPrecio()%>" min="0"></td>
                </tr>
                <tr>
                    <td>Categoria:</td>
                    <td>
                        <select name="idCategoria">
                            <%
                                
                                    for (Categoria item : categorias) {
                            %>
                            <option value="<%= item.getIdCategorias()%>" 
                                    <%= (item.getIdCategorias() == produ.getIdCategorias().getIdCategorias()) ? "selected" : ""%>
                                    ><%= item.getDescripcion()%></option>
                            <%
                                    }
                                

                            %>
                    </td>
                </tr>
                <tr>
                    <td>Proveedor:</td>
                    <td>
                        
                    </td>
                </tr>
            </table>
            <input type="submit">
        </form>
    </body>
</html>
