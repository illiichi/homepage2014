import utest._
import model._
import model.Screen.{Point, Center}

object StyleTest extends TestSuite{
  def tests = TestSuite {
    val screen = (1000, 1000)
    val conv = new Screen.Converter(screen._1, screen._2)
    val fig = Figure("hoge", 0.5)

    "none" -{
      val style = fig.style(None)(conv)
      assert(style == "display:none;")
    }

    val l  = Some((Point(Center.point, 0.5), Center(0.8)))
    val l2 = Some((Point(Center.point, 0.5), Center(0.2)))
    "left_top" -{
      val style = fig.style(l)(conv)
      assert(
        style.contains("width:300px;"),
        style.contains("height:150px;"),
        style.contains("top:500px;"),
        style.contains("left:500px;")
      )
    }

    "center" -{
      val style = fig.style(l, origin = Figure.Center)(conv)
      assert(
        style.contains("width:600px;"),
        style.contains("height:300px;"),
        style.contains("top:350px;"),
        style.contains("left:200px;")
      )
    }
    "center2" -{
      val style = fig.style(l2, origin = Figure.Center)(conv)
      assert(
        style.contains("width:600px;"),
        style.contains("height:300px;"),
        style.contains("top:350px;"),
        style.contains("left:200px;")
      )
    }
    "right" -{
      val style = fig.style(l2, origin = Figure.RightBottom)(conv)
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
