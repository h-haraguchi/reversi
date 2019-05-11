package reversi.core

import kotlin.math.sign

class Game {

	private val board = Board()
	private var currentSide: Side? = null
	private var isStarted = false
	private var isEnd = false

	fun start(currentSide: Side): GameInformation {
		if (isStarted) {
			throw IllegalArgumentException("もう既にゲームは始まっているぞ。")
		}
		isStarted = true
		val validList = board.validIndexList(currentSide)
		val boardInfo = board.boardInfo()
		this.currentSide = currentSide
		return GameInformation(Status.of(currentSide), currentSide, boardInfo, validList)
	}

	fun takeAction(side: Side, placement: SquareIndex): ActionResult {
		if (side != currentSide) {
			throw IllegalArgumentException("${currentSide!!.label}の番です。")
		}

		val validList = board.validIndexList(side)
		if (validList.none{ it == placement }) {
			throw IllegalArgumentException("${placement}に置くことは不可能です。")
		}
		board.place(side, placement)
		val nextValidList = board.validIndexList(side.opponent())
		val boardInfo = board.boardInfo()
		val actionResult  = if (nextValidList.isNotEmpty()) {
			ActionResult(false, GameInformation(Status.of(side.opponent()), side.opponent(), boardInfo, nextValidList))
		} else {
			val oneMoreValidList = board.validIndexList(side)
			if (oneMoreValidList.isNotEmpty()) {
				ActionResult(false, GameInformation(Status.of(side), side, boardInfo, oneMoreValidList))
			} else {
				ActionResult(true, GameInformation(Status.END, null, boardInfo, oneMoreValidList))
			}
		}
		this.currentSide = actionResult.gameInfo.nextSide
		this.isEnd = actionResult.isGameEnd
		return actionResult
	}

	fun result(): GameResult {
		if (!isEnd) {
			throw IllegalArgumentException("まだゲームは終わっていないぞ。")
		}
		val whiteCount = board.discCountOf(Side.WHITE)
		val blackCount = board.discCountOf(Side.BLACK)
		val compareResult = whiteCount.compareTo(blackCount)
		val score = "${Side.WHITE.label}: $whiteCount, ${Side.BLACK.label}: $blackCount"
		return when (compareResult.sign) {
			0 -> GameResult("引き分けです。 $score")
			1 -> GameResult("${Side.WHITE.label}の勝利です！ $score")
			-1 -> GameResult("${Side.BLACK.label}の勝利です！ $score")
			else -> throw IllegalStateException("Sign of ${compareResult.sign} is not supported.")
		}
	}

	enum class Status {
		WHITE, BLACK, END;

		companion object {
			fun of(side: Side): Status {
				return when (side) {
					Side.WHITE -> WHITE
					Side.BLACK -> BLACK
				}
			}
		}
	}
}