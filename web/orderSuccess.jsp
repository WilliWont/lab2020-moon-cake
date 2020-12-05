<%-- 
    Document   : orderSuccess
    Created on : Oct 13, 2020, 8:32:10 AM
    Author     : Will
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <script src="js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/orderSuccess.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Success</title>
    </head>
    <body>
        <div class="container">
            <div class="content">
                <br><br> <h2 style="color:#0275d8">Success</h2>
                <h3>Your order has been confirmed</h3>
                <c:set var="user" value="${sessionScope.SES_USER}"/>
                <p style="font-size:20px;color:#5C5C5C;">
                    Thank you for ordering at our store, your order 
                    <c:if test="${not empty user}">id is ${param.txtOrderID} and it</c:if> will be delivered within 3-4 business days
                    </p>
                    <a href="store" class="btn btn-primary">Return to Store</a>

                <c:if test="${not empty user}">
                    <a href="viewOrder" class="btn btn-success">View Order</a>
                </c:if>

                <br><br>
            </div>
        </div>
    </body>
</html>
