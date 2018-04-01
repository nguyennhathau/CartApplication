<%-- 
    Document   : listInvoice
    Created on : Oct 19, 2017, 9:28:41 AM
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
        <h1>List Invoice</h1>

        <table>
            <tr>
                <th>InvoiceID |</th>
                <th>Invoice Date |</th>
                <th>CustomerName |</th>
                <th>View detail</th>
            </tr>
            <c:forEach var="i" items="${invoices}">
                <tr>
                    <td><c:out value="${i.invoiceID}"/> </td>
                    <td><c:out value="${i.invoiceDate}"/> </td>
                    <td><c:out value="${i.customerName}"/> </td>
                    
                    <td><form method="post" action="cartServlet">
                             <input type="hidden" name="invoiceID" value="${i.invoiceID}">
                                <input type="submit" value="view">
                          </form>
                    </td>
                </tr>
            </c:forEach>
        </table>


    </body>
</html>
