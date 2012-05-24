package odo.verte.serv.tree

import odo.verte.serv.DataAccess
import com.google.appengine.api.datastore.KeyFactory
import odo.verte.data.{DB, Tree}
import odo.verte.utils.JSON.map2read

/**
 * Created with IntelliJ IDEA.
 * User: Oleg
 * Date: 25.05.12
 * Time: 0:12
 * To change this template use File | Settings | File Templates.
 */

class TreeDataAccess extends DataAccess[Tree] {
  implicit def serialize(tree: Tree) = Map(
    "key" -> KeyFactory.keyToString(tree.key),
    "size" -> tree.size,
    "name" -> tree.name
  )

  implicit def deserialize(map: Map[String, Any]) = Tree(
    key = DB.key(map.read[String]("key")),
    size = map.read[Double]("size").toInt,
    name = map.read[String]("name")
  )

  val myClass = classOf[Tree]
}
