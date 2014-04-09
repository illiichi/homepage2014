package view

import scala.scalajs.js
import scalatags._
import scalatags.all._
import scalatags.SvgTags._
import scalatags.SvgStyles._
import js.Dynamic.{ global => g }
import rx._
import rx.ops._

object View {
  def render(): Unit = {
    g.document.body.innerHTML = 
      div(
        div(id := "container"),
        div(`class` := "control-panel-container")(
          div(`class` := "back-panel"),
          div(`class` := "control-panel")(
            div(`class` := "header")(
              img(`class` := "logo",  src:="images/illi-ichi_logo.svg"),
              img(`class` := "kanji", src:="images/illi-ichi_kanji.svg")
            ),
            input(placeholder:="type one of below", id := "menu-input"),
            ul(
              li("About me"),
              li("What's illi-ichi"),
              li(`class`:="nest-menu")(
                span("Slide of"),
                ul(
                  li("Scala"),
                  li("Amber Smalltalk"),
                  li("Elm"),
                  li("ClojureScript")
                )
              )
            )
          )
        )
      ).toString

    val menuInput = Var("")
    Obs(menuInput){ g.console.log(menuInput()) }

    val menuInputElement = g.document.getElementById("menu-input")
    menuInputElement.oninput = { (e:js.Dynamic) => 
      menuInput() = e.target.value.asInstanceOf[String]
    }
    menuInputElement.focus()

    def getScreenSize() = {
      val s = g.document.documentElement
      def toInt(n: js.Dynamic) = n.asInstanceOf[js.Number].toInt
      (toInt(s.clientWidth), toInt(s.clientHeight))
    }
    val screenSize = Var(getScreenSize())
    g.window.onresize = { _:js.Dynamic =>
      screenSize() = getScreenSize()
    }
    screenSize.foreach{ case (x, y) =>
        val style = g.document.getElementById("container").style
        style.width = s"${x}px"
        style.height = s"${y}px"
        g.console.log((x, y).toString)
    }
  }
}
