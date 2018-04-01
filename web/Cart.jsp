<%-- 
    Document   : Cart
    Created on : Oct 16, 2017, 9:32:42 AM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Your Cart</h1>
        <table>
            <tr>
                <th>Quantity</th>
                <th>Description</th>
                <th>Price</th>
                <th>Amount</th>
                <th>delete</th>
            </tr>
            <c:forEach var="item" items="${cart.items}">
                <tr>
                    <td><form action="cartServlet" method="post">
                            <input type="hidden" name="code" value="${item.product.code}">
                            <input type="text" name="quantity" value="${item.quantity}">
                            <input type="submit" name="action" value="Update">
                        </form></td>
                    <td>${item.product.description}</td>
                    <td>${item.product.formattedPrice}</td>
                    <td>${item.totalFormatted}</td>

                    <td>
                        <form action="" method="post">
                            <input type="hidden" name="code" value="${item.product.code}">
                            <input type="hidden" name="quantity" value="0">
                            <input type="submit" value="remove Item">
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <c:if test="${cart.count == 1}">
            <p>you have 1 item in your cart</p>
        </c:if>
        <c:if test="${cart.count > 1}">
            <p>you have ${cart.count} items in your cart.</p>
        </c:if>
        <form action="cartServlet" method="post">
            <input type="hidden" name="action" value="shop">
            <input type="submit" value="continue buy">         
        </form>
        <p></p>
        
        <form action="cartServlet" method="post">
            <input type="hidden" name="action" value="checkout">
            <label>Customer Name:</label>       
            <input type="text" name="customerName"> 
            <input type="submit" value="Checkout"> 
            
        </form>
    </body>
</html>
