
Ext.require('Ext.form.Panel');

Ext.getBody().mask('You are amazing!');

Ext.application({
    name: 'App',
    appFolder: 'js/app',
    controllers: ['Files'],
    launch: function() {
        console.log('Launch main class');
        Ext.create('Ext.form.Panel',{
            items: [{
                xtype: 'uploadForm'
            },{
                xtype: 'fileList'
            }],
            buttons: [{
                xtype: 'button',
                text: 'Upload'
            }],
            renderTo: 'fi-form',
            frame: true,
            bodyPadding: 10,
            width: 527,
            title: 'File Uploader'
        })
    }
});
