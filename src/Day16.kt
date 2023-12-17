import java.util.LinkedList

sealed class BeamDirection {
    abstract fun getNextIncrements(element: String): List<Triple<Int, Int, BeamDirection>>

    data object Right : BeamDirection() {
        override fun getNextIncrements(element: String): List<Triple<Int, Int, BeamDirection>> {
            return when (element) {
                "/" -> listOf(Triple(-1, 0, Up))
                "\\" -> listOf(Triple(1, 0, Down))
                "|" -> listOf(Triple(-1, 0, Up), Triple(1, 0, Down))
                "-" -> listOf(Triple(0, 1, Right), Triple(0, -1, Left))
                else -> listOf(Triple(0, 1, Right))
            }
        }

    }

    data object Left : BeamDirection() {
        override fun getNextIncrements(element: String): List<Triple<Int, Int, BeamDirection>> {
            return when (element) {
                "/" -> listOf(Triple(1, 0, Down))
                "\\" -> listOf(Triple(-1, 0, Up))
                "|" -> listOf(Triple(-1, 0, Up), Triple(1, 0, Down))
                "-" -> listOf(Triple(0, 1, Right), Triple(0, -1, Left))
                else -> listOf(Triple(0, -1, Left))
            }
        }

    }

    data object Up : BeamDirection() {
        override fun getNextIncrements(element: String): List<Triple<Int, Int, BeamDirection>> {
            return when (element) {
                "/" -> listOf(Triple(0, 1, Right))
                "\\" -> listOf(Triple(0, -1, Left))
                "|" -> listOf(Triple(-1, 0, Up), Triple(1, 0, Down))
                "-" -> listOf(Triple(0, 1, Right), Triple(0, -1, Left))
                else -> listOf(Triple(-1, 0, Up))
            }
        }
    }

    data object Down : BeamDirection() {
        override fun getNextIncrements(element: String): List<Triple<Int, Int, BeamDirection>> {
            return when (element) {
                "/" -> listOf(Triple(0, -1, Left))
                "\\" -> listOf(Triple(0, 1, Right))
                "|" -> listOf(Triple(-1, 0, Up), Triple(1, 0, Down))
                "-" -> listOf(Triple(0, 1, Right), Triple(0, -1, Left))
                else -> listOf(Triple(1, 0, Down))
            }
        }

    }

}


fun main() {

    val lines = readInput("input")

    val initialBeam = Triple(0, 0, BeamDirection.Right)

    val answer = runBeamConfiguration(initialBeam, lines)
    answer.println()

    // Part 2
    val configs = getAllConfigs(lines)
    configs.maxOf { runBeamConfiguration(it, lines) }.println()
}

fun getAllConfigs(grid: List<String>): List<Triple<Int, Int, BeamDirection>> {
    val configs = mutableListOf<Triple<Int, Int, BeamDirection>>()

    for (i in grid.indices) {
        for (j in grid[0].indices) {
            // First Row
            if (i == grid.indices.first) {
                when (j) {
                    grid[0].indices.first -> {
                        configs.add(Triple(i, j, BeamDirection.Down))
                        configs.add(Triple(i, j, BeamDirection.Right))
                    }

                    grid[0].indices.last -> {
                        configs.add(Triple(i, j, BeamDirection.Down))
                        configs.add(Triple(i, j, BeamDirection.Left))
                    }

                    else -> {
                        configs.add(Triple(i, j, BeamDirection.Down))
                    }
                }
            }

            // Last Row
            if (i == grid.indices.last) {
                when (j) {
                    grid[0].indices.first -> {
                        configs.add(Triple(i, j, BeamDirection.Up))
                        configs.add(Triple(i, j, BeamDirection.Right))
                    }

                    grid[0].indices.last -> {
                        configs.add(Triple(i, j, BeamDirection.Up))
                        configs.add(Triple(i, j, BeamDirection.Left))
                    }

                    else -> {
                        configs.add(Triple(i, j, BeamDirection.Up))
                    }
                }
            }

            // First column
            if (j == grid[0].indices.first) {
                configs.add(Triple(i, j, BeamDirection.Right))
            }

            // Last column
            if (j == grid[0].indices.last) {
                configs.add(Triple(i, j, BeamDirection.Left))
            }

        }
    }

    return configs
}

fun runBeamConfiguration(initialBeam: Triple<Int, Int, BeamDirection>, lines: List<String>): Int {

    val visitedRowsWithDirection = mutableSetOf<Triple<Int, Int, BeamDirection>>()

    val queue = LinkedList<Triple<Int, Int, BeamDirection>>()

    queue.add(initialBeam)

    while (queue.isNotEmpty()) {
        val current = queue.remove()

        if (visitedRowsWithDirection.contains(current)) {
            continue
        }

        visitedRowsWithDirection.add(current)

        val nextDirections = current.third.getNextIncrements(lines[current.first][current.second].toString())
        nextDirections.forEach {
            val nextVertex = Triple(current.first + it.first, current.second + it.second, it.third)
            if (isWithinGrid(nextVertex, lines)) {
                queue.add(nextVertex)
            }
        }
    }

    return visitedRowsWithDirection.map { Pair(it.first, it.second) }.toSet().size
}

fun isWithinGrid(vertex: Triple<Int, Int, BeamDirection>, grid: List<String>): Boolean {
    return (vertex.first in grid.indices && vertex.second in grid[0].indices)
}
