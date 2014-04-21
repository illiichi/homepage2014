import utest._
import logic._

object InputStateTest extends TestSuite{
  def tests = TestSuite{
    "parse" -{
      "empty_map" -{
        val parser = new InputParser(Seq())
        import parser._
        "no_input" -{
          assert(parse("") == NoInput)
        }
        "wrong_input"-{
          assert(parse("1") == WrongInput)
        }
      }

      "some_map" -{
        val parser = new InputParser(Seq(
          ("Ab", 1), ("bBb", 2), ("abcD", 3)
        ))
        import parser._
        "none" -{ assert(parse("")     == NoInput)}
        "lower" -{
          "a"    -{ assert(parse("a")    == duringInput(parser, 1, 1)) }
          "ab"   -{ assert(parse("ab")   == complete(parser, 1)) }
          "abc"  -{ assert(parse("abc")  == duringInput(parser, 3, 3)) }
          "abcd" -{ assert(parse("abcd") == complete(parser, 3)) }
          "b"    -{ assert(parse("b")    == duringInput(parser, 2, 1)) }
          "bb"   -{ assert(parse("bb")   == duringInput(parser, 2, 2))}
          "bbb"  -{ assert(parse("bbb")  == complete(parser, 2))}

          "wrong_input"-{ assert(
              parse("1") == WrongInput,
              parse("abcde") == WrongInput,
              parse("bbbb") == WrongInput
          )}
        }
        "upper" -{
          "a"    -{ assert(parse("A")    == duringInput(parser, 1, 1)) }
          "ab"   -{ assert(parse("AB")   == complete(parser, 1)) }
          "abc"  -{ assert(parse("ABC")  == duringInput(parser, 3, 3)) }
          "abcd" -{ assert(parse("ABCD") == complete(parser, 3)) }
          "b"    -{ assert(parse("B")    == duringInput(parser, 2, 1)) }
          "bb"   -{ assert(parse("BB")   == duringInput(parser, 2, 2))}
          "bbb"  -{ assert(parse("BBB")  == complete(parser, 2))}

          "wrong_input"-{ assert(
              parse("1") == WrongInput,
              parse("abcde") == WrongInput,
              parse("bbbb") == WrongInput,
              parse("ABCDE") == WrongInput,
              parse("BBBB") == WrongInput
          )}
        }
      }
    }
  }


/*
 hack for error below

[error] stable identifier required, but {
[error]   val $temp4: model.InputParser[Int] = parser;
[error]   $log4.apply(utest.LoggedValue.apply("parser", "model.InputParser[Int]", $temp4));
[error]   $temp4
[error] } found.
 */
  def duringInput[A](parser: InputParser[A], x: A, c: Int) = parser.DuringInput(x, c)
  def complete[A](parser: InputParser[A], x: A) = parser.Complete(x)

}
