/**
 * Written by Jan-Ole Petersen
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
 * Function for checking the state of a Task
 * @param state state to be checked
 * @returns {*} string representation of the state
 */
function checkState(state)
    {
        if(state == 1)
            return "Offen";
        else if (state == 2)
        return "In Bearbeitung";
        else if (state == 3)
        return "Abgeschlossen";
    }

/**
 * Function to check the type of SubTask and to get slider or checkbox value
 * @param subTask SubTask to be checked
 * @returns {string} HTML string with SubTask content
 */
function checkSubtask(subTask)
    {
        if('value' in subTask)
        {
            return "<p>CheckBox " + (subTask['value'] ? "gesetzt" : "nicht gesetzt") + "</p>";
        }
        else
        {
            return "<p>Sliderwert: " + subTask['slider'] + "</p>";
        }
    }
/**
 * Function for string representation of a location
 * @param location location to be represented
 * @returns {string} string of the location
 */
function buildLocation(location)
    {
        return location['building'] + "." + location['floor'] + "." + location['room'];
    }

/**
 * Function to convert type from german to english
 * @param type type to be converted
 * @returns {*} string with the english representation
 */
function typeToEng(type)
    {
        switch (type)
        {
            case "Transportauftrag":
                type = "transportTask";
                break;
            case "Wartungsauftrag":
                type = "maintainanceTask";
                break;
            case "Reinigungsauftrag":
                type = "cleaningTask";
                break;
        }
        return type;
    }

/**
 * Function to convert type from english to german
 * @param type type to be converted
 * @returns {*} string with the german representation
 */
function typeToGer(type)
    {
        switch (type)
        {
            case "transportTask":
                type = "Transportauftrag";
                break;
            case "maintainanceTask":
                type = "Wartungsauftrag";
                break;
            case "cleaningTask":
                type = "Reinigungsauftrag";
                break;
        }
        return type;
    }
/**
 * Function for populating drop down menu for beacon objects
 * @param beaconObjects beacon objects to populate drop down
 * @returns {string} HTML string of the drop down menu
 */
function populateBeaconObjectDropdown(beaconObjects)
    {
        var result = "<select id='beaconObject' class='form-control'>";
        result += "<option value='' disabled selected>BeaconObjekt wählen</option>";
        for(var i = 0; i < beaconObjects.length; i++)
        {
            result += "<option value='" + beaconObjects[i]['id'] + "'>" + beaconObjects[i]['name'] + "</option>";
        }
        result += "</select>";
        return result;
    }

/**
 * Function for populating drop down menu for locations
 * @param locations location to populate drop down
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
 * Function for populating drop down menu for roles
 * @param roles roles to populate drop down
 * @returns {string} HTML string of the drop down menu
 */
 function populateRoleDropdown(roles) {
     var result = "<select id='role' class='form-control'>";
     result += "<option value='' disabled selected>Rolle wählen</option>";
     for(var i = 0; i < roles.length; i++)
     {
         result += "<option value='" + roles[i]['id'] + "'>" + roles[i]['name'] + "</option>";
     }
     result += "</select>";
     return result;
 }

/**
 * Function for closing a Task
 */
function closeTask()
 {
     var id = parseInt($('#id').text());
     var type = $('#type').text();
     console.log(id);
     console.log(type);
     $.ajax({
         type: 'POST',
         url: 'TaskDetails.php',
         data: {
             method: "close",
             id: id,
             type: typeToEng(type)
         },
         success: function (response) {
             console.log("reload");
             $('#myModal').on('hidden.bs.modal', function () {
                 location.reload();
             })

         }
     });
 }

/**
 * Function for updating a Task
 */
