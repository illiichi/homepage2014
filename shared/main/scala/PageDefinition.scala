package model

trait Definition{
  def menu: Seq[(String, (Effect, Action))]
  lazy val parser = new InputParser(menu)
  def defaultStyles: Seq[Style]
  def illegalStyles: Seq[Style]
}

object IlliIchiPage extends Definition{
  val menu = {
    import Effects._
    import Actions._
    Seq(
    ("about me"                     , (introduction , justSee)) ,
    ("what's illi-ichi"             , (fallForward , justSee)) ,
    ("show code (open github page)" , (fukusukeGlassOn , openPage("https://github.com/illi-ichi"))) ,
    ("slide of scala"               , (rollingFukusuke(5) ,  showSlide("http://www.slideshare.net/slideshow/embed_code/14441333"))) ,
    ("slide of amber smalltalk"     , (rollingFukusuke(15) , showSlide("http://www.slideshare.net/slideshow/embed_code/16705652"))) ,
    ("slide of elm"                 , (rollingFukusuke(3) ,  showSlide("http://www.slideshare.net/slideshow/embed_code/23731604"))) ,
    ("slide of clojurescript"       , (rollingFukusuke(13) , showSlide("http://www.slideshare.net/slideshow/embed_code/27494131")))
    )
  }

  object Figures{
    import definition.Const.Ids
    val fukusuke = Figure(Ids.fukusuke, 1.0)
    val fukusuke_peko = Figure(Ids.fukusuke_peko, 1.0)
    val fukusuke_glass = Figure(Ids.fukusuke_glass, 1.0)
    val fish = Figure(Ids.fish, 0.5)
    val fish2 = Figure(Ids.fish2, 0.53)

    val all = Seq(fukusuke, fukusuke_peko, fukusuke_glass, fish, fish2)

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

/*
<iframe src="http://www.slideshare.net/slideshow/embed_code/14441333" width="427" height="356" frameborder="0" marginwidth="0" marginheight="0" scrolling="no" style="border:1px solid #CCC; border-width:1px 1px 0; margin-bottom:5px; max-width: 100%;" allowfullscreen> </iframe> <div style="margin-bottom:5px"> <strong> <a href="https://www.slideshare.net/maedaunderscore/scala-14441333" title="Scalaノススメ" target="_blank">Scalaノススメ</a> </strong> from <strong><a href="http://www.slideshare.net/maedaunderscore" target="_blank">Yasuyuki Maeda</a></strong> </div>
<iframe src="http://www.slideshare.net/slideshow/embed_code/16705652" width="427" height="356" frameborder="0" marginwidth="0" marginheight="0" scrolling="no" style="border:1px solid #CCC; border-width:1px 1px 0; margin-bottom:5px; max-width: 100%;" allowfullscreen> </iframe> <div style="margin-bottom:5px"> <strong> <a href="https://www.slideshare.net/maedaunderscore/elmfunctional-reactive-programming" title="Elmで始めるFunctional Reactive Programming " target="_blank">Elmで始めるFunctional Reactive Programming </a> </strong> from <strong><a href="http://www.slideshare.net/maedaunderscore" target="_blank">Yasuyuki Maeda</a></strong> </div>
<iframe src="http://www.slideshare.net/slideshow/embed_code/23731604" width="427" height="356" frameborder="0" marginwidth="0" marginheight="0" scrolling="no" style="border:1px solid #CCC; border-width:1px 1px 0; margin-bottom:5px; max-width: 100%;" allowfullscreen> </iframe> <div style="margin-bottom:5px"> <strong> <a href="https://www.slideshare.net/maedaunderscore/ambersmalltalk" title="AmberとSmalltalkとオブジェクト指向" target="_blank">AmberとSmalltalkとオブジェクト指向</a> </strong> from <strong><a href="http://www.slideshare.net/maedaunderscore" target="_blank">Yasuyuki Maeda</a></strong> </div>
<iframe src="http://www.slideshare.net/slideshow/embed_code/27494131" width="427" height="356" frameborder="0" marginwidth="0" marginheight="0" scrolling="no" style="border:1px solid #CCC; border-width:1px 1px 0; margin-bottom:5px; max-width: 100%;" allowfullscreen> </iframe> <div style="margin-bottom:5px"> <strong> <a href="https://www.slideshare.net/maedaunderscore/clojurescript" title="ClojureScriptという選択肢" target="_blank">ClojureScriptという選択肢</a> </strong> from <strong><a href="http://www.slideshare.net/maedaunderscore" target="_blank">Yasuyuki Maeda</a></strong> </div>
 */
}
