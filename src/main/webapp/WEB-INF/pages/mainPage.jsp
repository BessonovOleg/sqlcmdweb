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

<!-- delete table confirm -->
<div class="modal" id="confirmModal" style="display: none; z-index: 1050;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body" id="confirmMessage">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" id="confirmOk">Ok</button>
                <button type="button" class="btn btn-default" id="confirmCancel">Cancel</button>
            </div>
        </div>
    </div>
</div>
<!-- ----------------- -->

<!-- table for change value -->
<div class="modal" id="dialogUpdate" style="display: none; z-index: 1049;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body" id="dialogUpdateBody">
                <table class="table table-condensed table-bordered" id="tblUpdateData">
                    <thead>
                    <tr></tr>
                    </thead>

                    <tbody>
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" id="dialogUpdateBtnOk">Ok</button>
                <button type="button" class="btn btn-default" id="dialogUpdateBtnCancel">Cancel</button>
            </div>
        </div>
    </div>
</div>
<!-- -->





<!-- table for create table -->
<div class="modal" id="dialogCreateTable" style="display: none; z-index: 1048;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title">Create new table</h4>
            </div>
            <div class="modal-body" id="dialoAddTable">
                        <input type="text" id="edTblName" placeholder="Input table name">
                        <br>
                        <table class="table" id="tblAddTable">
                            <tbody>
                            </tbody>
                        </table>
                        <tr><button class="btn btn-success" id="btnAddNewColumn">Add column</button> </tr>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" id="dialogAddBtnOk">Ok</button>
                <button type="button" class="btn btn-default" id="dialogAddBtnCancel">Cancel</button>
            </div>
        </div>
    </div>
</div>
<!-- -->



<div class="container">
    <div class = "row">
        <h2 class="text-center">Sqlcmd-WEB</h2>

        <div class="col-xs-3">
            <table class="table table-condensed " id="tblTables">
                <thead>
                    <tr>
                        <th>Tables</th>
                        <th>
                            <div class="btn-group pull-right" role="group">
                                <button type="button" id="btnAddTable" class="btn btn-success">
                                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                                </button>

                                <button type="button" id="btnRemoveTable" class="btn btn-danger">
                                    <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                                </button>
                            </div>
                        </th>
                    </tr>
                </thead>

                <tbody>
                </tbody>
            </table>
        </div>

        <div class="col-xs-9">
            <table class="table table-condensed table-bordered" id="tblContents">
                <thead>
                    <tr></tr>
                </thead>

                <tbody></tbody>
            </table>
        </div>
    </div >

    <button type="button" id="btnTest" class="btn btn-default">test</button>

</div>


<springmain:url value="/resources/webjars/jquery/3.2.1/jquery-3.2.1.min.js" var="minjs"/>
<springmain:url value="/resources/webjars/js/mainPageScript.js" var="mpjs"/>
<springmain:url value="/resources/webjars/bootstrap/3.2.0/js/bootstrap.min.js" var="bsJs"/>

<script src="${minjs}"></script>
<script src="${mpjs}"></script>
<script src="${bsJs}"></script>

</body>
</html>
