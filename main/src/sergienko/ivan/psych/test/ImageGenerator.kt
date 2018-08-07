package sergienko.ivan.psych.test

import com.itextpdf.text.Document
import com.itextpdf.text.Image
import com.itextpdf.text.PageSize
import com.itextpdf.text.RectangleReadOnly
import com.itextpdf.text.pdf.PdfWriter
import java.awt.Color
import java.awt.Font
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB
import java.io.FileOutputStream
import javax.imageio.ImageIO
import java.io.ByteArrayOutputStream


//A4 at 600 PPI - 4960x7016, size in mm - 210x297
//Required image width on PDF - 12x10 cm

private const val A4_WIDTH_MILLIMETERS = 210
private const val A4_HEIGHT_MILLIMETERS = 297
private const val A4_WIDTH_PIXELS = 595 * 4
private const val A4_HEIGHT_PIXELS = 842 * 4

private const val IMAGE_WIDTH_MM = 120
private const val IMAGE_HEIGHT_MM = 100

private const val IMAGE_WIDTH_PIXELS = A4_WIDTH_PIXELS / A4_WIDTH_MILLIMETERS * IMAGE_WIDTH_MM
private const val IMAGE_HEIGHT_PIXELS = A4_HEIGHT_PIXELS / A4_HEIGHT_MILLIMETERS * IMAGE_HEIGHT_MM

fun generateImage(): BufferedImage = BufferedImage(IMAGE_WIDTH_PIXELS, IMAGE_HEIGHT_PIXELS, TYPE_INT_RGB)
        .apply {
            with(graphics) {
                color = Color.BLACK
                drawRect(0, 0, IMAGE_WIDTH_PIXELS, IMAGE_HEIGHT_PIXELS)
                color = Color.WHITE
                font = Font("Arial Black", Font.PLAIN, 9)
                drawString("Some text", 10, 12)
            }
        }

fun BufferedImage.toPDF(path: String) {
    val imageOffsetLeft = (A4_WIDTH_PIXELS.toFloat() / 2) - (IMAGE_WIDTH_PIXELS.toFloat() / 2)
    val document = Document(
            RectangleReadOnly(A4_WIDTH_PIXELS.toFloat(), A4_HEIGHT_PIXELS.toFloat()),
            imageOffsetLeft,
            0f,
            (A4_HEIGHT_PIXELS / 10f),
            0f
    )
    PdfWriter.getInstance(document, FileOutputStream(path))

    document.open()
    val baos = ByteArrayOutputStream()
    ImageIO.write(this, "png", baos)
    val iTextImage = Image.getInstance(baos.toByteArray())
    document.add(iTextImage)
    document.close()
}

fun main(args: Array<String>) {
    generateImage().toPDF("C:\\Users\\Ivan\\Pictures\\pdf\\some.pdf")
}