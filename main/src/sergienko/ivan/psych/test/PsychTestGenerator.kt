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

package sergienko.ivan.psych.test

import sergienko.ivan.psych.test.generator.cmsToPixel
import sergienko.ivan.psych.test.generator.fillSymbolTable
import sergienko.ivan.psych.test.generator.toImage
import sergienko.ivan.psych.test.pdf.toPDF
import java.awt.image.BufferedImage
import java.io.File
import java.nio.file.Path
import java.util.*
import javax.imageio.ImageIO

const val DPI = 300

/**
 * Test image generator entry point.
 *
 * @author Ivan Sergienko
 * */
fun main(args: Array<String>) {
    val gridWidth = 24
    val gridHeight = 21
    val cellSize = cmsToPixel(0.5, DPI.toDouble()).toInt()


    val symbolTable = fillSymbolTable(gridWidth, gridHeight, cellSize)
    val directory = generateSubdirectory()

    val imageColored =
            symbolTable.toImage(
                    gridWidth = gridWidth,
                    gridHeight = gridHeight,
                    cellSize = cellSize,
                    drawGrid = true,
                    coloredA = true
            )
    imageColored.save("${directory.toAbsolutePath()}/colored.jpg")

    val imageNotColored =
            symbolTable.toImage(
                    gridWidth = gridWidth,
                    gridHeight = gridHeight,
                    cellSize = cellSize
            )

    imageNotColored.toPDF(
            path = "${directory.toAbsolutePath()}/print.pdf"
    )
    imageNotColored.save("${directory.toAbsolutePath()}/noColor.jpg")

    println("Generated images in directory: ${directory.toAbsolutePath()}")
}

private fun generateSubdirectory(): Path {
    val name = (0..6).map { symbols[random.nextInt(symbols.size)] }.joinToString(separator = "")

    val newDir = File(name)
    val created = newDir.mkdir()
    println("Created directory ${newDir.toPath().toAbsolutePath()}: $created")

    return newDir.toPath()
}

private fun BufferedImage.save(path: String) {
    val saved = ImageIO.write(this, "jpg", File(path))
    println("Saved image $path: $saved")
}

private val random = Random()
private val symbols = ('a'..'z').toList()