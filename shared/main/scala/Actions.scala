package model
/*
sealed trait Effect
case object Introduction             extends Effect
case object FallForward              extends Effect
case object FukusukeGlassOn          extends Effect
case class RollingFukusuke(max: Int) extends Effect

sealed trait Action
case object  JustSee                extends Action
case class   OpenPage(url: String)  extends Action
case class   ShowSlide(url: String) extends Action
 */

object Actions{
  def justSee =  () => {}
  def openPage(url:String) = () => {
    view.View.openUrl(url)
  }
  def showSlide(url:String) = () => {
  }
}

import model.Screen._

class Effects(base: Seq[Style]){
  def introduction: Effect = count => base
  def fallForward(back: Figure, fig: Figure, menu: Figure, animation: Figure): Effect = count => {
    val deg = (count.getOrElse(16) / 16.0 * 90).toInt
    val styles = 
      if (count.isEmpty) Seq(
        menu.style(Point(Left(0.2), 0.1), 0.4, additionalStyle = Figure.Scale(0.7)),
        back.css("opacity:0"),
        animation.style(Point(Right(0.8), 0.1), 0.4, origin = Figure.RightTop)
      )
      else Seq(
        back.css(s"background:white;transform:perspective(300px) rotateX(${-deg}deg);")
      )

    (base :+ 
      fig.style(
        Point(Right(0.8), 0.9), 0.2,
        origin = Figure.RightBottom
      )
    ) ++ styles
  }
  def zoom(fig1: Figure, fig2: Figure): Effect = count => {
    val size = count.getOrElse(20)
    val fig = if (size < 10) fig1 else fig2

    base :+ fig.style(
      Point(Right(0.8), 0.9), 0.2,
      origin = Figure.RightBottom,
      additionalStyle = Figure.Zoom(1 + 0.3 * size)
    )
  }
  def rolling(half: Int, max: Int, fig: Figure): Effect = count => {
    val rate = count.map{cnt =>  
      if (cnt <= half) cnt / half.toDouble * 0.5
      else (cnt - half) / (max - half).toDouble *0.5 + 0.5
    }.getOrElse(1.0)

    println(rate)
    base :+ fig.style(
      Between(Point(Right(0.8), 0.9), Point(Left(0.4), 0.9), rate), 0.2,
      origin = Figure.RightBottom,
      additionalStyle = Figure.Rotate((720 * rate).toInt)
    )
  }
}
