package definition

import logic._

trait Layout{
  object Figures{
    import definition.Const.Ids
    val fukusuke = Figure(Ids.fukusuke, 1.0)
    val fukusuke_peko = Figure(Ids.fukusuke_peko, 1.0)
    val fukusuke_glass = Figure(Ids.fukusuke_glass, 1.0)
    val fish = Figure(Ids.fish, 0.5)
    val fish2 = Figure(Ids.fish2, 0.53)
    val name = Figure(Ids.name, 0.2975778546712803)
    val tooltip = Figure(Ids.tooltip, 0.7)

    val lambda = Figure(Ids.lambda, 1.0)
    val slide = Figure(Ids.slide, 0.8321678321678322)
    val all = Seq(fukusuke, fukusuke_peko, fukusuke_glass, fish, fish2, lambda, slide, name)

    val menu = Figure(Ids.control_panel_container, 1.13)
    val background = Figure(Ids.container_front, 1.0)
  }

  import logic.Screen._
  val menuStyle = Figures.menu.style(Center.p(0.5, 0.4), 0.4, origin = Figure.Center)
  val tooltip = Figures.tooltip.css("display:inline")

  object Background{
    val default = Figures.background.css("background:white;")
    val illegal = Figures.background.css("background:red;")
  }
  val base = Seq(menuStyle, Background.default)
  object Fukusuke {
    val apology = Figures.fukusuke_peko.style(
      Center.p(0.5, 0.5), 0.8, origin = Figure.Center)

    val base = Figures.fukusuke.style(
      Right.p(0.8, 0.9), 0.2, origin = Figure.RightBottom)
  }

  val effects = new Effects(base)
  val fukusukeGlassOn = effects.zoom(Figures.fukusuke, Figures.fukusuke_glass)
  def rollingFukusuke(max: Int) = effects.rolling(6, 6 + max, Figures.fukusuke, Figures.slide, Figures.menu)
  val showAnotherWorld = effects.fallForward(Figures.background, Figures.fukusuke, Figures.menu, Figures.lambda)
  val introduction = effects.introduction(Figures.fish, Figures.fish2, Figures.name)

  val defaultStyles = base :+ Fukusuke.base :+ tooltip
  val illegalStyles = base :+ Background.illegal :+ Fukusuke.apology
}
