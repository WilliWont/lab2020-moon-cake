<%-- 
    Document   : viewOrder
    Created on : Oct 13, 2020, 8:43:39 AM
    Author     : Will
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="js/jquery-latest-min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/viewOrder.js"></script>

        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/base.css">
        <link rel="stylesheet" href="css/order.css">

        <title>Order</title>
    </head>
    <body>

        <div class="container">
            <div class="content">
                <div class="row">

                    <!--cart-->
                    <div class="col-md-4 order-md-2 mb-4 to-hide" id="cart-template">
                        <h4 class="d-flex justify-content-between align-items-center mb-3">
                            <span class="text-muted">Your cart</span>
                            <span class="badge badge-secondary badge-pill" id="cart-count">COUNT_HERE</span>
                        </h4>
                        <ul class="list-group mb-3" id="cart-product-list">

                            <li class="list-group-item d-flex to-fullyhide
                                justify-content-between lh-condensed"
                                id="item-template">
                                <div>
                                    <h6 class="my-0"><strong id="cart-quantity">QUANTITY</strong> x <span id="cart-name">NAME</span></h6>
                                </div>
                                <span class="text-muted" id="cart-price">\$PRICE</span>
                            </li>

                            <li class="list-group-item d-flex justify-content-between">
                                <span>Total</span>
                                <strong id="cart-total">\$CART_TOTAL</strong>
                            </li>
                            <li class="list-group-item">
                                <div><strong>Recipient: </strong><span id="cart-recipient"></span></div>
                                <div><strong>Address: </strong><span id="cart-address"></span></div>
                                <div><strong>Phone: </strong><span id="cart-phone"></span></div>
                            </li>
                        </ul>
                    </div> 
                    <!--cart-->

                    <!--orders-->
                    <div class="col-md-8 order-md-1">
                        <h4 class="mb-3">My Orders</h4>
                        <a href="store" class="btn btn-primary">return to store</a>
                        <hr>
                        <c:set var="orderList" value="${requestScope.ORDER_LIST}"/>
                        <c:if test="${not empty orderList}">
                            <div class="form-row">
                                <input class="form-control col-6" id="input-search" type="text" placeholder="enter order ID">
                                <button class="btn btn-outline-primary col-2" onclick="getOrder($('#input-search').val())">Get</button>
                            </div>
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th scope="col">ID</th>
                                        <th scope="col">Order Date</th>
                                        <th scope="col">Payment Method</th>
                                        <th scope="col">Payment Status</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${orderList}" var="order">
                                        <tr id="order-row-${order.id}" onclick="getOrder(${order.id})">
                                            <td>${order.id}</td>
                                            <td>
                                                <fmt:formatDate  value="${order.orderDate}" pattern="MMM dd, yyyy"/>
                                            </td>
                                            <td>${order.paymentMethod}</td>
                                            <c:set var="paymentDate" value="${order.paymentDate}"/>
                                            <c:if test="${empty paymentDate}">
                                                <td>${order.paymentStatus}</td>
                                            </c:if>
                                            <c:if test="${not empty paymentDate}">
                                                <td>${order.paymentStatus} at 
                                                    <fmt:formatDate  value="${paymentDate}" pattern="HH:mm MMM dd, yyyy"/>
                                                </td>
                                            </c:if>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:if>
                        <c:if test="${empty orderList}">
                            <div class="col-12">
                                you currently have no order
                            </div>
                        </c:if>
                    </div>
                    <!--orders-->
                </div>
            </div>
        </div>
    </body>
</html>
