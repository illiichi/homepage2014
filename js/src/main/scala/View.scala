package view

import scala.scalajs.js
import scalatags._
import scalatags.all._
import scalatags.SvgTags._
import scalatags.SvgStyles._
import js.Dynamic.{ global => g }
import rx._
import rx.ops._

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
        div(id := "container")(
          div(`class`:="container-back")(
            canvas(id:="back-canvas")
          ),
          div(`class`:="container-front", id:="container-front")(
            for((ident, path) <- Seq(
              ("fukusuke", "images/fukusuke1.svg"), 
              ("fukusuke_peko", "images/fukusuke2.svg"), 
              ("fukusuke_glass", "images/fukusuke3.svg"),
              ("fish", "images/fish.svg"),
              ("fish2","images/fish02.svg"))
            ) yield img(id:=ident, src := path)
          )
        ),
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
    val c = g.document.getElementById("back-canvas")
    c.width = width
    c.height = height
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
    val menuInputElement = g.document.getElementById("menu-input")
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
        Seq("container", "container-front", "back-canvas").foreach{ id =>
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
