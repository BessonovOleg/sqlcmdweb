

$('#tt').tree({
    lines:"true",
    onClick: function(node){
        //alert(node.text);  // alert node text property when clicked
        //$('#head1').text(node.text);

        $('#myDivTest').text(node.text);
    }
});


