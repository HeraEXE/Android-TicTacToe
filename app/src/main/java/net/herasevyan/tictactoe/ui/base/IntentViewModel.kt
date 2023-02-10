package net.herasevyan.tictactoe.ui.base

import androidx.lifecycle.ViewModel

abstract class IntentViewModel : ViewModel() {

    protected abstract fun handleIntent()
}