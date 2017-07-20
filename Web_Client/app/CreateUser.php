<?php
/**
 *HTML base code by Kim Huber, adjusted by Jan-Ole Petersen
 * PHP code by Jan-Ole Petersen
 */

/**
 * Load Model and Api
 */
include 'autoload.php';

/**
 * Start of session and checking if user is logged in
 */
session_start();
if(!isset($_SESSION['user'])){
    header("location:Login.php");
    exit;
}

/**
 * Api instance for communication with the REST-Server
 */
$api_instance = new Swagger\Client\Api\RoleApi();
try {
    /**
     * Get all roles
     */
    $roles = $api_instance->rolesGet();

} catch (Exception $e) {
    echo 'Exception when calling RoleApi->rolesGet: ', $e->getMessage(), PHP_EOL;
}

/**
 * Function for populating drop down menu for roles
 * @param $roles roles to populate drop down
 * @return string HTML string of the drop down menu
 */
function populateRoleDropdown($roles)
{
    $result="";
    foreach ($roles as $role)
    {
        $result .= '<li value="' . $role->getId() . '" name="' . $role->getName() . '"><a href="#">' . $role->getName() . '</a></li>';
    }
    return $result;
}

/**
 * Get content of form
 */
if(isset($_POST['submit']))
{
    /**
     * If password is equal to second password, create new user
     */
    if($_POST['password'] == $_POST['confirm_password'])
    {
        $date = new DateTime();
        $roleApi_instance = new \Swagger\Client\Api\RoleApi();
        $userApi_instance = new Swagger\Client\Api\UserApi();
        $role_id = $_POST['roleInput'];
        try {
            $role = $roleApi_instance->rolesRoleIdGet($role_id);

        } catch (Exception $e) {
            echo 'Exception when calling RoleApi->rolesGet: ', $e->getMessage(), PHP_EOL;
        }

        $body = new \Swagger\Client\Model\User($_POST);
        $body->setLastUpdate(substr($date->format(DateTime::ATOM), 0, 19) . ".000+02:00[Europe/Berlin]");
        $body->setRole($role);
        try {
            $userApi_instance->usersPost($body);
            header("Location: ShowUsers.php");
        } catch (Exception $e) {
            echo 'Exception when calling UserApi->usersPost: ', $e->getMessage(), PHP_EOL;
        }
    }
    else
    {
        echo "Fehler in Formular";
    }
}
/**
 * Title of the page
 */
$title = "Neuen Nutzer anlegen";
/**
 * Which menu item to show
 */
$scriptExpand = "'Nutzer'";
/**
 * Additional script for the page
 */
$script ="'assets/js/CreateUser.js'";
/**
 * HTML code of the template content
 */
$content ='
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div id="pushobj">
            <div class="container">
                <form class="card card-signup" method="POST">
                    <div class="row">
                        <h2 class="text-center">Nutzer anlegen</h2>
                    </div>
                    
                    <div class="row">
                        <p class="col-md-1 col-md-offset-1">Name:</p>
                        <div class="col-lg-2 col-md-2 col-sm-2">
                            <input value="" placeholder="Name" type="text" class="form-control" name="name">
                            <span class="form-control-feedback">
			                    <i class="fa fa-check"></i>
		                    </span>
                        </div>
                    </div>
                    <div class="row">
                        <p class="col-md-1 col-md-offset-1">E-Mail:</p>
                        <div class="col-lg-2 col-md-2 col-sm-2">
                            <input value="" placeholder="E-Mail" type="email" class="form-control" name="email">
                            <span class="form-control-feedback">
			                    <i class="fa fa-check"></i>
		                    </span>
                        </div>
                    </div>
                    <div class="row">
                        <p class="col-md-1 col-md-offset-1">Passwort:</p>
                        <div class="col-lg-2 col-md-2 col-sm-2">
                            <input value="" placeholder="Passwort" type="password" class="form-control" name="password">
                            <span class="form-control-feedback">
			                    <i class="fa fa-check"></i>
		                    </span>
                        </div>
                        <div class="col-lg-2 col-md-2 col-sm-2 col-md-offset-2">
                            <button type="submit" name="submit" class="btn btn-info">Nutzer anlegen</button>
                        </div>
                    </div>
                    <div class="row">
                        <p class="col-md-1 col-md-offset-1">Passwort wiederholen:</p>
                        <div class="col-lg-2 col-md-2 col-sm-2">
                            <input value="" placeholder="Passwort" type="password" class="form-control" name="confirm_password">
                            <span class="form-control-feedback">
			                    <i class="fa fa-check"></i>
		                    </span>
                        </div>
                    </div>
                    <div class="row">
                        <h5 class="col-md-1 col-md-offset-1">Rolle:</h5>
                        <div class="col-md-3 col-md-offset-1 dropdown">
                            <a href="#" class="btn btn-simple dropdown-toggle" id="r" data-toggle="dropdown">
                                Rolle
                                <b class="caret"></b>
                            </a>
                            <ul class="dropdown-menu role">
                                '.populateRoleDropdown($roles).'
                            </ul>
                        </div>
                    </div>
                    <input type="hidden" name="roleInput">
                </form>
            </div>
        </div>
    </div>';

include 'Template.php';
?>
