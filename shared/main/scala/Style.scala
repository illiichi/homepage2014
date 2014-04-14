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
    size: Horizontal,
    scale: Double = 1.0,
    origin: Position = LeftTop,
    additionalStyle:ScreenPosition => String = NoMore
  ): Style = { (conv: Screen.Converter) =>
    def toStyle(x:Double, y: Double, width: Double, height: Double) =
      (id, Seq(
        s"width:${width.toInt}px;height:${height.toInt}px;",
        s"top:${y.toInt}px;left:${x.toInt}px;"
      ).mkString)

    (origin, (location, size)) match {
      case (LeftTop, (leftTop, right)) => 
        val (x1, y1) = conv(leftTop)
        val x2 = conv.horizontal(right)
        val width = x2 - x1
        val height = width * aspectRatio
        toStyle(x1, y1, width, height)
      case (Center, (center, right)) =>
        val (cx, cy) = conv(center)
        val x2 = conv.horizontal(right)
        val width = Math.abs(x2 - cx) * 2
        val height = width * aspectRatio
        val (x1, y1) = (cx - width / 2, cy - height / 2)
        toStyle(x1, y1, width, height)
      case (RightBottom, (rightBottom, left)) =>
        val (x2, y2) = conv(rightBottom)
        val x1 = conv.horizontal(left)
        val width = x2 - x1
        val height = width * aspectRatio
        val y1 = y2 - height
        toStyle(x1, y1, width, height)
    }
  }

  def hide = (conv: Screen.Converter) => (id, "display:none;")
}
