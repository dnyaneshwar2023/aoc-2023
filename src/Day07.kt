fun main() {

    val lines = readInput("input")

    val hands = lines.map {
        it.split(" ").let { line ->
            Pair(line[0], line[1].toInt())
        }
    }

    val sortedHands = hands.sortedWith(::compareHands)

    sortedHands.println()

    var answer = 0
    for (i in sortedHands.indices) {
        answer += sortedHands[i].second * (i + 1)
    }

    answer.println()

}

fun compareHands(a: Pair<String, Int>, b: Pair<String, Int>): Int {
    return if (a.first.getHandType() < b.first.getHandType()) {
        -1
    } else if (a.first.getHandType() > b.first.getHandType()) {
        1
    } else {
        compareHandValues(a.first, b.first)
    }
}

fun compareHandValues(first: String, second: String): Int {
    val cards = listOf('J', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A')

    for (i in first.indices) {
        if (first[i] != second[i]) {
            if (cards.indexOf(first[i]) < cards.indexOf(second[i])) {
                return -1
            } else if (cards.indexOf(first[i]) > cards.indexOf(second[i])) {
                return 1
            }
        }
    }
    return 0
}


fun String.getHandType(): Int {
    val map = this.groupBy { ch -> ch }.toMutableMap()
    map.fuckTheRule()
    return when {
        map.size == 1 -> 7
        map.size == 5 -> 1
        map.values.any { it.size == 4 } -> 6
        map.size == 2 -> 5
        map.values.any { it.size == 3 } -> 4
        map.values.count { it.size == 2 } == 2 -> 3
        else -> 2
    }
}

private fun MutableMap<Char, List<Char>>.fuckTheRule() {
    if (this.size == 1) {
        return
    }
    if(!this.containsKey('J')) {
        return
    }
    val jackCard = this.get('J')
    this.remove('J')
    val currentLargestCard = this.maxBy { it.value.size  }
    this[currentLargestCard.key] = currentLargestCard.value + jackCard!!
}
