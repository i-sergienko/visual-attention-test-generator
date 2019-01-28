package sergienko.ivan.psych.test.image

import java.awt.Color
import java.awt.Font
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB

//Required image width on PDF - 12x10 cm

const val DOTS_PER_MM = (595 * 4) / 210

private const val IMAGE_WIDTH_MM = 120
private const val IMAGE_HEIGHT_MM = 100

private const val CHARACTER_HEIGHT = 3

private const val IMAGE_WIDTH_PIXELS = DOTS_PER_MM * IMAGE_WIDTH_MM
private const val IMAGE_HEIGHT_PIXELS = DOTS_PER_MM * IMAGE_HEIGHT_MM
private const val CHAR_SIZE_PIXELS = DOTS_PER_MM * CHARACTER_HEIGHT

fun generateImage(borderWidth: Int = 3): BufferedImage =
        BufferedImage(IMAGE_WIDTH_PIXELS + borderWidth * 2, IMAGE_HEIGHT_PIXELS + borderWidth * 2, TYPE_INT_RGB)
                .apply {
                    with(createGraphics()) {
                        background = Color.WHITE
                        color = Color.WHITE
                        fillRect(borderWidth, borderWidth, IMAGE_WIDTH_PIXELS, IMAGE_HEIGHT_PIXELS)
                        color = Color.BLACK

                        val grid = CharGrid(
                                characterSizePixels = CHAR_SIZE_PIXELS,
                                widthPixels = IMAGE_WIDTH_PIXELS,
                                heightPixels = IMAGE_HEIGHT_PIXELS,
                                numDistractors = 217
                        )

                        val fontSize = (CHAR_SIZE_PIXELS.toFloat() * 0.6).toInt()
                        font = Font("Arial Black", Font.PLAIN, fontSize)

                        grid.positionedChars().forEach {
                            val (position, char) = it
                            val (x, y) = position
                            drawString(char.toString(), x + borderWidth, y + CHAR_SIZE_PIXELS)
                        }
                    }
                }