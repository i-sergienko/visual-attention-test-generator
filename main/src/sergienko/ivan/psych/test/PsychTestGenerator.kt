package sergienko.ivan.psych.test

import sergienko.ivan.psych.test.image.generateImage
import sergienko.ivan.psych.test.pdf.A4_HEIGHT_PIXELS
import sergienko.ivan.psych.test.pdf.toPDF

fun main(args: Array<String>) {
    generateImage().toPDF(
            path = "C:\\Users\\Ivan\\Pictures\\pdf\\some.pdf",
            marginTop = (A4_HEIGHT_PIXELS / 10f)
    )
}