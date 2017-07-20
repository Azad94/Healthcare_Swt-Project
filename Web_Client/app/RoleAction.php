
<?php
/**
 * Written by Radoslaw Speier
 */
include 'autoload.php';

/*
 * A class for getting Role information and altering Beacons
 */
class RoleAction{
    
    /* role Api */
    private $api_instance;
    
    public function __construct() {
       try{ 
           $this->api_instance=new Swagger\Client\Api\RoleApi();
           
       } 
       catch(Exception $e){
           echo $e;
       }
    }
    /*function returns details for a role with a $id */
    function details($id){
       
       $role = $this->api_instance->rolesRoleIdGet($id);
       echo "Id: ".$role->getId()."<br> Name: " . $role->getName();
    
       }
    /*functions returns the values of a role so they can be edited with js&html*/ 
    function edit($id){
       
       $role = $this->api_instance->rolesRoleIdGet($id);
       
       $jsonArray=array ('id'=>utf8_encode($role->getId()),
                         'name'=>utf8_encode($role->getName()),
                         );
       echo json_encode($jsonArray);
       
   } 
    /*function deletes a role with an id */
    function delete($id){
       $role = $this->api_instance->rolesRoleIdDelete($id);
       echo "role deleted ";
   } 
   
   function save(){
       $id = $_POST['id'];
       $name = $_POST['name'];
       
       
       $body = new \Swagger\Client\Model\Role($_POST);
       $this->api_instance->rolesRoleIdPut($id, $body);
   }
   
}


$action = $_POST['action'];
$id = $_POST['id'];
$roleAction = new RoleAction();

/* decide which action should be taken */
switch($action){
    
    case "details":
        $roleAction->details($id);
        break;
    case "edit":
        $roleAction->edit($id);
        break;
    case "delete":
        $roleAction->delete($id);
        break;
    case "save":
        echo "save";
        $roleAction->save();
        break;
}




?>

