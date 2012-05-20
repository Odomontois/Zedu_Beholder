package odo.verte.data

import annotation.target.field
import javax.persistence.Persistence

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
}
