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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cart</title>
        <script src="js/jquery-latest-min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/notification.js"></script>
        <script src="js/cart.js"></script>
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/base.css">
        <link rel="stylesheet" href="css/cart.css">
    </head>
    <body>
        <%@ include file="WEB-INF/jspf/storeNotification.jspf"%>
        <c:set var="cartItemList" value="${sessionScope.SES_CART.productList}"/>
        <div class="container">
            <div class="content">
                <!--Section: Block Content-->
                <section>
                    <!--Grid row-->
                    <div class="row">
                        <!--Grid column-->
                        <div class="col-lg-8">
                            <!-- Card -->
                            <div class="card wish-list mb-3">
                                <c:if test="${not empty cartItemList}">
                                    <div class="card-body">
                                        <c:set value="${fn:length(cartItemList)}" var="cartCount"/>
                                        <h5 class="mb-4">Cart (<span>${cartCount}</span> items)</h5>

                                        <c:forEach 
                                            var="product" 
                                            items="${cartItemList}" 
                                            varStatus="counter">
                                            <div class="row mb-4">
                                                <div class="col-md-5 col-lg-3 col-xl-3">
                                                    <div class="view zoom overlay z-depth-1 rounded mb-3 mb-md-0">
                                                        <div class="mask waves-effect waves-light">
                                                            <img class="img-fluid w-100"
                                                                 src="${product.imagePath}">
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-md-7 col-lg-9 col-xl-9">
                                                    <div>
                                                        <div class="d-flex justify-content-between">
                                                            <div>
                                                                <h5>${product.name}</h5>
                                                                <p class="mb-3 text-muted text-uppercase small">
                                                                    ${product.categoryName}
                                                                </p>
                                                            </div>
                                                            <div>
                                                                <div class="form-row justify-content-end">
                                                                    <form class="form-cart-item col-md-4">
                                                                        <input class="quantity col-md-12 product-quantity" 
                                                                               min="1" 
                                                                               name="txtQuantity" 
                                                                               value="${product.quantity}" 
                                                                               type="number"
                                                                               onkeypress="return event.charCode >= 48"
                                                                               >
                                                                        <input type="hidden" name="txtCakeID" value="${product.id}">
                                                                    </form>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="d-flex justify-content-between align-items-center">
                                                            <div>
                                                                <c:url var="deleteAddr" value="removeItem">
                                                                    <c:param name="txtCakeID" value="${product.id}"/>
                                                                </c:url>
                                                                <a href="${deleteAddr}" type="button" class="card-link-secondary small text-uppercase mr-3"><i
                                                                        class="fas fa-trash-alt mr-1"></i> Remove item </a>
                                                            </div>
                                                            <p class="mb-0"><span><strong id="product-price">\$${product.price}</strong></span></p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <c:if test="${counter.count != cartCount}">
                                                <hr class="mb-4">
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                </c:if>
                                <c:if test="${empty cartItemList}">
                                    <div class="card-body">
                                        <h5 class="mb-4">Cart</h5>
                                        <div class="col-md-5 col-lg-3 col-xl-3">
                                            no item in cart
                                        </div>
                                    </div>
                                </c:if>
                            </div>
                            <!-- Card -->

                        </div>
                        <!--Grid column-->

                        <!--Grid column-->
                        <div class="col-lg-4">

                            <!-- Card -->
                            <div class="card mb-3">
                                <div class="card-body">
                                    <h5 class="mb-3">Total amount</h5>
                                    <c:if test="${not empty cartItemList}">
                                        <ul class="list-group list-group-flush">
                                            <li class="list-group-item d-flex justify-content-between align-items-center px-0">
                                            </li>
                                            <li class="list-group-item d-flex justify-content-between align-items-center border-0 px-0 mb-3">
                                                <div>
                                                    <strong>Cart Total:</strong>
                                                </div>
                                                <span><strong id="cart-total">\$${sessionScope.SES_CART.cartTotal}</strong></span>
                                            </li>
                                        </ul>

                                        <a href="address" class="btn btn-primary">go to checkout</a>
                                        <a href="store" class="col-12">return to store</a>
                                    </c:if>
                                    <c:if test="${empty cartItemList}">
                                        <a href="store" class="col-12">return to store</a>
                                    </c:if>
                                </div>
                            </div>
                            <!-- Card -->


                        </div>
                        <!--Grid column-->

                    </div>
                    <!--Grid row-->

                </section>
                <!--Section: Block Content-->
            </div>
        </div>
    </body>
</html>
