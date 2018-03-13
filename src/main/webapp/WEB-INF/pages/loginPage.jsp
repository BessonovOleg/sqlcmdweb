<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Sqlcmd-WEB</title>

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

    <springspring:form class="form-signin" id="loginform">
        <springspring:input type="text" id="host"   path = "host"   class="form-control" placeholder="127.0.0.1:5432"  />
        <springspring:input type="text" id="dbName" path = "dbName" class="form-control" placeholder="Database name" />
        <springspring:input type="text" id="login"  path = "login"  class="form-control" placeholder="Login" />
        <springspring:input type="password" id="password" path = "password" class="form-control" placeholder="Password" />
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
    </springspring:form>

    <c:if test="${not empty error}">
        <div class="text-center error">${error}</div>
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
