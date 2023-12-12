fun main() {

    val lines = readInput("input")

    val linesWithRules = lines.map {
        Pair(it.split(" ").first(),
            it.split(" ").last()
                .split(",").map { num -> num.toLong() }
        )
    }
    val answer = linesWithRules.sumOf {
        getCount(it.first, it.second, 0, 0, 0, mutableMapOf())
    }

    answer.println()


    val answer2 = linesWithRules.sumOf {
        getCount(
            (it.first + "?").repeat(5).dropLast(1),
            it.second + it.second + it.second + it.second + it.second,
            0,
            0,
            0,
            mutableMapOf()
        )
    }

    answer2.println()
}

fun getCount(
    line: String,
    counts: List<Long>,
    pos: Int,
    currentCount: Long,
    countPos: Int,
    memory: MutableMap<Triple<Int, Long, Int>, Long>
): Long {
    val key = Triple(pos, currentCount, countPos)
    if (memory.containsKey(key)) {
        return memory[key]!!
    }
    return if (pos == line.length) {
        if (counts.size == countPos || (countPos == counts.size - 1 && counts[countPos] == currentCount)) 1 else 0
    } else {
        val ret: Long
        ret = when {
            line[pos] == '#' -> getCount(line, counts, pos + 1, currentCount + 1, countPos, memory)
            line[pos] == '.' || countPos == counts.size -> {
                if (countPos < counts.size && currentCount == counts[countPos]) {
                    getCount(line, counts, pos + 1, 0, countPos + 1, memory)
                } else if (currentCount == 0L) {
                    getCount(line, counts, pos + 1, 0, countPos, memory)
                } else {
                    0
                }
            }

            else -> {
                val hashCount = getCount(line, counts, pos + 1, currentCount + 1, countPos, memory)
                val dotCount: Long
                dotCount = if (currentCount == counts[countPos]) {
                    getCount(line, counts, pos + 1, 0, countPos + 1, memory)
                } else if (currentCount == 0L) {
                    getCount(line, counts, pos + 1, 0, countPos, memory)
                } else {
                    0
                }
                hashCount + dotCount
            }
        }
        memory[key] = ret
        ret
    }
}
