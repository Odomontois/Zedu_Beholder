/**
 * Created with IntelliJ IDEA.
 * User: Oleg
 * Date: 20.05.12
 * Time: 10:51
 * To change this template use File | Settings | File Templates.
 */
Ext.define('AM.view.tree.Edit', {
    extend:'Ext.window.Window',
    alias:'widget.treeedit',

    title:'Edit Tree',
    layout:'fit',
    autoShow:true,

    initComponent:function () {
        this.items = [
            {
                xtype:'form',
                items:[
                    {
                        xtype:'textfield',
                        name:'name',
                        fieldLabel:'Name'
                    },
                    {
                        xtype:'numberfield',
                        name:'size',
                        fieldLabel:'Size',
                        allowDecimals:false,
                        maxValue:99,
                        minValue:0
                    }
                ]
            }
        ];
        this.buttons = [
            {
                text:'Save',
                action:'save'
            },
            {
                text:'Delete',
                action:'delete'
            },
            {
                text:'Cancel',
                scope:this,
                handler:this.close
            }
        ];

        this.callParent(arguments);
    }
});