
Ext.define('App.view.uploader.grid.PagingToolbar',{

    initComponent: function() {
        this.callParent(arguments);
    },
    extend: 'Ext.toolbar.Paging',
    store: 'File',
    alias : 'widget.paginator',
    displayInfo: true,      
    flex: 1,
    displayMsg: 'Shows  {0} - {1} from {2}'

});


