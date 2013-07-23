
Ext.define('App.view.uploader.form.ShareForm',{
     extend: 'Ext.FormPanel',
    initComponent: function() {
        this.callParent(arguments);
    },
    alias : 'widget.shareForm',
    bodyPadding:'10px',
    items:[
        {
            xtype: 'hiddenfield',
            name: 'hash',
            value: 'myhash'
        }, {
            xtype: 'numberfield',
            name: 'days',
            fieldLabel: 'Days',
            value: 5,
            maxValue: 30,
            minValue: 1
        }
    ],
    buttons: [{
            xtype: 'button',
            text: 'Share'
    }]
});