/**
 * Written by Jan-Ole Petersen
 * This function is for filling the hidden input with values of the items of the drop down menu
 */
$(function(){
    $(".role li").click(function(){
        var value = $(this).attr("value");
        var name = $(this).attr("name");
        $("input[name='roleInput']").val(value);
        $('#r').text(name);

    });
});