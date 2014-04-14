package model
import model.Screen._
/*
width
height

translate
scale
rotate
rotate-origin
skew
opacity
display:none or block
 */ 

object Figure{
  sealed trait Position
  case object LeftTop extends Position
  case object Center extends Position
  case object RightBottom extends Position

  case class ScreenPosition(p: (Int, Int), size: (Int, Int))
  def NoMore(p: ScreenPosition) = ""
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
      (id, Seq(
        s"width:${width.toInt}px;height:${height.toInt}px;",
        s"top:${y.toInt}px;left:${x.toInt}px;"
      ).mkString)
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
    }
  }

  def css(str: String) = (conv: Screen.Converter) => (id, str)

  def hide = (conv: Screen.Converter) => (id, "display:none;")
}
