package sergienko.ivan.psych.test.generator

// The number of CMs per Inch
val CM_PER_INCH = 0.393700787
// The number of Inches per CMs
val INCH_PER_CM = 2.545
// The number of Inches per mm's
val INCH_PER_MM = 25.45

/**
 * Converts the given pixels to cm's based on the supplied DPI
 * @param pixels
 * @param dpi
 * @return
 */
fun pixelsToCms(pixels: Double, dpi: Double): Double {
    return inchesToCms(pixels / dpi)
}

/**
 * Converts the given cm's to pixels based on the supplied DPI
 * @param cms
 * @param dpi
 * @return
 */
fun cmsToPixel(cms: Double, dpi: Double): Double {
    return cmToInches(cms) * dpi
}

/**
 * Converts the given cm's to inches
 * @param cms
 * @return
 */
fun cmToInches(cms: Double): Double {
    return cms * CM_PER_INCH
}

/**
 * Converts the given inches to cm's
 * @param inch
 * @return
 */
fun inchesToCms(inch: Double): Double {
    return inch * INCH_PER_CM
}