$(window).ready(function(){

    $('#tblTables tbody').on('click','tr', function() {
        //Mark selected row in table
        $('#tblTables tr').removeClass('marked');
        $(this).addClass('marked');

        //Load data

    });

    //Listener button [delete table]
    $('#btnRemoveTable').on('click', function(e){
        var tableName = $("#tblTables .marked");
        if (tableName.length > 0){
            var YOUR_MESSAGE_STRING_CONST = "Are you sure you want to drop '" + tableName.text() + "' ?";
            confirmDialog(YOUR_MESSAGE_STRING_CONST,function () {
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