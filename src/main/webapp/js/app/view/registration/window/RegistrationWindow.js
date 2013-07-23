Ext.define('App.view.registration.window.RegistrationWindow', {
    extend: 'App.view.common.Window',
    initComponent: function() {
        this.callParent(arguments);
    },
    requires: [
    'App.view.registration.form.RegistrationForm'
    ],
    alias: 'widget.registerWindow',
    title: 'Registration form',
    items: [{
        xtype: 'registerForm'
    }]

});