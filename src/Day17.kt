fun main() {

    val lines = readInput("input")

    val config = listOf(
        Triple(0, 0, BeamDirection.Right), Triple(0, 0, BeamDirection.Down)
    )

    val answer = config.minOf {
        getMinimumHeatPath(it, lines, mutableMapOf())
    }
    answer.println()
}

fun getMinimumHeatPath(
    beam: Triple<Int, Int, BeamDirection>,
    grid: List<String>,
    memory: MutableMap<Triple<Int, Int, BeamDirection>, Int>
): Int {
    val x = beam.first
    val y = beam.second

    if (!isWithinGrid(beam, grid)) {
        return Int.MAX_VALUE
    }

    if (x == grid.indices.last && y == grid[0].indices.last) {
        return grid[x][y].digitToInt()
    }
    if (memory.containsKey(beam)) {
        return memory[beam]!!
    }

    beam.println()


    var answer = Int.MAX_VALUE
    var acc = 0

    IntRange(0, 2).forEach {
        val nextBeam = beam.third.getNextNth(it, x, y)
        if (isWithinGrid(nextBeam, grid)) {
            acc += grid[nextBeam.first][nextBeam.second].digitToInt()
            answer = minOf(
                answer, acc +
                        nextBeam.third.getPerpendicularVertices(nextBeam.first, nextBeam.second)
                            .minOf { b -> getMinimumHeatPath(b, grid, memory) }

            )
        }
    }

    memory.put(beam, answer)
    return answer
}
