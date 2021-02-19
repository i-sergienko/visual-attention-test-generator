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
 * @return value in cms
 *
 * @author Ivan Sergienko
 */
fun pixelsToCms(pixels: Double, dpi: Double): Double {
    return inchesToCms(pixels / dpi)
}

/**
 * Converts the given cm's to pixels based on the supplied DPI
 * @param cms
 * @param dpi
 * @return value in pixels
 *
 * @author Ivan Sergienko
 */
fun cmsToPixel(cms: Double, dpi: Double): Double {
    return cmToInches(cms) * dpi
}

/**
 * Converts the given cm's to inches
 * @param cms
 * @return value in inches
 *
 * @author Ivan Sergienko
 */
fun cmToInches(cms: Double): Double {
    return cms * CM_PER_INCH
}

/**
 * Converts the given inches to cm's
 * @param inch
 * @return value in cms
 *
 * @author Ivan Sergienko
 */
fun inchesToCms(inch: Double): Double {
    return inch * INCH_PER_CM
}