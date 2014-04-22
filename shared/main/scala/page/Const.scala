package definition

object Const{
  object Ids{
    val fukusuke = "fukusuke"
    val fukusuke_peko = "fukusuke_peko"
    val fukusuke_glass = "fukusuke_glass"
    val fish = "fish"
    val fish2 = "fish2"
    val name = "name"

    val lambda = "lambda"
    val slide = "slide"
    val back_buddha = "back-buddha"

    val container = "container"
    val back_canvas = "back-canvas"
    val container_front = "container-front"
    val background = "background"

    val control_panel_container = "control-panel-container"
    val menu_input = "menu-input"
  }

  object Classes{
    val container_back = "container-back"
    val container_front = "container-front"
    val control_panel_container = "control-panel-container"
    val figure = "figure"
  }

  val objects = {
    import Ids._
    Seq(fukusuke, fukusuke_peko, fukusuke_glass, fish, fish2)
  }

  val imageUrls = Seq(
    (Ids.fukusuke, "images/fukusuke1.svg"),
    (Ids.fukusuke_peko, "images/fukusuke2.svg"),
    (Ids.fukusuke_glass, "images/fukusuke3.svg"),
    (Ids.fish, "images/fish.svg"),
    (Ids.fish2,"images/fish02.svg"),
    (Ids.name,"images/name.svg")
  )

  val githubPage = "https://github.com/illi-ichi/homepage2014"
}
