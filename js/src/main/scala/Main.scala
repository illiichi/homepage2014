import scala.scalajs.js
import js.annotation.JSExport
import rx._
import rx.ops._

@JSExport
object Main {
  val page = new definition.IlliIchiPage with view.BrowserViewAction
  import page.parser.InputState

  @JSExport
  def main(): Unit = {
    val view = new _root_.view.View()

    val inputState = view.menuText.map(page.parser.parse)
    val styles = Rx { mainFlow(inputState(), view.screenSize()) }

    view.start(styles)
  }

  def mainFlow(inputState: InputState, screenSize: (Int, Int)):Map[String, String] = {
    import page.parser._
    val converter = new logic.Screen.Converter(screenSize)

    (inputState match {
      case NoInput => page.defaultStyles
      case DuringInput((effect, _), count) => effect(Some(count))
      case Complete((effect, action)) => 
        action()
        effect(None)
      case WrongInput => page.illegalStyles
    }).map{ f => f(converter) }.toMap
  }
}
