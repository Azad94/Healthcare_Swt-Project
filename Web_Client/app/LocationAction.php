
<?php
/** Written by Radoslaw Speier **/

include 'autoload.php';

/*
 * A class for getting Role information and altering Beacons
 */
class LocationAction{
    /* location api */
    private $api_instance;
    
    public function __construct() {
       try{ 
           $this->api_instance=new Swagger\Client\Api\LocationApi;
           
       } 
       catch(Exception $e){
           echo $e;
       }
    }
    /**display location information including task for this location**/
    function details($id){
       
       try{ 
        $location = $this->api_instance->locationsLocationIdGet($id);
       }
       catch(Exception $e){
         echo $e->getMessage();    
       }
       try{
           $transportTasks=null;
           $cleaningTasks=null;
            try{  
                 $transportTasks = $this->api_instance->locationsTransporttasksGet($location->getFloor(), $location->getBuilding(),$location->getRoom());
            } catch(Exception $e){
                echo $e->getMessage();
            }
        
            try{
                 $cleaningTasks = $this->api_instance->locationsCleaningtasksGet($location->getFloor(), $location->getBuilding(),$location->getRoom());
            } catch (Exception $e){
                echo $e->getMessage();
            }
            echo $this->table($location->getId(),$location->getBuilding(),$location->getFloor(),$location->getRoom(),$transportTasks,$cleaningTasks,"readonly"); 
       } catch (Exception $e){
           echo $e->getMessage();
       }
       
    
    }
    /*edit location information*/
    function edit($id){
       
       $location = $this->api_instance->locationsLocationIdGet($id);
       
       
       $jsonArray=array ('id'=>utf8_encode($location->getId()),
                         'building'=>utf8_encode($location->getBuilding()),
                         'floor'=>utf8_encode($location->getFloor()),
                         'room'=>utf8_encode($location->getRoom()),
                         'footer'=>"knopf");
       echo json_encode($jsonArray);
       
   } 
    /* delete location information with this id */
    function delete($id){
       $location = $this->api_instance->locationsLocationIdDelete($id);
       echo "Ort mit der id $id wird gelöscht";
   } 
   
   /*save edited location nformation*/
   function save(){
       $id = $_POST['id'];
       $building = $_POST['building'];
       $room = $_POST['room'];
       $floor = $_POST['floor'];
       
       $body = new \Swagger\Client\Model\Location($_POST);
       $this->api_instance->locationsLocationIdPut($id, $body);
   }
   
   /** create a table with location information **/
   private function table($id,$building,$floor,$room,$transportTasks,$cleaningTasks,$readOnly="",$image=null){
       return '<table border="1">
           <tr>
              <th>Location</th>
              '.
           $this->element("ID",$id,$readOnly).
           $this->element("Gebäude",$building,$readOnly).
           $this->element("Flur",$floor,$readOnly).
           $this->element("Raum",$room,$readOnly).
           $this->transportTasks($transportTasks).
           $this->cleaningTasks($cleaningTasks).
           '</table>';
       
           
   }
   
   /*create a tableElement */
   private function element($name,$value,$readOnly=""){
       return '<tr><td>'.$name.'</td><td><input type="text" value='.$value.' '.$readOnly.'></td></tr>';
   }
   
   /*create a transpor task table row */
   private function transportTasks($transportTasks){
       $return = "";
       if($transportTasks!=null){
            foreach($transportTasks as $transportTask ) {
            $return.= '<tr><td>'."TransportTask".'</td><td><input type="text" value='.$transportTask->getName().' '."".'></td></tr>';
            }
       }
       return $return;
   }
   /*create a cleaning task table row */
   private function cleaningTasks($cleaningTasks){
       $return = "";
       if ($cleaningTasks!=null){
            foreach($cleaningTasks as $cleaningTask ) {
            $return.= '<tr><td>'."TransportTask".'</td><td><input type="text" value='.$cleaningTask->getName().' '."".'></td></tr>';
            }
       }
       return $return;
   }
}


$action = $_POST['action'];
$id = $_POST['id'];
$locationAction = new LocationAction();

/* decide which action to take */
switch($action){
    
    case "details":
        $locationAction->details($id);
        break;
    case "edit":
        $locationAction->edit($id);
        break;
    case "qr":
        $locationAction->qr($id);
        break;
    case "delete":
        $locationAction->delete($id);
        break;
    case "save":
        echo "save";
        $locationAction->save();
        break;
}




?>

