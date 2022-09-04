package rhino.take.image_pos

import java.awt.Image
import java.awt.image.BufferedImage
import java.lang.ref.WeakReference
import java.nio.file.Path
import javax.imageio.ImageIO

data class ImageWrapper(
  val point:Point
) {
  fun image():BufferedImage =
    ImageIO.read(point.path?.toFile() ?: error("path is not set."))

}