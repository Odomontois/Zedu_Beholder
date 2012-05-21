package odo.verte.serv.tree

import javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}
import odo.verte.data.{DB, Tree}
import collection.JavaConversions._
import com.google.appengine.api.datastore.KeyFactory


/**
 * Created with IntelliJ IDEA.
 * User: Oleg
 * Date: 20.05.12
 * Time: 18:27
 * To change this template use File | Settings | File Templates.
 */

class TreeList extends HttpServlet {
  override def doGet(req: HttpServletRequest, resp: HttpServletResponse) {
    val manager = DB.manager
    resp.setContentType("application/json")
    try {
      val query = manager.createQuery(
        "select from " + classOf[Tree].getName
      )

      val results = collectionAsScalaIterable(query.getResultList)

      val trees =
        (for (result <- results) yield (result match {
          case tree: Tree => tree
          case _ => throw new DBException
        }))

      val resultList = for (tree <- trees) yield Map(
        "key" -> KeyFactory.keyToString(tree.key),
        "name" -> tree.name,
        "size" -> tree.size
      )

      resp.getWriter.write("[" +
        (for (item <- resultList) yield "{"
          + (for ((name, value) <- item) yield
          name +
            ":" +
            (value match {
              case number: Int => number.toString
              case other => "'" + other.toString + "'"
            })).mkString(",")
          + "}"
          ).mkString(",")
        + "]")
    } finally {
      manager.close()
    }
  }
}

