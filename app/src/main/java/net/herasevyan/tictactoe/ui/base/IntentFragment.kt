package net.herasevyan.tictactoe.ui.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class IntentFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    protected abstract suspend fun updateState()
}