package sergienko.ivan.psych.test.pdf

import com.itextpdf.text.Document
import com.itextpdf.text.Image
import com.itextpdf.text.RectangleReadOnly
import com.itextpdf.text.pdf.PdfWriter
import sergienko.ivan.psych.test.DPI
import sergienko.ivan.psych.test.generator.cmsToPixel
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import javax.imageio.ImageIO

val A4_WIDTH_PIXELS = cmsToPixel(21.0, DPI.toDouble())
val A4_HEIGHT_PIXELS = cmsToPixel(29.7, DPI.toDouble())

fun BufferedImage.toPDF(
        path: String
) {
    val imageOffsetLeft = (A4_WIDTH_PIXELS.toFloat() / 2) - (width.toFloat() / 2)
    val document = Document(
            RectangleReadOnly(A4_WIDTH_PIXELS.toFloat(), A4_HEIGHT_PIXELS.toFloat()),
            imageOffsetLeft,
            0f,
            (A4_HEIGHT_PIXELS / 10.0).toFloat(),
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