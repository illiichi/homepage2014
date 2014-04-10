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
  case object CenterTop extends Position
  case object Center extends Position

  case class ScreenPosition(p: (Int, Int), size: (Int, Int))
  def NoMore(p: ScreenPosition) = ""
}

case class Figure(id: String, leftTop: VirtualPosition, rightBottom: VirtualPosition){
  import Figure._
  def style(
    location: Option[VirtualPosition],
    scale: Double = 1.0,
    style:ScreenPosition => String = NoMore,
    position: Position = LeftTop
  )(implicit conv: Screen.Converter) = {
    location match {
      case Some(position) => 
        val (x1, y1) = conv(leftTop)
        val (x2, y2) = conv(rightBottom)
        s"width:${x2 - x1}px;height:${y2 - y1}px;"
      case None => "display:none;"
    }
  }
}
