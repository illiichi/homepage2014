import utest._
import model._
import model.Screen.{Point, Center}

object StyleTest extends TestSuite{
  def tests = TestSuite {
    val screen = (1000, 1000)
    implicit val converter = new Screen.Converter(screen._1, screen._2)
    val fig = Figure("hoge", Point(Center(0.2), 0.4), Point(Center(0.8), 0.6))

    "none" -{
      val style = fig.style(None)
      assert(style == "display:none;")
    }

    "default" -{
      val style = fig.style(Some(Point(Center.point, 0.5)))
      assert(
        style.contains("width:600px;"),
        style.contains("height:200px;")
      )
    }
  }
}
