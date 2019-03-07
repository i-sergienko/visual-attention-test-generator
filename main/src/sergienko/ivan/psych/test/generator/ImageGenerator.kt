package sergienko.ivan.psych.test.generator

import sergienko.ivan.psych.test.DPI
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Font
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB
import java.util.*

val random = Random()

fun generate(gridWidth: Int, gridHeight: Int, cellSize: Int): BufferedImage {
    val image = BufferedImage(gridWidth*cellSize+1, gridHeight*cellSize+1, TYPE_INT_RGB).apply {
        with(createGraphics()) {
            background = Color.WHITE
            color = Color.WHITE

            fillRect(0, 0, gridWidth*cellSize+1, gridHeight*cellSize+1)

            stroke = BasicStroke(1.0f)
            color = Color.BLACK

            font = Font("Arial Black", Font.PLAIN, cmsToPixel(0.15, DPI.toDouble()).toInt())

            val grid = Array(gridWidth) { Array(gridHeight) { ' ' } }


            color = Color.BLACK

//            var counter = 1
//            (0..height step cellSize).forEach {
//                val currentY = it
//                (0..width - cellSize step cellSize).forEach {
//                    val currentX = it
////                    val coords = randomizeCoordinatesWithinBounds(currentX, currentY, cel)
//                    drawString(counter.toString(), currentX + cellSize/4, currentY + cellSize/2)
//                    counter++
//                }
//            }

            (0 until 30).forEach {
                var x = random.nextInt(7)
                var y = random.nextInt(14)

                while (grid[x][y] == 'A' ||
                    (x > 0 && grid[x - 1][y] == 'A') ||
                    (x < 7 && grid[x + 1][y] == 'A') ||
                    (y > 0 && grid[x][y - 1] == 'A') ||
                    (y < 13 && grid[x][y + 1] == 'A') // ||
//                    (x > 0 && y > 0 && grid[x - 1][y - 1] == 'A') ||
//                    (x > 0 && y < 13 && grid[x - 1][y + 1] == 'A') ||
//                    (x < 15 && y > 0 && grid[x + 1][y - 1] == 'A') ||
//                    (x < 15 && y < 13 && grid[x + 1][y + 1] == 'A')
                ) {
                    x = random.nextInt(7)
                    y = random.nextInt(14)
                }

                grid[x][y] = 'A'
                grid[15 - x][y] = 'A'
                val coordinates = randomizeCoordinatesWithinBounds(x * cellSize, y * cellSize, cellSize)

                drawString(
                    "A",
                    coordinates.first,
                    coordinates.second
                )
                drawString(
                    "A",
                    (15 - x)*cellSize + (cellSize - (coordinates.first - x*cellSize) - 6),
                    coordinates.second
                )
            }


            color = Color.BLACK

            var xGrid = 0
            var yGrid = 0
            (0..height step cellSize).forEach {
                val currentY = it

                xGrid = 0
                (0..width - cellSize step cellSize).forEach {
                    val currentX = it

                    if (yGrid <= 13 && grid[xGrid][yGrid] != 'A') {
                        val coordinates = randomizeCoordinatesWithinBounds(currentX, currentY, cellSize)

                        drawString(
                            distractors.get(random.nextInt(distractors.size)).toString(),
                            coordinates.first,
                            coordinates.second
                        )
                    }

                    xGrid++
                }
                yGrid++
            }
        }
    }

    val imageInFrame = BufferedImage((gridWidth+2)*cellSize+1, (gridHeight+2)*cellSize+1, TYPE_INT_RGB)
        .apply {
            with(graphics) {
                color = Color.WHITE
                fillRect(0, 0, (gridWidth+2)*cellSize+1, (gridHeight+2)*cellSize+1)

                color = Color.BLACK
                drawLine(0, 0, width, 0)
                drawLine(cellSize*(gridWidth+2), 0, cellSize*(gridWidth+2), cellSize*(gridHeight+2)*cellSize)
                drawLine(0, (gridHeight+2)*cellSize, cellSize*(gridWidth+2), cellSize*(gridHeight+2))
                drawLine(0, 0, 0, cellSize*(gridHeight+2))

                graphics.drawImage(image, cellSize, cellSize, null)
            }
        }

    return imageInFrame
}

private fun randomizeCoordinatesWithinBounds(x: Int, y: Int, cellSize: Int): Pair<Int, Int> {
    val newX = random.nextInt((cellSize / 1.4).toInt()).let {
        when {
            x + it <= x + cellSize / 10 -> x + cellSize / 10
            x + it >= (x + (cellSize / 1.4)).toInt() -> (x + (cellSize / 1.4)).toInt()
            else -> x + it
        }
    }
    val newY = random.nextInt((cellSize / 1.1).toInt()).let {
        when {
            y + it <= y + cellSize / 3 -> y + cellSize / 3
            y + it >= (y + cellSize / 1.1).toInt() -> (y + cellSize / 1.1).toInt()
            else -> y + it
        }
    }

    return newX to newY
}

private val distractors = ('B'..'Z').toList()