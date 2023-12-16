fun main() {

    val lines = readInput("input")

    val inputs = lines[0].split(',')

    val answer = inputs.sumOf { hash(it) }

    answer.println()

    part2Day15(lines)

}

fun hash(line: String): Int {
    fun loop(line: String, index: Int, hash: Int): Int {
        if (index == line.length) {
            return hash
        }
        return loop(line, index + 1, (((hash + line[index].code)) * 17 % 256))
    }
    return loop(line, 0, 0)
}


fun part2Day15(lines: List<String>) {

    val boxMap = mutableMapOf<Int, MutableList<Pair<String, Int>>>()

    lines[0].split(',').forEach {
        if (it.contains('=')) {
            val line = it.split("=").first()
            val focalLength = it.split("=").last().toInt()
            val boxNumber = hash(line)

            if (!boxMap.containsKey(boxNumber)) {
                boxMap[boxNumber] = mutableListOf()
            }

            val boxContents = boxMap[boxNumber]!!
            val lineIndex = boxContents.indexOfFirst { it.first == line }
            if (lineIndex != -1) {
                boxContents[lineIndex] = Pair(line, focalLength)
            } else {
                boxContents.add(Pair(line, focalLength))
            }
        } else {
            val line = it.split("-").first()
            val boxNumber = hash(line)

            if (!boxMap.containsKey(boxNumber)) {
                boxMap[boxNumber] = mutableListOf()
            }

            boxMap[boxNumber]!!.removeIf { label -> label.first == line }
        }
    }

    var answer = 0

    boxMap.keys.forEach {
        for (i in boxMap[it]!!.indices) {
            answer += (it + 1) * boxMap[it]!![i].second * (i + 1)
        }
    }

    answer.println()

}
