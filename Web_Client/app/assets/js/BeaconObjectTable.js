/**
 * Written by Jan-Ole Petersen, except jQuery Redirect v1.0.6
 * base by Kim Huber
 */

/**
 * Load script for table
 */
$.getScript("assets/js/InitTable.js");


/**
 * Function for creating the action buttons in table
 * the class after table-action is the call class for the clickfunktion
 * @param value value of the item
 * @param row content of the row
 * @param index index of the item
 */
function operateFormatter(value, row, index) {
    return [
        '<a rel="tooltip" title="Details" class="table-action details" href="javascript:void(0)">',
        '<i class="fa  fa-info-circle"></i>',
        '</a>',
        '<a rel="tooltip" title="Bearbeiten" class="table-action edit" href="javascript:void(0)">',
        '<i class="fa fa-edit"></i>',
        '</a>',
        '<a rel="tooltip" title="Entfernen" class="table-action remove" href="javascript:void(0)">',
        '<i class="fa fa-remove"></i>',
        '</a>'
    ].join('');
}

/**
 * Function for populating drop down menu for beacons
 * @param beacons beacons to populate drop down
 * @returns {string} HTML string of the drop down menu
 */
function populateBeaconDropdown(beacons)
{
    var result = "<select id='beacon' class='form-control'>";
    result += "<option value='' disabled selected>Beacon wählen</option>";
    for(var i = 0; i < beacons.length; i++)
    {
        result += "<option value='" + beacons[i]['uuid'] + "'>" + beacons[i]['uuid'] + "</option>";
    }
    result += "</select>";
    return result;
}

/**
 * Function for populating drop down menu for beacons
 * @param locations locations to populate drop down
 * @returns {string} HTML string of the drop down menu
 */
function populateLocationDropdown(locations)
{
    var result = "<select id='location' class='form-control'>";
    result += "<option value='' disabled selected>Ort wählen</option>";
    for(var i = 0; i < locations.length; i++)
    {
        result += "<option value='" + locations[i]['id'] + "'>" + locations[i]['building']+ "." + locations[i]['floor'] + "." + locations[i]['room'] + "</option>";
    }
    result += "</select>";
    return result;
}

/**
 * This function is called if a beacon object should be updated
 */
function putBeaconObject()
{
    $.ajax({
        type: 'POST',
        url: 'BeaconObjectDetails.php',
        data: {
            method: "put",
            id: $('#id').val(),
            name: $('#name').val(),
            beaconUuid: $('#beacon').val(),
            locationId: $('#location').val(),
        },
        success: function (response) {
            $('#myModal').on('hidden.bs.modal', function () {
                location.reload();
            })

        }
    });

}

/**
 * This function is called if a beacon object should be deleted
 */
function deleteBeaconObject() {
    var id = parseInt($('#id').text());
    $.ajax({
        type: 'POST',
        url: 'BeaconObjectDetails.php',
        data: {
            method: "delete",
            id: id
        },
        success: function (response) {
            $('#myModal').on('hidden.bs.modal', function () {
                location.reload();
            })


        }
    });
}


