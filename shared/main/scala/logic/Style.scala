package logic
import logic.Screen._
import definition._

object Figure{
  sealed trait Position
  case object LeftTop extends Position
  case object Center extends Position
  case object RightTop extends Position
  case object RightBottom extends Position

  case class ScreenPosition(p: (Int, Int), size: (Int, Int))
  def NoMore(p: ScreenPosition) = ""
  def Rotate(deg: Int) = (p: ScreenPosition) => 
    withPrefix(s"transform:rotate(${-deg}deg);")
  def FallForward(deg:Int) = (p: ScreenPosition) => 
    withPrefix(s"transform:perspective(100px) rotateX(${-deg}deg);")
  def Zoom(scale: Double) = (p: ScreenPosition) =>
    withPrefix(s"transform:scale(${scale});", s"transform-origin:${p.size._1}px ${p.size._2}px;")
  def Scale(scale: Double) = (p: ScreenPosition) =>
    withPrefix(s"transform:scale(${scale});")

  def withPrefix(styles: String*) =
    styles.mkString + styles.map("-webkit-"+_).mkString
}

/* 
 apect ratio = height / width 
*/
case class Figure(id: String, aspectRatio: Double){
  import Figure._
  // (left top, right) -- height will be calcurated by aspect ratio
  type Location = (VirtualPosition, Horizontal)

  def style(
    location: VirtualPosition, 
    size: Double,
    scale: Double = 1.0,
    origin: Position = LeftTop,
    additionalStyle:ScreenPosition => String = NoMore
  ): Style = { (conv: Screen.Converter) =>
    def p[A](f: =>A) = {val a = f; println(a); a}
    def toStyle(x:Double, y: Double, width: Double, height: Double) = p{
      val p = ScreenPosition((y.toInt, x.toInt), (width.toInt, height.toInt))
      (id, Seq(
        s"width:${width.toInt}px;height:${height.toInt}px;",
        s"top:${y.toInt}px;left:${x.toInt}px;"
      ).mkString ++ additionalStyle(p))
    }

    val width = conv.actual(size)
    val height = width * aspectRatio

    origin match {
      case LeftTop =>         
        val (x1, y1) = conv(location)
        val x2 = x1 + width
        toStyle(x1, y1, width, height)
      case Center =>
        val (cx, cy) = conv(location)
        val (x1, y1) = (cx - width / 2, cy - height / 2)
        toStyle(x1, y1, width, height)
      case RightBottom =>
        val (x2, y2) = conv(location)
        val (x1, y1) = (x2 - width, y2 - height)
        toStyle(x1, y1, width, height)
      case RightTop =>
        val (x2, y1) = conv(location)
        val x1 = x2 - width
        toStyle(x1, y1, width, height)
    }
  }

  def css(str: String) = (conv: Screen.Converter) => (id, str)

  def hide = (conv: Screen.Converter) => (id, "display:none;")
}

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
        back.css(
          "background:white;"
          + Figure.withPrefix(s"transform:perspective(300px) rotateX(${-deg}deg);")))

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
