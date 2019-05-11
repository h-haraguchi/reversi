package reversi.controller

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reversi.core.*

@RestController
@RequestMapping(value = ["/reversi/api"], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
class ReversiApiController {

	private var game: Game = Game()

	@PostMapping(value = ["/new"])
	fun newGame() {
		this.game = Game()
	}

	@PostMapping(value = ["/start"])
	fun start(@RequestBody startData: RequestOfStart): ResponseOfStart {
		return ResponseOfStart(game.start(Side.valueOf(startData.firstSide)))
	}

	@PostMapping(value = ["/action"])
	fun takeAction(@RequestBody actionData: RequestOfAction): ResponseOfAction {
		val actionResult = game.takeAction(Side.valueOf(actionData.side), SquareIndex(actionData.x, actionData.y))
		return ResponseOfAction(actionResult)
	}

	@GetMapping(value = ["/end"])
	fun end(): ResponseOfEnd {
		return ResponseOfEnd(game.result().message)
	}
}

data class RequestOfStart(@JsonProperty("first_side") val firstSide: String)
data class ResponseOfStart(val gameInfo: GameInformation)
data class RequestOfAction(
		@JsonProperty("side") val side: String,
		@JsonProperty("x") val x: Int,
		@JsonProperty("y") val y: Int
)
data class ResponseOfAction(val actionResult: ActionResult)
data class ResponseOfEnd(val message: String)