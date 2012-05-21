package odo.verte.serv.tree

import javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}
import odo.verte.data.{DB, Tree}
import odo.verte.data.TreeJSON._
import odo.verte.utils.JSON


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

      val trees = DB.loadResult[Tree](query)

      resp.getWriter.write(JSON.dump(trees))
    } finally {
      manager.close()
    }
  }

}

