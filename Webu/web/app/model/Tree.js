/**
 * Created with IntelliJ IDEA.
 * User: Oleg
 * Date: 20.05.12
 * Time: 10:50
 * To change this template use File | Settings | File Templates.
 */
Ext.define('AM.model.Tree', {
    extend:'Ext.data.Model',
    fields:[
        {name:'key', type:'string'},
        {name:'name', type:'string'},
        {name:'size', type:'int'}
    ],
    idProperty:"key"
})
