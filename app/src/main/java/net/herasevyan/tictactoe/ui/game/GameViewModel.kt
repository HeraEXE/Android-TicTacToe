package net.herasevyan.tictactoe.ui.game

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import net.herasevyan.tictactoe.data.GameField
import net.herasevyan.tictactoe.data.GameHistoryDao
import net.herasevyan.tictactoe.ui.base.IntentViewModel
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val gameHistoryDao: GameHistoryDao
    ) : IntentViewModel() {

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

    override fun handleIntent() {
        viewModelScope.launch {
            intent.consumeAsFlow().collect {
                when (it) {
                    GameIntent.Restore -> restore()
                    GameIntent.PlayAgain -> clear()
                    is GameIntent.MakeMove -> makeMove(it)
                }
            }
        }
    }

    private fun restore() {
        mutableState.value = GameState.Restore(game.winner, game.flattenGameField)
    }

    private fun makeMove(intent: GameIntent.MakeMove) {
        val wasXTurn = game.isXTurn
        val winner = game.makeMove(intent.row, intent.column)
        if (winner != Winner.NONE) {
            viewModelScope.launch {
                gameHistoryDao.addGameField(GameField(game.flattenGameField.joinToString(",")))
            }
        }
        mutableState.value = GameState.UpdateMove(
            intent.imageView,
            winner,
            if (wasXTurn != game.isXTurn) wasXTurn else null
        )

    }

    private fun clear() {
        game.clear()
        mutableState.value = GameState.ClearField
    }
}