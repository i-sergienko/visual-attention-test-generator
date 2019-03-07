package sergienko.ivan.psych.test

import sergienko.ivan.psych.test.generator.cmsToPixel
import sergienko.ivan.psych.test.generator.generate
import sergienko.ivan.psych.test.pdf.A4_HEIGHT_PIXELS
import sergienko.ivan.psych.test.pdf.toPDF
import java.awt.Dimension
import java.awt.Graphics
import java.awt.image.BufferedImage
import java.awt.image.ImageObserver
import javax.swing.JFrame
import javax.swing.JFrame.EXIT_ON_CLOSE
import javax.swing.SwingUtilities.invokeLater

const val DPI = 300//72

fun main(args: Array<String>) {
    val gridWidth = 24
    val gridHeight = 21
    val cellSize = cmsToPixel(0.5, DPI.toDouble()).toInt()

    generate(gridWidth, gridHeight, cellSize).toPDF(
        path = "C:\\Users\\Ivan\\Pictures\\pdf\\some.pdf"
    )
//    invokeLater { createAndShowGUI() }
}

private fun createAndShowGUI() {
    val gridWidth = 24
    val gridHeight = 21
    val cellSize = cmsToPixel(0.5, DPI.toDouble()).toInt()

    val frame = ImageFrame(image = generate(gridWidth, gridHeight, cellSize))

    //Display the window.
    frame.defaultCloseOperation = EXIT_ON_CLOSE
    frame.setSize(gridWidth*cellSize, gridHeight*cellSize)
    frame.repaint()
    frame.isVisible = true
}

class ImageFrame(
    private val image: BufferedImage
) : JFrame() {
    init {
        title = "Psych test"
        minimumSize = Dimension((image.width * 1.5).toInt(), (image.height * 1.5).toInt())
        maximumSize = minimumSize
    }

    override fun paint(g: Graphics) {
        g.drawImage(image, width/2 - image.width / 2, height/2-image.height/2, null)
    }
}