Ext.define('App.controller.Login',{
    extend: 'Ext.app.Controller',
    views: [
    'login.form.LoginForm', 
    'login.window.LoginWindow',
    ],
    loginWnd: null,
    
    init: function() {

        console.log('Login');
        this.dialog = Ext.create('App.view.common.Dialog');
        this.loginWnd = Ext.widget('loginWindow');
        Ext.getBody().unmask(true);
        this.loginWnd.show();
        
        this.control({
            /* Login button handler */
            'panel button[text = "Login"]': {
                click: this.onLoginClick
            }
        });
    },

    onLoginClick: function(btn) {

        me = this;
        loginForm = btn.up('form').getForm();
        
        if (loginForm.isValid()) {
            this.loginWnd.getEl().mask('Login...');
            Ext.Ajax.request({
                url: 'login.htm',
                params: {
                    'email': loginForm.getValues().email,
                    'password': loginForm.getValues().password
                },
                success: function(res, req) {

                    var objAjax = Ext.decode(res.responseText);
                    if (objAjax.success == 'true') {
                        window.location.href = 'upload.htm';
                        window.event.returnValue = false;
                    } else {
                        me.loginWnd.getEl().unmask(true);  
                        me.dialog.setText(objAjax.responceText);
                        me.dialog.setIcon('warning');
                        me.dialog.show();
                    }
                }
            });
        }
    }

});