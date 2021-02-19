/*
 * Copyright 2018 Ivan Sergienko, Alexey Sergienko.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sergienko.ivan.psych.test.generator

import sergienko.ivan.psych.test.DPI
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB
import java.util.*

val random = Random()

fun Array<Array<Pair<Char, Coordinates>>>.toImage(
    gridWidth: Int,
    gridHeight: Int,
    cellSize: Int,
    drawGrid: Boolean = false,
    coloredA: Boolean = false,
    frame: Boolean = true
): BufferedImage {
    val table = this
    val image = BufferedImage(gridWidth * cellSize + 1, gridHeight * cellSize + 1, TYPE_INT_RGB).apply {
        val img = this
        with(createGraphics()) {
            background = Color.WHITE
            color = Color.WHITE

            fillRect(0, 0, gridWidth * cellSize + 1, gridHeight * cellSize + 1)

            if (drawGrid) {
                drawGrid(img, cellSize)
            }
            drawSymbols(grid = table, coloredA = coloredA)
        }
    }

    return when {
        frame -> frameImage(gridWidth, cellSize, gridHeight, image)
        else -> image
    }
}

fun fillSymbolTable(gridWidth: Int, gridHeight: Int, cellSize: Int): Array<Array<Pair<Char, Coordinates>>> {
    val symbolTable = Array(gridWidth) { Array(gridHeight) { ' ' to Coordinates(0, 0) } }

    (0 until 30).forEach {
        var x = random.nextInt(gridWidth / 2 - 1)
        var y = random.nextInt(gridHeight / 2)

        while (symbolTable[x][y].first == 'A' ||
            (x > 0 && symbolTable[x - 1][y].first == 'A') ||
            (x < gridWidth / 2 - 1 && symbolTable[x + 1][y].first == 'A') ||
            (y > 0 && symbolTable[x][y - 1].first == 'A') ||
            (y < gridHeight - 1 && symbolTable[x][y + 1].first == 'A') ||
            (x > 0 && y > 0 && symbolTable[x - 1][y - 1].first == 'A') ||
            (x > 0 && y < gridHeight - 1 && symbolTable[x - 1][y + 1].first == 'A') ||
            (x < gridWidth / 2 - 1 && y > 0 && symbolTable[x + 1][y - 1].first == 'A') ||
            (x < gridWidth / 2 - 1 && y < gridHeight - 1 && symbolTable[x + 1][y + 1].first == 'A')
        ) {
            x = random.nextInt(gridWidth / 2 - 1)
            y = random.nextInt(gridHeight)
        }

        val coordinates = randomizeCoordinatesWithinBounds(x * cellSize, y * cellSize, cellSize)
        symbolTable[x][y] = 'A' to Coordinates(coordinates.first, coordinates.second)
        symbolTable[(gridWidth - 1) - x][y] = 'A' to Coordinates(
            x = ((gridWidth - 1) - x) * cellSize + (cellSize - (coordinates.first - x * cellSize) - 20),
            y = coordinates.second
        )
    }

    (1..314).forEach {
        var x = random.nextInt(gridWidth)
        var y = random.nextInt(gridHeight)

        while (symbolTable[x][y].first != ' ') {
            x = random.nextInt(gridWidth)
            y = random.nextInt(gridHeight)
        }

        val symbol = distractors[random.nextInt(distractors.size)]
        val coordinates = randomizeCoordinatesWithinBounds(x * cellSize, y * cellSize, cellSize)
        symbolTable[x][y] = symbol to Coordinates(coordinates.first, coordinates.second)
    }

    return symbolTable
}

private fun Graphics2D.drawSymbols(
    grid: Array<Array<Pair<Char, Coordinates>>>,
    coloredA: Boolean = false
) {
    font = Font("Arial", Font.PLAIN, cmsToPixel(0.24, DPI.toDouble()).toInt())

    grid.toList().map { it.toList() }.flatMap { it }
        .forEach {
            val (symbol, coordinates) = it

            when (symbol) {
                'A' -> {
                    if (coloredA) {
                        color = Color.RED
                    }
                    drawString(symbol.toString(), coordinates.x, coordinates.y)
                }
                else -> {
                    color = Color.BLACK
                    drawString(symbol.toString(), coordinates.x, coordinates.y)
                }
            }
        }

}

private fun Graphics2D.drawGrid(
    bufferedImage: BufferedImage,
    cellSize: Int
) {
    stroke = BasicStroke(1.0f)
    color = Color.BLACK

    (0 until bufferedImage.width step cellSize).forEach {
        drawLine(it, 0, it, bufferedImage.height)
    }

    (0 until bufferedImage.height step cellSize).forEach {
        drawLine(0, it, bufferedImage.width, it)
    }
}

private fun frameImage(
    gridWidth: Int,
    cellSize: Int,
    gridHeight: Int,
    image: BufferedImage
): BufferedImage {
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

                graphics.drawImage(image, cellSize / 2, cellSize / 2, null)
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

data class Coordinates(
    val x: Int,
    val y: Int
)