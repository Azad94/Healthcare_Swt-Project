/**
 * Written by Radoslaw Speier, base by Kim Huber
 * @type {number}
 */
 var count = 0;//Ändern Button
 var print = 0;//Druck Button
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
            
            '<a rel="tooltip" title="QR-Code" class="table-action qr" href="javascript:void(0)">',
            '<i class="fa  fa-qrcode"></i>',
            '</a>',
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
        /*function for displaying and printing qr code*
         * @param e eventhandler
         * @param value value of the item
         * @param row content of the row
         * @param index index of the item
         */
        'click .qr' : function(e, value, row, index) {
          //console.log($.parseJSON(JSON.stringify(row))['uuid']);
          var uuid = $.parseJSON(JSON.stringify(row))['uuid'];
          
          $.ajax({
          type: 'POST',
          url: 'BeaconAction.php' ,
          data: { action: "qr" , uuid : uuid},
          success: function(response) {
             
             
            $('.modal-body').html(response);
            var button = "<button type=\"button\" id=\"print\" class=\"btn btn-primary\" >Drucken</button>";
            if (print<1){
                $('.modal-footer').append(button);
                print++;
            }
            $('#myModal').modal('show');
            $('#print').click(function(e){
                fensterOeffnen(response,"QR :"+uuid);
                
               
            });
        
            
          }
    });
           

        },
        /* function for edting the beacon
         * @param e eventhandler
         * @param value value of the item
         * @param row content of the row
         * @param index index of the item
         */
        'click .edit': function (e, value, row, index) {
            var uuid = $.parseJSON(JSON.stringify(row))['uuid'];
            $.ajax({
                type: 'POST',
                url: 'BeaconAction.php' ,
                data: { action: "edit" , uuid: uuid},
                success: function(response) {
                    
                    var parsedJson = JSON.parse(response);
                    
                    var bodyString = createTable(parsedJson.uuid,parsedJson.major,parsedJson.minor,"");
                    
                    $('.modal-body').html(bodyString);
                    var button = "<button type=\"button\" id=\"edit\" class=\"btn btn-primary\" >Ändern</button>";
                    
                    if (count<1){
                      $('.modal-footer').append(button);
                      count++;
                    }
                    $('.modal-title').text("Bearbeiten");
                    removePrintButton();
                    $('#myModal').modal('show');
                    
                    $('#edit').click(function(e){
                       
                       uuid = $('#UUID').val();
                       major = $('#MAJOR').val();
                       minor = $('#MINOR').val();
                       
                       $.ajax({
                            type: 'POST',
                            url: 'BeaconAction.php' ,
                            data: { action: "save" ,uuid:uuid, major: major, minor : minor},
                            success: function(response) {
                               $('.modal-body').html(response );
                               //"Beacon mit uuid: "+uuid+" geändert"
                            }
                       });
                       
                       $(this).remove();
                       count--;
                    });
                    
                    
                }
             });
        },
        /*function for displayng the beacon */
        'click .details': function (e, value, row, index) {
            
            var uuid = $.parseJSON(JSON.stringify(row))['uuid'];
            
            $.ajax({
                type: 'POST',
                url: 'BeaconAction.php' ,
                data: { action: "details" , uuid: uuid},
                success: function(response) {
                    $('.modal-body').html(response);
                    removePrintButton();
                    $('#myModal').modal('show');
                }
             });
        },
        /*function to remove the beacon */
        'click .remove': function (e, value, row, index) {
            var uuid = $.parseJSON(JSON.stringify(row))['uuid'];
            $.ajax({
                type: 'POST',
                url: 'BeaconAction.php' ,
                data: { action: "delete" , uuid: uuid},
                success: function(response) {
                    $('.modal-body').html(response);
                    removePrintButton();
                    $('#myModal').modal('show');
                }
             });

        }
    };

    $(window).resize(function () {
        $table.bootstrapTable('resetView');
    });
    
    /** creates a html table for beacons **/
    function createTable(uuid,major,minor,readonly){
        return "<table border=\"0 \">"
               +  "<tr>"
               +    " <th>Beacon</th>"
               + createRow("UUID",uuid,"readonly","text")
               + createRow("MAJOR",major,readonly,"number")
               + createRow("MINOR",minor,readonly,"number")+"</table>";
    }
    
    /**creates a beacon row entry for a html table **/
    function createRow(name,value,readonly,type)
    {
        return "<tr><td>"+name+"</td><td><input class='form-control' id=\""+name+"\" type=\""+type+"\" value="+value+" "+readonly+"></td></tr>";
    }
    //** opens a new nrowser print window for a QR code **/
    function fensterOeffnen (text,title) { 
  
        var qrWindow = window.open(title, "QR Code drucken", "width=800,height=600,left=100,top=200"); 
        qrWindow.document.write(text); 
        qrWindow.focus();
        qrWindow.print();
    }
    
    /*removes the print button, so that it won't appear in a different window */
    function removePrintButton(){
        element = document.getElementById("print");
        if (element!=null){
            element.remove();
            print=0;}
                     
    }