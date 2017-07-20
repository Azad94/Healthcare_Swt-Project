
<?php
/**
 * Written by Radoslaw Speier
 */
include 'autoload.php';
require 'QR.php';

/*
 * A class for getting Beacon information and altering Beacons
 */
class BeaconAction{
    
    /* Beacon Api*/
    private $api_instance;
    
    public function __construct() {
       try{ 
           $this->api_instance=new Swagger\Client\Api\BeaconApi;
           
       } 
       catch(Exception $e){
           echo $e;
       }
    }
    /*function returns details for a beacon with $uuid */
    function details($uuid){
       
        
       $beacon = $this->api_instance->beaconsBeaconIdGet($uuid);
       echo $this->table($beacon->getUuid(),$beacon->getMajor(),$beacon->getMinor(),"readonly");
    
       }
       
    /*functions returns the values of a beacon so they can be edited with js&html*/   
    function edit($uuid){
       
       $beacon = $this->api_instance->beaconsBeaconIdGet($uuid);
       
       $jsonArray=array (
                         'uuid'=>utf8_encode($beacon->getUuid()),
                         'major'=>utf8_encode($beacon->getMajor()),
                         'minor'=>utf8_encode($beacon->getMinor()),
                         'footer'=>"knopf");
       echo json_encode($jsonArray);
       
   } 
   /*returns the QR Code representaion of the beacon with $uuid*/
    function qr($uuid){
       $beacon = $this->api_instance->beaconsBeaconIdGet($uuid);
       
       $qrString = $beacon->getUuid().$beacon->getMajor().$beacon->getMinor();
       
       $qr = new QR();
       echo $qr->generateQRImageTag($qrString);
   } 
   /*deletes the beacon with the $uuid */
    function delete($uuid){
       $beacon =$this->api_instance->beaconsBeaconIdDelete($uuid); 
       echo "Beacon with id: $uuid deleted";
   } 
   /*saves the edited beacon values */
   function save(){
       
       $uuid = $_POST['uuid'];
       $major = $_POST['major'];
       $minor = $_POST['minor'];
       
       $body = new \Swagger\Client\Model\Beacon($_POST);
       $this->api_instance->beaconsBeaconIdPut($uuid, $body);
   }
   
   
   /* creates a table to display beacon details */
   private function table($uuid,$major,$minor,$readOnly="",$image=null){
       return '<table border="1">
           <tr>
              <th>Beacon</th>
              '.
           
           $this->element("UUID",$uuid,$readOnly).
           $this->element("MAJOR",$major,$readOnly).
           $this->element("MINOR",$minor,$readOnly).'</table>';
           
   }
   
   /* creates a beacon entry element */
   private function element($name,$value,$readOnly=""){
       return '<tr><td>'.$name.'</td><td><input type="text" value='.$value.' '.$readOnly.'></td></tr>';
   }
}


$action = $_POST['action'];
$uuid = $_POST['uuid'];
$beaconAction = new BeaconAction();

/* decide which action should be taken */
switch($action){
    
    case "details":
        $beaconAction->details($uuid);
        break;
    case "edit":
        $beaconAction->edit($uuid);
        break;
    case "qr":
        $beaconAction->qr($uuid);
        break;
    case "delete":
        $beaconAction->delete($uuid);
        break;
    case "save":
        echo "save";
        $beaconAction->save();
        break;
}




?>

