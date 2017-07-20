<?php
/**
 * HTML code by Kim Huber
 * PHP code by Jan-Ole Petersen
 */

/**
 * Load Model and Api
 */
include 'autoload.php';

/**
 * Start session and check if user is logged in
 */
session_start();
if(!isset($_SESSION['user'])){
    header("location:Login.php");
    exit;
}

/**
 * Set defaults if no filter is set
 */
$date = new DateTime();
$open = 1;
$closed = 1;
$processing = 1;
$own = 0;
$main = 1;
$trans = 1;
$clean = 1;
$since = new DateTime("1970-01-01");
$since = $since->format(DateTime::ATOM);
$before = new DateTime("now");
$before = $before->format(DateTime::ATOM);
$limit = 100;

/**
 * Set parameters if filter is set
 */
if(isset($_SESSION['taskParams']))
{
    $open = $_SESSION['taskParams']['open'];
    $closed = $_SESSION['taskParams']['closed'];
    $processing = $_SESSION['taskParams']['processing'];
    if($open == 0 && $processing == 0 && $closed == 0) $open = $processing = $closed = 1;
    $own = $_SESSION['taskParams']['own'];
    $main = $_SESSION['taskParams']['maintenance'];
    $trans = $_SESSION['taskParams']['transport'];
    $clean = $_SESSION['taskParams']['cleaning'];
    if($main == 0 && $trans == 0 && $clean == 0) $main = $trans = $clean = 1;
    if($_SESSION['taskParams']['since'] != 0)
    {
        $since = new DateTime($_SESSION['taskParams']['since']);
        $since = $since->format(DateTime::ATOM);
    }

    if($_SESSION['taskParams']['before'] != 0)
    {
        $before = new DateTime($_SESSION['taskParams']['before']);
        $before = $before->format(DateTime::ATOM);
    }

    if($_SESSION['taskParams']['limit'] != 0)
        $limit = $_SESSION['taskParams']['limit'];

    unset($_SESSION['taskParams']);
}

$tasks = [];
/**
 * Body for the request for Tasks
 */
$user_name = new \Swagger\Client\Model\UserName([
    "user_name" => $_SESSION['user']->getName(),
    "id" => $_SESSION['user']->getId()
]);

/**
 * Get MaintenanceTasks
 */
if($main == 1)
{
    $api_instance = new Swagger\Client\Api\MaintainanceTaskApi();

    try {
        $result = $api_instance->maintainancetasksGet($user_name, $limit, $before, $since, $open, $processing, $closed, $own);

        $tasks = array_merge($tasks, $result);
    } catch (Exception $e) {
        echo 'Exception when calling MaintainanceTaskApi->maintainancetasksGet: ', $e->getMessage(), PHP_EOL;
    }

}

/**
 * Get TransportTasks
 */
if($trans == 1)
{
    $api_instance = new Swagger\Client\Api\TransportTaskApi();

    try {
        $result = $api_instance->transporttasksGet($user_name, $limit, $before, $since, $open, $processing, $closed, $own);

        $tasks = array_merge($tasks, $result);
    } catch (Exception $e) {
        echo 'Exception when calling TransportTaskApi->transporttasksGet: ', $e->getMessage(), PHP_EOL;
    }

}

/**
 * Get CleaningTasks
 */
if($clean == 1)
{

    $api_instance = new Swagger\Client\Api\CleaningTaskApi();
    try {
        $result = $api_instance->cleaningtasksGet($user_name, $limit, $before, $since, $open, $processing, $closed, $own);

        $tasks = array_merge($tasks, $result);
    } catch (Exception $e) {
        echo 'Exception when calling CleaningTaskApi->cleaningtasksGet: ', $e->getMessage(), PHP_EOL;
    }

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
                        $creator = $row->getCreator()->getName() != null ? $row->getCreator()->getName() : "Keiner";
                        $result .=  '<tr>'
                        . '<td>' . $row->getId() . '</td>'
                        . '<td>' . $creator . '</td>'
                        . '<td>' . checkInstance($row) . '</td>'
                        . '<td>' . checkState($row->getState()) . '</td>'
                        . '<td>' . $row->getDescription() . '</td>'
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
                    <th data-field="id" data-sortable="true">Auftrags-ID</th>
                    <th data-field="name" data-sortable="true">Ersteller</th>
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