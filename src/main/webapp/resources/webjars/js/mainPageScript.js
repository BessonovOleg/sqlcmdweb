$(window).ready(function(){

    $('#tblTables tbody').on('click','tr', function() {
        //Mark selected row in table
        $('#tblTables tr').removeClass('marked');
        $(this).addClass('marked');

        //Load data

    });

    loadTableNames();

    }
)


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