package model
/*
sealed trait Effect
case object Introduction             extends Effect
case object FallForward              extends Effect
case object FukusukeGlassOn          extends Effect
case class RollingFukusuke(max: Int) extends Effect

sealed trait Action
case object  JustSee                extends Action
case class   OpenPage(url: String)  extends Action
case class   ShowSlide(url: String) extends Action
 */

object Actions{
  def justSee =  () => {}
  def openPage(url:String) = () => {
    view.View.openUrl(url)
  }
  def showSlide(url:String) = () => {
  }
}
import model.IlliIchiPage._   // fix: wrong dependency
import model.Screen._

object Effects{
  def introduction: Effect = count => Seq()
  def fallForward: Effect = count => Seq()
  def fukusukeGlassOn: Effect = count => {
    val size = count.getOrElse(20)
    Seq(Figures.fukusuke.style(
      Point(Right(0.8), 0.9), 0.2,
      origin = Figure.RightBottom,
      additionalStyle = Figure.FallForward(10 * size)
    ))
  }
  def rollingFukusuke(max: Int): Effect = count => Seq()
}
