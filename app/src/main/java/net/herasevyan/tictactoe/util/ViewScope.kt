package net.herasevyan.tictactoe.util

import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import net.herasevyan.tictactoe.R
import java.io.Closeable
import kotlin.coroutines.CoroutineContext

private const val KEY_VIEW_SCOPE = R.id.view_scope

val View.viewScope: CoroutineScope
    get() {
        getTag(KEY_VIEW_SCOPE)?.let {
            if (it is CoroutineScope) return it
        }

        val scope = CloseableCoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
        setTag(KEY_VIEW_SCOPE, scope)

        addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(view: View) {}

            override fun onViewDetachedFromWindow(view: View) {
                removeOnAttachStateChangeListener(this)
                setTag(KEY_VIEW_SCOPE, null)
                scope.close()
            }
        })

        return scope
    }

internal class CloseableCoroutineScope(context: CoroutineContext) : Closeable, CoroutineScope {
    override val coroutineContext: CoroutineContext = context

    override fun close() {
        coroutineContext.cancel()
    }
}