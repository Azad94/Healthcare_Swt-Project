/**
 * Written by Kim Huber
 * This script is for initialization of the menu and configuring it
 */
$(document).ready(function () {
    $('#menu').multilevelpushmenu({
        
        containersToPush: [$('#pushobj')],
        menuWidth: '20%',
        menuHeight: '100%',
        overlapWidth: 20,
        backText: 'zurück',
        collapsed: false,
        onItemClick: function() {
            
            var event = arguments[0]
                $menuLevelHolder = arguments[1],
                $item = arguments[2],
                options = arguments[3];
            var itemHref = $item.find( 'a:first' ).attr( 'href' );
            location.href = itemHref;
        }

    });
    $('#menu').multilevelpushmenu('option', 'menuHeight', $(document).height());
    $('#menu').multilevelpushmenu('redraw');
});


$(window).resize(function () {
    $('#menu').multilevelpushmenu('option', 'menuHeight', $(document).height());
    $('#menu').multilevelpushmenu('redraw');
});