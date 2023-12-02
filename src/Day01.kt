fun main() {

    val lines = readInput("input")

    // Part 1
    println(countScore(lines))

    // Part 2
    val digitList = mapOf<String, Int>(
        Pair("one", 1),
        Pair("1", 1),
        Pair("two", 2),
        Pair("2", 2),
        Pair("three", 3),
        Pair("3", 3),
        Pair("four", 4),
        Pair("4", 4),
        Pair("five", 5),
        Pair("5", 5),
        Pair("six", 6),
        Pair("6", 6),
        Pair("seven", 7),
        Pair("7", 7),
        Pair("eight", 8),
        Pair("8", 8),
        Pair("nine", 9),
        Pair("9", 9),
    )

    println(countScore(lines, digitList))
}

// Part 1
private fun countScore(lines: List<String>): Int {
    val answer = lines.sumOf { it ->
        val firstDigit = it.first { it in '0'..'9' }.digitToInt()
        val lastDigit = it.last { it in '0'..'9' }.digitToInt()
        firstDigit * 10 + lastDigit
    }
    return answer
}

// Part 2
private fun countScore(lines: List<String>, digits: Map<String, Int>): Int {

    return lines.sumOf {
        val firstDigit = it.findAnyOf(digits.keys)
        val lastDigit = it.findLastAnyOf(digits.keys)

        digits[firstDigit!!.second]!! * 10 + digits[lastDigit!!.second]!!
    }
}
