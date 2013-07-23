
Ext.define('App.view.login.form.LoginForm',{
     extend: 'App.view.common.Form',
    initComponent: function() {
        this.callParent(arguments);
        console.log('Loading Login Form');
    },
    
    alias : 'widget.loginForm',
    buttons: [{ 
        xtype: 'button',
        text: 'Login'
    }]

});