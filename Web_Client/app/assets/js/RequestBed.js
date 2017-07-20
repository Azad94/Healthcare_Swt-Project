/**
 * Written by Jan-Ole Petersen
 * These functions are for filling the hidden inputs with values of the items of the drop down menu and list on the top
 */
$(function () {
    $(".location li").click(function(){

        var value = $(this).attr("value");
        var name = $(this).attr("name");
        $("input[name='locationInput']").val(value);
        $('#loc').text(name);

    });
});

$(function () {
    $(".bed li").click(function(){

        var value = $(this).attr("value");
        $("input[name='bedInput']").val(value);

    });
});