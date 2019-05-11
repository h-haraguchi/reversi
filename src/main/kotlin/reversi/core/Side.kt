package reversi.core

enum class Side(val label: String, val authority: SquareStatus) {
	WHITE("白", SquareStatus.WHITED),
	BLACK("黒", SquareStatus.BLACKED);

	fun opponent(): Side {
		return when (this) {
			WHITE -> BLACK
			BLACK -> WHITE
		}
	}
}