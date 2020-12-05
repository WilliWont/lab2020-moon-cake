<%-- 
    Document   : viewCart
    Created on : Jul 13, 2020, 6:31:13 PM
    Author     : Will
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
    <head>
        <c:set var="user" value="${sessionScope.SES_USER}"/>
        <script src="js/jquery-latest-min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/address.js"></script>

        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/base.css">
        <link rel="stylesheet" href="css/address.css">

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Addresses</title>
    </head>
    <body>
        <div class="container">
            <div class="content">
                <a href="viewCart" class="btn btn-outline-secondary">return to address</a>
                <div class="row">
                    <!--cart-->
                    <c:set var="cartItemList" value="${sessionScope.SES_CART.productList}"/>
                    <c:set value="${fn:length(cartItemList)}" var="cartCount"/>
                    <div class="col-md-4 order-md-2 mb-4">
                        <h4 class="d-flex justify-content-between align-items-center mb-3">
                            <span class="text-muted">Your cart</span>
                            <span class="badge badge-secondary badge-pill">${cartCount}</span>
                        </h4>
                        <ul class="list-group mb-3" id="cart-product-list">
                            <c:forEach 
                                var="product" 
                                items="${cartItemList}" 
                                varStatus="counter">
                                <li class="list-group-item d-flex justify-content-between lh-condensed">
                                    <div>
                                        <h6 class="my-0" id="product-id-${product.id}"><strong>${product.quantity}</strong> x ${product.name}</h6>
                                        <small class="text-muted">${product.categoryName}</small>
                                    </div>
                                    <span class="text-muted">\$${product.price}</span>
                                </li>
                            </c:forEach>
                            <li class="list-group-item d-flex justify-content-between">
                                <span>Total</span>
                                <strong id="cart-total-view">\$${sessionScope.SES_CART.cartTotal}</strong>
                            </li>
                        </ul>


                        <a href="store" class="col-md-12"> return to store </a>

                    </div> 
                    <!--cart-->

                    <!--address-->
                    <div class="col-md-8 order-md-1">
                        <h4 class="mb-3">Address Book</h4>
                        <c:if test="${not empty cartItemList}">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th scope="col">Name</th>
                                        <th scope="col">Address</th>
                                        <th scope="col">Phone</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:set var="addressList" value="${requestScope.ADDR_LIST}"/>
                                    <c:forEach items="${addressList}" var="address">
                                        <tr onclick="setAddress(${address.id})" id="address-row-${address.id}">
                                            <td>${address.nameFull}</td>
                                            <td>${address.addrLine} ${address.ward} ${address.district} ${address.city}</td>
                                            <td>${address.phone}</td>
                                        </tr>
                                    </c:forEach>
                                    <tr onclick="setAddress(0)" id="address-row-0">
                                        <td colspan="3">New Address...</td>
                                    </tr>
                                </tbody>
                            </table>
                            <hr class="mb-4">
                            <form action="setupCheckout">
                                <input type="hidden" value="" name="txtAddrID" id="addr-id">
                                <input class="btn btn-primary btn-lg btn-block" 
                                       id="btn-checkout"
                                       type="submit" value="Go To Checkout">
                            </form>
                        </c:if>
                        <c:if test="${empty cartItemList}">
                            <div class="col-12">
                                empty cart, checkout unavailable
                            </div>
                        </c:if>
                    </div> 
                    <!--address-->
                </div>
            </div>
        </div>
    </body>
</html>
