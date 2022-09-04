package rhino.take.image_pos

import com.fasterxml.jackson.databind.ObjectMapper
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.PrintStream
import java.nio.file.Files
import java.nio.file.*

data class PointList(
  val list: List<Point>
) {
  //
  fun byPath(path: Path): Point =
    list.find { it.path == path } ?: Point(path = path)

  //
  fun average(): Point =
    list.filterNot { it.path == null || it.x == Int.MIN_VALUE }
      .let { filteredList ->
        val x = filteredList.map { it.x }.average()
        val y = filteredList.map { it.y }.average()
        Point(null, x.toInt(), y.toInt())
      }

  //
  fun save() {
    PrintStream(FileOutputStream(_pointsFile.toFile())).use { ps ->
      val mapList : List<Map<String, Any?>> =
        list.map{
          mapOf(
            "path" to it.path?.toString(),
            "x" to it.x,
            "y" to it.y
          )}

      ObjectMapper()
        .writerWithDefaultPrettyPrinter()
        .writeValueAsString(mapList)
        .let { ps.println(it) }
    }
  }


  //
  companion object {
    private val _pointsFile = Paths.get("./points.jspn")

    //
    fun load(): PointList =
      if (Files.exists(_pointsFile)) {
        val mapList: List<*> = ObjectMapper().readValue(_pointsFile.toFile(), List::class.java)
//        println(mapList)
        mapList
          .map { it ->
            val item = it as Map<*, *>
            Point(
              path = Paths.get(item["path"] as String),
              x = item["x"] as Int,
              y = item["y"] as Int,
            )
          }
          .let {
//          println(it.take(10))
            PointList(it) }
      } else
        PointList(listOf())
  }
}