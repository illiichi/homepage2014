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
    val view = new _root_.view.View()

    val inputState = view.menuText.map(definition.parser.parse)
    val styles = Rx { mainFlow(inputState(), view.screenSize()) }

    view.start(styles)
  }

  def mainFlow(inputState: InputState, screenSize: (Int, Int)):Seq[(String, String)] = {
    import definition.parser._
    val converter = new Screen.Converter(screenSize)
    (inputState match {
      case NoInput => definition.defaultStyles
      case DuringInput((effect, _), count) => definition.styles(effect, Some(count))
      case Complete((effect, action)) => 
        definition.doIt(action)
        definition.styles(effect, None)
      case WrongInput => definition.illegalStyles
    }).map{ f => f(converter)}
  }
}
