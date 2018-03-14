<%@ taglib prefix="springmain" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sqlcmd-WEB</title>

    <springmain:url value="/resources/img/favicon.ico" var="fi"/>
    <link rel="icon" type="image/x-icon" class="js-site-favicon" href="${fi}">

    <springmain:url value="/resources/webjars/bootstrap/3.2.0/css/bootstrap.min.css" var="bootstrapCss"/>
    <link href="${bootstrapCss}" rel="stylesheet">

    <springmain:url value="/resources/webjars/bootstrap/3.2.0/css/bootstrap-theme.css" var="bsT"/>
    <link href="${bsT}" rel="stylesheet">

    <springmain:url value="/resources/webjars/css/mainpage.css" var="mpCs"/>
    <link href="${mpCs}" rel="stylesheet">

</head>
<body>
    <h1>Main page</h1>

    <div class="container">
        <button class="btn btn-sm" id="buttonLoad">Загрузить</button>
    </div>



<springmain:url value="/resources/webjars/jquery/3.2.1/jquery-3.2.1.min.js" var="minjs"/>
<springmain:url value="/resources/webjars/js/mainPageScript.js" var="mpjs"/>
<springmain:url value="/resources/webjars/bootstrap/3.2.0/js/bootstrap.min.js" var="bsJs"/>

<script src="${minjs}"></script>
<script src="${mpjs}"></script>
<script src="${bsJs}"></script>

</body>
</html>
