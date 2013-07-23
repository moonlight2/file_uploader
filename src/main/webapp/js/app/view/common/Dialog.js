Ext.define('App.view.common.Dialog', {

    alias: 'myDialog',
    icon: null,
    title: 'Info',
    setText: function(txt) {
        this.text = txt;
    },
    setTitle: function(tit) {
        this.title = tit;
    },
    setIcon: function(icon) {
        switch(icon) {
            case 'info':
                this.icon = Ext.MessageBox.INFO;
                break;
            case 'warning':
                this.icon = Ext.MessageBox.WARNING;
                break;
            case 'error':
                this.icon = Ext.MessageBox.ERROR;
                break;
            case 'question':
                this.icon = Ext.MessageBox.QUESTION;
                break;
            default:
                this.icon = null;
        }
    },
    show: function() {

        me = this;
        var dialog = Ext.create('Ext.window.MessageBox', {
            buttons: [{
                text: 'Close',
                handler: function() {
                    dialog.close();
                }
            }]
        });

        dialog.show({
            title: me.title,
            msg: me.text,
            icon: me.icon
        });
    }
});