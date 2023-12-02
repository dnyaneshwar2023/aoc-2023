fun main() {

    val lines = readInput("input")

    val availableColours = mapOf(
        Pair("blue", 14),
        Pair("red", 12),
        Pair("green", 13),
    )


    // Part 1
    val answer = lines.sumOf {
        line ->
        val gameNumber = line.split(":").first()
            .split(' ').last().toInt()

        val colourMapper = colourMapper(line)

        if (colourMapper.any { it ->
                availableColours.getOrDefault(it.key, 0) < it.value
            }) {
            0
        } else {
            gameNumber
        }
    }
    answer.println()


    // part 2

    val answer2 = lines.sumOf { line ->
        val gameNumber = line.split(":").first()
            .split(' ').last().toInt()

        val colourMapper = colourMapper(line)

        colourMapper.values.reduce { current, it -> current * it }
    }
    answer2.println()
}

private fun colourMapper(line: String): MutableMap<String, Int> {
    val gameSets = line.split(":").last()
        .split(";")

    val colourMapper = mutableMapOf<String, Int>()

    gameSets.forEach { gameSet ->
        val colorsRevealed = gameSet.split(",")
        colorsRevealed.forEach {
            val count = it.trim().split(' ').first().toInt()
            val colour = it.trim().split(' ').last()
            if (!colourMapper.containsKey(colour)) {
                colourMapper[colour] = count
            } else {
                colourMapper[colour] = maxOf(count, colourMapper[colour]!!)
            }
        }
    }
    return colourMapper
}
