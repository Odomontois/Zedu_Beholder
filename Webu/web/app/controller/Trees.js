/**
 * Created with IntelliJ IDEA.
 * User: Oleg
 * Date: 20.05.12
 * Time: 10:50
 * To change this template use File | Settings | File Templates.
 */
Ext.define('AM.controller.Trees', {
        extend:'Ext.app.Controller',

        views:[
            'tree.List',
            'tree.Edit'
        ],

        stores:[
            'Trees'
        ],

        models:[
            'Tree'
        ],

        init:function () {
            this.control({
                'treelist':{
                    itemdblclick:this.editTree
                },
                'treeedit #saveButton':{
                    click:this.updateTree
                },
                'treeedit #deleteButton':{
                    click:this.deleteTree
                },
                'treeedit #addButton':{
                    click:this.addTree
                },
                'treelist #save':{
                    click:this.saveAll
                },
                'treeedit #cancelButton':{
                    click:this.cancelEdit
                },
                'treelist #addNew':{
                    click:this.addNew
                }
            })
        },

        editTree:function (grid, record) {
            var view = Ext.widget('treeedit');

            view.down('form').loadRecord(record);
        },
        updateTree:function (button) {
            var win = button.up('window'),
                form = win.down('form'),
                record = form.getRecord(),
                values = form.getValues();

            record.set(values);
            win.close();
        },
        addNew:function (tool) {
            var record = Ext.create('AM.model.Tree', {name:"", size:0});
            var view = Ext.widget('treeedit');
            view.down("#addButton").setVisible(true);
            view.down("#saveButton").setVisible(false);
            view.down("#deleteButton").setVisible(false);
            view.down('form').loadRecord(record);
        },
        saveAll:function (tool) {
            this.getTreesStore().sync();
        },
        deleteTree:function (button) {
            var win = button.up('window'),
                form = win.down('form'),
                record = form.getRecord();

            this.getTreesStore().remove(record);
            win.close();
        },
        addTree:function (button) {
            var win = button.up('window'),
                form = win.down('form'),
                record = form.getRecord(),
                values = form.getValues();
            record.set(values);
            this.getTreesStore().add(record);
            win.close();
        },
        cancelEdit:function (button) {
            var win = button.up('window');
            win.close();
        }
    }
)
