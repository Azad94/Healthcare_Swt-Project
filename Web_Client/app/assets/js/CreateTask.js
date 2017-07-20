/**
 * Written by Jan-Ole Petersen
 * These functions are for filling the hidden inputs with values of the items of the drop down menus and lists
 */
$(function(){
    $(".object li").click(function(){
        var value = $(this).attr("value");
        $("input[name='objectInput']").val(value);
    });
});
$(function () {
    $(".task li").click(function(){
        var value = $(this).attr("value");
        $("input[name='taskInput']").val(value);
    });
});
$(function () {
    $(".location li").click(function(){
        var value = $(this).attr("value");
        $("input[name='locationInput']").val(value);
    });
});

$(function () {
    $(".frequency li").click(function(){
        var value = $(this).attr("value");
        $("input[name='freqInput']").val(value);
    });
});

$(function () {
    $(".level li").click(function(){
        var value = $(this).attr("value");
        $("input[name='levelInput']").val(value);
    });
});
$(function () {
    $(".mainKind li").click(function(){
        var value = $(this).attr("value");
        $("input[name='mainKindInput']").val(value);
    });
});


/**
 * Written by Kim Huber
 * These functions create a new table-row for door-subtasks, change the text and hidden input of the new dropdown
 */
$(document).ready(function(){
    var i=1;
    var j=1;
    $("#addRowDoor").click(function(){
        $('#doorAddr'+i).html("<td>"+
            "<div id='taskDoor"+i+"' class='btn-group'>"+
            "<a type='button' class='btn btn-default'>Beschriftung</a>"+
            "<button type='button' class='btn btn-default dropdown-toggle' data-toggle='dropdown' aria-expanded='false'>"+
            "<span class='caret'></span>"+
            "<span class='sr-only'>Toggle Dropdown</span>"+
            "</button>"+
            "<ul class='dropdown-menu' role='menu'>"+
            "<li value='Magnet funktioniert'><a>Magnet funktioniert</a></li>"+
            "<li value='Scharniere'><a>Scharniere</a></li>"+
            "<li value='Temperatur'><a>Temperatur</a></li>"+
            "<li>"+
            "<div class='form-group'>"+
            "<input type='text' value='' placeholder='Eigene Beschriftung' name='ownBedLabel"+i+" class='form-control' />"+
            "</div>"+
            "</li>"+
            "</ul>"+
            "</div>"+
            "<input type='hidden' name='taskDoorLabel"+i+"'>"+
            "</td>"+
            "<td>"+
            "<fieldset class='form-group'>"+
            "<div class='form-check'>"+
            "<label class='form-check-label'>"+
            "<input type='radio' class='form-check-input' name='optionsRadiosDoor"+i+"' value='checkBox'>"+
            "Checkbox"+
            "</label>"+
            "</div>"+
            "<div class='form-check'>"+
            "<label class='form-check-label'>"+
            "<input type='radio' class='form-check-input' name='optionsRadiosDoor"+i+"' value='slider'>"+
            "Slider"+
            "</label>"+
            "</div>"+
            "</fieldset>"+
            "</td>");

        $('#wartungTableDoor').append('<tr id="doorAddr'+(i+1)+'"></tr>');

        $("#taskDoor"+i+" .dropdown-menu li").click(function () {
            $(this).parent().parent().children("a").text($(this).attr("value"));
            $(this).parent().parent().parent().children("input").attr("value",$(this).attr("value"));
        });

        $("#taskDoor"+i+" input").change(function () {
            $(this).parent().parent().parent().parent().children("a").text($(this).val());
            $(this).parent().parent().parent().parent().parent().children("input").attr("value",$(this).val());
        });

        $("input[name='subtaskNumber']").val(i);
        i++;
    });

    /**
     * Written by Kim Huber
     * These functions create a new table-row for bed-subtasks, change the text and hidden input of the new dropdown
     */
    $("#addRowBed").click(function(){
        $('#bedAddr'+j).html("<td>"+
            "<div id='taskBed"+j+"' class='btn-group'>"+
            "<a type='button' class='btn btn-default'>Beschriftung</a>"+
            "<button type='button' class='btn btn-default dropdown-toggle' data-toggle='dropdown' aria-expanded='false'>"+
            "<span class='caret'></span>"+
            "<span class='sr-only'>Toggle Dropdown</span>"+
            "</button>"+
            "<ul class='dropdown-menu' role='menu'>"+
            "<li value='Hebemotor funktioniert'><a>Hebemotor funktioniert</a></li>"+
            "<li value='Räder funktioniert'><a>Räder funktioniert</a></li>"+
            "<li>"+
            "<div class='form-group'>"+
            "<input type='text' value='' placeholder='Eigene Beschriftung' name='ownBedLabel"+j+"' class='form-control' />"+
            "</div>"+
            "</li>"+
            "</ul>"+
            "</div>"+
            "<input type='hidden' name='taskBedLabel"+j+"'>"+
            "</td>"+
            "<td>"+
            "<fieldset class='form-group'>"+
            "<div class='form-check'>"+
            "<label class='form-check-label'>"+
            "<input type='radio' class='form-check-input' name='optionsRadiosBed"+j+"' value='checkBox'>"+
            "Checkbox"+
            "</label>"+
            "</div>"+
            "<div class='form-check'>"+
            "<label class='form-check-label'>"+
            "<input type='radio' class='form-check-input' name='optionsRadiosBed"+j+"' value='slider'>"+
            "Slider"+
            "</label>"+
            "</div>"+
            "</fieldset>"+
            "</td>");

        $('#wartungTableBed').append('<tr id="bedAddr'+(j+1)+'"></tr>');

        $("#taskBed"+j+" .dropdown-menu li").click(function () {
            $(this).parent().parent().children("a").text($(this).attr("value"));
            $(this).parent().parent().parent().children("input").attr("value",$(this).attr("value"));
            console.log("click value: " + $(this).val());
        });

        $("#taskBed"+j+" input").change(function () {
            $(this).parent().parent().parent().parent().children("a").text($(this).val());
            $(this).parent().parent().parent().parent().parent().children("input").attr("value",$(this).val());
            console.log("change value: " + $(this).val());
        });

        $("input[name='subtaskNumber']").val(j);
        j++;

    });
});

/**
 * Written by Kim Huber
 * These functions change the text and hidden input of the dropdowns
 * if someone click on a dropdown the function look at the nodes above him, go into the right children and change it
 * it work with the node-tree so its dynamically for all dropdowns
 */
$('.dropdown-menu li').click(function () {
    $(this).parent().parent().children("a").text($(this).attr("value"));
});

$('#bedAddr0 .dropdown-menu li').click(function () {
    $(this).parent().parent().parent().children("input").attr("value",$(this).attr("value"));
});

$('#doorAddr0 .dropdown-menu li').click(function () {
    $(this).parent().parent().parent().children("input").attr("value",$(this).attr("value"));
});

$("#taskDoor0 input").change(function () {
    $(this).parent().parent().parent().parent().children("a").text($(this).val());
    $(this).parent().parent().parent().parent().parent().children("input").attr("value",$(this).val());
});

$("#taskBed0 input").change(function () {
    $(this).parent().parent().parent().parent().children("a").text($(this).val());
    $(this).parent().parent().parent().parent().parent().children("input").attr("value",$(this).val());
});

$(function toggleOn() {
    $('#toggleButton').change(function() {
        $('#dauerauftragZyklus').toggle();
    })
});
