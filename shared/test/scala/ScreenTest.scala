import utest._
import model.Screen._

object ScreenTest extends TestSuite{
  def tests = TestSuite {
    "square_window" -{
      val converter = new Converter(700,700)
      "left_top"     -{ assert(converter(Point(Left(0.0), 0.0))   == (0, 0)) }
      "left_center"  -{ assert(converter(Point(Left(0.5), 0.5))   == (0, 350)) }
      "center"       -{ assert(converter(Point(Center(0.5), 0.5)) == (350, 350)) }
      "right_center" -{ assert(converter(Point(Right(0.5), 0.5))  == (700, 350)) }
      "right_bottom" -{ assert(converter(Point(Right(1.0), 1.0))  == (700, 700)) }
      "boundary"     -{ assert(
        converter(Point(Left(1.0), 0.2))                          == (0, 140),
        converter(Point(Center(0), 0.2))                          == (0, 140),
        converter(Point(Center(1.0), 0.8))                        == (700, 560),
        converter(Point(Right(0), 0.8))                           == (700, 560)
      )}
      "between"      -{ assert(
        converter(Between(Point(Left(0.0),0), Point(Right(1.0),1.0), 0.0)) == (0, 0),
        converter(Between(Point(Left(0.0),0), Point(Right(1.0),1.0), 0.2)) == (140, 140),
        converter(Between(Point(Left(0.0),0), Point(Right(1.0),1.0), 0.8)) == (560, 560),
        converter(Between(Point(Left(0.0),0), Point(Right(1.0),1.0), 1.0)) == (700, 700),
        converter(Between(Point(Right(1.0),0), Point(Left(0.0),1.0), 0.0)) == (700, 0),
        converter(Between(Point(Right(1.0),0), Point(Left(0.0),1.0), 0.2)) == (560, 140),
        converter(Between(Point(Right(1.0),0), Point(Left(0.0),1.0), 0.8)) == (139, 560), // rounding error
        converter(Between(Point(Right(1.0),0), Point(Left(0.0),1.0), 1.0)) == (0, 700),
        converter(Between(Point(Right(0),0.2), Point(Left(1),0.8), 0.5)) == (350, 350)
      )}
    }

    "horizontal_window" -{
      val converter = new Converter(1000,500)
      "left_top"     -{ assert(converter(Point(Left(0.0), 0.0))   == (0, 0)) }
      "left_center"  -{ assert(converter(Point(Left(0.5), 0.5))   == (125, 250)) }
      "center"       -{ assert(converter(Point(Center(0.5), 0.5)) == (500, 250)) }
      "right_center" -{ assert(converter(Point(Right(0.5), 0.5))  == (875, 250)) }
      "right_bottom" -{ assert(converter(Point(Right(1.0), 1.0))  == (1000, 500)) }
      "boundary"     -{ assert(
        converter(Point(Left(1.0), 0.2))                          == (250, 100),
        converter(Point(Center(0), 0.2))                          == (250, 100),
        converter(Point(Center(1.0), 0.8))                        == (750, 400),
        converter(Point(Right(0), 0.8))                           == (750, 400)
      )}
      "between"      -{ assert(
        converter(Between(Point(Left(0.0),0), Point(Right(1.0),1.0), 0.0)) == (0, 0),
        converter(Between(Point(Left(0.0),0), Point(Right(1.0),1.0), 0.2)) == (200, 100),
        converter(Between(Point(Left(0.0),0), Point(Right(1.0),1.0), 0.8)) == (800, 400),
        converter(Between(Point(Left(0.0),0), Point(Right(1.0),1.0), 1.0)) == (1000, 500),
        converter(Between(Point(Right(1.0),0), Point(Left(0.0),1.0), 0.0)) == (1000, 0),
        converter(Between(Point(Right(1.0),0), Point(Left(0.0),1.0), 0.2)) == (800, 100),
        converter(Between(Point(Right(1.0),0), Point(Left(0.0),1.0), 0.8)) == (199, 400), // rounding error
        converter(Between(Point(Right(1.0),0), Point(Left(0.0),1.0), 1.0)) == (0, 500),
        converter(Between(Point(Right(0),0.2), Point(Left(1),0.8), 0.5)) == (500, 250)
      )}
    }

    "vertical_window" -{
      val converter = new Converter(500,1000)
      "left_top"     -{ assert(converter(Point(Left(0.0), 0.0))   == (0, 250)) }
      "left_center"  -{ assert(converter(Point(Left(0.5), 0.5))   == (0, 500)) }
      "center"       -{ assert(converter(Point(Center(0.5), 0.5)) == (250, 500)) }
      "right_center" -{ assert(converter(Point(Right(0.5), 0.5))  == (500, 500)) }
      "right_bottom" -{ assert(converter(Point(Right(1.0), 1.0))  == (500, 750)) }
      "boundary"     -{ assert(
        converter(Point(Left(1.0), 0.2))                          == (0, 350),
        converter(Point(Center(0), 0.2))                          == (0, 350),
        converter(Point(Center(1.0), 0.8))                        == (500, 650),
        converter(Point(Right(0), 0.8))                           == (500, 650)
      )}
      "between"      -{ assert(
        converter(Between(Point(Left(0.0),0), Point(Right(1.0),1.0), 0.0)) == (0, 250),
        converter(Between(Point(Left(0.0),0), Point(Right(1.0),1.0), 0.2)) == (100, 350),
        converter(Between(Point(Left(0.0),0), Point(Right(1.0),1.0), 0.8)) == (400, 650),
        converter(Between(Point(Left(0.0),0), Point(Right(1.0),1.0), 1.0)) == (500, 750),
        converter(Between(Point(Right(1.0),0), Point(Left(0.0),1.0), 0.0)) == (500, 250),
        converter(Between(Point(Right(1.0),0), Point(Left(0.0),1.0), 0.2)) == (400, 350),
        converter(Between(Point(Right(1.0),0), Point(Left(0.0),1.0), 0.8)) == (99, 650), // rounding error
        converter(Between(Point(Right(1.0),0), Point(Left(0.0),1.0), 1.0)) == (0, 750),
        converter(Between(Point(Right(0),0.2), Point(Left(1),0.8), 0.5)) == (250, 500)
      )
      }
    }
  }
}