window.operateEvents = {
    /**
     * This function is called on click on the edit button. It gets the information about the selected beacon object and offers a form for editing it.
     * @param e eventhandler
     * @param value value of the item
     * @param row content of the row
     * @param index index of the item
     */
    'click .edit': function (e, value, row, index) {
        var id = $.parseJSON(JSON.stringify(row))['id'];
        $.ajax({
            type: 'POST',
            url: 'BeaconObjectDetails.php',
            data: {method: "getput", id: id},
            success: function (response) {
                var response = $.parseJSON(response);
                var html =  "<h4>BeaconObject" + "</h4>" +
                    "<p>ID: " + "<input readonly id='id' class='form-control' value='" + response['beaconObject']['id'] + "'>" + "</p>" +
                    "<p>Name: " + "<input type='text' id='name' class='form-control' value='" + response['beaconObject']['name'] + "'>" + "</p>" +
                    "<h5>Beacon: " + "</h5>" +
                    "<p>UUID: " + populateBeaconDropdown(response['beacons']) + "</p>" +
                    "<h5>Ort: " + populateLocationDropdown(response['locations']) + "</h5>";



                $('.modal-title').text("Bearbeiten");
                $('.modal-body').html(html);
                $('.modal-footer').html("<button id='save' class='btn btn-default' data-dismiss='modal' onclick='putBeaconObject()'>Speichern</button>" +
                                        "<button type='button' class='btn btn-default' data-dismiss='modal'>Schließen</button>");

                $('#myModal').modal('show');
            }
        });
    },


    /**
     * This function is called on click on the details button and gets the information about the selected beacon object.
     * @param e eventhandler
     * @param value value of the item
     * @param row content of the row
     * @param index index of the item
     */
    'click .details': function (e, value, row, index) {
        var id = $.parseJSON(JSON.stringify(row))['id'];
        $.ajax({
            type: 'POST',
            url: 'BeaconObjectDetails.php',
            data: {method: "get", id: id},
            success: function (response) {
                var beaconObject = $.parseJSON(response);

                var img;
                if(beaconObject['pictureOfObject'] == null)
                {
                    img = "Kein Foto";
                }
                else
                {
                    img = "<img src='data:image/png;base64," + beaconObject['pictureOfObject']['picture'] + " ' width='250' height='250'>"
                }


                var html =  "<h4>BeaconObject" + "</h4>" +
                            "<p id='id'>ID: " + beaconObject['id'] + "</p>" +
                            "<p>Name: " + beaconObject['name'] + "</p>" +
                            "<p>Foto: " + img + "</p>" +
                            "<h5>Beacon: " + "</h5>" +
                            "<p>UUID: " + beaconObject['beacon']['uuid'] + "</p>" +
                            "<p>Major: " + beaconObject['beacon']['major'] + "</p>" +
                            "<p>Minor: " + beaconObject['beacon']['minor'] + "</p>" +
                            "<h5>Ort: " + "</h5>" +
                            "<p>ID: " + beaconObject['location']['id'] + "</p>" +
                            "<p>Gebäude: " + beaconObject['location']['building'] + "</p>" +
                            "<p>Stockwerk: " + beaconObject['location']['floor'] + "</p>" +
                            "<p>Raum: " + beaconObject['location']['room'] + "</p>";


                $('.modal-title').text("Details");
                $('.modal-body').html(html);
                /*
                Since the server does not support the object history, this button is deactivated

                $('.modal-footer').html("<button id='history' class='btn btn-default' data-dismiss='modal' onclick='toHistory()'>Historie anzeigen</button>" +
                    "<button type='button' class='btn btn-default' data-dismiss='modal'>Schließen</button>");
                */
                $('.modal-footer').html("<button type='button' class='btn btn-default' data-dismiss='modal'>Schließen</button>");

                $('#myModal').modal('show');
            }
        });
    },
    /**
     * This function is called on click on the remove button and deletes the selected beacon object.
     * @param e eventhandler
     * @param value value of the item
     * @param row content of the row
     * @param index index of the item
     */
    'click .remove': function (e, value, row, index) {
        var id = ($.parseJSON(JSON.stringify(row))['id']);
        $('.modal-body').html("<p>Möchten Sie das BeaconObjekt mit der ID <span id='id' value='"+id+"'>" + id + "</span> wirklich löschen?</p>");
        $('.modal-footer').html("<button id='delete' class='btn btn-default' data-dismiss='modal' onclick='deleteBeaconObject()'>Löschen</button>" +
            "<button type='button' class='btn btn-default' data-dismiss='modal'>Schließen</button>");
        $('#myModal').modal('show');


    },
}
$(window).resize(function () {
    $table.bootstrapTable('resetView');
});

/**
 * This function redirects to the task history of the selected object
 */
function toHistory() {
    var text = document.getElementById("id").textContent;
    var id = text.substring(text.lastIndexOf(' ') + 1);
    $.redirect('ObjectHistory.php', {'id' : id});
}





