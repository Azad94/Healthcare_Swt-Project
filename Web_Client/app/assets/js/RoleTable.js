/**
 * Written by Radoslaw Speier, base by Kim Huber
  * @type {number}
 */
 var count = 0;//Ändern Button
 
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

    window.operateEvents = {
   
         /* function for editing a selected role
          * @param e eventhandler
          * @param value value of the item
          * @param row content of the row
          * @param index index of the item
          */
        'click .edit': function (e, value, row, index) {
            var id = $.parseJSON(JSON.stringify(row))['id'];
            $.ajax({
                type: 'POST',
                url: 'RoleAction.php' ,
                data: { action: "edit" , id: id},
                success: function(response) {
                    //alert(response);
                    console.log(response);
                    var parsedJson = $.parseJSON(response);
                    
                    var bodyString = createTable(parsedJson.id,parsedJson.name,"");
                    
                    $('.modal-body').html(bodyString);
                    var button = "<button type=\"button\" id=\"edit\" class=\"btn btn-primary\" >Ändern</button>";
                    
                    if (count<1){
                      $('.modal-footer').append(button);
                      count++;
                    }
                    $('.modal-title').text("Bearbeiten");
                    $('#myModal').modal('show');
                    
                    $('#edit').click(function(e){
                       id = $('#Id').val();
                       name = $('#Name').val();
                       
                       $.ajax({
                            type: 'POST',
                            url: 'RoleAction.php' ,
                            data: { action: "save" , id: id ,name:name},
                            success: function(response) {
                               $('.modal-body').html(response);
                               
                            }
                       });
                       
                       $(this).remove();
                       count--;
                    });
                    
                    
                }
             });
        },
        /* function for displaying a selected role
         * @param e eventhandler
         * @param value value of the item
         * @param row content of the row
         * @param index index of the item
         */
        'click .details': function (e, value, row, index) {
            
            var id = $.parseJSON(JSON.stringify(row))['id'];
            
            $.ajax({
                type: 'POST',
                url: 'RoleAction.php' ,
                data: { action: "details" , id: id},
                success: function(response) {
                    $('.modal-body').html(response);
                    $('#myModal').modal('show');
                }
             });
        },
        /* function for deleting a selected role
         * @param e eventhandler
         * @param value value of the item
         * @param row content of the row
         * @param index index of the item
         */
        'click .remove': function (e, value, row, index) {
            var id = $.parseJSON(JSON.stringify(row))['id'];
            $.ajax({
                type: 'POST',
                url: 'RoleAction.php' ,
                data: { action: "delete" , id: id},
                success: function(response) {
                    $('.modal-body').html(response);
                    $('#myModal').modal('show');
                }
             });

        }
    };

    $(window).resize(function () {
        $table.bootstrapTable('resetView');
    });
    
       
    /** creates a html table for a role id and name**/   
    function createTable(id,name,readonly){
        return "<table border=\"0 \">"
               +  "<tr>"
               +    " <th>Beacon</th>"
               + createRow("Id",id,"readonly","number")
               + createRow("Name",name,readonly,"text")
               +"</table>";
    }
    /**creates a html table row 
     * name is the name of element
     * value is the value
     * readonly indicates if element is readonly or not
     * type indicate if it's a text or number field for example**/
    function createRow(name,value,readonly,type)
    {
        return "<tr><td>"+name+"</td><td><input class='form-control' id=\""+name+"\" type=\""+type+"\" value="+value+" "+readonly+"></td></tr>";
    }
   