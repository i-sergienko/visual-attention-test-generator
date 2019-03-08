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
    val image = BufferedImage(gridWidth * cellSize + 1, gridHeight * cellSize + 1, TYPE_INT_RGB).apply {
        with(createGraphics()) {
            background = Color.WHITE
            color = Color.WHITE

            fillRect(0, 0, gridWidth * cellSize + 1, gridHeight * cellSize + 1)

            stroke = BasicStroke(1.0f)

            color = Color.BLACK

            (0 until width step cellSize).forEach {
                drawLine(it, 0, it, height)
            }

            (0 until height step cellSize).forEach {
                drawLine(0, it, width, it)
            }

            font = Font("Arial", Font.PLAIN, cmsToPixel(0.24, DPI.toDouble()).toInt())

            val grid = Array(gridWidth) { Array(gridHeight) { ' ' } }

//            color = Color.RED

            (0 until 30).forEach {
                var x = random.nextInt(gridWidth / 2 - 1)
                var y = random.nextInt(gridHeight / 2)

                while (grid[x][y] == 'A' ||
                    (x > 0 && grid[x - 1][y] == 'A') ||
                    (x < gridWidth / 2 - 1 && grid[x + 1][y] == 'A') ||
                    (y > 0 && grid[x][y - 1] == 'A') ||
                    (y < gridHeight - 1 && grid[x][y + 1] == 'A') ||
                    (x > 0 && y > 0 && grid[x - 1][y - 1] == 'A') ||
                    (x > 0 && y < 13 && grid[x - 1][y + 1] == 'A') ||
                    (x < 15 && y > 0 && grid[x + 1][y - 1] == 'A') ||
                    (x < 15 && y < 13 && grid[x + 1][y + 1] == 'A')
                ) {
                    x = random.nextInt(gridWidth / 2 - 1)
                    y = random.nextInt(gridHeight)
                }

                grid[x][y] = 'A'
                grid[(gridWidth - 1) - x][y] = 'A'
                val coordinates = randomizeCoordinatesWithinBounds(x * cellSize, y * cellSize, cellSize)

                drawString(
                    "A",
                    coordinates.first,
                    coordinates.second
                )
                drawString(
                    "A",
                    ((gridWidth - 1) - x) * cellSize + (cellSize - (coordinates.first - x * cellSize) - 6),
                    coordinates.second
                )
            }


            color = Color.BLACK

            (1..314).forEach {
                var x = random.nextInt(gridWidth)
                var y = random.nextInt(gridHeight)

                while (grid[x][y] != ' ') {
                    x = random.nextInt(gridWidth)
                    y = random.nextInt(gridHeight)
                }

                val symbol = distractors[random.nextInt(distractors.size)]
                grid[x][y] = symbol

                val coordinates = randomizeCoordinatesWithinBounds(x * cellSize, y * cellSize, cellSize)

                drawString(
                    symbol.toString(),
                    coordinates.first,
                    coordinates.second
                )
            }
        }
    }

    val imageInFrame = BufferedImage((gridWidth + 1) * cellSize + 1, (gridHeight + 1) * cellSize + 1, TYPE_INT_RGB)
        .apply {
            with(graphics) {
                color = Color.WHITE
                fillRect(0, 0, (gridWidth + 1) * cellSize + 1, (gridHeight + 1) * cellSize + 1)

                color = Color.BLACK
                drawLine(0, 0, width, 0)
                drawLine(
                    cellSize * (gridWidth + 1),
                    0,
                    cellSize * (gridWidth + 1),
                    cellSize * (gridHeight + 1) * cellSize
                )
                drawLine(0, (gridHeight + 1) * cellSize, cellSize * (gridWidth + 1), cellSize * (gridHeight + 1))
                drawLine(0, 0, 0, cellSize * (gridHeight + 1))

                graphics.drawImage(image, cellSize/2, cellSize/2, null)
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
            y + it <= y + cellSize / 2 -> y + cellSize / 2
            y + it >= (y + cellSize / 1.1).toInt() -> (y + cellSize / 1.1).toInt()
            else -> y + it
        }
    }

    return newX to newY
}

private val distractors = ('B'..'Z').toList()


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