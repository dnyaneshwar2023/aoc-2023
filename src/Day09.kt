fun main() {

    val lines = readInput("input")
        .map { it.split(" ").map { it.toInt() } }

    val answer = lines.sumOf { line ->
        calculateSteps(line.toMutableList())
    }

    answer.println()

    val answer2 = lines.sumOf { line ->
        calculateStepsForPrevious(line.toMutableList())
    }

    answer2.println()
}

fun calculateSteps(line: MutableList<Int>): Int {

    if (line.toSet().size == 1) {
        return line.last()
    }

    val nextLine = mutableListOf<Int>()

    for (i in 1 until line.size) {
        nextLine.add(line[i] - line[i - 1])
    }
    return line.last() + calculateSteps(nextLine)
}

fun calculateStepsForPrevious(line: MutableList<Int>): Int {

    if (line.toSet().size == 1) {
        return line.first()
    }

    val nextLine = mutableListOf<Int>()

    for (i in 0 until line.size - 1) {
        nextLine.add(line[i] - line[i + 1])
    }
    return line.first() + calculateStepsForPrevious(nextLine)
}
