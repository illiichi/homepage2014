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

class View(){
  val menuText = Var("")
  val screenSize = Var(windowSize())

  def start(styles: Rx[Seq[(String, String)]]){
    render()

    eventHook(menuText, screenSize)
    styles.foreach(changeAttribute)
  }

  def render(): Unit = {
    g.document.body.innerHTML = 
      div(id:=Ids.background)(
        `object`("data".attr := "images/buddha01.svg", `class`:="back-buddha"),
        div(id := Ids.lambda, `class`:= Classes.figure),
        div(id := Ids.container)(
          div(`class`:= Classes.container_front, id:=Ids.container_front)(
            for(
              (ident, path) <- Const.imageUrls
            ) yield `object`(id:=ident, `class` := Classes.figure, "data".attr := path)
          )
        ),
        div(
          `class` := s"${Classes.figure} ${Classes.control_panel_container}",
          id:= Ids.control_panel_container)(
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
              li("Show code (open github page)"),
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
        Seq(Ids.container).foreach{ id =>
          val style = g.document.getElementById(id).style
          style.width = s"${x}px"
          style.height = s"${y}px"
        }
    }
  }

  def changeAttribute(xs: Seq[(String, String)]){
    xs.foreach { case (id, style) =>
        g.document.getElementById(id).setAttribute("style", style)
    }
  }

  def windowSize() = {
    val s = g.document.documentElement
    def toInt(n: js.Dynamic) = n.asInstanceOf[js.Number].toInt
    (toInt(s.clientWidth), toInt(s.clientHeight))
  }
}

object View{
  def openUrl(url: String){
    g.window.open(url, "_blank")
  }

  def putLambdaCube(){
    g.document.getElementById(Ids.lambda).innerHTML =
      `object`("data".attr := "images/lambda-cube.svg").toString
  }
}
