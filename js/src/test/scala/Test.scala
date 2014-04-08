import utest._

object Test extends TestSuite{
  def tests = TestSuite{
    "A" -{
      assert(1 == 1)
    }
  }
}
