import utest._
import model._
import model.Screen.{Point, Center}

object StyleTest extends TestSuite{
  def tests = TestSuite {
    val screen = (1000, 1000)
    val conv = new Screen.Converter(screen._1, screen._2)
    val fig = Figure("hoge", 0.5)

    "none" -{
      val (id, style) = fig.hide(conv)
      assert(style == "display:none;")
    } 

    val l  = Point(Center.point, 0.5)

    "left_top" -{
      val (id, style) = fig.style(l, 0.3)(conv)
      assert(
        style.contains("width:300px;"),
        style.contains("height:150px;"),
        style.contains("top:500px;"),
        style.contains("left:500px;")
      )
    }

    "center" -{
      val (id, style) = fig.style(l, 0.6, origin = Figure.Center)(conv)
      assert(
        style.contains("width:600px;"),
        style.contains("height:300px;"),
        style.contains("top:350px;"),
        style.contains("left:200px;")
      )
    }
    "right" -{
      val (id, style) = fig.style(l, 0.3, origin = Figure.RightBottom)(conv)
      assert(
        style.contains("width:300px;"),
        style.contains("height:150px;"),
        style.contains("top:350px;"),
        style.contains("left:200px;")
      )
    }
  }
}


/*
 style:"transform: rotateX(60deg) rotateY(10deg) perspective(100px);"
*/ 
