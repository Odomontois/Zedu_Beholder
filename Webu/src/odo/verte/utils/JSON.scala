package odo.verte.utils

import util.parsing.json.{JSON => scalaJson}
import odo.verte.serv.tree.ParseException
import java.io.InputStream

/**
 * Created with IntelliJ IDEA.
 * User: Oleg
 * Date: 21.05.12
 * Time: 22:53
 * To change this template use File | Settings | File Templates.
 */


object JSON {
  type Deserialize[T] = (Map[String, Any]) => T
  type Serialize[T] = (T) => Map[String, Any]

  def load[T](source: String)(implicit deserialize: Deserialize[T]): List[T] = {
    deserializeJSON[T](
      scalaJson.parseFull(source) match {
        case None => throw new ParseException
        case Some(data) => data
      })(deserialize)
  }

  def load[T](source: InputStream)(implicit deserialize: Deserialize[T]): List[T] = {
    val stringSource = scala.io.Source.fromInputStream(source).getLines.mkString("\n")
    this.load[T](stringSource)(deserialize)
  }

  def dump[T](value: T)(implicit serialize: Serialize[T]): String = {
    serializeJSON(serialize(value))
  }

  def dump[T](values: List[T])(implicit serialize: Serialize[T]): String = {
    serializeJSON(values.map(serialize))
  }

  private[utils] def deserializeJSON[T](value: Any)(deserialize: Deserialize[T]): List[T] = {
    value match {
      case map: Map[String, Any] => List(deserialize(map))
      case list: List[Any] => (for (item <- list) yield deserializeJSON[T](item)(deserialize)).fold(Nil)(_ ++ _)
    }
  }

  private[utils] def serializeJSON(value: Any): String = {
    value match {
      case byte: Byte => byte.toString
      case short: Short => short.toString
      case int: Int => int.toString
      case long: Long => long.toString
      case float: Float => float.toString
      case double: Double => double.toString
      case boolean: Boolean => boolean.toString
      case string: String => "'" + string + "'"
      case list: List[Any] => "[" + (for (item <- list) yield serializeJSON(item)).mkString(",") + "]"
      case map: Map[String, Any] => "{" + (for ((name, value) <- map) yield "'" + name + "':" + serializeJSON(value)).mkString(",") + "}"
    }
  }

  implicit def map2read(map: Map[String, Any]) = new Read(map)
}

class Read(map: Map[String, Any]) {
  def read[T](name: String): T = {
    map.get(name) match {
      case None => throw new ParseException
      case Some(value) => value match {
        case appropriate: T => appropriate
        case other => throw new ParseException
      }
    }
  }
}

