package logic

object Screen{
  sealed trait Horizontal
  case class Left(r: Double) extends Horizontal
  case class Center(r: Double) extends Horizontal
  case class Right(r: Double) extends Horizontal
  object Left{ def p(x:Double, y:Double) = Point(Left(x), y) }
  object Center{ def p(x:Double, y:Double) = Point(Center(x), y) }
  object Right{ def p(x:Double, y:Double) = Point(Right(x), y) }

  sealed trait VirtualPosition
  // 0 <= x, y <= 1
  case class Point(x: Horizontal, y: Double) extends VirtualPosition
  // 0 <= rate <= 1
  case class Between(from: Point, to: Point, rate: Double) extends VirtualPosition

  val ratio = 1.0  // it will not work if ratio isn't 1

  class Converter(width: Int, height: Int) {
    def this(t: (Int, Int)) = this(t._1, t._2)

    val (cw, ch) = {
      if(width < height) (width.toDouble, width * ratio)
      else               (height / ratio, height.toDouble)
    }
    val (cx, cy) = ((width - cw) / 2, (height - ch) / 2)

    def apply(v: VirtualPosition):(Int, Int) = {
      def floor(t:(Double, Double)) = (t._1.toInt, t._2.toInt)
      floor (
        v match {
          case Point(h, y) => (horizontal(h), vertical(y))
          case Between(from, to, r) => {
            def middle(x1: Double, x2: Double) =
              if(x1 < x2) (x2 - x1) * r + x1
              else        (x1 - x2) * (1.0 - r) + x2

            val (x1, y1) = apply(from)
            val (x2, y2) = apply(to)
            (middle(x1, x2), middle(y1, y2))
          }
        }
      )
    }
    def horizontal(h: Horizontal) = h match {
      case Left(x) => cx * x
      case Center(x) => cx + cw * x
      case Right(x) => cw + cx * (1 + x)
    }
    def actual(x: Double):Int = (x * cw).toInt


    def vertical(r: Double) = cy + ch * r
  }
}
