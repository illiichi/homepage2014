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

object View{
  def start(
    menuInput: Var[String], screenSize: Var[(Int, Int)],
    styles: Rx[Seq[(String, String)]]){
    render()

    eventHook(menuInput, screenSize)
    styles.foreach(changeAttribute)
  }

  def render(): Unit = {
    g.document.body.innerHTML = 
      div(
        div(id := Ids.container)(
          div(`class`:= Classes.container_back)(
            canvas(id:=Ids.back_canvas)
          ),
          div(`class`:= Classes.container_front, id:=Ids.container_front)(
            for((ident, path) <- Const.imageUrls) yield img(id:=ident, src := path)
          )
        ),
        div(`class` := Classes.control_panel_container)(
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

  def grid(width:Int, height:Int)  = {
    val c = g.document.getElementById(Ids.back_canvas)
    val ctx = (c.asInstanceOf[js.Dynamic]).getContext("2d")
    val (w, h) = (width / 30, height / 30)
    ctx.beginPath()
    ctx.fillStyle = "black"
    ctx.strokeStyle = "green"
    ctx.fillRect(0,0,width, height)
    for(i <- (1 to 30)){
      ctx.moveTo(w * i, 0); ctx.lineTo(w * i, height)
      ctx.moveTo(0, h * i); ctx.lineTo(width, h * i)
    }
    ctx.stroke()
  }

  def eventHook(menuInput: Var[String], screenSize: Var[(Int, Int)]){
    val menuInputElement = g.document.getElementById(Ids.menu_input)
    menuInputElement.oninput = { (e:js.Dynamic) => 
      menuInput() = e.target.value.asInstanceOf[String]
    }
    menuInputElement.focus()

    g.window.onresize = { _:js.Dynamic =>
      val s = g.document.documentElement
      def toInt(n: js.Dynamic) = n.asInstanceOf[js.Number].toInt
      screenSize() = (toInt(s.clientWidth), toInt(s.clientHeight))
    }
    screenSize.foreach{ case (x, y) =>
        Seq(Ids.container, Ids.container_front, Ids.back_canvas).foreach{ id =>
          val style = g.document.getElementById(id).style
          style.width = s"${x}px"
          style.height = s"${y}px"
        }
        grid(x, y)
    }
  }

  def changeAttribute(xs: Seq[(String, String)]){
    xs.foreach { case (id, style) =>
        g.document.getElementById(id).style = style
    }
  }
}
