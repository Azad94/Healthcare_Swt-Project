<?php
/**
 * HTML base code by Kim Huber, adjusted by Jan-Ole Petersen
 * PHP code by Jan-Ole Petersen
 */
include "autoload.php";

session_start();
if(!isset($_SESSION['user'])){
    header("location:Login.php");
    exit;
}

if(isset($_POST['submit']))
{
    $api_instance = new Swagger\Client\Api\RoleApi();
    
    $body = new \Swagger\Client\Model\Role($_POST); // \Swagger\Client\Model\Location |
     echo($body);
    try {
        echo $body;
        $api_instance->rolesPost($body);
        header("Location: ShowRoles.php");
        exit;
    } catch (Exception $e) {
        echo 'Exception when calling RolesApi->rolesPost: ', $e->getMessage(), PHP_EOL;
    }

}

$title = "Role erstellen";
$scriptExpand = "'Rollen'";

$script ="assets/js/emptyScript.js";
$content='
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div id="pushobj">
            <div class="container">
                <form class="card card-signup" method="POST">
                    <div class="row">
                        <h2 class="text-center">Neue Rolle erstellen</h2>
                    </div>
                    <div class="row">
                    <p class="col-md-1 col-md-offset-1">Rolle</p>
                        <div class="col-lg-2 col-md-2 col-sm-2">
                            <div class="form-group label-floating">
                                <label class="control-label">Role</label>
                                <input type="text" class="form-control" name="name">
                            </div>
                        </div>
                    <div class="col-lg-2 col-md-2 col-sm-2 col-md-offset-2">
                            <button type="submit" name="submit" class="btn btn-info">Abschicken</button>
                    </div>    
                    </div>
                    
                </form>
            </div>
        </div>
    </div>';


include 'Template.php';
?>