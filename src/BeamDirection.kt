sealed class BeamDirection {
    abstract fun getNextIncrements(element: String): List<Triple<Int, Int, BeamDirection>>
    abstract fun getNextNth(step: Int, x: Int, y: Int): Triple<Int, Int, BeamDirection>
    abstract fun getPerpendicularVertices(x: Int, y: Int): List<Triple<Int, Int, BeamDirection>>

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

        override fun getNextNth(step: Int, x: Int, y: Int): Triple<Int, Int, BeamDirection> {
            return Triple(x, y + step, this)
        }

        override fun getPerpendicularVertices(x: Int, y: Int): List<Triple<Int, Int, BeamDirection>> {
            return listOf(Triple(x - 1, y, Up), Triple(x + 1, y, Down))
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

        override fun getNextNth(step: Int, x: Int, y: Int): Triple<Int, Int, BeamDirection> {
            return Triple(x, y - step, this)
        }

        override fun getPerpendicularVertices(x: Int, y: Int): List<Triple<Int, Int, BeamDirection>> {
            return listOf(Triple(x - 1, y, Up), Triple(x + 1, y, Down))
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

        override fun getNextNth(step: Int, x: Int, y: Int): Triple<Int, Int, BeamDirection> {
            return Triple(x - step, y, this)
        }

        override fun getPerpendicularVertices(x: Int, y: Int): List<Triple<Int, Int, BeamDirection>> {
            return listOf(Triple(x, y - 1, Left), Triple(x, y + 1, Right))

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

        override fun getNextNth(step: Int, x: Int, y: Int): Triple<Int, Int, BeamDirection> {
            return Triple(x + step, y, this)
        }

        override fun getPerpendicularVertices(x: Int, y: Int): List<Triple<Int, Int, BeamDirection>> {
            return listOf(Triple(x, y - 1, Left), Triple(x, y + 1, Right))
        }

    }

}
