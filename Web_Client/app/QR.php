<?php
/**
 * Written by Radoslaw Speier
 */
require('phpqrcode/qrlib.php');
/** a class for the generation of QR images **/
class QR {
    /** generates a QR image in png format inside an img html tag**/
    function generateQRImageTag( $identifier){

        ob_start();
        QRCode::png($identifier,false,'L',9,2);
        $imageString = base64_encode(ob_get_clean());
        return '<img src ="data:image/png;base64,'.$imageString.'"/>';
    }
}
?>