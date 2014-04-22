package definition

import logic._

trait PageDefinition{
  def menu: Seq[(String, (Effect, Action))]
  lazy val parser = new InputParser(menu)
  def defaultStyles: Seq[Style]
  def illegalStyles: Seq[Style]
}

trait ViewAction{
  def openUrl(url: String): Unit
  def putLambdaCube():Unit
  def putSlideSharePage(slideHtml: String):Unit
}

trait IlliIchiPage extends PageDefinition with Layout{ self: ViewAction =>

  val menu = {
    import Actions._
    Seq(
    ("who am I"                     , (introduction , justSee)) ,
    ("meaning of illi-ichi"             , (showAnotherWorld , startLambdaCubeAnimation)) ,
    ("show code (open github page)" , (fukusukeGlassOn , openPage(Const.githubPage))) ,
    ("about scala"               , (rollingFukusuke(5) ,  showSlide(SlideShare.scala))) ,
    ("about amber smalltalk"     , (rollingFukusuke(15) , showSlide(SlideShare.amber))) ,
    ("about elm"                 , (rollingFukusuke(3) ,  showSlide(SlideShare.elm))) ,
    ("about clojurescript"       , (rollingFukusuke(13) , showSlide(SlideShare.clojurescript)))
    )
  }

  object Actions{
    def justSee =  () => {}
    def startLambdaCubeAnimation = () => {
      self.putLambdaCube()
    }
    def openPage(url:String) = () => {
      self.openUrl(url)
    }

    var lastSlide:SlideShare = _
    def showSlide(slide: SlideShare) = () => {
      if(lastSlide != slide){
        lastSlide = slide
        self.putSlideSharePage(slide.toHtml)
      }
    }
  }
}
