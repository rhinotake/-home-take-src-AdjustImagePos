package rhino.take.image_pos

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.layout.AnchorPane
import javafx.stage.Stage

object Main {
  @JvmStatic
  fun main(args:Array<String>) {
    Application.launch(App::class.java, *args)
  }

  class App : Application() {
    override fun start(stage: Stage) {
      val loader = FXMLLoader(this::class.java.getResource("./MainPanel.fxml"))
      val pane :AnchorPane = loader.load()
      val control:MainPanel = loader.getController()

      stage.scene = Scene(pane)
      stage.show()
    }
  }
}