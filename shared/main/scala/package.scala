package object model{
  type Effect = Option[Int] => Seq[Style]
  type Action = () => Unit

  type Style = Screen.Converter => (String, String)
}
