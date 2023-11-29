<%-- 
    Document   : index
    Created on : 20 nov de 2023, 20:08:35
    Author     : HOMELADER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Farmacia Nilss</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Tangerine">
    <style>
        body {
            margin: 0;
            padding: 0;
            background-image: url('https://static.vecteezy.com/system/resources/previews/003/476/771/non_2x/pharmacy-background-medicine-objects-pills-on-wooden-table-top-view-template-for-web-banner-of-pharmacy-sale-or-advertising-vector.jpg'); /* Reemplaza 'tu_imagen.jpg' con la ruta de tu imagen */
            background-size: cover;
            background-position: center;
            font-family: 'Tangerine', serif; /* Cambia 'Open Sans' según tus preferencias */
            color: #D713E1; /* Color del texto en la página */
            font-size: 30px;
        }

        h1 {
            text-align: center;
            padding: 20px;
        }

        ul {
            list-style-type: none;
            margin: 0;
            padding: 0;
            text-align: center;
        }

        li {
            display: inline;
            margin-right: 50px;
        }

        select {
            padding: 5px;
        }
    </style>
</head>
<body>

    <h1>Farmacia Nilss</h1>

    <ul>
        <li><a href="CategoriaServlet">Categorias</a></li>
        <li><a href="ClienteServlet">Clientes</a></li>
        <li><a href="ProveedoreServlet">Proveedores</a></li>
        <li><a href="FacturaServlet">Facturas</a></li>
        <li><a href="ProductoServlet">Productos</a></li>
        <li><a href="VentaServlet">Ventas</a></li>
    </ul>

    <select>
        <option value="CategoriaServlet">Categorias</option>
        <option value="ClienteServlet">Clientes</option>
        <option value="ProveedoreServlet">Proveedores</option>
        <option value="FacturaServlet">Facturas</option>
        <option value="ProductoServlet">Productos</option>
        <option value="VentaServlet">Ventas</option>
    </select>

</body>
</html>
