<?php
include "autoload.php";
/**
 * HTML base code by Kim Huber, adjusted by Jan-Ole Petersen
 * PHP code by Jan-Ole Petersen
 */

/**
 * Start session and check if user is logged in
 */
session_start();
if(!isset($_SESSION['user'])){
    header("location:Login.php");
    exit;
}

/**
 * Function for population the Table
 * @param $tableData Data to populate the table
 * @return string HTML string of the table rows
 */
function populateTable($tableData){

    $result="";
    if($tableData != null)
    foreach ($tableData as $row)
    {
        $result .=  '<tr>'
            . '<td>' . $row->getId() . '</td>'
            . '<td>' . $row->getName() . '</td>'
            . '<td>' . $row->getPassword() . '</td>'
            . '<td>' . $row->getEmail() . '</td>'
            . '<td>' . $row->getRole()->getName() . '</td>'
            . '</tr>';
    }
    return $result;
}

/**
 * Get all users
 */
$api_instance = new Swagger\Client\Api\UserApi();
try {
    $users = $api_instance->usersGet();
} catch (Exception $e) {
    echo 'Exception when calling UserApi->usersGet: ', $e->getMessage(), PHP_EOL;
}

/**
 * Title of the page
 */
$title = "Nutzer anzeigen";
/**
 * Which menu item to show
 */
$scriptExpand = "'Nutzer'";
/**
 * Additional script for the page
 */
$script ='"assets/js/UserTable.js"';
/**
 * HTML code of the template content
 */
$content='
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
                    <th data-field="password" data-sortable="true">Passwort</th>
                    <th data-field="email" data-sortable="true">E-Mail</th>
                    <th data-field="role" data-sortable="true">Rolle</th>
                    <th data-field="actions" data-formatter="operateFormatter" data-events="operateEvents">Aktionen</th>
                    </thead>
                    <tbody>
                    '. populateTable($users).'
                    </tbody>
                </table>
            </div>
        </div>
    </div>';

include 'Template.php';
?>