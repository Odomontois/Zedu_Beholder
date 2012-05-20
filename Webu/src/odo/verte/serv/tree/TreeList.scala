package odo.verte.serv.tree

import javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}
import HttpServletResponse.SC_OK
import util.parsing.json.JSON
import odo.verte.data.{DB, Tree}
import collection.JavaConversions._
import com.google.appengine.api.datastore.{Key, KeyFactory}


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

class TreeCreate extends HttpServlet {
  def buildTree(tree: Map[String, _]): Tree = {
    val size = tree("size").asInstanceOf[Double].toInt
    val name = tree("name").asInstanceOf[String]
    new Tree(null, size, name)
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

class TreeDelete extends HttpServlet {
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
      for (tree <- trees) manager.remove(tree)
    } finally {
      manager.getTransaction.commit()
      manager.close()
    }
    resp.setStatus(SC_OK)
  }
}

private[tree] class ParseException extends Exception

private[tree] class DBException extends Exception