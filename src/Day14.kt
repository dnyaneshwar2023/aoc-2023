fun main() {

    val lines = readInput("input")

    val columns = mutableListOf<String>()



    for (i in 0 until lines[0].length) {
        var column = ""
        for(j in lines.indices) {
            column += lines[j][i]
        }
        columns.add(column)
    }
    val answer = columns.sumOf {
        findLoad(it)
    }

    answer.println()
}

fun findLoad(line: String): Int {
    var currentLoadIndex = 0
    var answer = 0


    for (i in line.indices) {
        if (line[i] == '#') {
            currentLoadIndex = i + 1
        } else if (line[i] == 'O') {
            answer += (line.length - currentLoadIndex)
            currentLoadIndex++
        }
    }
    return answer
}
