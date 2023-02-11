package net.herasevyan.tictactoe.ui.game_history

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
class GameHistoryViewModel @Inject constructor(
    private val gameHistoryDao: GameHistoryDao
) : IntentViewModel() {

    val intent = Channel<GameHistoryIntent>(Channel.UNLIMITED)

    val state: StateFlow<GameHistoryState> get() = mutableState

    private val mutableState = MutableStateFlow<GameHistoryState>(GameHistoryState.Inactive)

    override fun handleIntent() {
        viewModelScope.launch {
            intent.consumeAsFlow().collect {
                when (it) {
                    is GameHistoryIntent.DeleteGameField -> deleteGameField(it.gameField)
                    GameHistoryIntent.DeleteHistory -> deleteHistory()
                    GameHistoryIntent.LoadHistory -> loadHistory()
                }
            }
        }
    }

    private fun deleteGameField(gameField: GameField) {
        viewModelScope.launch {
            gameHistoryDao.deleteGameField(gameField)
        }
    }

    private fun deleteHistory() {
        viewModelScope.launch {
            gameHistoryDao.deleteHistory()
        }
    }

    private fun loadHistory() {
        viewModelScope.launch {
            gameHistoryDao.loadHistory().collect {
                mutableState.value = GameHistoryState.LoadHistory(it)
            }
        }
    }
}