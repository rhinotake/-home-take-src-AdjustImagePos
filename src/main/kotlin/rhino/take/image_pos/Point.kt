package rhino.take.image_pos

import java.nio.file.Path

data class Point(
  val path: Path? = null,
  val x: Int = Int.MIN_VALUE,
  val y: Int = Int.MIN_VALUE
) {
}