
Ext.define('App.store.File', {   
    extend: 'Ext.data.Store',
    model: 'App.model.File',
    autoLoad: false,
    autoSync: false,
    pageSize: 7,
    proxy: {
        type: 'ajax',
        url: 'handlefiles.htm',
        actionMethods: {
            create: 'POST',
            read: 'GET',
            update: 'PUT',
            destroy: 'DELETE'
        },
        reader: {
            type: 'json',
            successProperty: 'success',
            root: 'files',
            messageProperty: 'message'
        },
        writer: {
            type: 'json',
            writeAllFields: false,
            root: 'files'
        }
    }
});
