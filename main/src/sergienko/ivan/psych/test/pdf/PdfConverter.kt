package sergienko.ivan.psych.test.pdf

import com.itextpdf.text.Document
import com.itextpdf.text.Image
import com.itextpdf.text.RectangleReadOnly
import com.itextpdf.text.pdf.PdfWriter
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import javax.imageio.ImageIO

const val DOTS_PER_MM = (595 * 4) / 210
const val A4_WIDTH_PIXELS = DOTS_PER_MM * 210
const val A4_HEIGHT_PIXELS = DOTS_PER_MM * 297

fun BufferedImage.toPDF(
        path: String
) {
    val imageOffsetLeft = (A4_WIDTH_PIXELS.toFloat() / 2) - (width.toFloat() / 2)
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