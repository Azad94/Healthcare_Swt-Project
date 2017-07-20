<?php

/* define font awesom icons */
define("trash" , "fa fa-trash-o");
define("create", "fa fa-plus-square-o");
define("edit",   "fa fa-pencil-square-o");
define("listItems" , "fa fa-list-ol");
define("users",  "fa fa-users");
define("laptop", "fa fa-laptop");
define("tags", "fa fa-tags");
define("hospital", "fa fa-hospital-o");
define("start","fa fa-play");
define("preferences","fa fa-cog");
define("logout", "fa fa-sign-out");
define("bed", "fa fa-bed");
define("user", "fa fa-user");
/**
 * Written by Kim Huber, Radoslaw Speier
 * This class controls rights for showing menu items
 *
 */
 class Controller {
       
    /* ICONS */
    private $users     = "fa fa-users";
    private $tags      = "fa fa-tags";
    private $hospital  = "fa fa-hospital-o";
    private $object    = "fa fa-gift";
    private $tasks     = "fa fa-tasks";

   
    /** creates a menu depending on the role given, 
     * if no valid role is given a default menu will be created */
    public function createMenu($role){
        echo "<nav>";
        print '<h2><i class="fa fa-reorder"></i>'.$role.'</h2>';
        print "<ul>";
        Controller::createListElement("Start","index.php",start);
        Controller::createAuftragMenu();
        switch($role){
            case "Verwalter":
                Controller::createObjektMenu();
                Controller::createLocationsMenu();
                break;
            case "Administrator":
                Controller::createObjektMenu();
                Controller::createNutzerMenu();
                Controller::createLocationsMenu();
                Controller::createListElement("Einstellungen","Preferences.php",preferences);
                break;
        }
        Controller::createListElement("Profil", "Profile.php", user);
        Controller::createListElement("Logout","Logout.php", logout);
        print "</ul>";
        echo "</nav>";
     
        
    }
    /*create task menu */
     function createAuftragMenu(){
        
        Controller::createHeader($this->tasks,"Aufträge");
        Controller::createListElement("Aufträge filtern","FilterTasks.php",laptop);
        Controller::createListElement("Aufträge anzeigen","TaskTable.php",laptop);
        Controller::createListElement("Aufträge erstellen","CreateTask.php",laptop);
         Controller::createListElement("Bett anfordern","RequestBed.php",bed);
         print"</ul></li>";
    }
    /* create Object menu */
    function createObjektMenu(){
        Controller::createHeader($this->object,"Objekte");
        Controller::createListElement("Objekte anzeigen", "ShowObjects.php",listItems);
        Controller::createListElement("Objekt erstellen","CreateObject.php",create);
        Controller::createBeaconObjects();
        
        print"</ul></li>";
    }
    /* create BeaconObject menu */
    function createBeaconObjects(){
        Controller::createHeader($this->tags,"Beacons");
        Controller::createListElement("Beacons anzeigen", "ShowBeacons.php", listItems);
        Controller::createListElement("Beacon erstellen", "CreateBeacon.php", create);
        print"</ul></li>";
    }
    
    /*create user menu */
    function createNutzerMenu(){
        Controller::createHeader($this->users,"Nutzer");
        Controller::createListElement("Nutzer anzeigen","ShowUsers.php",listItems);
        Controller::createListElement("Nutzer erstellen","CreateUser.php",create);
        Controller::createRolesMenu();
        print"</ul></li>";
    }
    
    /*create role menu */
      function createRolesMenu(){
        Controller::createHeader($this->tags,"Rollen");
        Controller::createListElement("Rollen anzeigen", "ShowRoles.php", listItems);
        Controller::createListElement("Rolle erstellen", "CreateRole.php", create);
        print"</ul></li>";
    }
    
    /*create locations menu */
      function createLocationsMenu(){
        Controller::createHeader($this->hospital,"Orte");
        Controller::createListElement("Orte anzeigen","ShowLocations.php",listItems);
        Controller::createListElement("Ort erstellen","CreateLocation.php",create);
        print"</ul></li>";
    }

    /*create meu header **/
    function createHeader($icon, $name ){
        print '<li><a href="#"><i class="'.$icon.'"></i>'.$name.'</a>';
        print '<h2><i class="'.$icon.'"></i>'.$name.'</h2>';
        print '<ul>';
        
    }
    /* create a menu list entry */
    function createListElement($name ,$link, $icon){
        print '<li><a href="'.$link.'"><i class="'.$icon.'"></i>'.$name.'</a></li>';
    }

}
