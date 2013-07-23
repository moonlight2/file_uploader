
Ext.define('App.view.registration.form.RegistrationForm',{
    extend: 'App.view.common.Form',
     
    initComponent: function() {
        this.callParent(arguments);
    },
    alias : 'widget.registerForm',
    buttons: [{ 
        xtype: 'button',
        text: 'Register'
    }]
});