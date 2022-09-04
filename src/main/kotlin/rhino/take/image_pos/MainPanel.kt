package rhino.take.image_pos

import javafx.embed.swing.SwingFXUtils
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.image.ImageView
import javafx.scene.image.WritableImage
import javafx.scene.input.MouseEvent
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.Line2D
import java.net.URL
import java.nio.file.Paths
import java.util.*

class MainPanel {
  @FXML
  private lateinit var resources: ResourceBundle

  @FXML
  private lateinit var location: URL

  @FXML
  private lateinit var _prevButton: Button

  @FXML
  private lateinit var _nextButton: Button

  @FXML
  private lateinit var _quitButton: Button

  @FXML
  private lateinit var _imageView: ImageView

  @FXML
  private lateinit var _message: Label

  private val _imageWrapperList = ImageWrapperList.load(Paths.get("/home/take/tmp-new-ics/jjj/"))

  @FXML
  fun initialize() {
    assert(_prevButton != null) { "fx:id=\"_prevButton\" was not injected: check your FXML file 'MainPanel.fxml'." }
    assert(_nextButton != null) { "fx:id=\"_nextButton\" was not injected: check your FXML file 'MainPanel.fxml'." }
    assert(_quitButton != null) { "fx:id=\"_quitButton\" was not injected: check your FXML file 'MainPanel.fxml'." }
    assert(_imageView != null) { "fx:id=\"_imageView\" was not injected: check your FXML file 'MainPanel.fxml'." }


    fun resetImage() {
      val current = _imageWrapperList.current()

      _message.text = "${current.point}"
      println(_message.text)

      val image = current.image().also {
        val x = current.point.x.toDouble()
        val y = current.point.y.toDouble()
        val g = it.graphics as Graphics2D
        g.color = Color.red
        g.stroke = BasicStroke(3f)
        g.draw(Line2D.Double(x - 10.0, y, x + 10.0, y))
        g.draw(Line2D.Double(x, y - 10.0, x, y + 10.0))
      }

      val writableImage = WritableImage(image.width, image.height)
      SwingFXUtils.toFXImage(image, writableImage)
      _imageView.image = writableImage

      _prevButton.isDisable = _imageWrapperList.hasPrev().not()
      _nextButton.isDisable = _imageWrapperList.hasPNext().not()
    }

    resetImage()

    _nextButton.onAction = object : EventHandler<ActionEvent> {
      override fun handle(event: ActionEvent) {
        _imageWrapperList.next()
        resetImage()
      }
    }
    _prevButton.onAction = object : EventHandler<ActionEvent> {
      override fun handle(event: ActionEvent) {
        _imageWrapperList.prev()
        resetImage()
      }
    }

    _imageView.onMouseClicked = object : EventHandler<MouseEvent> {
      override fun handle(event: MouseEvent) {
        println("---- ${event}")


        _imageWrapperList.current()
          .let { it.copy(point = it.point.copy(x = event.x.toInt(), y = event.y.toInt())) }
          .let {            _imageWrapperList.update(it) }

//        resetImage()
        _nextButton.fire()
      }
    }

    _quitButton.onAction = object : EventHandler<ActionEvent> {
      override fun handle(event: ActionEvent) {
        System.exit(0)
      }
    }
  }
}