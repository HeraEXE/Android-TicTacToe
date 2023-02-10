package net.herasevyan.tictactoe.ui.base

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    protected abstract fun handleIntent()
}