import scala.scalajs.js
import js.annotation.JSExport
import rx._
import rx.ops._
import model._

@JSExport
object Main {
  val definition = IlliIchiPage
  import definition.parser.InputState

  @JSExport
  def main(): Unit = {
    val menuText = Var("")
    val screenSize = Var((0,0))
    val inputState = menuText.map(definition.parser.parse)
    val styles = Rx { mainFlow(inputState(), screenSize()) }

    view.View.start(menuText, screenSize, styles)
  }

  def mainFlow(inputState: InputState, screenSize: (Int, Int)) = {
    Seq(("fukusuke", "display:none"))
  }
}
