fun main() {
    val lines = readInput("input").filter { it.trim().isNotEmpty() }
    val instructions = lines[0];

    val locationMap = mutableMapOf<String, Pair<String, String>>()

    lines.slice(1..<lines.size).forEach { it ->
        val key = it.split("=").first().trim()
        val value = it.split("=").last().trim('(', ')', ' ')
            .split(',')
            .let {
                Pair(it[0].trim(), it[1].trim())
            }
        locationMap.put(key, value)
    }

//    var currentLocation = "AAA"
//    var steps = 0
//    var instructionIndex = 0
//
//
//    while (currentLocation != "ZZZ") {
//        steps++
//
//        when (instructions[instructionIndex]) {
//            'L' -> currentLocation = locationMap[currentLocation]!!.first
//            'R' -> currentLocation = locationMap[currentLocation]!!.second
//        }
//        instructionIndex = (instructionIndex + 1) % instructions.length
//    }
//
//    steps.println()


    var answer = 1L

    locationMap.keys.filter { it.endsWith("A") }
        .forEach { answer = calculateLCM(answer, solveSteps(it, locationMap, instructions)) }


    answer.println()
}

fun solveSteps(currentLocation: String, locationMap: Map<String, Pair<String, String>>, instructions: String): Long {
    var steps = 0L
    var instructionIndex = 0
    var location = currentLocation

    while (!location.endsWith("Z")) {
        steps++

        when (instructions[instructionIndex]) {
            'L' -> location = locationMap[location]!!.first
            'R' -> location = locationMap[location]!!.second
        }
        instructionIndex = (instructionIndex + 1) % instructions.length
    }

    return steps
}


fun calculateGCD(a: Long, b: Long): Long {
    var x = a
    var y = b
    while (y != 0L) {
        val temp = y
        y = x % y
        x = temp
    }
    return x
}

fun calculateLCM(a: Long, b: Long): Long {
    if (a == 0L || b == 0L) {
        // LCM is not defined for zero, handle it according to your requirements
        throw IllegalArgumentException("Cannot calculate LCM for zero.")
    }

    // Calculate LCM using the formula: LCM(a, b) = |a * b| / GCD(a, b)
    return Math.abs(a * b) / calculateGCD(a, b)
}
