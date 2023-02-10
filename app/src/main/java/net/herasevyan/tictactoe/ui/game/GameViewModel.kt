package net.herasevyan.tictactoe.ui.game

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import net.herasevyan.tictactoe.ui.base.BaseViewModel

class GameViewModel(private val savedStateHandle: SavedStateHandle) : BaseViewModel() {

    companion object {
        private const val GAME = "game"
    }

    val intent = Channel<GameIntent>(Channel.UNLIMITED)

    val state: StateFlow<GameState> get() = mutableState

    private val mutableState = MutableStateFlow<GameState>(GameState.Inactive)

    private var game: TicTacToeGame
    get() = savedStateHandle[GAME] ?: TicTacToeGame().also {
            savedStateHandle[GAME] = it
        }
    set(value) { savedStateHandle[GAME] = value }

    init { handleIntent() }

    override fun handleIntent() {
        viewModelScope.launch {
            intent.consumeAsFlow().collect {
                when (it) {
                    is GameIntent.Start -> start()
                    is GameIntent.PlayAgain -> clear()
                    is GameIntent.MakeMove -> makeMove(it)
                }
            }
        }
    }

    private fun start() {
        mutableState.value = GameState.Start(game.winner, game.flattenGameField)
    }

    private fun makeMove(intent: GameIntent.MakeMove) {
        val isPrevPlayer1 = game.isPlayer1
        val winner = game.makeMove(intent.row, intent.column)
        mutableState.value = GameState.UpdateMove(
            intent.imageView,
            winner,
            if (isPrevPlayer1 != game.isPlayer1) isPrevPlayer1 else null
        )

    }

    private fun clear() {
        game.clear()
        mutableState.value = GameState.ClearField
    }
}