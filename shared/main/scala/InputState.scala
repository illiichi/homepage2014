package model

class InputParser[State](rawMap: Seq[(String, State)]){
  val map = rawMap.map{case (key, value) => (key.toUpperCase, value)}

  sealed trait InputState
  case object NoInput                                  extends InputState
  case class DuringInput(candidate: State, count: Int) extends InputState
  case class Complete(state: State)                    extends InputState
  case object WrongInput                               extends InputState

  def parse(str: String) = {
    val input = str.toUpperCase

    if(input.isEmpty) NoInput 
    else map.find(_._1.startsWith(input)).map { 
      case (`input`, value) => Complete(value)
      case (_, value) => DuringInput(value, input.length)
    }.getOrElse(WrongInput)
  }
}
