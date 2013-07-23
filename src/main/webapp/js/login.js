Ext.getBody().mask('Please, wait...');
Ext.application({
    name: 'App',
    appFolder: 'js/app',
    controllers: ['Login'],
    launch: function() {
        console.log('Launch login class');
    }
});