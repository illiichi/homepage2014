package model

object Actions{
  import view.View
  def justSee =  () => {}
  def startLambdaCubeAnimation = () => {
    View.putLambdaCube()
  }
  def openPage(url:String) = () => {
    View.openUrl(url)
  }
  def showSlide(slide: SlideShare) = () => {
    View.showSlide(slide)
  }
}

import model.Screen._

class Effects(base: Seq[Style]){
  def introduction(fig1: Figure, fig2: Figure, name: Figure): Effect = count => {
    val rate = count.getOrElse(8) / 8.0
    val figStyle = 
      fig1.style(
        Between(Point(Right.edge, 0.2), Point(Center(0), 0.65), rate), 0.2,
        additionalStyle = Figure.Zoom(0.1 / rate + 0.9))
    val fig2Style =
      if (rate > 0.3) fig2.style(
        Between(Point(Right.edge, 1.0), Point(Center(0.8), 0.80), (rate - 0.3) / 0.7), 0.1
      ) else fig2.hide

    val nameStyle =
      if (rate > 0.99) name.style(Point(Center(0.2), 0.65), 0.6)
      else if( rate > 0.8) name.style(Point(Right(0.8), 0.7), 0.6)
      else name.hide

    base :+ figStyle :+ fig2Style :+ nameStyle
  }
  def fallForward(back: Figure, fig: Figure, menu: Figure, animation: Figure): Effect = count => {
    val max = 20
    val deg = (count.getOrElse(max) / max.toDouble * 90).toInt
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
  def rolling(half: Int, max: Int, fig: Figure, slide: Figure, menu: Figure): Effect = count => {
    val rate = count.map{cnt =>  
      if (cnt <= half) cnt / half.toDouble * 0.5
      else (cnt - half) / (max - half).toDouble *0.5 + 0.5
    }.getOrElse(1.0)

    val otherStyles = 
      if (count.isEmpty) Seq(
        slide.style(Point(Right(0.9), 0.1), 0.8, origin = Figure.RightTop),
        menu.style(Point(Left(0.2), 0.1), 0.2)
      )
      else Seq()

    (base :+ fig.style(
      Between(Point(Right(0.8), 0.9), Point(Left(0.4), 0.9), rate), 0.2,
      origin = Figure.RightBottom,
      additionalStyle = Figure.Rotate((720 * rate).toInt)
    )) ++ otherStyles
  }
}
