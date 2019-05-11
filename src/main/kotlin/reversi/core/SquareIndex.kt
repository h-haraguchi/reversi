package reversi.core

data class SquareIndex(val x: Int, val y: Int) {

	fun next(direction: Direction): SquareIndex? {
		return when (direction) {
			Direction.UP -> if (y == 1) null else SquareIndex(x, y - 1)
			Direction.DOWN -> if (y == 8) null else SquareIndex(x, y + 1)
			Direction.LEFT -> if (x == 1) null else SquareIndex(x - 1, y)
			Direction.RIGHT -> if (x == 8) null else SquareIndex(x + 1, y)
			Direction.UP_LEFT -> if (y == 1 || x == 1) null else SquareIndex(x - 1, y - 1)
			Direction.UP_RIGHT -> if (y == 1 || x == 8) null else SquareIndex(x + 1, y - 1)
			Direction.DOWN_LEFT -> if (y == 8 || x == 1) null else SquareIndex(x - 1, y + 1)
			Direction.DOWN_RIGHT -> if (y == 8 || x == 8) null else SquareIndex(x + 1, y + 1)
		}
	}

	override fun toString(): String {
		return "$x-$y"
	}
}