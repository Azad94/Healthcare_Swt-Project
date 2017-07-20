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

$api_instance = new Swagger\Client\Api\LocationApi();

try {

    $locations = $api_instance->locationsGet();
} catch (Exception $e) {
    echo 'Exception when calling LocationApi->locationsGet: ', $e->getMessage(), PHP_EOL;
}

/**
 * Function for population the Table
 * @param $tableData Data to populate the table
 * @return string HTML string of the table rows
 */
function populateTable($tableData)
{
    $result="";
    foreach ($tableData as $row)
    {
        $result .=  '<tr>'
            . '<td>' . $row->getId() . '</td>'
            . '<td>' . $row->getBuilding() . '</td>'
            . '<td>' . $row->getFloor() . '</td>'                         // ART DES TASKS
            . '<td>' . $row->getRoom() . '</td>'                      // STATE INT IN WORT
            . '</tr>';
    }
    return $result;
}

$title = "Orte anzeigen";
$scriptExpand = "'Orte'";

$script ='"assets/js/LocationsTabelle.js"';
$content='
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div id="pushobj">
        
            <div id="myModal" class="modal fade" role="dialog">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Details</h4>
                  </div>
                  <div class="modal-body">
                  hi
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Schließen</button>
                  </div>
                </div>
            
              </div>
            </div>
            <div class="fresh-table full-color-blue full-screen-table">
                <table id="fresh-table">
                    <thead>
                    <th data-field="id" data-sortable="true">ID</th>
                    <th data-field="building" data-sortable="true">Gebäude</th>
                    <th data-field="floor" data-sortable="true">Stockwerk</th>
                    <th data-field="room" data-sortable="true">Raum</th>
                    <th data-field="actions" data-formatter="operateFormatter" data-events="operateEvents">Aktionen</th>
                    </thead>
                    <tbody>
                    '.populateTable($locations).'
                    </tbody>
                </table>
            </div>
        </div>
    </div>';

include 'Template.php';
?>