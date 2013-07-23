
Ext.define('App.view.common.Form',{
     extend: 'Ext.form.Panel',
    initComponent: function() {
        this.callParent(arguments);
    },

    labelWidth: 55,
    frame: true,
    bodyPadding:'7px',
    defaultType: 'textfield',
    items: [{
        fieldLabel: 'Login',
        name: 'email',
        anchor:'100%',
        allowBlank:false
    },{
        fieldLabel: 'Password',
        name: 'password',
        inputType: 'password',
        anchor: '100%',
        allowBlank:false
    }]

});