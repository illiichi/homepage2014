import scala.scalajs.js
import js.Dynamic.{ global => g }
import scalatags._
import scalatags.all._
import rx._

import definition.Const.Classes

package object view{
  implicit class ReactiveStyle(styles: Rx[Map[String, String]]){
    def attrRx(elementId: String, klass: String = Classes.figure) = {
      Obs(styles, skipInitial = true){
        val el = g.document.getElementById(elementId)
        val elementStyle=styles().get(elementId).getOrElse("display:none")
        el.setAttribute("style", elementStyle)
      }

      val elementStyle = styles.now.get(elementId).getOrElse("display:none")
      Seq(id:=elementId, style:=elementStyle, `class`:=klass)
    }
  }
}

