import scala.scalajs.js
import js.annotation.JSExport
import rx._

@JSExport
object Main {
  @JSExport
  def main(): Unit = view.View.render()
}
