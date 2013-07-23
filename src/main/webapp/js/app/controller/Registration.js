
Ext.define('App.controller.Registration',{
    extend: 'Ext.app.Controller',
    views: [
    'registration.form.RegistrationForm', 
    'registration.window.RegistrationWindow',
    ],
    
    init: function() {

        this.dialog = Ext.create('App.view.common.Dialog');
        this.registerWnd = Ext.widget('registerWindow');
        Ext.getBody().unmask(true);
        this.registerWnd.show();

        this.control({
            /* Login button handler */
            'panel button[text = "Register"]': {
                click: this.onRegisterClick
            }
        });
    },

    onRegisterClick: function(btn) {
        
        registerForm = btn.up('form').getForm();
        me = this;
        
        if (registerForm.isValid()) {
            this.registerWnd.getEl().mask('Please, wait...');
            Ext.Ajax.request({
                url: 'registration.htm',
                params: {
                    'email': registerForm.getValues().email,
                    'password': registerForm.getValues().password
                },
                success: function(res, req) {
                    console.log(res);
                    var objAjax = Ext.decode(res.responseText);
                    if (objAjax.success == 'true') {

                        me.registerWnd.getEl().unmask(true);  
                        me.dialog.setText(objAjax.responceText);
                        me.dialog.show();
                    } else {
                        me.registerWnd.getEl().unmask(true);  
                        me.dialog.setText(objAjax.responceText);
                        me.dialog.setIcon('error');
                        me.dialog.show();
                    }
                }
            });
        }
    }

});