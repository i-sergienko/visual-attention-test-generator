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

const val DPI = 300//72

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
}

private fun generateSubdirectory(): Path {
    val name = (0..6).map { symbols[random.nextInt(symbols.size)] }.joinToString(separator = "")

    val newDir = File("./$name")
    newDir.mkdir()

    return newDir.toPath()
}

private fun BufferedImage.save(path: String) {
    ImageIO.write(this, "jpg", File(path))
}

private val random = Random()
private val symbols = ('a'..'z').toList()