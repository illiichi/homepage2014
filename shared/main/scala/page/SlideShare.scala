package definition

case class SlideShare(
  embedUrl:String,
  linkUrl: String,
  title: String
){
  def toHtml = s"""<iframe src="${embedUrl}" width="100%" height="100%" frameborder="0" marginwidth="0" marginheight="0" scrolling="no" style="border:1px solid #CCC; border-width:1px 1px 0; margin-bottom:5px; max-width: 100%;" allowfullscreen> </iframe> <div style="margin-bottom:5px"> <strong> <a href="${linkUrl}" title="${title}" target="_blank">${title}</a> </strong></div>"""
}

object SlideShare{
  val scala = SlideShare(
    "http://www.slideshare.net/slideshow/embed_code/14441333",
    "https://www.slideshare.net/maedaunderscore/scala-14441333", 
    "Scalaノススメ")

  val elm = SlideShare(
    "http://www.slideshare.net/slideshow/embed_code/16705652",
    "https://www.slideshare.net/maedaunderscore/elmfunctional-reactive-programming",
    "Elmで始めるFunctional Reactive Programming ")

  val amber = SlideShare(
    "http://www.slideshare.net/slideshow/embed_code/23731604",
    "https://www.slideshare.net/maedaunderscore/ambersmalltalk",
    "AmberとSmalltalkとオブジェクト指向")

  val clojurescript = SlideShare(
    "http://www.slideshare.net/slideshow/embed_code/27494131",
    "https://www.slideshare.net/maedaunderscore/clojurescript",
    "ClojureScriptという選択肢")
}
