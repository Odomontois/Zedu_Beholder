package odo.verte.serv.tree

import javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}
import odo.verte.data.{DB, Tree}
import com.google.appengine.api.datastore.KeyFactory
import util.parsing.json.JSON
import javax.servlet.http.HttpServletResponse.SC_OK


class TreeUpdate extends HttpServlet {
  def buildTree(tree: Map[String, _]): Tree = {
    val size = tree("size").asInstanceOf[Double].toInt
    val name = tree("name").asInstanceOf[String]
    val key = KeyFactory.stringToKey(tree("key").asInstanceOf[String])
    new Tree(key, size, name)
  }

  override def doPost(req: HttpServletRequest, resp: HttpServletResponse) {
    var source = scala.io.Source.fromInputStream(req.getInputStream).getLines.mkString("\n")
    val trees = JSON.parseFull(source) match {
      case Some(value) => value match {
        case tree: Map[String, _] => List(buildTree(tree))
        case list: List[Any] => for (value: Any <- list) yield value match {
          case tree: Map[String, _] => buildTree(tree)
          case _ => throw new ParseException
        }
        case _ => throw new ParseException
      }
      case None => Nil
    }
    val manager = DB.manager
    try {
      manager.getTransaction.begin()
      for (tree <- trees) manager.persist(tree)
      manager.getTransaction.commit()
    } finally {
      manager.close()
    }
    resp.setStatus(SC_OK)
  }
}
