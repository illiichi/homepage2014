import scala.scalajs.js
import js.annotation.JSExport
import rx._
import rx.ops._
import view.PageRendering
import logic.Screen

@JSExport
object Main {
  val page = new definition.IlliIchiPage with view.BrowserViewAction
  import page.parser._

  @JSExport
  def main(): Unit = {
    val view = new PageRendering()

    val inputState = view.menuText.map(page.parser.parse)

    inputState.foreach( { 
      case Complete((_, action)) => action()
      case _ =>
    }, skipInitial = true)

    val styles = Rx { toStyleAttribute(inputState(), view.screenSize()) }

    view.start(styles)
  }

  def toStyleAttribute(inputState: InputState, screenSize: (Int, Int)):Map[String, String] = {
    val converter = new Screen.Converter(screenSize)

    (inputState match {
      case NoInput => page.defaultStyles
      case WrongInput => page.illegalStyles
      case DuringInput((effect, _), count) => effect(Some(count))
      case Complete((effect, _)) => effect(None)
    }).map{ f => f(converter) }.toMap
  }
}
