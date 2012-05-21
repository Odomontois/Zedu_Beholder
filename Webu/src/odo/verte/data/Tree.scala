package odo.verte.data

import javax.persistence.{GenerationType, Entity}
import Annotations.{Id, GeneratedValue}
import com.google.appengine.api.datastore.{KeyFactory, Key}
import odo.verte.utils.JSON.map2read


/**
 * Created with IntelliJ IDEA.
 * User: Oleg
 * Date: 20.05.12
 * Time: 18:16
 * To change this template use File | Settings | File Templates.
 */

@Entity
case class Tree(@Id
                @GeneratedValue(strategy = GenerationType.IDENTITY)
                var key: Key,
                var size: Int = 0,
                var name: String = "")

object TreeJSON {
  implicit val serialize = (tree: Tree) => Map(
    "key" -> KeyFactory.keyToString(tree.key),
    "size" -> tree.size,
    "name" -> tree.name
  )

  implicit val deserialize = (map: Map[String, Any]) => Tree(
    key = DB.key(map.read[String]("key")),
    size = map.read[Double]("size").toInt,
    name = map.read[String]("name")
  )
}