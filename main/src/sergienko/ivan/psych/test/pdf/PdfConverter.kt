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

/**
 * Image to PDF converter.
 *
 * @author Ivan Sergienko
 * */
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