function putTask()
 {

     $.ajax({
         type: 'POST',
         url: 'TaskDetails.php',
         data: {
             method: "put",
             id: $('#id').val(),
             type: typeToEng($('#type').val()),
             description: $('#description').val(),
             level: $('#level').val(),
             beaconObjectId: $('#beaconObject').val(),
             roleId: $('#role').val(),
             targetLocationId: $('#location').val(),
             frequency: $('#frequency').val()
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
            var type = $.parseJSON(JSON.stringify(row))['art'];
            type = typeToEng(type);
            $.ajax({
                type: 'POST',
                url: 'TaskDetails.php',
                data: {method: "getput", id: id, type: type},
                success: function (response) {

                    var response = $.parseJSON(response);
                    var html =  "<h4>Auftrag" + "</h4>" +
                        "<p>ID: " + "<input readonly id='id' class='form-control' value='" + response['task']['id'] + "'>" + "</p>" +
                        "<p>Auftragstyp: " + "<input readonly id='type' type='text' class='form-control' value='" + typeToGer(response['task']['type']) + "'>" + "</p>" +
                        "<p>Ersteller: " + "<input readonly type='text' id='creator' class='form-control' value='" + response['task']['creator']['name'] + "'>" + "</p>" +
                        "<p>Kommentar: " + "<input type='text' id='description' class='form-control' value='" + response['task']['description'] + "'>" + "</p>" +
                        "<p>Priorität: " + "<input type='number' min='1' max='3' id='level' class='form-control' value='" + response['task']['level'] + "'>" + "</p>" +
                        "<h5>BeaconObject: " + "</h5>" +
                        "<p>Name: " + populateBeaconObjectDropdown(response['beaconObjects']) + "</p>" +
                        "<h5>Rolle: " + "</h5>" +
                        "<p>Name: " + populateRoleDropdown(response['roles']) + "</p>";

                    if("targetLocation" in response['task'])
                    {
                        html += "<h5>Zielort: " + "</h5>" +
                            "<p>" + populateLocationDropdown(response['locations']) + "</p>";
                    }
                    if('repeatTaskInDay' in response['task'])
                    {
                        html += "<p>Wiederholungszyklus: " +
                            "<select id='frequency' class='form-control'>" +
                            "<option value='' disabled selected>Zyklus wählen</option>" +
                                "<option value='7'>1 Woche</option>" +
                                "<option value='14'>2 Wochen</option>" +
                                "<option value='30'>1 Monat</option>" +
                                "<option value='60'>2 Monate</option>" +
                                "<option value='180'>6 Monate</option>" +
                                "<option value='365'>1 Jahr</option>" +
                                "<option value='730'>2 Jahreoption>" +
                            "</select>";
                    }


                    $('.modal-title').text("Bearbeiten");
                    $('.modal-body').html(html);
                    $('.modal-footer').html("<button id='save' class='btn btn-default' data-dismiss='modal' onclick='putTask()'>Speichern</button>" +
                        "<button type='button' class='btn btn-default' data-dismiss='modal'>Schließen</button>");

                    $('#myModal').modal('show');
                }
            });
        },
        /**
         * This function is called on click on the details button and gets the information about the selected Task.
         * @param e eventhandler
         * @param value value of the item
         * @param row content of the row
         * @param index index of the item
         */
        'click .details': function (e, value, row, index) {
            var id = $.parseJSON(JSON.stringify(row))['id'];
            var type = $.parseJSON(JSON.stringify(row))['art'];
            type = typeToEng(type);

            $.ajax({
                type: 'POST',
                url: 'TaskDetails.php',
                data: {method: "get", id: id, type: type},
                success: function (response) {
                    var task = $.parseJSON(response);
                    var html =  "<h4>Auftrag" + "</h4>" +
                        "<p id='id'>ID: " + task['id'] + "</p>" +
                        "<p>Name: " + task['name'] + "</p>" +
                        "<p>Ersteller: " + task['creator']['name'] + "</p>" +
                        "<p>Editor: " + (('editor' in task) ? task['editor']['name'] : "nicht editiert") + "</p>" +
                        "<p>Kommentar: " + task['description'] + "</p>" +
                        "<p>Priorität: " + task['level'] + "</p>" +
                        "<p>Erstellungszeit: " + task['creationTime'] + "</p>" +
                        "<p>Annahmezeit: " + (('acceptedTime' in task) ? task['acceptedTime'] : "nicht angenommen") + "</p>" +
                        "<p>Abschlusszeit: " + (('closedTime' in task) ? task['closedTime'] : "nicht abgeschlossen") + "</p>" +
                        "<p>Status: " + checkState(task['state']) + "</p>" +
                        "<h5>BeaconObject: " + "</h5>" +
                        "<p>ID: " + task['beaconObject']['id'] + "</p>" +
                        "<p>Name: " + task['beaconObject']['name'] + "</p>" +
                        "<p>Beacon-UUID: " + task['beaconObject']['beacon']['uuid'] + "</p>" +
                        "<p>Objektort: " + buildLocation(task['beaconObject']['location']) + "</p>" +
                        "<br>" +
                        "<p>Auftragstyp: " + typeToGer(task['type']) + "</p>" +
                        "<br>" +
                        "<h5>Rolle: " + "</h5>" +
                        "<p>Name: " + task['role']['name'] + "</p>";
                    if('targetLocation' in task)
                    {
                        html += "<h5>Zielort: " + "</h5>" +
                            "<p>" + buildLocation(task['targetLocation']) + "</p>";
                    }
                    if('subTasks' in task)
                    {
                        html += "<h5>SubTasks: " + "</h5>";
                        for(var i = 0; i < task['subTasks'].length; i++)
                        {
                            html += "<p>Titel: " + task['subTasks'][i]['title'] + "</p>" +
                                    checkSubtask(task['subTasks'][i]);

                        }

                    }
                    if('repeatTaskInDay' in task)
                    {
                        html += "<h5>Zyklus: " + "</h5>";
                        if(task['repeatTaskInDay'] > 0)
                        {
                            html += "<p>Wiederhole alle " + task['repeatTaskInDay'] + " Tage</p>";
                        }
                        else
                        {
                            html += "<p>Kein zyklischer Auftrag</p>";
                        }

                    }


                    $('.modal-title').text("Details");
                    $('.modal-body').html(html);
                    $('.modal-footer').html("<button type='button' class='btn btn-default' data-dismiss='modal'>Schließen</button>");


                    $('#myModal').modal('show');
                }
            });

        },
        /**
         * This function is called on click on the remove button and closes the selected Task.
         * @param e eventhandler
         * @param value value of the item
         * @param row content of the row
         * @param index index of the item
         */
        'click .remove': function (e, value, row, index) {
            var id = ($.parseJSON(JSON.stringify(row))['id']);
            var type = ($.parseJSON(JSON.stringify(row))['art']);
            var state = ($.parseJSON(JSON.stringify(row))['status']);
            if(state != "Abgeschlossen")
            {
                $('.modal-body').html("<p>Möchten Sie den <span id='type' value='"+type+"'>" + type + "</span> mit der ID <span id='id' value='"+id+"'>" + id + "</span> abschließen?</p>");
                $('.modal-footer').html("<button id='delete' class='btn btn-default' data-dismiss='modal' onclick='closeTask()'>Abschließen</button>" +
                    "<button type='button' class='btn btn-default' data-dismiss='modal'>Schließen</button>");
            }
            else
            {
                $('.modal-body').html("<p>Auftrag ist bereits abgeschlossen!</p>");
                $('.modal-footer').html("<button type='button' class='btn btn-default' data-dismiss='modal'>Schließen</button>")
            }

            $('#myModal').modal('show');

        }
    };

    $(window).resize(function () {
        $table.bootstrapTable('resetView');
    });