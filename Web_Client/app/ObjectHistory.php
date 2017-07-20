<?php
/**
 * HTML base code by Kim Huber, adjusted by Jan-Ole Petersen
 * PHP code by Jan-Ole Petersen
 *
 *
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
 * Get all Tasks for the specified id
 */
$beaconObjectId = $_POST['id'];
$beaconObjectApi_instance = new \Swagger\Client\Api\BeaconObjectApi();
$tasks = [];
try
{
    array_merge($tasks, $beaconObjectApi_instance->beaconObjectsBeaconObjectIdMaintainancetasksGet($beaconObjectId));
    array_merge($tasks, $beaconObjectApi_instance->beaconObjectsBeaconObjectIdCleaningtasksGet($beaconObjectId));
    array_merge($tasks, $beaconObjectApi_instance->beaconObjectsBeaconObjectIdTransporttasksGet($beaconObjectId));
}
catch (Exception $e)
{
    $e->getMessage();
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
            . '<td>' . $row->getCreator()->getName() . '</td>'
            . '<td>' . checkInstance($row) . '</td>'                         // ART DES TASKS
            . '<td>' . checkState($row->getState()) . '</td>'                      // STATE INT IN WORT
            . '<td>' . $row->getDiscription() . '</td>'
            . '</tr>';
    }
    return $result;
}

/**
 * Function for converting state to a word
 * @param $state state to be converted
 * @return string word representation of the state
 */
function checkState($state)
{
    if($state == 1)
        return "Offen";
    elseif ($state == 2)
        return "In Bearbeitung";
    elseif ($state == 3)
        return "Abgeschlossen";
}

/**
 * Function for checking the kind of a Task
 * @param $task Task to be checked
 * @return string word representation of the Task kind
 */
function checkInstance($task)
{
    if($task instanceof \Swagger\Client\Model\MaintainanceTask)
        return "Wartungsauftrag";
    elseif ($task instanceof \Swagger\Client\Model\TransportTask)
        return "Transportauftrag";
    elseif ($task instanceof \Swagger\Client\Model\CleaningTask)
        return "Reinigungsauftrag";
}


/**
 * Title of the page
 */
$title = "Auftragstabelle";
/**
 * Additional script for the page
 */
$script = '"assets/js/TaskTable.js"';
/**
 * Which menu item to show
 */
$scriptExpand="'Aufträge'";
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
                <table id="fresh-table">
                    <thead>
                    <th data-field="id" data-sortable="true">Auftragsnummer</th>
                    <th data-field="name" data-sortable="true">Beauftragter</th>
                    <th data-field="art" data-sortable="true">Art</th>
                    <th data-field="status" data-sortable="true">Status</th>
                    <th data-field="comment">Kommentar</th>
                    <th data-field="actions" data-formatter="operateFormatter" data-events="operateEvents">Aktionen</th>
                    </thead>
                    <tbody>
                    '.populateTable($tasks).'
                    </tbody>
                </table>
            </div>
        </div>
    </div>';



include 'Template.php';

?>