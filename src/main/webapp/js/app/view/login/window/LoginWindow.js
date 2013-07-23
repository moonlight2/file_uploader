Ext.define('App.view.login.window.LoginWindow', {
    extend: 'App.view.common.Window',
    initComponent: function() {
        this.callParent(arguments);
    },
    requires: [
    'App.view.login.form.LoginForm'
    ],
    alias: 'widget.loginWindow',
    title: 'Login form',
    items: [{
        xtype: 'loginForm'
    }]

});