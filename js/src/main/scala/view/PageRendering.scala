package view

import scala.scalajs.js
import scalatags._
import scalatags.all._
import scalatags.SvgTags._
import scalatags.SvgStyles._
import js.Dynamic.{ global => g }
import rx._
import rx.ops._

import definition.Const
import definition.Const.Ids
import definition.Const.Classes

class PageRendering(){
  val menuText = Var("")
  val screenSize = Var(windowSize())

  def start(styles: Rx[Map[String, String]]){
    render(styles)

    eventHook(menuText, screenSize)
  }

  def render(styles: Rx[Map[String, String]]): Unit = {
    g.document.body.innerHTML = 
      div(id:=Ids.background)(
        `object`("data".attr := "images/buddha01.svg",
                 `class`:="back-buddha", id:=Ids.back_buddha),
        div(styles.attr(Ids.lambda)),
        div(id := Ids.container)(
          div(styles.attr(Ids.container_front, Classes.container_front))(
            for(
              (ident, path) <- Const.imageUrls
            ) yield `object`(styles.attr(ident), "data".attr := path),
            div(styles.attr(Ids.slide))
          )
        ),
        div(
          styles.attr(Ids.control_panel_container, 
            s"${Classes.figure} ${Classes.control_panel_container}")
          )(
          div(`class` := "back-panel"),
          div(`class` := "control-panel")(
            div(`class` := "header")(
              img(`class` := "logo",  src:="images/illi-ichi_logo.svg"),
              img(`class` := "kanji", src:="images/illi-ichi_kanji.svg")
            ),
            input(placeholder:="type one of below", id := "menu-input"),
            ul(
              li("Who am I"),
              li("Meaning of illi-ichi"),
              li("Show code (open github page)"),
              li(`class`:="nest-menu")(
                span("About"),
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
  }

  def eventHook(menuText: Var[String], screenSize: Var[(Int, Int)]){
    val menuInput = g.document.getElementById(Ids.menu_input)
    menuInput.oninput = { (e:js.Dynamic) => 
      menuText() = e.target.value.asInstanceOf[String]
    }
    menuInput.focus()

    g.window.onresize = { _:js.Dynamic =>
      screenSize() = windowSize() 
    }
    screenSize.foreach{ case (x, y) =>
        // big svg need resize by js because of chrome bug
        Seq(Ids.container, Ids.back_buddha).foreach{ id =>
          val style = g.document.getElementById(id).style
          style.width = s"${x}px"
          style.height = s"${y}px"
        }
    }
  }

  def windowSize() = {
    val s = g.document.documentElement
    def toInt(n: js.Dynamic) = n.asInstanceOf[js.Number].toInt
    (toInt(s.clientWidth), toInt(s.clientHeight))
  }
}

trait BrowserViewAction extends definition.ViewAction{
  def openUrl(url: String){
    g.window.open(url, "_blank")
  }

  def putLambdaCube(){
    g.document.getElementById(Ids.lambda).innerHTML =
      `object`("data".attr := "images/lambda-cube.svg").toString
  }

  def putSlideSharePage(slideHtml: String){
    g.document.getElementById(Ids.slide).innerHTML = slideHtml
  }
}
