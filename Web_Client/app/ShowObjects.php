<?php
/**
 * HTML base code by Kim Huber, adjusted by Jan-Ole Petersen
 * PHP code by Jan-Ole Petersen
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


/**
 * Get all beacon objects
 */
$api_instance = new Swagger\Client\Api\BeaconObjectApi();
try {
    $beaconObjects = $api_instance->beaconObjectsGet();

} catch (Exception $e) {
    echo 'Exception when calling BeaconObjectApi->beaconObjectsGet: ', $e->getMessage(), PHP_EOL;
}

/**
 * Function for population the Table
 * @param $tableData Data to populate the table
 * @return string HTML string of the table rows
 */
function populateTable($tableData){

    $result="";
    foreach ($tableData as $row)
    {
        $result .=  '<tr>'
            . '<td>' . $row->getId() . '</td>'
            . '<td>' . $row->getName() . '</td>'
            . '<td>' . $row->getBeacon()->getUuid() . '</td>'
            . '<td>' . $row->getLocation()->getBuilding() . '</td>'
            . '<td>' . $row->getLocation()->getFloor() . '</td>'
            . '<td>' . $row->getLocation()->getRoom() . '</td>'
            . '</tr>';
    }
    return $result;
}

/**
 * Title of the page
 */
$title = "Objekte anzeigen";
/**
 * Which menu item to show
 */
$scriptExpand = "'Objekte'";
/**
 * Additional script for the page
 */
$script ='"assets/js/BeaconObjectTable.js"';

/**
 * HTML code of the template content
 */
$content = '
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div id="pushobj">
        
            <div id="myModal" class="modal fade" role="dialog">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title"></h4>
                  </div>
                  <div class="modal-body">
                  </div>
                  <div class="modal-footer">
                  </div>
                </div>
            
              </div>
            </div>
            <div class="fresh-table full-color-blue full-screen-table">
                <table id="fresh-table">
                    <thead>
                    <th data-field="id" data-sortable="true">ID</th>
                    <th data-field="name" data-sortable="true">Name</th>
                    <th data-field="beaconUuid" data-sortable="true">Beacon-UUID</th>
                    <th data-field="building" data-sortable="true">Gebäude</th>
                    <th data-field="floor" data-sortable="true">Stockwerk</th>
                    <th data-field="room" data-sortable="true">Raum</th>
                    <th data-field="actions" data-formatter="operateFormatter" data-events="operateEvents">Aktionen</th>
                    </thead>
                    <tbody>
                    '.populateTable($beaconObjects).'
                    </tbody>
                </table>
            </div>
        </div>
    </div>';

include 'Template.php';
?>