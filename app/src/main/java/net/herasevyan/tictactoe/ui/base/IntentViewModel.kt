package net.herasevyan.tictactoe.ui.base

import androidx.lifecycle.ViewModel

abstract class IntentViewModel : ViewModel() {

    init { handleIntent() }

    protected abstract fun handleIntent()
}