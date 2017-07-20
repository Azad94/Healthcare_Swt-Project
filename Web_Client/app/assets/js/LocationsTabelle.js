 /**
       * 
       * @type Number
       * base by Kim Huber
       * adjusted by Radoslaw Speier 
       */  
    var count=0;//ändern Button
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
        
        /* function for editing a location
         * @param e eventhandler
         * @param value value of the item
         * @param row content of the row
         * @param index index of the item
         */
        'click .edit': function (e, value, row, index) {
            var id = $.parseJSON(JSON.stringify(row))['id'];
            $.ajax({
                type: 'POST',
                url: 'LocationAction.php' ,
                data: { action: "edit" , id: id},
                success: function(response) {
                    
                    var parsedJson = JSON.parse(response);
                    
                    var bodyString = createTable(parsedJson.id,parsedJson.building,parsedJson.room,parsedJson.floor,"");
                    
                    $('.modal-body').html(bodyString);
                    var button = "<button type=\"button\" id=\"edit\" class=\"btn btn-primary\" >Ändern</button>";
                    
                    if (count<1){
                      $('.modal-footer').append(button);
                      count++;
                    }
                    $('#myModal').modal('show');
                    
                    $('#edit').click(function(e){
                       id = $('#ID').val();
                       building = $('#BUILDING').val();
                       room = $('#ROOM').val();
                       floor = $('#FLOOR').val();
                       
                       $.ajax({
                            type: 'POST',
                            url: 'LocationAction.php' ,
                            data: { action: "save" , id: id ,building:building, room: room, floor : floor},
                            success: function(response) {
                                
                               $('.modal-body').html("Ort mit id: "+id+" geändert");
                               
                            }
                       });
                       
                       $(this).remove();
                       count--;
                    });
                    
                    
                }
             });
        },
        /* function for displaing Location details
         * @param e eventhandler
         * @param value value of the item
         * @param row content of the row
         * @param index index of the item
         */
        'click .details': function (e, value, row, index) {
           
            var id = $.parseJSON(JSON.stringify(row))['id'];
            
            $.ajax({
                type: 'POST',
                url: 'LocationAction.php' ,
                data: { action: "details" , id: id},
                success: function(response) {
                    $('.modal-body').html(response);
                    $('#myModal').modal('show');
                }
             });
        },
        /*function for deleting a Location
         * @param e eventhandler
         * @param value value of the item
         * @param row content of the row
         * @param index index of the item
         */
        'click .remove': function (e, value, row, index) {
             var id = $.parseJSON(JSON.stringify(row))['id'];
            $.ajax({
                type: 'POST',
                url: 'LocationAction.php' ,
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
    
    /* create Table for edit view */
     function createTable(id,building,room,floor,readonly){
        return "<table border=\"0 \">"
               +  "<tr>"
               +    " <th>Ort</th>"
               + createRow("Id","ID",id,"readonly","number")
               + createRow("Gebäude","BUILDING",building,readonly,"number")
               + createRow("Raum","ROOM",room,readonly,"number")
               + createRow("Flur","FLOOR",floor,readonly,"number")+"</table>";
    }
    
    /* creates one row of a table **/
    function createRow(germanName,name,value,readonly,type)
    {
        return "<tr><td>"+germanName+"</td><td><input class='form-control' id=\""+name+"\" type=\""+type+"\" value="+value+" "+readonly+"></td></tr>";}