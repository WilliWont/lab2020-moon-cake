<%-- 
    Document   : store
    Created on : Jul 12, 2020, 10:35:59 AM
    Author     : Will
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="tiennh.util.CakeHelper" %>
<%@ page import="tiennh.util.AccountHelper" %>
<%@ page import="tiennh.util.DBHelper" %>


<!DOCTYPE html>
<html>
    <head>
        <c:set var="curUser" value="${sessionScope.SES_USER}"/>
        <c:set var="curUserRole" value="${curUser.role}"/>
        <c:set var="adminRole" value="${AccountHelper.ADMIN_ROLE}"/>
        <c:set var="cateList" value="${applicationScope.CATE_LIST}"/>

        <c:if test="${curUserRole != adminRole}">
            <c:set var="minPrice" value="${CakeHelper.MIN_PRICE_RANGE_IN_STOCK}"/>
            <c:set var="maxPrice" value="${CakeHelper.MAX_PRICE_RANGE_IN_STOCK}"/>
        </c:if>
        <c:if test="${curUserRole == adminRole}">
            <c:set var="minPrice" value="${CakeHelper.MIN_PRICE_RANGE}"/>
            <c:set var="maxPrice" value="${CakeHelper.MAX_PRICE_RANGE}"/>
        </c:if>

        <!--search variables-->
        <script>
            const MIN_PRICE = ${minPrice};
            const MAX_PRICE = ${maxPrice};
            const PER_PAGE = ${DBHelper.PER_PAGE};
        </script>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="js/jquery-latest-min.js"></script>
        <script src="js/jquery-ui.min.js"></script>

        <script src="js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/jquery-ui.min.css">

        <link rel="stylesheet" href="css/base.css">
        <link rel="stylesheet" href="css/store.css">

        <c:if test="${curUserRole != adminRole}">
            <script src="js/notification.js"></script>
            <script src="js/add-item.js"></script>
        </c:if>
        <c:if test="${curUserRole == adminRole}">
            <script src="js/img-preview.js"></script>
            <script src="js/create-item.js"></script>
            <script src="js/update-item.js"></script>
        </c:if>

        <script src="js/search-item.js"></script>
        <title>Store</title>
    </head>
    <body>
        <%@ include file="WEB-INF/jspf/storeNotification.jspf"%>

        <%--if admin, add modal--%>
        <c:if test="${curUserRole == adminRole}">
            <%-- Modal --%>
            <%@ include file="WEB-INF/jspf/adminModal.jspf"%>
        </c:if>

        <div class="panel-group d-none" id="search-template">
            <div class="panel">
                <div class="panel-heading">
                    <h4 class="panel-title" data-toggle="collapse" data-target="#search-panel">
                        Search Result
                    </h4>
                </div>
                <div id="search-panel" class="panel-collapse collapse show">
                    <div class="panel-body " id="search-result-list">

                        <form id="item-template" class="d-none div-cake product-item ">
                            <input type="hidden" name="txtCakeID" value="" />
                            <div class="cake-img-container">
                                <img class="cake-img" src="">
                            </div>

                            <div class="cake-info-container">
                                <div class="cake-info-content">
                                    <h6 class="div-cake-name">
                                    </h6>
                                    <c:if test="${curUserRole != adminRole}">
                                        <input type="submit" 
                                               class="btn btn-info btn-add-item" 
                                               value="\$${product.price}"/>
                                    </c:if>

                                    <c:if test="${curUserRole == adminRole}">
                                        <input type="submit" 
                                               class="btn btn-warning btn-get-item col-md-6" 
                                               value="Update">
                                    </c:if>
                                </div>
                            </div>
                        </form> 

                    </div>
                </div>
                <hr>
            </div>
        </div> <!--template-->

        <div class="container">
            <div class="content">

                <div id="store-header">
                    <div id="welcome-container">
                        <div id="welcome-msg-container">
                            <h1 id="welcome-msg"> 
                                <div><a href="store" class="store-link">The Cake Store</a></div>
                                <c:if test="${not empty curUser}">
                                    Welcome <span id="welcome-name">${sessionScope.SES_USER.nameF} 
                                    </span> 
                                </c:if>
                            </h1> 
                        </div>
                        <!--welcome-->
                        <div class="form-row" id="btn-welcome-group">
                            <c:if test="${empty curUser}">
                                <form action="loginForm"><input type="submit" value="login" class="btn btn btn-success mr-1"></form>
                                <form action="viewCart"><input type="submit" value="view cart" class="btn btn-info mr-1"></form>
                                </c:if> 

                            <c:if test="${not empty curUser}">
                                <form action="logout">
                                    <input type="submit" value="logout" 
                                           class="btn btn-outline-danger mr-1">
                                </form>
                                <c:if test="${curUserRole == adminRole}">
                                    <!-- Button trigger modal -->
                                    <button type="button" class="btn btn-primary mr-1" id="btn-create-modal"
                                            data-toggle="modal" data-target="#createModal">
                                        Create Cake
                                    </button>
                                </c:if>
                                <c:if test="${curUserRole != adminRole}">
                                    <form action="viewCart"><input type="submit" value="view cart" class="btn btn-info mr-1"></form>
                                    <form action="viewOrder"><input type="submit" value="view order" class="btn btn-success mr-1"></form>
                                    </c:if>
                                </c:if>
                        </div> 
                        <!--buttons-->
                        <hr class="w-25">
                    </div> 
                    <!--welcome & btn-->
                    <div id="search-container" class="col-md-12">
                        <form id="store-search" class="form-search">
                            <div class="form-row">
                                <div class="form-group col-md-3 slider-fix">
                                    <label for="cakeCateID">category: </label>
                                    <select class="form-control" id="cakeCateID" name="txtCateID">
                                        <option value="">all</option>
                                        <c:forEach var="category" items="${cateList}">
                                            <option value=${category.id}>${category.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div> 
                            <div class="form-row">
                                <div class="form-group col-md-7 slider-fix">
                                    <label for="search-slider">
                                        price range: 
                                        <span id="lbl-price-min">$<input type="text" name="txtMinPrice" class="sliderValue" data-index="0" value="${minPrice}" /></span> 
                                        - 
                                        <span id="lbl-price-max">$<input type="text" name="txtMaxPrice" class="sliderValue" data-index="1" value="${maxPrice}" /></span></label>
                                    <div class="col-4">
                                        <div id="search-slider"></div>
                                    </div>
                                </div><!--slider-->
                            </div><!--cate & slider-->
                            <div class="form-row col-md-6">
                                <input type="text" class="form-control col-md-8" name="txtSearch" value="${fn:trim(param.txtSearch)}"/>
                                <input type="submit" value="search" class="btn btn-outline-secondary col-md-4 btn-lg"/>
                            </div>
                            <!--
                            TODO: FIX CATE AND SLIDER STYLING
                            -->
                        </form>

                    </div>  
                    <!--search-->        
                </div> <!--store header-->

                <hr>

                <div id="div-search-result">
                    <c:set var="cateList" value="${requestScope.PRODUCT_LIST}"/>

                    <c:if test="${not empty cateList}">
                        <c:forEach var="category" items="${cateList}">
                            <c:set var="productList" value="${category.cakeList}"/>

                            <div class="panel-group">
                                <div class="panel">
                                    <div class="panel-heading">
                                        <h4 class="panel-title" data-toggle="collapse" data-target="#cate-${category.categoryID}">
                                            ${category.categoryName}
                                        </h4>
                                    </div>
                                    <div id="cate-${category.categoryID}" class="panel-collapse collapse show">
                                        <div class="panel-body ">

                                            <c:forEach var="product" items="${productList}" varStatus="counter">
                                                <form class="div-cake product-item 
                                                      <c:if test="${product.quantityStock == 0}">
                                                          empty-item
                                                      </c:if>
                                                      ">
                                                    <input type="hidden" name="txtCakeID" value="${product.id}" />
                                                    <div class="cake-img-container">
                                                        <img class="cake-img" src="${product.imagePath}">
                                                    </div>

                                                    <div class="cake-info-container">
                                                        <div class="cake-info-content">
                                                            <h6 class="div-cake-name">
                                                                ${product.name}
                                                            </h6>
                                                            <c:if test="${curUserRole != adminRole}">
                                                                <input type="submit" 
                                                                       class="btn btn-info" 
                                                                       value="\$${product.price}" />
                                                            </c:if>

                                                            <c:if test="${curUserRole == adminRole}">
                                                                <input type="submit" 
                                                                       class="btn btn-warning btn-get-item col-md-6" 
                                                                       value="Update">
                                                            </c:if>
                                                        </div>
                                                    </div>
                                                </form> 
                                            </c:forEach> <%--item--%>

                                        </div>
                                    </div>
                                    <hr>
                                </div>
                            </div>

                        </c:forEach> <!--cate-->
                    </c:if>

                    <c:if test="${empty cateList}">
                        <h1>no product found</h1>
                    </c:if>

                </div> <!--cake list-->
            </div>
        </div>
    </body>
</html>
