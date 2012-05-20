/**
 * Created with IntelliJ IDEA.
 * User: Oleg
 * Date: 20.05.12
 * Time: 10:52
 * To change this template use File | Settings | File Templates.
 */
Ext.define('AM.view.tree.List', {
    extend:'Ext.grid.Panel',
    alias:'widget.treelist',
    store:'Trees',

    title:'All Tree',
    tools:[
        {
            itemId:"save",
            type:'save',
            tooltip:'Save All',
            tooltipType:'title'
        },
        {
            itemId:"addNew",
            type:'plus',
            tooltip:'Create New',
            tooltipType:'title'
        }
    ],

    initComponent:function () {

        this.columns = [
            {header:'Name', dataIndex:'name', flex:1},
            {header:'Size', dataIndex:'size', flex:1}
        ];

        this.callParent(arguments);
    }
});
