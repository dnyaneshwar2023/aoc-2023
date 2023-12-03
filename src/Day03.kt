import kotlin.collections.listOf
import kotlin.collections.listOf as listOf1

fun main() {

    val lines = readInput("input")

    var answer = partOne(lines)

    answer.println()
    partTwo(lines).println()
}

private fun partOne(lines: List<String>): Int {
    var answer = 0

    for (index in lines.indices) {
        var startIndex = 0

        while (startIndex < lines[index].length) {
            while (startIndex < lines[index].length && lines[index][startIndex] !in '0'..'9') {
                startIndex++;
            }
            var endIndex = startIndex

            while (endIndex < lines[index].length && lines[index][endIndex] in '0'..'9') {
                endIndex++;
            }

            if (minOf(startIndex, endIndex) >= lines[index].length) {
                break
            }

            val partNumber = lines[index].substring(startIndex, endIndex).toInt()
            val symbolState = isSymbol(
                lines.getOrNull(index - 1),
                maxOf(startIndex - 1, 0),
                minOf(endIndex, lines[index].length - 1)
            ) || isSymbol(
                lines.getOrNull(index + 1),
                maxOf(startIndex - 1, 0),
                minOf(endIndex, lines[index].length - 1)
            ) || isSymbol(
                lines[index],
                maxOf(startIndex - 1, 0),
                maxOf(startIndex - 1, 0),
            ) || isSymbol(
                lines[index],
                minOf(endIndex, lines[index].length - 1),
                minOf(endIndex, lines[index].length - 1)
            )

            if (symbolState) answer += partNumber

            startIndex = endIndex
        }
    }
    return answer
}

private fun partTwo(lines: List<String>): Int {

    val gearMap = mutableMapOf<Pair<Int, Int>, List<Int>>()

    for (index in lines.indices) {
        var startIndex = 0

        while (startIndex < lines[index].length) {
            while (startIndex < lines[index].length && lines[index][startIndex] !in '0'..'9') {
                startIndex++;
            }
            var endIndex = startIndex

            while (endIndex < lines[index].length && lines[index][endIndex] in '0'..'9') {
                endIndex++;
            }

            if (minOf(startIndex, endIndex) >= lines[index].length) {
                break
            }

            val partNumber = lines[index].substring(startIndex, endIndex).toInt()
            val symbols = getSymbols(
                lines.getOrNull(index - 1),
                maxOf(startIndex - 1, 0),
                minOf(endIndex, lines[index].length - 1),
                index - 1
            ) + getSymbols(
                lines.getOrNull(index + 1),
                maxOf(startIndex - 1, 0),
                minOf(endIndex, lines[index].length - 1),
                index + 1
            ) + getSymbols(
                lines[index],
                maxOf(startIndex - 1, 0),
                maxOf(startIndex - 1, 0),
                index
            ) + getSymbols(
                lines[index],
                minOf(endIndex, lines[index].length - 1),
                minOf(endIndex, lines[index].length - 1),
                index
            )

            symbols.forEach { pair ->
                if (gearMap.containsKey(pair)) {
                    gearMap[pair] = gearMap[pair]!! + listOf(partNumber)
                } else {
                    gearMap[pair] = listOf(partNumber)
                }
            }

            startIndex = endIndex
        }
    }

    return gearMap.values.sumOf {
        if (it.size != 2)
            0
        else
            it.reduce { acc, i -> acc * i }
    }
}


private fun isSymbol(line: String?, start: Int, end: Int): Boolean {
    if (line == null) {
        return false;
    }
    return line.substring(start, end + 1).any {
        it != '.' && it !in '1'..'9'
    }
}

private fun getSymbols(line: String?, start: Int, end: Int, lineNumber: Int): List<Pair<Int, Int>> {
    if (line == null) return listOf();

    return line.indices
        .filter { index -> line[index] == '*' && index in start..end }
        .map { index -> Pair(index, lineNumber) }
}
