package odo.verte.data

import collection.JavaConversions._
import javax.persistence.{Query, Persistence}
import odo.verte.serv.tree.DBException
import com.google.appengine.api.datastore.{KeyFactory, Key}

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
