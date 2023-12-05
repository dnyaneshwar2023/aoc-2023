fun main() {


    val lines = readInput("input").filter { it.isNotEmpty() }

    val seeds = lines[0].trim().split(":").last().trim().split(" ").map { it.trim().toLong() }

    val seedToSoil = lines.slice(lines.indexOfFirst { it.trim().contains("seed-to-soil") } + 1..<lines.indexOfFirst {
        it.trim().contains("soil-to-fertilizer")
    }).getTriples()

    val soilToFertilizer =
        lines.slice(lines.indexOfFirst { it.trim().contains("soil-to-fertilizer") } + 1..<lines.indexOfFirst {
            it.trim().contains("fertilizer-to-water")
        }).getTriples()

    val fertilizerToWater = lines.slice(lines.indexOfFirst {
        it.trim().contains("fertilizer-to-water")
    } + 1..<lines.indexOfFirst { it.trim().contains("water-to-light") }).getTriples()

    val waterToLight =
        lines.slice(lines.indexOfFirst { it.trim().contains("water-to-light") } + 1..<lines.indexOfFirst {
            it.trim().contains("light-to-temperature")
        }).getTriples()

    val lightToTemperature =
        lines.slice(lines.indexOfFirst { it.trim().contains("light-to-temperature") } + 1..<lines.indexOfFirst {
            it.trim().contains("temperature-to-humidity")
        }).getTriples()

    val temperatureToHumidity =
        lines.slice(lines.indexOfFirst { it.trim().contains("temperature-to-humidity") } + 1..<lines.indexOfFirst {
            it.trim().contains("humidity-to-location")
        }).getTriples()

    val humidityToLocation =
        lines.slice(lines.indexOfFirst { it.trim().contains("humidity-to-location") } + 1..<lines.size)
            .getTriples()


    val location = seeds.minOf {
        it.getNextValue(seedToSoil)
            .getNextValue(soilToFertilizer)
            .getNextValue(fertilizerToWater)
            .getNextValue(waterToLight)
            .getNextValue(lightToTemperature)
            .getNextValue(temperatureToHumidity)
            .getNextValue(humidityToLocation)
    }

    location.println()

    var answer = Long.MAX_VALUE
    for (i in seeds.indices step 2) {
        val current = mutableListOf(Pair(seeds[i], seeds[i] + seeds[i + 1] - 1))
            .getNext(seedToSoil)
            .getNext(soilToFertilizer)
            .getNext(fertilizerToWater)
            .getNext(waterToLight)
            .getNext(lightToTemperature)
            .getNext(temperatureToHumidity)
            .getNext(humidityToLocation)
            .minOf { minOf(it.first, it.second) }
        answer = minOf(answer, current)
//        current.println()
    }

    answer.println()
}


private fun Long.getNextValue(mappings: List<Triple<Long, Long, Long>>): Long {
    return mappings.find { this in it.second..<it.second + it.third }
        ?.let { it.first + (this - it.second) } ?: this
}

private fun MutableList<Pair<Long, Long>>.getNext(mappings: List<Triple<Long, Long, Long>>): MutableList<Pair<Long, Long>> {
    val newRanges = mutableListOf<Pair<Long, Long>>()

    for (i in 0 until this.size) {
        var found = false;
        mappings.forEach { value ->
            val left = maxOf(this[i].first, value.second)
            val right = minOf(this[i].second, value.second + value.third - 1)
            if (!found && left <= right) {
                found = true;
                if (this[i].first < left) {
                    this.add(Pair(this[i].first, left - 1))
                }

                if (this[i].second > right) {
                    this.add(Pair(right + 1, this[i].second))
                }
//                println("Bug ${Pair(value.first + left - value.second, value.first + right - value.second)}")
                newRanges.add(
                    Pair(value.first + left - value.second, value.first + right - value.second)
                )
            }
        }
        if (!found) {
            newRanges.add(this[i])
        }
    }

    return newRanges

}

fun List<String>.getTriples(): List<Triple<Long, Long, Long>> {
    return this.map {
        it.split(" ").let { numbers -> Triple(numbers[0].toLong(), numbers[1].toLong(), numbers[2].toLong()) }
    }
}
