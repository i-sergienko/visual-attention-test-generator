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
        val gridWidth = (widthPixels / (characterSizePixels)) / 2 - 4
        val gridHeight = (heightPixels / (characterSizePixels)) / 2
        val slotWidth = widthPixels / gridWidth

        println("Grid width: $gridWidth, grid height: $gridHeight")

        populateSymbols(gridWidth, gridHeight, slotWidth)
//        populateDistractors(
//                gridWidth,
//                gridHeight,
//                slotWidth
//        )
    }

    private fun populateSymbols(
            gridWidth: Int,
            gridHeight: Int,
            slotWidth: Int
    ) {
        val random = Random()
        (1..(numSymbols / 2))
                .forEach {
                    var (left, right) = generateSymbols(
                            random,
                            gridWidth,
                            gridHeight,
                            slotWidth
                    )

                    while (gridMap.contains(left.first) || gridMap.contains(left.second)) {
                        val (currentLeft, currentRight) =  generateSymbols(
                        random,
                        gridWidth,
                        gridHeight,
                        slotWidth
                        )

                        left = currentLeft
                        right = currentRight
                    }

                    gridMap[left.first] = right.first
                    gridMap[left.second] = right.second
                }
    }

    private fun populateDistractors(
            gridWidth: Int,
            gridHeight: Int,
            slotWidth: Int
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
                            symbols = symbols
                    )

                    while (gridMap.contains(position)) {
                        val (currentPosition, currentCharacter) = generateDistractor(
                                random = random,
                                gridWidth = gridWidth,
                                gridHeight = gridHeight,
                                slotWidth = slotWidth,
                                symbols = symbols
                        )

                        position = currentPosition
                        character = currentCharacter
                    }

                    gridMap[position] = character
                }
    }

    private fun generateSymbols(
            random: Random,
            gridWidth: Int,
            gridHeight: Int,
            slotWidth: Int
    ): Pair<Pair<Position, Position>, Pair<PositionedChar, PositionedChar>> {
        val positionInGridLeft = Position(random.nextInt(gridWidth / 2 + 1) - 1, random.nextInt(gridHeight))
        val positionInGridRight = positionInGridLeft.mirrored(gridWidth)
        val leftDivisor = (random.nextInt(100)).toDouble() / 100.0
        val leftOffset = (characterSizePixels * leftDivisor).toInt()
        val topDivisor = (random.nextInt(100)).toDouble() / 100.0
        val topOffset = (characterSizePixels * topDivisor).toInt()
        val charPositionLeft = Position(
                (positionInGridLeft.first * slotWidth) + leftOffset,
                (positionInGridLeft.second * slotWidth) + topOffset
        )
        val charPositionRight = Position(
                (positionInGridRight.first * slotWidth) - leftOffset,
                (positionInGridRight.second * slotWidth) + topOffset
        )

        return Pair(positionInGridLeft, positionInGridRight) to
                Pair(PositionedChar(charPositionLeft, 'A'), PositionedChar(charPositionRight, 'A'))
    }

    private fun Position.mirrored(gridWidth: Int) = Position(first = gridWidth - first, second = second)

    private fun generateDistractor(
            random: Random,
            gridWidth: Int,
            gridHeight: Int,
            slotWidth: Int,
            symbols: List<Char>
    ): Pair<Position, PositionedChar> {
        val positionInGrid = Position(random.nextInt(gridWidth) + 1, random.nextInt(gridHeight) + 1)
        val leftDivisor = (random.nextInt(100)).toDouble() / 100.0
        val leftOffset = (characterSizePixels * leftDivisor).toInt()
        val topDivisor = (random.nextInt(100)).toDouble() / 100.0
        val topOffset = (characterSizePixels * topDivisor).toInt()
        val charPosition = Position(
                (positionInGrid.first * slotWidth) + leftOffset,
                (positionInGrid.second * slotWidth) + topOffset
        )

        return positionInGrid to PositionedChar(charPosition, symbols[random.nextInt(symbols.size)])
    }

    fun positionedChars() = gridMap.values.toList()
}

typealias Position = Pair<Int, Int>
typealias PositionedChar = Pair<Position, Char>