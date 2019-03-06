package sergienko.ivan.psych.test

import sergienko.ivan.psych.test.generator.cmsToPixel
import sergienko.ivan.psych.test.generator.generate
import java.awt.Dimension
import java.awt.Graphics
import java.awt.image.BufferedImage
import java.awt.image.ImageObserver
import javax.swing.JFrame
import javax.swing.JFrame.EXIT_ON_CLOSE
import javax.swing.SwingUtilities.invokeLater

const val WIDTH = 12
const val HEIGHT = 10

const val DPI = 100//72

fun main(args: Array<String>) {
    invokeLater { createAndShowGUI() }
}

private fun createAndShowGUI() {
    val width = cmsToPixel(WIDTH.toDouble(), DPI.toDouble()).toInt() - 7
    val height = cmsToPixel(HEIGHT.toDouble(), DPI.toDouble()).toInt() + 14

    val cellSize = cmsToPixel(0.75, DPI.toDouble()).toInt()

    val frame = ImageFrame(
        width = width,
        height = height,
        image = generate(width, height, cellSize)
    )

    //Display the window.
    frame.defaultCloseOperation = EXIT_ON_CLOSE
    frame.setSize(width, height)
    frame.repaint()
    frame.isVisible = true
}

class ImageFrame(
    width: Int,
    height: Int,
    private val image: BufferedImage
) : JFrame() {
    init {
        title = "Psych test"
        minimumSize = Dimension((image.width * 1.5).toInt(), (image.height * 1.5).toInt())
        maximumSize = minimumSize
    }

    override fun paint(g: Graphics) {
        g.drawImage(image, image.width / 4, image.height/4, null)
    }
}