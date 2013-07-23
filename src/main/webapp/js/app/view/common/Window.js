Ext.define('App.view.common.Window', {
    extend: 'Ext.window.Window',
    initComponent: function() {
        this.callParent(arguments);
    },
    width: 300,
    height: 150,
    layout: 'fit',
    plain:true,
    closable: false,
    bodyStyle:'padding:5px;',
    headerPosition: 'top'
});
