$(window).ready(function(){


    $('#tblTables tbody').on('click','tr', function() {
        //Mark selected row in table
        $('#tblTables tr').removeClass('marked');
        $(this).addClass('marked');

        //Load data
        var tableName = $("#tblTables .marked");
        if (tableName.length > 0){
            $.ajax({
                type: 'GET',
                url: '../rest/tabledata',
                data:{tblname:tableName.text()},
                dataType: "json",
                success: fillTableContents
            });
        }
    });


    $('#tblContents tbody').on('click','tr', function() {
        //Mark selected row in table
        $('#tblContents tr').removeClass('marked');
        $(this).addClass('marked');
    });


    //Listener button [delete table]
    $('#btnRemoveTable').on('click', function(e){
        var tableName = $("#tblTables .marked");
        if (tableName.length > 0){
            var MESSAGE_STRING_CONST = "Are you sure you want to drop '" + tableName.text() + "' ?";
            confirmDialog(MESSAGE_STRING_CONST,function () {
                $.ajax({
                   type: "POST",
                   url: "../rest/deletetable",
                   data: {tblname: tableName.text() }
                }).done(function( msg ) {
                   alert(msg);
                   loadTableNames();
                });
            });
        }else {
            alert("Please, select table from list");
        }
    });

    $('#btnFill').on('click',function () {
        $.ajax({
            type: 'GET',
            url: '../rest/datatable',
            data:{tblname:"1"},
            dataType: "json",
            success: fillTableContents
        });
    });

    //Load list tables
    loadTableNames();

    }
);

function loadTableNames(){
    $.ajax({
        type:'GET',
        url:'../rest/tables',
        dataType:"json",
        success: function (data) {
            $("#tblTables tbody > tr").remove();
            data.forEach(function(d) {
                $("#tblTables tbody").append("<tr><td colspan='2'>"+d+"</td></tr>");
            });
        }
    });
}

function confirmDialog(message, onConfirm){
    var fClose = function(){
        modal.modal("hide");
    };
    var modal = $("#confirmModal");
    modal.modal("show");
    $("#confirmOk").unbind('click');
    $("#confirmMessage").empty().append(message);
    $("#confirmOk").one('click', onConfirm);
    $("#confirmOk").one('click', fClose);
    $("#confirmCancel").one("click", fClose);
}

function fillTableContents(dataTable){

    $("#tblContents thead tr > th").remove();
    $("#tblContents tbody > tr").remove();

    var headers = dataTable.columnCaptions;
    var tableContents = dataTable.data;
    var rowID = 1;

    headers.forEach(function(headCaption){
        $("#tblContents thead tr").append("<th>" + headCaption + "</th>");
    });

    tableContents.forEach(function (tableData) {
        $("#tblContents tbody").append("<tr id='rowID"+rowID+"'></tr>>");
            tableData.forEach(function (value) {
                $("#rowID"+ rowID).append("<td>"+value+"</td>");
            });
        rowID++;
    });
}