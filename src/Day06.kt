import kotlin.math.min

fun main() {

    val lines = readInput("input")

    part1(lines)
    part2(lines)
}

private fun part1(lines: List<String>) {
    val times = lines[0].split(":").last().trim().split(" ").filter { it.isNotEmpty() }.map { it.toLong() }
    val distances = lines[1].split(":").last().trim().split(" ").filter { it.isNotEmpty() }.map { it.toLong() }

    var score = 1L
    for (i in times.indices) {
        val answer = getMinimumElement(times[i], distances[i])
        val ways = (times[i] + 1 - 2 * answer)
        score *= ways
    }
    println("Part 1")
    score.println()
}

private fun part2(lines: List<String>) {
    val times = lines[0].split(":").last().trim().split(" ").filter { it.isNotEmpty() }
        .reduce { acc, it -> acc + it }
        .toLong()
    val distances =
        lines[1].split(":").last().trim().split(" ").filter { it.isNotEmpty() }
            .reduce { acc, it -> acc + it }
            .toLong()

    var score = 1L
    val answer = getMinimumElement(times, distances)
    val ways = (times + 1 - 2 * answer)
    score *= ways
    println("Part 2")
    score.println()
}

fun getMinimumElement(time: Long, distance: Long): Long {

    var low = 1L
    var high = time
    var answer = time

    while (low <= high) {
        var mid = (low + high) / 2

        if (mid * (time - mid) > distance) {
            answer = mid;
            high = mid - 1L;
        } else {
            low = mid + 1L;
        }
    }

    return minOf(answer, time - answer)
}
