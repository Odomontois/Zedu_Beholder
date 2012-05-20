/**
 * Created with IntelliJ IDEA.
 * User: Oleg
 * Date: 18.05.12
 * Time: 22:56
 * To change this template use File | Settings | File Templates.
 */
Ext.onReady(function() {});

Ext.Loader.setConfig({
    enabled : true
})

Ext.application({
    requires: ['Ext.container.Viewport','AM.controller.Trees'],
    name: 'AM',

    appFolder: 'app',

    controllers:[
        'Trees'
    ],

    launch: function() {
        Ext.create('Ext.container.Viewport', {
            layout: 'fit',
            items: [
                {
                    xtype: 'treelist'
                }
            ]
        });
    }
});
