$(window).ready(function(){
    //    $("#loginform").attr("action","../main/connect");
    //    $("#loginform").attr("method","POST");

        $("#buttonLoad").click(function () {
            $.ajax({
                type:'GET',
                url:'../rest/tables',
                dataType:"json",
                success: function (data) {
                    alert(data);
                }
            });
        });
    }
)