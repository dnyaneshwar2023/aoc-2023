fun main() {

    var lines = readInput("input")

    val games = mutableListOf<List<String>>()

    while (lines.indexOfFirst { it == "" } != -1) {
        val index = lines.indexOfFirst { it == "" }
        games.add(lines.slice(0..<index))
        if (index + 1 < lines.size) {
            lines = lines.slice(index + 1..<lines.size)
        }
    }

    if (lines.isNotEmpty()) {
        games.add(lines)
    }

    var answer = part1(games)
    answer.println()

    answer = part2(games)
    answer.println()

}

private fun part2(games: MutableList<List<String>>) = games.sumOf {

    val rows = it
    val columns = populateColumns(it)
    var currentAns = 0
    for (i in 0..<rows.size - 1) {
        currentAns += 100 * getMirrorLengthWithSmuge(rows, i)
        if (currentAns != 0) {
            break
        }
    }

    if (currentAns == 0) {
        for (i in 0..<columns.size - 1) {
            currentAns += getMirrorLengthWithSmuge(columns, i)
            if (currentAns != 0) {
                break
            }
        }
    }


    currentAns

}


private fun part1(games: MutableList<List<String>>) = games.sumOf {

    val rows = it
    val columns = populateColumns(it)
    var currentAns = 0
    for (i in 0..<rows.size - 1) {
        currentAns += 100 * getMirrorLength(rows, i)
        if (currentAns != 0) {
            break
        }
    }

    for (i in 0..<columns.size - 1) {
        currentAns += getMirrorLength(columns, i)
        if (currentAns != 0) {
            break
        }
    }

    currentAns

}

fun getMirrorLength(line: List<String>, index: Int): Int {
    var start = index
    var end = index + 1

    while (start >= 0 && end < line.size && (line[start] == line[end])) {
        start--
        end++
    }

    return if ((start == -1 || end == line.size) && start != index) {
        index + 1
    } else {
        0
    }
}

fun getMirrorLengthWithSmuge(line: List<String>, index: Int): Int {
    var start = index
    var end = index + 1
    var smugeAvailable = 1
    while (start >= 0 && end < line.size) {
        if (line[start] == line[end]) {
            start--
            end++
        } else if (smugeAvailable > 0) {
            if (IntRange(0, line[start].length - 1).count { line[start][it] != line[end][it] } == 1) {
                start--
                end++
                smugeAvailable = 0
            } else {
                break
            }
        } else {
            break
        }
    }

    return if ((start == -1 || end == line.size) && start != index) {
        index + 1
    } else {
        0
    }
}

fun populateColumns(list: List<String>): List<String> {
    val columns = mutableListOf<String>()
    for (i in 0 until list[0].length) {
        var col = ""

        for (j in list.indices) {
            col += list[j][i]
        }

        columns.add(col)
    }
    return columns.toList()
}
