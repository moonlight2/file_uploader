    
Ext.define('App.view.uploader.window.ShareWindow', {
    extend: 'Ext.window.Window',
    initComponent: function() {
        this.callParent(arguments);
    },
    requires: [
    'App.view.uploader.form.ShareForm'
    ],
    alias: 'widget.shareWindow',
    title: 'Share your file',
    html:'<p id="text">Select the number of days in which to share the link</p><p id="responce"></p><p id="link-block"></p> ',
    x:150,                     
    y:50,                      
    width: 340,
    height: 200,               
    bodyPadding:'10px',        
    bodyStyle: 'background-color:#fff', 
    closeAction: 'close', 
    modal: true,                                  
    headerPosition: 'top',
    items: [{
        xtype: 'shareForm'
    }]
});
