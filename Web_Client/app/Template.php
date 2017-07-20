<?php
/**
 * Written by Radoslaw Speier and Kim Huber
 */


?>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>
            <?php echo $title ?>
        </title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <link rel="stylesheet" href="assets/font-awesome-4.7.0/css/font-awesome.min.css">

    <link href="assets/css/bootstrap.min.css" rel="stylesheet" />
    <link href="assets/css/jquery.multilevelpushmenu.min.css" rel="stylesheet">
    <link rel="stylesheet" href="assets/css/menu-style.css">
    <link href="assets/css/material-kit.css" rel="stylesheet"/>
    <link href="assets/css/fresh-bootstrap-table.css" rel="stylesheet" />
    </head>
    <body>
        <!--[if lt IE 7]>
            <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
        <![endif]-->

        <!--div id="pushobj"-->
            
              
                       <?php echo $content ?>
               
           
               
        <!--/div-->
        
            <div id="menu">
             <?php
                    require 'Controller/Controller.php';
                    $controller = new Controller();
                    $controller->createMenu($_SESSION['user']->getRole()->getName());
             ?>
            </div>
        </div>
        </body>
        <script type="text/javascript" src="assets/js/jquery.min.js"></script>
        <script type="text/javascript" src="assets/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="assets/js/bootstrap-table.js"></script>
        <script src="assets/js/jquery.multilevelpushmenu.min.js"></script>
        <script type="text/javascript" src="assets/js/InitMenu.js"></script>
        <script src="assets/js/material.min.js"></script>
        <script src="assets/js/material-kit.js" type="text/javascript"></script>
        <script type="text/javascript" src=<?php echo $script ?>>
            
        </script>

        <script type="text/javascript" src="assets/js/bootstrap-datepicker.js"></script>
       
        
        <script type="text/javascript">$(document).ready(function()
            {   
                $('#menu').multilevelpushmenu('expand', <?php echo $scriptExpand  ?>);
            });
        </script>
      
    
</html>
