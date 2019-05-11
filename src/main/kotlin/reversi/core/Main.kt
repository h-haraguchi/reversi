package reversi.core

import java.util.*

fun main(args: Array<String>) {
	val game = Game()
	var gameInfo = game.start(Side.WHITE)
	while (gameInfo.status != Game.Status.END) {
		val availableList = gameInfo.availableIndexList
		val randomPlacement = availableList[Random().nextInt(availableList.size)]
		val actionResult = game.takeAction(gameInfo.nextSide()!!, randomPlacement)
		gameInfo = actionResult.gameInfo
	}
}