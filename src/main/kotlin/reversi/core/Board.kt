package reversi.core

class Board {

	private val squareMap: MutableMap<SquareIndex, SquareStatus> = makeInitialMap()

	private fun makeInitialMap(): MutableMap<SquareIndex, SquareStatus> {
		val initialMap = mutableMapOf<SquareIndex, SquareStatus>()
		for (x in 1..8) {
			for (y in 1..8) {
				if ((x == 4 && y == 5) || (x == 5 && y == 4)) {
					initialMap[SquareIndex(x, y)] = SquareStatus.BLACKED
				} else if ((x == 4 && y == 4) || (x == 5 && y == 5)) {
					initialMap[SquareIndex(x, y)] = SquareStatus.WHITED
				} else {
					initialMap[SquareIndex(x, y)] = SquareStatus.BLANK
				}
			}
		}
		return initialMap
	}

	fun validIndexList(side: Side): List<SquareIndex> {
		return squareMap.entries
				.filter { entry -> entry.value == SquareStatus.BLANK }
				.filter { entry -> Direction.values().any { canReverse(side, entry.key, it) } }
				.map { entry -> entry.key }
				.toList()
	}

	fun place(side: Side, placement: SquareIndex) {
		squareMap[placement] = side.authority
		Direction.values()
				.filter { canReverse(side, placement, it) }
				.forEach { reverse(side, placement, it) }
	}

	fun boardInfo(): Map<SquareIndex, SquareStatus> = squareMap.toMap()

	private fun canReverse(side: Side, current: SquareIndex, direction: Direction): Boolean {
		var isExistsTarget = false
		var next = current.next(direction)
		while (next?.let { squareMap[it] == side.opponent().authority } == true) {
			isExistsTarget = true
			next = next.next(direction)
		}
		return if (isExistsTarget) { // 囲む敵がいるか
			next?.let { squareMap[it] == side.authority } == true // 相棒がいるか
		} else {
			false
		}
	}

	private fun reverse(side: Side, current: SquareIndex, direction: Direction) {
		var next = current.next(direction)
		while (next?.let { squareMap[it] == side.opponent().authority } == true) {
			squareMap[next] = side.authority
			next = next.next(direction)
		}
	}

	fun discCountOf(side: Side): Int {
		return squareMap.values.count { status -> status == side.authority }
	}
}