<%-- 
    Document   : index
    Created on : Oct 16, 2017, 8:49:20 AM
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
        <h1>List of product</h1>
        <p><a href="">add new</a></p>
        <c:if test="${products.size()!=0}">
            <table>
                <tr>
                    <th>Code</th>
                    <th>Description</th>
                    <th>Price</th>
                    <th>Action</th>
                </tr>
                <c:forEach var="p" items="${products}">
                    <tr>
                        <td><c:out value="${p.code}"/></td>
                        <td><c:out value="${p.description}"/></td>
                        <td><c:out value="${p.formattedPrice}"/></td>
                        <td><form method="post" action="cartServlet">
                                <input type="hidden" name="code" value="${p.code}">
                                <input type="submit" value="add to cart">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <c:if test="${products.size()==0}">
            <p>There are no product</p>
        </c:if>
            <h1><a href="invoiceServlet">View Invoice LineItem</a></h1>
    </body>
</html>
