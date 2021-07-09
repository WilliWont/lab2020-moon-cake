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
        <script src="js/notification.js"></script>
        <script src="js/validation.js"></script>
        <script src="js/checkout.js"></script>

        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/checkout.css">

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Checkout</title>
    </head>
    <body>
        <%@ include file="WEB-INF/jspf/storeNotification.jspf"%>

        <div class="container">
            <div class="content">
                <c:if test="${not empty sessionScope.SES_USER}">
                    <a href="address" class="btn btn-outline-secondary">return to address</a>
                </c:if>
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


                        <a href="store" class="col-md-12 "> return to store </a>

                    </div> 
                    <!--cart-->

                    <!--checkout-->
                    <div class="col-md-8 order-md-1">
                        <h4 class="mb-3">Billing address</h4>
                        <c:if test="${not empty cartItemList}">
                            <form action="confirmCheckout" id="form-checkout">
                                <c:set var="userAddress" value="${requestScope.USER_ADDR}"/>
                                <c:if test="${empty userAddress}">
                                    <div class="row">
                                        <div class="col-md-6 mb-3">
                                            <label for="form-addr-nameF">First name</label>
                                            <input type="text" class="form-control" 
                                                   id="form-addr-nameF" 
                                                   name="txtNameF">
                                            <div class="valid-feedback">
                                            </div>
                                            <div class="invalid-feedback">
                                            </div>
                                        </div>
                                        <div class="col-md-6 mb-3">
                                            <label for="form-addr-nameL">Last name</label>
                                            <input type="text" class="form-control" 
                                                   id="form-addr-nameL" 
                                                   name="txtNameL">
                                            <div class="valid-feedback">
                                            </div>
                                            <div class="invalid-feedback">
                                            </div>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <!--address line-->
                                        <div class="col-md-8 mb-3">
                                            <label for="form-addr-addrLine">Address Line</label>
                                            <input type="text" class="form-control" 
                                                   id="form-addr-addrLine" name="txtAddrLine">
                                            <div class="valid-feedback">
                                            </div>
                                            <div class="invalid-feedback">
                                            </div>
                                        </div>
                                        <div class="col-md-4 mb-3">
                                            <label for="form-addr-phone">Phone</label>
                                            <input type="text" class="form-control" 
                                                   name="txtAddrPhone"
                                                   id="form-addr-phone">
                                            <div class="valid-feedback">
                                            </div>
                                            <div class="invalid-feedback">
                                            </div>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <!--city-->
                                        <div class="col-md-5 mb-3">
                                            <label for="form-addr-city">City</label>
                                            <input type="text" class="form-control"
                                                   name="txtAddrCity"
                                                   id="form-addr-city">
                                            <div class="valid-feedback">
                                            </div>
                                            <div class="invalid-feedback">
                                            </div>
                                        </div>

                                        <!--district-->
                                        <div class="col-md-4 mb-3">
                                            <label for="form-addr-district">District</label>
                                            <input class="form-control" 
                                                   name="txtAddrDistrict"
                                                   id="form-addr-district">
                                            <div class="valid-feedback">
                                            </div>
                                            <div class="invalid-feedback">
                                            </div>
                                        </div>

                                        <!--ward-->
                                        <div class="col-md-3 mb-3">
                                            <label for="form-addr-ward">Ward</label>
                                            <input type="text" class="form-control" 
                                                   name="txtAddrWard"
                                                   id="form-addr-ward">
                                            <div class="valid-feedback">
                                            </div>
                                            <div class="invalid-feedback">
                                            </div>
                                        </div>

                                    </div>
                                </c:if> <!--empty addr-->
                                <c:if test="${not empty userAddress}"> 
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th scope="col">Name</th>
                                                <th scope="col">Address</th>
                                                <th scope="col">Phone</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>${userAddress.nameFull}</td>
                                                <td>${userAddress.addrLine} ${userAddress.ward} 
                                                    ${userAddress.district} ${userAddress.city}</td>
                                                <td>${userAddress.phone}</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    <input type="hidden" value="${userAddress.id}" name="txtAddrID">
                                </c:if> <!--has addr-->

                                <hr class="mb-4">

                                <h4 class="mb-3">Payment</h4>

                                <div class="d-block my-3">
                                    <div class="custom-control custom-radio">
                                        <input id="credit" name="paymentMethod" type="radio" class="custom-control-input" checked="" required="">
                                        <label class="custom-control-label" for="credit">Cash On Demand</label>
                                    </div>
                                </div>

                                <hr class="mb-4">
                                <button class="btn btn-primary btn-lg btn-block" 
                                        id="btn-checkout"
                                        type="submit">Confirm Checkout</button>
                            </form>
                        </c:if>
                        <c:if test="${empty cartItemList}">
                            <div class="col-12">
                                empty cart, checkout unavailable
                            </div>
                        </c:if>
                    </div> 
                    <!--checkout-->
                </div>
            </div>
        </div>
    </body>
</html>
