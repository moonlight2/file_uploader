
Ext.define('App.view.uploader.form.UploadForm',{
     extend: 'Ext.form.field.File',
    initComponent: function() {
        this.callParent(arguments);
    },
    
    alias : 'widget.uploadForm',
    name: 'file',
    fieldLabel: 'File',
    labelWidth: 50,
    msgTarget: 'side',
    allowBlank: false,
    anchor: '100%',
    buttonText: 'Select a File...'
});