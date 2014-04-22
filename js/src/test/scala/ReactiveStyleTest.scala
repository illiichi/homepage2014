import utest._

import scala.scalajs.js
import scalatags._
import scalatags.all._
import js.Dynamic.{ global => g }
import rx._

import view._

object ReactiveStyleTest extends TestSuite{
  def tests = TestSuite {
    "dom_operation" -{
      g.document.body.innerHTML = div(id:="hoge", style:="width:100px").toString

      val el = g.document.getElementById("hoge")
      assert(el.getAttribute("style").toString == "width:100px")
    }

    "reactive" -{
      "initial" -{
        val v = Var("width:200px")
        val styles = Rx{Map("hoge" -> v())}

        g.document.body.innerHTML = div(styles.attrRx("hoge")).toString

        val el = g.document.getElementById("hoge")
        assert(el.getAttribute("style").toString == "width:200px")
        assert(el.getAttribute("class").toString == definition.Const.Classes.figure)
      }

      "change" -{
        val v = Var("width:200px")
        val styles = Rx{Map("hoge" -> v())}

        g.document.body.innerHTML = div(styles.attrRx("hoge")).toString

        v() = "width:100px"
        val el = g.document.getElementById("hoge")
        assert(el.getAttribute("style").toString == "width:100px")
        assert(el.getAttribute("class").toString == definition.Const.Classes.figure)
      }

      "to_show" -{
        val v = Var("hoge")
        val styles = Rx{Map(v() -> "width:100px")}

        g.document.body.innerHTML = div(styles.attrRx("fuga")).toString

        val el = g.document.getElementById("fuga")
        assert(el.getAttribute("style").toString == "display:none")
        assert(el.getAttribute("class").toString == definition.Const.Classes.figure)

        v() = "fuga"
        assert(el.getAttribute("style").toString == "width:100px")
        assert(el.getAttribute("class").toString == definition.Const.Classes.figure)
      }

      "mixed_attribute" -{
        val v = Var("width:200px")
        val styles = Rx{Map("hoge" -> v())}

        g.document.body.innerHTML = div(styles.attrRx("hoge"), "data".attr:="other").toString

        val el = g.document.getElementById("hoge")
        assert(el.getAttribute("style").toString == "width:200px")
        assert(el.getAttribute("class").toString == definition.Const.Classes.figure)
        assert(el.getAttribute("data").toString == "other")
      }

      "override" -{
        val v = Var("width:200px")
        val styles = Rx{Map("hoge" -> v())}

        g.document.body.innerHTML = div(styles.attrRx("hoge"), `class`:="other").toString

        val el = g.document.getElementById("hoge")
        assert(el.getAttribute("style").toString == "width:200px")
        assert(el.getAttribute("class").toString == s"other")
      }

      "asign_class" -{
        val v = Var("width:200px")
        val styles = Rx{Map("hoge" -> v())}

        g.document.body.innerHTML = div(styles.attrRx("hoge", "other")).toString

        val el = g.document.getElementById("hoge")
        assert(el.getAttribute("style").toString == "width:200px")
        assert(el.getAttribute("class").toString == s"other")
      }
    }
  }
}
