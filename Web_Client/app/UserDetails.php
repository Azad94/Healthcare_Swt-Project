<?php
/**
 * Written by Jan-Ole Petersen
 */

/**
 * Load Model and Api
 */
include "autoload.php";

/**
 * This class is for getting detailed information about a user, updating a user and deleting a user
 */
class UserDetails
{
    /**
     * API instances for communication with the REST server
     */
    private $userApi_instance;
    private $roleApi_instance;
    /**
     * id of the user
     */
    private $user_id;

    /**
     * UserDetails constructor.
     * @param $id id of the user
     */
    public function __construct($id)
    {
        $this->userApi_instance = new \Swagger\Client\Api\UserApi();
        $this->roleApi_instance = new \Swagger\Client\Api\RoleApi();
        $this->user_id = $id;
    }

    /**
     * Function for getting all information of a user
     */
    public function getDetailInfo()
    {
        try {
            $response = $this->userApi_instance->usersUserIdGet($this->user_id);

        } catch (Exception $e) {
            echo 'Exception: ', $e->getMessage(), PHP_EOL;
        }
        echo $response;
    }

    /**
     * Function for getting all information of an user plus the editable information for this user
     */
    public function getEditInfo()
    {
        try {
            $user = $this->userApi_instance->usersUserIdGet($this->user_id);
            $roles = $this->roleApi_instance->rolesGet();
        } catch (Exception $e) {
            echo 'Exception when calling BeaconObjectApi->beaconObjectsBeaconObjectIdGet: ', $e->getMessage(), PHP_EOL;
        }
        $result['user'] = $user;
        $result['roles'] = $roles;
        echo json_encode(\Swagger\Client\ObjectSerializer::sanitizeForSerialization($result));
    }

    /**
     * This function is for updating a user
     * @param $name name to update
     * @param $password password to update
     * @param $email email to update
     * @param $roleId id of the role to update
     */
    public function update($name, $password, $email, $roleId)
    {
        $date = new DateTime();
        try{

            if($roleId != '')
                $role = $this->roleApi_instance->rolesRoleIdGet($roleId);
            else
                $role = null;

            $body = $this->userApi_instance->usersUserIdGet($this->user_id);
            $body->setId($this->user_id);
            $body->setName($name);
            $body->setPassword($password);
            $body->setEmail($email);
            $body->setLastUpdate($date->format(DateTime::ATOM));
            if($role != null) $body->setRole($role);



            $this->userApi_instance->usersUserIdPut($this->user_id, $body);

        }
        catch (Exception $e)
        {
            echo $role;
            echo 'Exception: ', $e->getMessage(), PHP_EOL;
        }


        $response = "success";
    }

    /**
     * Function for deleting a user
     */
    public function delete()
    {
        try {
            $this->userApi_instance->usersUserIdDelete($this->user_id);
        } catch (Exception $e) {
            echo 'Exception when calling BeaconObjectApi->beaconObjectsBeaconObjectIdDelete: ', $e->getMessage(), PHP_EOL;
        }

    }
}

$action = new UserDetails($_POST['id']);

/**
 * Which method to call
 */
switch ($_POST['method'])
{
    case "get":
        $action->getDetailInfo();
        break;
    case "getput":
        $action->getEditInfo();
        break;
    case "put":
        $action->update($_POST['name'], $_POST['password'], $_POST['email'], $_POST['roleId']);
        break;
    case "delete":
        $action->delete();
        break;
}