/*
 This script is used to redirect to the object history page.


 jQuery Redirect v1.0.6
 Copyright (c) 2013-2017 Miguel Galante
 Copyright (c) 2011-2013 Nemanja Avramovic, www.avramovic.info
 Licensed under CC BY-SA 4.0 License: http://creativecommons.org/licenses/by-sa/4.0/
 This means everyone is allowed to:
 Share - copy and redistribute the material in any medium or format
 Adapt - remix, transform, and build upon the material for any purpose, even commercially.
 Under following conditions:
 Attribution - You must give appropriate credit, provide a link to the license, and indicate if changes were made. You may do so in any reasonable manner, but not in any way that suggests the licensor endorses you or your use.
 ShareAlike - If you remix, transform, or build upon the material, you must distribute your contributions under the same license as the original.
 */
(function ($) {
    'use strict';

    /**
     * jQuery Redirect
     * @param {string} url - Url of the redirection
     * @param {Object} values - (optional) An object with the data to send. If not present will look for values as QueryString in the target url.
     * @param {string} method - (optional) The HTTP verb can be GET or POST (defaults to POST)
     * @param {string} target - (optional) The target of the form. "_blank" will open the url in a new window.
     * @param {boolean} traditional - (optional) This provides the same function as jquery's ajax function. The brackets are omitted on the field name if its an array.  This allows arrays to work with MVC.net among others.
     */
    $.redirect = function (url, values, method, target, traditional) {
        method = (method && ["GET", "POST", "PUT", "DELETE"].indexOf(method.toUpperCase()) !== -1) ? method.toUpperCase() : 'POST';


        url = url.split("#");
        var hash = url[1] ? ("#" + url[1]) : "";
        url = url[0];

        if (!values) {
            var obj = $.parseUrl(url);
            url = obj.url;
            values = obj.params;
        }

        values = removeNulls(values);

        var form = $('<form>')
            .attr("method", method)
            .attr("action", url + hash);


        if (target) {
            form.attr("target", target);
        }

        var submit = {}; //Create a symbol
        form[0][submit] = form[0].submit;
        iterateValues(values, [], form, null, traditional);
        $('body').append(form);
        form[0][submit]();
    };

    //Utility Functions
    /**
     * Url and QueryString Parser.
     * @param {string} url - a Url to parse.
     * @returns {object} an object with the parsed url with the following structure {url: URL, params:{ KEY: VALUE }}
     */
    $.parseUrl = function (url) {

        if (url.indexOf('?') === -1) {
            return {
                url: url,
                params: {}
            };
        }
        var parts = url.split('?'),
            query_string = parts[1],
            elems = query_string.split('&');
        url = parts[0];

        var i, pair, obj = {};
        for (i = 0; i < elems.length; i+= 1) {
            pair = elems[i].split('=');
            obj[pair[0]] = pair[1];
        }

        return {
            url: url,
            params: obj
        };
    };

    //Private Functions
    var getInput = function (name, value, parent, array, traditional) {
        var parentString;
        if (parent.length > 0) {
            parentString = parent[0];
            var i;
            for (i = 1; i < parent.length; i += 1) {
                parentString += "[" + parent[i] + "]";
            }

            if (array) {
                if (traditional)
                    name = parentString;
                else
                    name = parentString + "[" + name + "]";
            } else {
                name = parentString + "[" + name + "]";
            }
        }

        return $("<input>").attr("type", "hidden")
            .attr("name", name)
            .attr("value", value);
    };

    var iterateValues = function (values, parent, form, isArray, traditional) {
        var i, iterateParent = [];
        Object.keys(values).forEach(function(i) {
            if (typeof values[i] === "object") {
                iterateParent = parent.slice();
                iterateParent.push(i);
                iterateValues(values[i], iterateParent, form, Array.isArray(values[i]), traditional);
            } else {
                form.append(getInput(i, values[i], parent, isArray, traditional));
            }
        });
    };

    var removeNulls = function (values) {
        var propNames = Object.getOwnPropertyNames(values);
        for (var i = 0; i < propNames.length; i++) {
            var propName = propNames[i];
            if (values[propName] === null || values[propName] === undefined) {
                delete values[propName];
            } else if (typeof values[propName] === 'object') {
                values[propName] = removeNulls(values[propName]);
            } else if (values[propName].length < 1) {
                delete values[propName];
            }
        }
        return values;
    };

}(window.jQuery || window.Zepto || window.jqlite));