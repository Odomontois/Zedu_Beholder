package odo.verte.serv.tree

import javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}
import odo.verte.data.{DB, Tree}
import odo.verte.utils.JSON
import odo.verte.data.TreeJSON._
import javax.servlet.http.HttpServletResponse.SC_OK

class TreeCreate extends HttpServlet {
  override def doPost(req: HttpServletRequest, resp: HttpServletResponse) {
    val trees = JSON.load[Tree](req.getInputStream)
    val manager = DB.manager
    try {
      for (tree <- trees) {
        manager.getTransaction.begin()
        manager.persist(tree)
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
  conforms
}
