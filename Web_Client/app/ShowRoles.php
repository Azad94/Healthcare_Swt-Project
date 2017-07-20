<?php
include "autoload.php";
/**
 * HTML base code by Kim Huber, adjusted by Radoslaw Speier
 * PHP code by Radoslaw Speier
 */

/**
 * Start session and check if user is logged in
 */
session_start();
if(!isset($_SESSION['user'])){
    header("location:Login.php");
    exit;
}

$api_instance = new Swagger\Client\Api\RoleApi();

try {

    $roles = $api_instance->rolesGet();
            
} catch (Exception $e) {
    echo 'Exception when calling RolesApi->rolesGet: ', $e->getMessage(), PHP_EOL;
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
            . '<td>' . $row->getName() . '</td>'
            . '</tr>';
    }
    return $result;
}
/**
 * Title of the page
 */
$title = "Rolen anzeigen";
/**
 * Which menu item to show
 */
$scriptExpand = "'Rollen'";
/**
 * Additional script for the page
 */
$script ='"assets/js/RoleTable.js"';
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
                    <th data-field="name" data-sortable="true">Name</th>
                    <th data-field="actions" data-formatter="operateFormatter" data-events="operateEvents">Aktionen</th>
                    </thead>
                    <tbody>
                    '.populateTable($roles).'
                    </tbody>
                </table>
            </div>
        </div>
    </div>';

include 'Template.php';
?>