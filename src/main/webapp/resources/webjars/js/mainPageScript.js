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

    //test
    $('#btnTest').on('click',function(){
        var m = $("#dialogUpdate");
        m.modal("show");
    });


    $('#dialogUpdate').on('show.bs.modal', function () {
        $('.modal .modal-body').css('overflow-y', 'auto');
        $('.modal .modal-body').css('max-height', $(window).height() * 0.7);
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

    $("#tblUpdateData thead tr > th").remove();
    $("#tblUpdateData tbody tr > td").remove();

    var headers = dataTable.columnCaptions;
    var tableContents = dataTable.data;
    var rowID = 1;

    headers.forEach(function(headCaption){
        $("#tblContents thead tr").append("<th>" + headCaption + "</th>");
        if(headCaption.toString() != "ID"){
            $("#tblUpdateData thead tr").append("<th>" + headCaption + "</th>");
            $("#tblUpdateData tbody tr").append('<td><input type="text" id="' + headCaption + '"></input></td>');
        }
    });

    //button control rows
    if(headers.length > 0){
        var strControl = "<th><div class='btn-group pull-right' role='group'>"+
        "<button type='button' id='btnAddTable' class='btn btn-success'>"+
        "<span class='glyphicon glyphicon-plus' aria-hidden='true'></span>"+
        "</button>"+
        "<button type='button' id='btnRemoveTable' class='btn btn-danger'>"+
        "<span class='glyphicon glyphicon-remove' aria-hidden='true'></span>"+
        "</button>"+
        "</div>"+
        "</th>";


        $("#tblContents thead tr").append(strControl);

    }


    tableContents.forEach(function (tableData) {
        $("#tblContents tbody").append("<tr id='rowID"+rowID+"'></tr>>");
            tableData.forEach(function (value) {
                $("#rowID"+ rowID).append("<td>"+value+"</td></td>");
            });
        rowID++;
    });

}