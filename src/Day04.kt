import kotlin.math.pow

fun main() {

    val lines = readInput("input")

    // part 1
    val answer = lines.sumOf {
        val matchingNumbers = getMatchingNumbers(it)
        if (matchingNumbers == 0) {
            0
        } else
            2.0.pow((matchingNumbers - 1).toDouble()).toInt()
    }

   answer.println()

    // part 2
    val cardCopies = mutableMapOf<Int, Int>()
    repeat(lines.size) { cardCopies[it + 1] = 1 }
    val score = lines.sumOf {
        val gameNumber = it.split(":").first()
            .trim()
            .split(" ")
            .last()
            .toInt()
        val matchingNumbers = getMatchingNumbers(it)

        cardCopies.keys.filter { it in gameNumber + 1..gameNumber + matchingNumbers }.forEach { key ->
            cardCopies[key] = cardCopies[key]!! + cardCopies[gameNumber]!!
        }

        cardCopies[gameNumber]!!.toInt()
    }
    score.println()
}

private fun getMatchingNumbers(it: String): Int {
    val winners = it.split(":").last()
        .trim()
        .split("|").first()
        .trim()
        .split(" ")
        .filter { it.trim().isNotEmpty() }
    val currentNumbers = it.split(":").last()
        .trim()
        .split("|").last()
        .trim()
        .split(" ")
        .filter { it.trim().isNotEmpty() }
    return currentNumbers.count { number -> winners.contains(number) }
}
