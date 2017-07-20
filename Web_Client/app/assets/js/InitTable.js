/**
 * Written by Kim Huber
 *
 * This script is for initialises all tables and configuring them
 * you can find all possible parameters in bootstrap-table.js under the BootstrapTable.DEFAULTS point
 */
var $table = $('#fresh-table'),
    full_screen = false,
    window_height;


$().ready(function(){

    window_height = $(window).height();
    table_height = window_height - 20;

    $table.bootstrapTable({
        toolbar: ".toolbar",

        showRefresh: true,
        search: true,
        showToggle: true,
        showColumns: true,
        pagination: true,
        striped: true,
        sortable: true,
        height: table_height,
        pageSize: 10,
        pageList: [25,50,100],

        formatShowingRows: function(pageFrom, pageTo, totalRows){
        },
        formatRecordsPerPage: function(pageNumber){
            return pageNumber + " rows visible";
        },
        icons: {
            refresh: 'fa fa-refresh',
            toggle: 'fa fa-th-list',
            columns: 'fa fa-columns',
            detailOpen: 'fa fa-plus-circle',
            detailClose: 'fa fa-minus-circle'
        }
    });
});