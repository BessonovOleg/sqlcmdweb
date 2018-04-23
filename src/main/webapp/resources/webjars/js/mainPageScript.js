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

    //Button create new table
    $('#btnAddTable').on('click',createNewTable);


    //Button add new column
    $('#btnAddNewColumn').on('click',function () {
        $('#tblAddTable tbody').append("<tr><td><input type='text' placeholder='column name'></td></tr>>");
    });


    $('#dialogUpdate').on('show.bs.modal', function () {
        $('.modal .modal-body').css('overflow-y', 'auto');
        $('.modal .modal-body').css('max-height', $(window).height() * 0.7);
    });


    //test
    $('#btnTest').on('click',function(){
        //var m = $("#dialogUpdate");
        //m.modal("show");
        alert($('#tblContents .marked').attr('id'));
    });



    //    alert('We will delete this row');



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
    //Load list tables
    loadTableNames();
    }
);

function loadTableNames(){

    $("#tblContents thead tr > th").remove();
    $("#tblContents tbody > tr").remove();
    $("#tblTables tbody > tr").remove();

    $.ajax({
        type:'GET',
        url:'../rest/tables',
        dataType:"json",
        success: function (data) {
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

function createNewTable(){
    $("#tblAddTable tbody tr > td").remove();
    $('#edTblName').val("");

    var dlg = $('#dialogCreateTable');
        dlg.modal('show');

    var fClose = function(){
        dlg.modal("hide");
    };

    var fDone = function(){
        var arrayColumnNames = [];
        var edTblName = $('#edTblName');
        if(!edTblName.val() || edTblName.val() == ''){
            alert('Table name cannot be empty!');
            return false;
        }

        var columnNameItems = $("#tblAddTable :input");
            columnNameItems.each(function () {
                arrayColumnNames.push($(this).val());
            });


        //For unique names
        arrayColumnNames = Array.from(new Set(arrayColumnNames));

        var tblinfo = {
            tblname:edTblName.val(),
            tblcolumns:arrayColumnNames
        };

        $.ajax({
            type: "POST",
            url: "../rest/createtable",
            data:JSON.stringify(tblinfo)
        }).done(function( msg ) {
            alert(msg);
            loadTableNames();
        });
    };

    $("#dialogAddBtnOk").unbind('click');
    $("#dialogAddBtnOk").one('click', fDone);
    $("#dialogAddBtnOk").one('click', fClose);
    $("#dialogAddBtnCancel").one("click", fClose);
}


function updateTableContents(rowId){
    var dlg = $('#dialogUpdate');
        dlg.modal('show');

    var fClose = function(){
        dlg.modal("hide");
    };

    var fDone = function () {
        var tableName = $("#tblTables .marked");
        var arrayColumnNames = [];
        var arrayColumnValues = [];

        if (tableName.length > 0){
            var columnNameItems = $("#tblContents .marked :input");
            columnNameItems.each(function () {
                arrayColumnNames.push($(this).attr('id'));
                arrayColumnValues.push($(this).val());
            });

            var data = {
                rowid:rowId,
                columnNames:arrayColumnNames,
                columnValues:arrayColumnValues
            };

            $.ajax({
                type: "POST",
                url: "../rest/updatetablecontents",
                data:JSON.stringify(data)
            }).done(function( msg ) {
                alert(msg);
            });
        }
    };

    $("#dialogUpdateBtnOk").unbind('click');
    $("#dialogUpdateBtnOk").one('click', fDone);
    $("#dialogUpdateBtnOk").one('click', fClose);
    $("#dialogUpdateBtnCancel").one("click", fClose);
}


function fillTableContents(dataTable){
    $("#tblContents thead tr > th").remove();
    $("#tblContents tbody > tr").remove();
    $("#tblUpdateData thead tr > th").remove();
    $("#tblUpdateData tbody tr > td").remove();

    var headers = dataTable.columnCaptions;
    var tableContents = dataTable.data;
    var rowNo = 1;

    headers.forEach(function(headCaption){
        $("#tblContents thead tr").append("<th>" + headCaption + "</th>");
        if(headCaption.toString().toUpperCase() != "ID"){
            $("#tblUpdateData thead tr").append("<th>" + headCaption + "</th>");
            $("#tblUpdateData tbody tr").append('<td><input type="text" id="' + headCaption + '"></input></td>');
        }
    });

    //button control rows
    if(headers.length > 0){
        var strControl = "<th><div class='btn-group pull-right' role='group'>"+
        "<button type='button' id='btnAddRow' class='btn btn-success'>"+
        "<span class='glyphicon glyphicon-plus' aria-hidden='true'></span>"+
        "</button>"+
        "<button type='button' id='btnEditRow' class='btn btn-warning'>"+
        "<span class='glyphicon glyphicon glyphicon-pencil' aria-hidden='true'></span>"+
        "</button>"+
        "<button type='button' id='btnDeleteRow' class='btn btn-danger'>"+
        "<span class='glyphicon glyphicon-remove' aria-hidden='true'></span>"+
        "</button>"+
        "</div>"+
        "</th>";
        $("#tblContents thead tr").append(strControl);
    }

    tableContents.forEach(function (tableData) {
        $("#tblContents tbody").append("<tr id='rowNo"+rowNo+"'></tr>>");
            var isIdAdded = false;
            tableData.forEach(function (value) {
                if (!isIdAdded){
                    $("#rowNo"+ rowNo).attr("rowID",value);
                    isIdAdded = true;
                }
                $("#rowNo"+ rowNo).append("<td>"+value+"</td></td>");
            });
        rowNo++;
    });

    //Delete row
    $('#btnDeleteRow').on('click',function(){
        var tableName = $("#tblTables .marked");
        var selectedRow = $('#tblContents .marked');

        if (tableName.length > 0 && selectedRow.length > 0){
                $.ajax({
                   type: "POST",
                   url: "../rest/deleterow",
                   data: {tblname: tableName.text(),rowid:selectedRow.attr('rowID') }
                }).done(function( msg ) {
                   alert(msg);
                });
        }
    });

    //Update row
    $('#btnEditRow').on('click',function () {
        var selectedRow = $('#tblContents .marked');
        if (selectedRow.length > 0){
            updateTableContents(selectedRow.attr('rowID'));
        }
    });

    //Add row
    $('#btnAddRow').on('click',function () {
        updateTableContents(0);
    });



}