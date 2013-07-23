
Ext.define('App.controller.Files',{
    extend: 'Ext.app.Controller',
    stores: ['File'],
    models: ['File'],
    views: [
    'uploader.form.UploadForm', 
    'uploader.grid.Grid', 
    'uploader.window.ShareWindow',
    'App.view.uploader.bar.ProgressBar'
    ],
    refs: [{
        ref: 'numField',
        selector: 'shareWindow shareForm numberfield'
    }, {
        ref: 'shareBtn',
        selector: 'shareWindow shareForm button'
    }, {
        ref: 'hiddenVal',
        selector: 'shareWindow shareForm hiddenfield'
    }],
    shareWnd: null,
    progBar: null,
    store: null,
    dialog: null,

    init: function() {

        this.progBar = Ext.widget('progressBar');
        this.shareWnd = Ext.widget('shareWindow');
        this.store = this.getStore('File');
        Ext.getBody().unmask(true);
        /* Load data for progress bar */
        this.loadProgressData();
        
        this.control({
            
            /* Upload button handler */
            'panel button[text = "Upload"]': {
                click: this.onUploadClick
            },

            /* Action column icons handler */
            'grid actioncolumn' : {
                click: this.onIconClick
            },
            
            /* Share button handler */
            'panel button[text = "Share"]': {
                click: this.onShareClick
            },

            'shareWindow' : {
                close: this.onWindowClose,
                show:  this.onWindowShow
            }
        });
    },
    
    onUploadClick: function(uploadBtn) {

        var form = uploadBtn.up('form').getForm();
        me = this;
        this.dialog = Ext.create('App.view.common.Dialog');
        if (form.isValid()) {
            form.submit({
                method:'POST',
                url: 'upload.htm',
                waitMsg: 'Uploading your file...',
                success: function (form, action) {
                    if(action.result.success == 'true') {
                        me.loadProgressData();
                    } else {
                        me.dialog.setText(action.result.responceText);
                        me.dialog.setIcon('error');
                        me.dialog.show();
                    }
                },
                failure: function (form, action) {
                    console.log(action.result);
                }
            });
        }
    },
    
    onIconClick: function(grid,cell,row,col,el) {

        var rec = this.store.getAt(row); 
        var iconName = el.getTarget().dataset.qtip;
        me = this;
        this.dialog = Ext.create('App.view.common.Dialog');
        switch(iconName) {
            case 'Delete':
                Ext.Msg.confirm('Hey!', 'Are you sure?', function(btn){
                    if (btn == 'yes') {
                        me.store.destroy({
                            params: {
                                'hash': rec.data.hash
                            },
                            callback: function(p, o) {
                                console.log(o);
                                if( o.success ) {
                                    me.store.remove(rec);
                                    var objAjax = Ext.decode(o.response.responseText);
                                    var percent = objAjax.totalInPercent/100;
                                    me.progBar.updateProgress(percent);
                                    me.progBar.updateText(objAjax.totalInMB + " of " + objAjax.sizeLimit + " MB");
                                } else {
                                    me.dialog.setText(o.error);
                                    me.dialog.setIcon('error');
                                    me.dialog.show();
                                }
                            }
                        });
                    }
                });  
                break;
            case 'Download':
                location.href = BASE_URL + "/file.htm?hash=" + rec.data.hash;  
                break;    
            case 'Share':
                var myhash = rec.data.hash; 
                this.shareWnd.show();
                this.getHiddenVal().setValue(myhash);
                break;
        }
    },
    
    onShareClick: function(shareBtn) {
        
        var form = shareBtn.up('form').getForm();
        me = this;

        if (form.isValid()) {
            form.submit({
                method:'POST',
                url: 'sharelink.htm',
                success: function (form, action) {
                    if(action.result.success == 'true') {
                        var respText = Ext.fly("responce");
                        respText.update(action.result.result);
                        respText.setStyle({
                            'visibility':'visible'
                        });
                        var linkBlock = Ext.fly("link-block");
                        var host = "http://"+window.document.location.host;
                        linkBlock.update("Your link: " + host + action.result.link);
                        respText.setStyle({
                            'visibility':'visible'
                        });
                        var textBlock = Ext.fly("text");
                        textBlock.setStyle({
                            'visibility':'hidden'
                        });
                        me.getNumField().disable(true);
                        shareBtn.disable(true);
                    } else {
                    //                        this.errorMessage();
                    }
                }
            });
        }
    },
    
    onWindowClose: function() {
        this.store.load();
    },
    
    onWindowShow: function() {
        this.getNumField().enable(true);
        this.getShareBtn().enable(true);
    },
    
    loadProgressData: function() {
        me = this;
        me.store.load({
            callback: function(records, operation, success) {
                var objAjax = Ext.decode(operation.response.responseText);
                var percent = objAjax.totalInPercent/100;
                me.progBar.updateProgress(percent);
                me.progBar.updateText(objAjax.totalInMB + " of " + objAjax.sizeLimit + " MB");
                
            }
        });
        
    }
    
});