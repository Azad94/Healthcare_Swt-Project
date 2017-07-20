/**
 * Written by Radoslaw Speier
 * @type {Element}
 */
majorRolle = document.getElementById("rolle");
majorselect = document.getElementById("majorselect");

minorObjekt = document.getElementById("objekt");
minorselect = document.getElementById("minorselect");

/* this function changes the value of the input majorRolle depending 
 * on the selection of the drop down list majorselect*/
majorselect.onchange = function(){
        var text = majorselect.options[majorselect.selectedIndex].value
         if(text != ""){
             majorRolle.value = text;
         }
    }
/* this function changes the value of the input minorObject depending 
 * on the selection of the drop down list mainorselect*/
minorselect.onchange = function(){
        var text = minorselect.options[minorselect.selectedIndex].value
         if(text != ""){
             minorObjekt.value = text;
         }
    }