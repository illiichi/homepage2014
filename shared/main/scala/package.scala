package object definition{
  type Effect = Option[Int] => Seq[Style]
  type Action = () => Unit

  type Style = logic.Screen.Converter => (String, String)
}
