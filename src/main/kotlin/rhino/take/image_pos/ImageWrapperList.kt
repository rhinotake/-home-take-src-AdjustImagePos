package rhino.take.image_pos

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.name
import kotlin.streams.toList

//
data class ImageWrapperList(val list: MutableList<ImageWrapper>) {
  private var _currentPos: Int = list.indices.first

  fun hasPrev() = 0 < _currentPos && _currentPos <= list.lastIndex
  fun hasPNext() = 0 <= _currentPos && _currentPos < list.lastIndex

  fun prev(): ImageWrapper = list[--_currentPos] ?: error("no prev")
  fun current(): ImageWrapper = list[_currentPos] ?: error("no current")
  fun next(): ImageWrapper = list[++_currentPos] ?: error("no next")

  //
  fun update(imageWrapper: ImageWrapper) {
    list
      .map {
        if (it.point.path == imageWrapper.point.path) imageWrapper
        else it
      }
      .let {
        list.clear()
        list.addAll(it)
      }

    list
      .map { it.point }
      .let { PointList(it).save() }
  }

  //
  companion object {
    //
    fun load(dir: Path): ImageWrapperList {
      val pointList = PointList.load()

      return Files.list(dir)
        .toList()
        .filter { it.name.endsWith(".JPG") }
        .sorted()
        .map { ImageWrapper(pointList.byPath(it)) }
        .let { ImageWrapperList(it.toMutableList()) }
    }
  }
}