package odo.verte.data

import annotation.target.field
import collection.JavaConversions._
import javax.persistence.{Query, Persistence}
import odo.verte.serv.tree.DBException
import com.google.appengine.api.datastore.{KeyFactory, Key}

/**
 * Created with IntelliJ IDEA.
 * User: Oleg
 * Date: 20.05.12
 * Time: 23:04
 * To change this template use File | Settings | File Templates.
 */

object Annotations {
  type Id = javax.persistence.Id@field
  type GeneratedValue = javax.persistence.GeneratedValue@field
}

object DB {
  val EMF = Persistence.createEntityManagerFactory("transactions-optional")

  def manager = EMF.createEntityManager()

  def loadResult[T](query: Query): List[T] = {
    val results = collectionAsScalaIterable(query.getResultList).toList
    for (result <- results) yield result match {
      case appropriate: T => appropriate
      case other => throw new DBException
    }
  }

  def key(string: String): Key = string match {
    case "" => null
    case some => KeyFactory.stringToKey(some)
  }
}
