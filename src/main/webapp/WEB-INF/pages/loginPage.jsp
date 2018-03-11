<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>SqlCmd</title>

    <spring:url value="/resources/img/favicon.ico" var="fi"/>
    <link rel="icon" type="image/x-icon" class="js-site-favicon" href="${fi}">

    <spring:url value="/resources/webjars/bootstrap/3.2.0/css/bootstrap.min.css" var="bootstrapCss"/>
    <link href="${bootstrapCss}" rel="stylesheet">

    <spring:url value="/resources/webjars/bootstrap/3.2.0/css/bootstrap-theme.css" var="bsT"/>
    <link href="${bsT}" rel="stylesheet">

    <spring:url value="/resources/webjars/css/loginpage.css" var="lpCs"/>
    <link href="${lpCs}" rel="stylesheet">

</head>
<body>


<div class="container">

    <h2 class="text-center">Sqlcmd-WEB</h2>

    <form class="form-signin" id="loginform2">
        <input type="text" id="host" class="form-control" placeholder="127.0.0.1:5432" required autofocus>
        <input type="text" id="dbName" class="form-control" placeholder="Database name" required>
        <input type="text" id="login" class="form-control" placeholder="Login" required>
        <input type="password" id="password" class="form-control" placeholder="Password" required>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
    </form>

    <c:if test="${not empty error}">
        <div class="error text-center">${error}</div>
    </c:if>

</div>

<spring:url value="/resources/webjars/jquery/3.2.1/jquery-3.2.1.min.js" var="minjs"/>
<spring:url value="/resources/webjars/js/loginFormScript.js" var="lfjs"/>
<spring:url value="/resources/webjars/bootstrap/3.2.0/js/bootstrap.min.js" var="bsJs"/>


<script src="${minjs}"></script>
<script src="${lfjs}"></script>
<script src="${bsJs}"></script>



</body>
</html>
