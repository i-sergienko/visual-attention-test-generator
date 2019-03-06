package sergienko.ivan.psych.test.generator

import sergienko.ivan.psych.test.DPI
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Font
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB
import java.util.*

val random = Random()

fun generate(width: Int, height: Int, cellSize: Int): BufferedImage {
    val image = BufferedImage(width, height, TYPE_INT_RGB).apply {
        with(createGraphics()) {
            background = Color.WHITE
            color = Color.WHITE
            stroke = BasicStroke(1.0f)

            (0 until width step cellSize).forEach {
                drawLine(it, 0, it, height)
            }

            (0 until height step cellSize).forEach {
                drawLine(0, it, width, it)
            }

            font = Font("Arial Black", Font.PLAIN, cmsToPixel(0.25, DPI.toDouble()).toInt())

            var counter = 1
            (0 until height step cellSize).forEach {
                val currentY = it
                (0 until width - cellSize step cellSize).forEach {
                    val currentX = it
//                    drawString(counter.toString(), currentX + cellSize/10, currentY + cellSize/3)
                    val coordinates = randomizeCoordinatesWithinBounds(currentX, currentY, cellSize)
                    drawString(distractors.get(random.nextInt(distractors.size)).toString(), coordinates.first, coordinates.second)
                    counter++
                }
            }
        }
    }

    return image
}

private fun randomizeCoordinatesWithinBounds(x: Int, y: Int, cellSize: Int): Pair<Int, Int> {
    val newX = random.nextInt((cellSize/1.4).toInt()).let {
        when {
            x + it <= x + cellSize/10 -> x + cellSize/10
            x + it >= (x + (cellSize/1.4)).toInt() -> (x + (cellSize/1.4)).toInt()
            else -> x + it
        }
    }
    val newY = random.nextInt((cellSize/1.1).toInt()).let {
        when {
            y + it <= y + cellSize/3 -> y + cellSize/3
            y + it >= (y + cellSize/1.1).toInt() -> (y + cellSize/1.1).toInt()
            else -> y + it
        }
    }

    return newX to newY
}

private val distractors = ('A'..'Z').toList()