package view.util

import scala.scalajs.js
import js.Dynamic.{ global => g }

object UserAgent {
  def isSmartPhone = {
    val userAgent = g.navigator.userAgent.toString
    Seq("iPhone", "iPad", "iPod", "Android").exists(userAgent.contains)
  }

  def ifSmartPhone[A](a:A) = if (isSmartPhone) Some(a) else None
}
