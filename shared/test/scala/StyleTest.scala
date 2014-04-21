import utest._
import logic._
import logic.Screen.{Point, Center}

object StyleTest extends TestSuite{
  def tests = TestSuite {
    "screen_1000x1000" -{
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

    "screen_1500x1000" -{
      val screen = (1500, 1000)
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
          style.contains("left:750px;")
        )
      }

      "center" -{
        val (id, style) = fig.style(l, 0.6, origin = Figure.Center)(conv)
        assert(
          style.contains("width:600px;"),
          style.contains("height:300px;"),
          style.contains("top:350px;"),
          style.contains("left:450px;")
        )
      }
      "right" -{
        val (id, style) = fig.style(l, 0.3, origin = Figure.RightBottom)(conv)
        assert(
          style.contains("width:300px;"),
          style.contains("height:150px;"),
          style.contains("top:350px;"),
          style.contains("left:450px;")
        )
      }
    }

    "screen_1000x1500" -{
      val screen = (1000, 1500)
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
          style.contains("top:750px;"),
          style.contains("left:500px;")
        )
      }

      "center" -{
        val (id, style) = fig.style(l, 0.6, origin = Figure.Center)(conv)
        assert(
          style.contains("width:600px;"),
          style.contains("height:300px;"),
          style.contains("top:600px;"),
          style.contains("left:200px;")
        )
      }
      "right" -{
        val (id, style) = fig.style(l, 0.3, origin = Figure.RightBottom)(conv)
        assert(
          style.contains("width:300px;"),
          style.contains("height:150px;"),
          style.contains("top:600px;"),
          style.contains("left:200px;")
        )
      }
    }

    "additional" -{
      val screen = (1500, 1000)
      val conv = new Screen.Converter(screen._1, screen._2)
      val fig = Figure("hoge", 0.5)
      val l  = Point(Center.point, 0.5)
      val (id, style) = fig.style(l, 0.3, additionalStyle=Figure.Zoom(2.5))(conv)
      "scale" -{
        assert(
          style.contains("width:300px"),
          style.contains("height:150px"),
          style.contains("transform:scale(2.5);"),
          style.contains("transform-origin:300px 150px")
        )
      }

      "vendor_prefix" -{
        assert(
          style.contains("transform:scale(2.5);"),
          style.contains("transform-origin:300px 150px"),
          style.contains("-webkit-transform:scale(2.5);"),
          style.contains("-webkit-transform-origin:300px 150px")
        )
      }
    }
  }
}
