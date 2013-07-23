
Ext.define('App.view.uploader.grid.Grid',{
    
    initComponent: function() {
        this.viewConfig = {
            forceFit: true
        };
        this.callParent(arguments);
    },
    requires: [
    'App.view.uploader.grid.PagingToolbar'
    ],
    extend: 'Ext.grid.GridPanel',
    alias : 'widget.fileList',
    store: 'File',
    anchor: '100%',
    columns:[{
        text: 'File',    
        dataIndex: 'filename',
        width: 150,
        menuDisabled: true
    }, {
        header: 'Size (MB)',
        width: 65,
        dataIndex: 'size',
        menuDisabled: true
    },{
        hidden: true,
        flex: 1,
        header: 'Hash',
        dataIndex: 'hash',
        menuDisabled: true
    },{
        header: 'Status',
        xtype: 'booleancolumn',
        flex: 1,
        trueText: 'Shared',
        falseText: '', 
        dataIndex: 'isShared',
        menuDisabled: true,
        sortable: false
    },{
        xtype: 'actioncolumn',
        width: 60,
        sortable: false,
        menuDisabled: true,
        items: [{
            icon: BASE_URL + '/js/extjs/resources/themes/images/default/custom/delete.png',
            tooltip: 'Delete'              
        }, {
            icon: BASE_URL + '/js/extjs/resources/themes/images/default/custom/download.png', 
            tooltip: 'Download'         
        }, {
            icon: BASE_URL + '/js/extjs/resources/themes/images/default/custom/share.png', 
            tooltip: 'Share'         
        }]
    }],
    bbar: [
    {
        xtype: 'paginator'
    }
    ]

});