package model

trait Definition{
  def menu: Seq[(String, (Effect, Action))]
  lazy val parser = new InputParser(menu)
  def defaultStyles: Seq[Style]
  def illegalStyles: Seq[Style]
}

object IlliIchiPage extends Definition{
  object Figures{
    import definition.Const.Ids
    val fukusuke = Figure(Ids.fukusuke, 1.0)
    val fukusuke_peko = Figure(Ids.fukusuke_peko, 1.0)
    val fukusuke_glass = Figure(Ids.fukusuke_glass, 1.0)
    val fish = Figure(Ids.fish, 0.5)
    val fish2 = Figure(Ids.fish2, 0.53)

    val lambda = Figure(Ids.lambda, 1.0)
    val slide = Figure(Ids.slide, 0.8321678321678322)
    val all = Seq(fukusuke, fukusuke_peko, fukusuke_glass, fish, fish2, lambda, slide)

    val menu = Figure(Ids.control_panel_container, 1.13)
    val background = Figure(Ids.container_front, 1.0)
  }
  import model.Screen._
  val menuStyle = Figures.menu.style(Point(Center.point, 0.4), 0.4, origin = Figure.Center)
  object Background{
    val default = Figures.background.css("background:white;")
    val illegal = Figures.background.css("background:red;")
  }
  val base = Figures.all.map(_.hide) :+ menuStyle :+ Background.default
  val defaultStyles = base :+ Fukusuke.base
  object Fukusuke {
    val apology = Figures.fukusuke_peko.style(
      Point(Center.point, 0.5), 0.8, origin = Figure.Center)

    val base = Figures.fukusuke.style(
      Point(Right(0.8), 0.9), 0.2, origin = Figure.RightBottom)
  }
  val illegalStyles = base :+ Background.illegal :+ Fukusuke.apology

  val effects = new Effects(base)
  val fukusukeGlassOn = effects.zoom(Figures.fukusuke, Figures.fukusuke_glass)
  def rollingFukusuke(max: Int) = effects.rolling(6, 6 + max, Figures.fukusuke, Figures.slide, Figures.menu)
  val showAnotherWorld = effects.fallForward(Figures.background, Figures.fukusuke, Figures.menu, Figures.lambda)
  val introduction = effects.introduction

  val menu = {
    import Actions._
    Seq(
    ("who am I"                     , (introduction , justSee)) ,
    ("meaning of illi-ichi"             , (showAnotherWorld , startLambdaCubeAnimation)) ,
    ("show code (open github page)" , (fukusukeGlassOn , openPage("https://github.com/illi-ichi"))) ,
    ("about scala"               , (rollingFukusuke(5) ,  showSlide(SlideShare.scala))) ,
    ("about amber smalltalk"     , (rollingFukusuke(15) , showSlide(SlideShare.amber))) ,
    ("about elm"                 , (rollingFukusuke(3) ,  showSlide(SlideShare.elm))) ,
    ("about clojurescript"       , (rollingFukusuke(13) , showSlide(SlideShare.clojurescript)))
    )
  }
}
