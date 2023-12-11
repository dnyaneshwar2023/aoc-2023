import kotlin.math.abs

fun main() {

    val lines = readInput("input")

    val matrix = lines.map {
        it.split("").filter { it.isNotEmpty() }
    }

    val galaxies = mutableListOf<Pair<Int, Int>>()

    for (i in matrix.indices) {
        for (j in matrix[0].indices) {
            if (matrix[i][j] == "#") {
                galaxies.add(Pair(i, j))
            }
        }
    }

    val emptyRows = mutableSetOf<Int>()
    val emptyCols = mutableSetOf<Int>()

    for (i in matrix.indices) {
        for (j in matrix[0].indices) {
            if (checkIfEmpty(matrix[i])) {
                emptyRows.add(i)
            }
        }
    }

    for (i in matrix[0].indices) {
        val column = mutableListOf<String>()
        for (j in matrix.indices) {
            column.add(matrix[j][i])
        }

        if (checkIfEmpty(column)) {
            emptyCols.add(i)
        }
    }

    var answer = 0L

    for (i in 0 until galaxies.size) {
        for (j in i + 1 until galaxies.size) {

            val rowDifference = abs(galaxies[i].first - galaxies[j].first)
            val colsDifference = abs(galaxies[i].second - galaxies[j].second)

            var rowExpansion = emptyRows.count {
                it in
                        minOf(galaxies[i].first, galaxies[j].first)..maxOf(
                    galaxies[i].first,
                    galaxies[j].first
                )
            }

            var colExpansion = emptyCols.count {
                it in
                        minOf(galaxies[i].second, galaxies[j].second)..maxOf(
                    galaxies[i].second,
                    galaxies[j].second
                )
            }
           // println("${galaxies[i]} ${galaxies[j]} ${rowDifference + colsDifference + rowExpansion + colExpansion}")
            answer += rowDifference + colsDifference + rowExpansion * (1000000 - 1) + colExpansion * (1000000 - 1)
        }
    }

    answer.println()

}

fun checkIfEmpty(list: List<String>): Boolean {
    return list.count { it == "." } == list.size
}
