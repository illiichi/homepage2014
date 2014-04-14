package model

sealed trait Effect
case object Introduction             extends Effect
case object FallForward              extends Effect
case object FukusukeGlassOn          extends Effect
case class RollingFukusuke(max: Int) extends Effect

sealed trait Action
case object  JustSee                extends Action
case class   OpenPage(url: String)  extends Action
case class   ShowSlide(url: String) extends Action

trait Definition{
  def menu: Seq[(String, (Effect, Action))]
  lazy val parser = new InputParser(menu)
  def styles(effect: Effect, count: Option[Int]): Seq[Style]
  def doIt(action: Action): Unit
  def defaultStyles: Seq[Style]
  def illegalStyles: Seq[Style]
}

object IlliIchiPage extends Definition{
  val menu = Seq(
    ("about me"                     , (Introduction , JustSee)) ,
    ("what's illi-ichi"             , (FallForward , JustSee)) ,
    ("show code (open github page)" , (FukusukeGlassOn , OpenPage("https://github.com/illi-ichi"))) ,
/* window.open("http://www.google.co.jp", "_blank") */
    ("slide of scala"               , (RollingFukusuke(5) ,  ShowSlide("http://www.slideshare.net/slideshow/embed_code/14441333"))) ,
    ("slide of amber smalltalk"     , (RollingFukusuke(15) , ShowSlide("http://www.slideshare.net/slideshow/embed_code/16705652"))) ,
    ("slide of elm"                 , (RollingFukusuke(3) ,  ShowSlide("http://www.slideshare.net/slideshow/embed_code/23731604"))) ,
    ("slide of clojurescript"       , (RollingFukusuke(13) , ShowSlide("http://www.slideshare.net/slideshow/embed_code/27494131")))
    )

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
  val menuStyle = Figures.menu.style(Point(Center.point, 0.3), 0.6, origin = Figure.Center)
  object Background{
    val default = Figures.background.css("background:white;")
    val illegal = Figures.background.css("background:red;")
  }
  val defaultStyles = Figures.all.map(_.hide) :+ menuStyle :+ Background.default
  val illegalStyles = defaultStyles :+ Background.illegal

  def styles(effect: Effect, count: Option[Int]) = defaultStyles
  def doIt(action: Action){}


/*
<iframe src="http://www.slideshare.net/slideshow/embed_code/14441333" width="427" height="356" frameborder="0" marginwidth="0" marginheight="0" scrolling="no" style="border:1px solid #CCC; border-width:1px 1px 0; margin-bottom:5px; max-width: 100%;" allowfullscreen> </iframe> <div style="margin-bottom:5px"> <strong> <a href="https://www.slideshare.net/maedaunderscore/scala-14441333" title="Scalaノススメ" target="_blank">Scalaノススメ</a> </strong> from <strong><a href="http://www.slideshare.net/maedaunderscore" target="_blank">Yasuyuki Maeda</a></strong> </div>
<iframe src="http://www.slideshare.net/slideshow/embed_code/16705652" width="427" height="356" frameborder="0" marginwidth="0" marginheight="0" scrolling="no" style="border:1px solid #CCC; border-width:1px 1px 0; margin-bottom:5px; max-width: 100%;" allowfullscreen> </iframe> <div style="margin-bottom:5px"> <strong> <a href="https://www.slideshare.net/maedaunderscore/elmfunctional-reactive-programming" title="Elmで始めるFunctional Reactive Programming " target="_blank">Elmで始めるFunctional Reactive Programming </a> </strong> from <strong><a href="http://www.slideshare.net/maedaunderscore" target="_blank">Yasuyuki Maeda</a></strong> </div>
<iframe src="http://www.slideshare.net/slideshow/embed_code/23731604" width="427" height="356" frameborder="0" marginwidth="0" marginheight="0" scrolling="no" style="border:1px solid #CCC; border-width:1px 1px 0; margin-bottom:5px; max-width: 100%;" allowfullscreen> </iframe> <div style="margin-bottom:5px"> <strong> <a href="https://www.slideshare.net/maedaunderscore/ambersmalltalk" title="AmberとSmalltalkとオブジェクト指向" target="_blank">AmberとSmalltalkとオブジェクト指向</a> </strong> from <strong><a href="http://www.slideshare.net/maedaunderscore" target="_blank">Yasuyuki Maeda</a></strong> </div>
<iframe src="http://www.slideshare.net/slideshow/embed_code/27494131" width="427" height="356" frameborder="0" marginwidth="0" marginheight="0" scrolling="no" style="border:1px solid #CCC; border-width:1px 1px 0; margin-bottom:5px; max-width: 100%;" allowfullscreen> </iframe> <div style="margin-bottom:5px"> <strong> <a href="https://www.slideshare.net/maedaunderscore/clojurescript" title="ClojureScriptという選択肢" target="_blank">ClojureScriptという選択肢</a> </strong> from <strong><a href="http://www.slideshare.net/maedaunderscore" target="_blank">Yasuyuki Maeda</a></strong> </div>
 */
}
