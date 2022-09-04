package rhino.take.image_pos

import javafx.application.Application
import kotlin.io.path.name

object MkConvertCommand {
  @JvmStatic
  fun main(args:Array<String>) {
    val pl = PointList.load()
    val average = pl.average()
    println("# average: ${average}")
    println()
    println("mkdir jjj2")
    println()

    pl.list.map{
      val name = it.path?.name ?: "xx"
      val x = average.x - it.x
      val y = average.y - it.y
      "convert jjj/${name} -distort Affine '0,0 ${x},${y}' jjj2/${name}"
    }
      .let{
        println(it.joinToString("\n"))
      }
  }
}