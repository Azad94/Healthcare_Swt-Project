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
 * Function for populating drop down menu for roles
 * @param roles roles to populate drop down
 * @returns {string} HTML string of the drop down menu
 */
function populateRoleDropdown(roles)
{
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
 * Function for updating a user
 */
function putUser()
{
    $.ajax({
        type: 'POST',
        url: 'UserDetails.php',
        data: {
            method: "put",
            id: $('#id').val(),
            name: $('#name').val(),
            password: $('#password').val(),
            email: $('#email').val(),
            roleId: $('#role').val(),
        },
        success: function (response) {
            $('#myModal').on('hidden.bs.modal', function () {
                location.reload();
            })

        }
    });
}

/**
 * Function for deleting a user
 */
function deleteUser() {
    var id = parseInt($('#id').text());
    $.ajax({
        type: 'POST',
        url: 'UserDetails.php',
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
     * This function is called on click on the edit button. It gets the information about the selected user and offers a form for editing it.
     * @param e eventhandler
     * @param value value of the item
     * @param row content of the row
     * @param index index of the item
     */
    'click .edit': function (e, value, row, index) {
        var id = $.parseJSON(JSON.stringify(row))['id'];
        $.ajax({
            type: 'POST',
            url: 'UserDetails.php',
            data: {method: "getput", id: id},
            success: function (response) {
                var response = $.parseJSON(response);
                var html =  "<h4>Nutzer" + "</h4>" +
                    "<p>ID: " + "<input readonly id='id' class='form-control' value='" + response['user']['id'] + "'>" + "</p>" +
                    "<p>Name: " + "<input type='text' id='name' class='form-control' value='" + response['user']['name'] + "'>" + "</p>" +
                    "<p>Passwort: " + "<input type='text' id='password' class='form-control' value='" + response['user']['password'] + "'>" + "</p>" +
                    "<p>E-Mail: " + "<input type='text' id='email' class='form-control' value='" + response['user']['email'] + "'>" + "</p>" +
                    "<h5>Rolle: " + populateRoleDropdown(response['roles']) + "</h5>";


                $('.modal-title').text("Bearbeiten");
                $('.modal-body').html(html);
                $('.modal-footer').html("<button id='save' class='btn btn-default' data-dismiss='modal' onclick='putUser()'>Speichern</button>" +
                    "<button type='button' class='btn btn-default' data-dismiss='modal'>Schließen</button>");

                $('#myModal').modal('show');
            }
        });
    },
    /**
     * This function is called on click on the details button and gets the information about the selected user.
     * @param e eventhandler
     * @param value value of the item
     * @param row content of the row
     * @param index index of the item
     */
    'click .details': function (e, value, row, index) {
        var id = $.parseJSON(JSON.stringify(row))['id'];
        $.ajax({
            type: 'POST',
            url: 'UserDetails.php',
            data: {method: "get", id: id},
            success: function (response) {
                var user = $.parseJSON(response);
                var html =  "<h4>Nutzer" + "</h4>" +
                    "<p id='id'>ID: " + user['id'] + "</p>" +
                    "<p>Name: " + user['name'] + "</p>" +
                    "<p>Passwort: " + user['password'] + "</p>" +
                    "<p>E-Mail: " + user['email'] + "</p>" +
                    "<p>Last Update: " + user['lastUpdate'] + "</p>" +
                    "<h5>Rolle: " + "</h5>" +
                    "<p>ID: " + user['role']['id'] + "</p>" +
                    "<p>Name: " + user['role']['name'] + "</p>";


                $('.modal-title').text("Details");
                $('.modal-body').html(html);
                $('.modal-footer').html("<button type='button' class='btn btn-default' data-dismiss='modal'>Schließen</button>");


                $('#myModal').modal('show');
            }
        });
    },
    /**
     * This function is called on click on the remove button and deletes the selected user.
     * @param e eventhandler
     * @param value value of the item
     * @param row content of the row
     * @param index index of the item
     */
    'click .remove': function (e, value, row, index) {
        var id = ($.parseJSON(JSON.stringify(row))['id']);
        var name = ($.parseJSON(JSON.stringify(row))['name']);
        $('.modal-body').html("<p>Möchten Sie den User \"" + name + "\" mit ID <span id='id' value='"+id+"'>" + id + "</span> wirklich löschen?</p>");
        $('.modal-footer').html("<button id='delete' class='btn btn-default' data-dismiss='modal' onclick='deleteUser()'>Löschen</button>" +
            "<button type='button' class='btn btn-default' data-dismiss='modal'>Schließen</button>");
        $('#myModal').modal('show');


    },
}
$(window).resize(function () {
    $table.bootstrapTable('resetView');
});