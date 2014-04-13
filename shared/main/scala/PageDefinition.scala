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
  type Style = String
  def menu: Seq[(String, (Effect, Action))]
  lazy val parser = new InputParser(menu)
  def effect(state: parser.InputState): Seq[Style]
  def action(action: Action): Unit
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
/*
<iframe src="http://www.slideshare.net/slideshow/embed_code/14441333" width="427" height="356" frameborder="0" marginwidth="0" marginheight="0" scrolling="no" style="border:1px solid #CCC; border-width:1px 1px 0; margin-bottom:5px; max-width: 100%;" allowfullscreen> </iframe> <div style="margin-bottom:5px"> <strong> <a href="https://www.slideshare.net/maedaunderscore/scala-14441333" title="Scalaノススメ" target="_blank">Scalaノススメ</a> </strong> from <strong><a href="http://www.slideshare.net/maedaunderscore" target="_blank">Yasuyuki Maeda</a></strong> </div>
<iframe src="http://www.slideshare.net/slideshow/embed_code/16705652" width="427" height="356" frameborder="0" marginwidth="0" marginheight="0" scrolling="no" style="border:1px solid #CCC; border-width:1px 1px 0; margin-bottom:5px; max-width: 100%;" allowfullscreen> </iframe> <div style="margin-bottom:5px"> <strong> <a href="https://www.slideshare.net/maedaunderscore/elmfunctional-reactive-programming" title="Elmで始めるFunctional Reactive Programming " target="_blank">Elmで始めるFunctional Reactive Programming </a> </strong> from <strong><a href="http://www.slideshare.net/maedaunderscore" target="_blank">Yasuyuki Maeda</a></strong> </div>
<iframe src="http://www.slideshare.net/slideshow/embed_code/23731604" width="427" height="356" frameborder="0" marginwidth="0" marginheight="0" scrolling="no" style="border:1px solid #CCC; border-width:1px 1px 0; margin-bottom:5px; max-width: 100%;" allowfullscreen> </iframe> <div style="margin-bottom:5px"> <strong> <a href="https://www.slideshare.net/maedaunderscore/ambersmalltalk" title="AmberとSmalltalkとオブジェクト指向" target="_blank">AmberとSmalltalkとオブジェクト指向</a> </strong> from <strong><a href="http://www.slideshare.net/maedaunderscore" target="_blank">Yasuyuki Maeda</a></strong> </div>
<iframe src="http://www.slideshare.net/slideshow/embed_code/27494131" width="427" height="356" frameborder="0" marginwidth="0" marginheight="0" scrolling="no" style="border:1px solid #CCC; border-width:1px 1px 0; margin-bottom:5px; max-width: 100%;" allowfullscreen> </iframe> <div style="margin-bottom:5px"> <strong> <a href="https://www.slideshare.net/maedaunderscore/clojurescript" title="ClojureScriptという選択肢" target="_blank">ClojureScriptという選択肢</a> </strong> from <strong><a href="http://www.slideshare.net/maedaunderscore" target="_blank">Yasuyuki Maeda</a></strong> </div>
 */

  def effect(state: parser.InputState) = Seq()
  def action(action: Action){}
}
