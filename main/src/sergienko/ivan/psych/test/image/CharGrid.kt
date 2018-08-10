package sergienko.ivan.psych.test.image

import java.util.*

class CharGrid(
        private val numSymbols: Int = 60,
        private val numDistractors: Int = 157,
        private val characterSizePixels: Int,
        private val widthPixels: Int,
        private val heightPixels: Int
) {
    private val gridMap: MutableMap<Position, PositionedChar> = HashMap()

    init {
        val gridWidth = (widthPixels / (characterSizePixels * 1.5)).toInt()
        val gridHeight = (heightPixels / (characterSizePixels * 1.5)).toInt()
        val slotWidth = widthPixels / gridWidth
        val slotHeight = heightPixels / gridHeight

        populateDistractors(
                gridWidth,
                gridHeight,
                slotWidth,
                slotHeight
        )
    }

    private fun populateDistractors(
            gridWidth: Int,
            gridHeight: Int,
            slotWidth: Int,
            slotHeight: Int
    ) {
        val random = Random()
        val symbols = ('B'..'Z').toList()
        (1..numDistractors)
                .forEach {
                    var (position, character) = generateDistractor(
                            random = random,
                            gridWidth = gridWidth,
                            gridHeight = gridHeight,
                            slotWidth = slotWidth,
                            slotHeight = slotHeight,
                            symbols = symbols
                    )

                    while (gridMap.contains(position)) {
                        val (currentPosition, currentCharacter) = generateDistractor(
                                random = random,
                                gridWidth = gridWidth,
                                gridHeight = gridHeight,
                                slotWidth = slotWidth,
                                slotHeight = slotHeight,
                                symbols = symbols
                        )

                        position = currentPosition
                        character = currentCharacter
                    }

                    gridMap[position] = character
                }
    }

    private fun generateDistractor(
            random: Random,
            gridWidth: Int,
            gridHeight: Int,
            slotWidth: Int,
            slotHeight: Int,
            symbols: List<Char>
    ): Pair<Position, PositionedChar> {
        val leftOffset = (characterSizePixels * 1.5 / (random.nextInt(100))).toInt() + characterSizePixels
        val topOffset = (characterSizePixels * 1.5 / (random.nextInt(100))).toInt() + characterSizePixels

        val positionInGrid = Position(random.nextInt(gridWidth), random.nextInt(gridHeight))
        val charPosition = Position(
                (positionInGrid.first * slotWidth) + leftOffset,
                (positionInGrid.second * slotHeight) + topOffset
        )

        return positionInGrid to PositionedChar(charPosition, symbols[random.nextInt(symbols.size)])
    }

    fun positionedChars() = gridMap.values.toList()
}

typealias Position = Pair<Int, Int>
typealias PositionedChar = Pair<Position, Char>