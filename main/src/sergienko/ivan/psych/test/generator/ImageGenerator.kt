package sergienko.ivan.psych.test.generator

import sergienko.ivan.psych.test.DPI
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Font
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB

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
                    drawString(counter.toString(), currentX + cellSize/4, currentY + cellSize/2)
                    counter++
                }
            }
        }
    }

    return image
}