
Ext.define('App.view.uploader.bar.ProgressBar',{
    extend: 'Ext.ProgressBar',

    initComponent: function() {

        this.callParent(arguments);
    },
    alias: 'widget.progressBar',
    renderTo: 'prog-bar',
    width: '350'

});
