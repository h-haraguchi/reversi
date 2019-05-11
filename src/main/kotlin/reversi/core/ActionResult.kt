package reversi.core

data class ActionResult(
		val isGameEnd: Boolean,
		val gameInfo: GameInformation
)