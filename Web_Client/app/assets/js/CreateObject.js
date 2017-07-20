/**
 * Written by Jan-Ole Petersen
 * These functions are for filling the hidden inputs with values of the items of the drop down menu
 */

$(function () {
    $(".beacon li").click(function(){

        var value = $(this).attr("value");
        var name = $(this).attr("name");
        $("input[name='beaconInput']").val(value);
        $('#beac').text(name);

    });
});

$(function () {
    $(".location li").click(function(){
        var value = $(this).attr("value");
        var name = $(this).attr("name");
        $("input[name='locationInput']").val(value);
        $('#loc').text(name);
    });
});

$(function () {
    $(".type li").click(function(){
        var value = $(this).attr("value");
        var name = $(this).attr("name");
        $("input[name='typeInput']").val(value);
        $('#typ').text(value);
    });
});