package odo.verte.serv

import odo.verte.utils.JSON
import odo.verte.data.DB
import javax.servlet.http.HttpServletResponse._
import javax.servlet.http.{HttpServlet, HttpServletResponse, HttpServletRequest}
import com.google.appengine.api.datastore.Key

/**
 * Created with IntelliJ IDEA.
 * User: Oleg
 * Date: 24.05.12
 * Time: 23:49
 * To change this template use File | Settings | File Templates.
 */
object DataAccess {
  type KeyHolder = {
    val key: Key
  }
}

import DataAccess._

abstract class DataAccess[T <: KeyHolder] extends HttpServlet {
  def serialize(value: T): Map[String, Any]

  def deserialize(map: Map[String, Any]): T

  val myClass: Class[T]


  override def doPost(req: HttpServletRequest, resp: HttpServletResponse) {
    getServletConfig().getInitParameter("accessType") match {
      case "create" => Create.doPost(req, resp)
      case "update" => Update.doPost(req, resp)
      case "delete" => Delete.doPost(req, resp)
      case _ => super.doPost(req, resp)
    }
  }

  override def doGet(req: HttpServletRequest, resp: HttpServletResponse) {
    getServletConfig().getInitParameter("accessType") match {
      case "list" => List.doGet(req, resp)
      case _ => super.doGet(req, resp)
    }
  }

  object Create {
    def doPost(req: HttpServletRequest, resp: HttpServletResponse) {
      val values = JSON.load[T](req.getInputStream)(deserialize(_))
      val manager = DB.manager
      try {
        for (value <- values) {
          manager.getTransaction.begin()
          manager.persist(value)
          manager.getTransaction.commit()
        }
      } catch {
        case ex: Exception => {
          manager.getTransaction.rollback()
          throw ex
        }
      } finally {
        manager.close()
      }
      resp.setStatus(SC_OK)
    }
  }

  object Delete {
    def doPost(req: HttpServletRequest, resp: HttpServletResponse) {
      val values = JSON.load[T](req.getInputStream)(deserialize(_))
      val manager = DB.manager
      try {

        for (value <- values) {
          manager.getTransaction.begin()
          val storedValue = manager.find(myClass, value.key)
          manager.remove(storedValue)
          manager.getTransaction.commit()
        }
      } catch {
        case ex: Exception => {
          manager.getTransaction.rollback()
          throw ex
        }
      } finally {
        manager.close()
      }
      resp.setStatus(SC_OK)
    }
  }

  object List {
    def doGet(req: HttpServletRequest, resp: HttpServletResponse) {
      val manager = DB.manager
      resp.setContentType("application/json")
      try {
        val query = manager.createQuery(
          "select from " + myClass.getName
        )
        val values = DB.loadResult[T](query)
        resp.getWriter.write(JSON.dump(values)(serialize(_)))
      } finally {
        manager.close()
      }
    }
  }

  object Update {
    def doPost(req: HttpServletRequest, resp: HttpServletResponse) {
      val values = JSON.load[T](req.getInputStream)(deserialize(_))
      val manager = DB.manager
      try {
        for (value <- values) {
          manager.getTransaction.begin()
          manager.persist(value)
          manager.getTransaction.commit()
        }
      } catch {
        case ex: Exception => {
          manager.getTransaction.rollback()
          throw ex
        }
      } finally {
        manager.close()
      }
      resp.setStatus(SC_OK)
    }
  }

}
