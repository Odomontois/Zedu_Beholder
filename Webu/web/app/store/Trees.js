/**
 * Created with IntelliJ IDEA.
 * User: Oleg
 * Date: 20.05.12
 * Time: 10:51
 * To change this template use File | Settings | File Templates.
 */
Ext.define('AM.store.Trees', {
    extend:'Ext.data.Store',
    model:'AM.model.Tree',
    autoLoad:true,

    proxy:{
        type:'ajax',
        api:{
            read:'/data/tree/list',
            update:'/data/tree/update',
            create:'/data/tree/create',
            destroy:'/data/tree/delete'
        },
        reader:{
            type:'json',
            root:'trees',
            successProperty:'success'
        }
    }
})
