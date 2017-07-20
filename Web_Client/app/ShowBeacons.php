<?php
/**
 * HTML base code by Kim Huber, adjusted by Radoslaw Speier
 * PHP code by Radoslaw Speier
 */

/**
 * Load Model and Api
 */
include "autoload.php";

/**
 * Start session and check if user is logged in
 */
session_start();
if(!isset($_SESSION['user'])){
    header("location:Login.php");
    exit;
}
$title = "Beacons anzeigen"; //html page title

$scriptExpand = "'Beacons'"; // needed for proper menu expansion after klick
$script ='"assets/js/BeaconsTable.js"'; //additional script content;


$api_instance = new Swagger\Client\Api\BeaconApi();


/* fill the table with beacon data */
function populateTable($tableData){
    
    
    $result="";
    foreach ($tableData as $row)
    {   
        $result .=  '<tr>'
             
            . '<td>' . $row->getUuid() . '</td>'
            . '<td>' . $row->getMajor() . '</td>'
            . '<td>' . $row->getMinor() . '</td>'
            . translate($row->getMajor(),$row->getMinor())
            . '</tr>';
    }
    return $result;
}
/* translate the major minor info into human readable html table entry information*/
function translate($major,$minor){
    $string="";
    switch($major) {
        
       case 0: $string.='<td> Techniker</td>';
               break;
       case 1: $string.='<td> Betten-Schieber</td>';
                break;
       default : $string.='<td> --- </td>';
                
    }
    switch($minor) {
       
           case 0 : $string.='<td> Bett</td>';
                    break;
           case 1 : $string.='<td> Tür</td>';
                    break;
           default: $string.='<td> --- </td>';
                    
    }
    return $string;
}

/** generate header information for the table **/
function generateHeader(){
    $attributes = Swagger\Client\Model\Beacon::attributeMap();
    $result = "";
    $attributes['Rolle']="Rolle";
    $attributes['Objekt']="Objekt";
    foreach($attributes as $key=> $value){
        $result.='<th data-field="'.$key.'" data-sortable="true">'.$value.'</th>';
    }
    return $result.='<th data-field="actions" data-formatter="operateFormatter" data-events="operateEvents">Aktionen</th>';
}

try {
    $beacons = $api_instance->beaconsGet();

} catch (Exception $e) {
    echo 'Exception when calling BeaconObjectApi->beaconObjectsGet: ', $e->getMessage(), PHP_EOL;
}


$content = '<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div id="pushobj">
            
            <div id="myModal" class="modal fade" role="dialog">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Details</h4>
                  </div>
                  <div class="modal-body">
                  
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Schließen</button>
                  </div>
                </div>
            
              </div>
            </div>
            <div class="fresh-table full-color-blue full-screen-table">
                <table id="fresh-table"><thead>'.generateHeader().'</thead>
                    <tbody>'.populateTable($beacons).''
        . '     </tbody>'
        .       '</table>'
        .'</div></div></div>';

include 'Template.php';
?>