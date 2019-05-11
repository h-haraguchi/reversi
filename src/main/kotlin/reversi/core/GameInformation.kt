package reversi.core

data class GameInformation(
		val status: Game.Status,
		val nextSide: Side?,
		val currentBoardMap: Map<SquareIndex, SquareStatus>,
		val availableIndexList: List<SquareIndex>
) {
	fun nextSide(): Side? {
		return when (status) {
			Game.Status.BLACK -> Side.BLACK
			Game.Status.WHITE -> Side.WHITE
			else -> null
		}
	}
}