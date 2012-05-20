package odo.verte.data

import javax.persistence.{GenerationType, Entity}
import Annotations.{Id, GeneratedValue}
import com.google.appengine.api.datastore.{KeyFactory, Key}


/**
 * Created with IntelliJ IDEA.
 * User: Oleg
 * Date: 20.05.12
 * Time: 18:16
 * To change this template use File | Settings | File Templates.
 */

@Entity
case class Tree(@Id
                @GeneratedValue(strategy = GenerationType.IDENTITY)
                var key: Key,
                var size: Int = 0,
                var name: String = "")