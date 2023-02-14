package net.herasevyan.tictactoe.ui.game

import android.animation.Animator
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.herasevyan.tictactoe.R
import net.herasevyan.tictactoe.databinding.FragmentGameBinding
import net.herasevyan.tictactoe.game_logic.Turn
import net.herasevyan.tictactoe.game_logic.Winner
import net.herasevyan.tictactoe.ui.base.IntentFragment

@AndroidEntryPoint
class GameFragment : IntentFragment<GameViewModel>(R.layout.fragment_game) {

    companion object {
        private const val ANIMATION_DURATION = 300L
    }

    override val viewModel: GameViewModel by viewModels()

    private val binding: FragmentGameBinding by viewBinding(FragmentGameBinding::bind)

    private lateinit var itemImgList: List<ImageView>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            viewLifecycleOwner.lifecycleScope.launch { adjustField() }

            backImg.setOnClickListener { findNavController().navigateUp() }

            item00Img.setOnClickListener { makeMove(GameIntent.MakeMove(0, 0, item00Img)) }
            item01Img.setOnClickListener { makeMove(GameIntent.MakeMove(0, 1, item01Img)) }
            item02Img.setOnClickListener { makeMove(GameIntent.MakeMove(0, 2, item02Img)) }
            item10Img.setOnClickListener { makeMove(GameIntent.MakeMove(1, 0, item10Img)) }
            item11Img.setOnClickListener { makeMove(GameIntent.MakeMove(1, 1, item11Img)) }
            item12Img.setOnClickListener { makeMove(GameIntent.MakeMove(1, 2, item12Img)) }
            item20Img.setOnClickListener { makeMove(GameIntent.MakeMove(2, 0, item20Img)) }
            item21Img.setOnClickListener { makeMove(GameIntent.MakeMove(2, 1, item21Img)) }
            item22Img.setOnClickListener { makeMove(GameIntent.MakeMove(2, 2, item22Img)) }

            resultTv.setOnClickListener { playAgain() }
            dimmerView.setOnClickListener { playAgain() }

            itemImgList = listOf(
                item00Img, item01Img, item02Img,
                item10Img, item11Img, item12Img,
                item20Img, item21Img, item22Img
            )

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.intent.send(GameIntent.Restore)
            }
        }
    }

    override suspend fun updateState() {
        viewModel.state.collect { state ->
            when (state) {
                GameState.Inactive -> Unit
                GameState.ClearField -> binding.clear()
                is GameState.UpdateMove -> updateMove(state)
                is GameState.Restore -> restore(state)
            }
        }
    }

    private suspend fun FragmentGameBinding.adjustField() {
        delay(100)
        val row0Params = row0Layout.layoutParams
        val row1Params = row1Layout.layoutParams
        val row2Params = row2Layout.layoutParams
        row0Params.height = item00Img.width
        row1Params.height = item00Img.width
        row2Params.height = item00Img.width
        row0Layout.layoutParams = row0Params
        row1Layout.layoutParams = row1Params
        row2Layout.layoutParams = row2Params
    }

    private fun playAgain() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.intent.send(GameIntent.PlayAgain)
        }
    }

    private fun makeMove(intent: GameIntent.MakeMove) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.intent.send(intent)
        }
    }

    private fun restore(state: GameState.Restore) {
        val flattenGameField = state.currentGame.flattenField
        for (i in flattenGameField.indices) {
            val gameCell = flattenGameField[i]
            if (gameCell != 0) {
                itemImgList[i].animateScaleInFadeIn(if (gameCell == 1) Turn.X else Turn.O)
            }
        }
        binding.checkWinner(state.currentGame.winner)
    }

    private fun updateMove(state: GameState.UpdateMove) {
        state.imageView.animateScaleInFadeIn(state.moveResult.turn)
        binding.checkWinner(state.moveResult.winner)
    }

    private fun FragmentGameBinding.checkWinner(winner: Winner) {
        if (winner != Winner.NONE) {
            dimmerView.animateFadeIn()
            resultTv.animateFadeIn()
        }
        when (winner) {
            Winner.X -> resultTv.setText(R.string.message_winner_x)
            Winner.O -> resultTv.setText(R.string.message_winner_o)
            Winner.DRAW -> resultTv.setText(R.string.message_draw)
            Winner.NONE -> Unit
        }
    }

    private fun FragmentGameBinding.clear() {
        for (itemImg in itemImgList) {
            if (itemImg.drawable != null) {
                itemImg.animateScaleOutFadeOut()
            }
        }
        resultTv.animateFadeOut()
        dimmerView.animateFadeOut()
    }

    private fun ImageView.animateScaleInFadeIn(turn: Turn) {
        alpha = 0f
        scaleX = 0f
        scaleY = 0f
        setImageResource(when (turn) {
            Turn.X -> R.drawable.ic_cross
            Turn.O -> R.drawable.ic_circle
        })
        animate()
            .setDuration(ANIMATION_DURATION)
            .alphaBy(0f)
            .alpha(1f)
            .scaleX(0f)
            .scaleY(0f)
            .scaleXBy(1f)
            .scaleYBy(1f)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator) = Unit
                override fun onAnimationEnd(p0: Animator) = Unit
                override fun onAnimationCancel(p0: Animator) = Unit
                override fun onAnimationRepeat(p0: Animator) = Unit
            })
            .start()
    }

    private fun View.animateFadeIn() {
        alpha = 0f
        isVisible = true
        animate()
            .setDuration(ANIMATION_DURATION)
            .alphaBy(0f)
            .alpha(1f)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator) = Unit
                override fun onAnimationEnd(p0: Animator) = Unit
                override fun onAnimationCancel(p0: Animator) = Unit
                override fun onAnimationRepeat(p0: Animator) = Unit
            })
            .start()
    }

    private fun View.animateFadeOut() {
        animate()
            .setDuration(ANIMATION_DURATION)
            .alphaBy(1f)
            .alpha(0f)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator) = Unit

                override fun onAnimationEnd(p0: Animator) {
                    isVisible = false
                }

                override fun onAnimationCancel(p0: Animator) = Unit
                override fun onAnimationRepeat(p0: Animator) = Unit
            })
            .start()
    }

    private fun ImageView.animateScaleOutFadeOut() {
        animate()
            .setDuration(ANIMATION_DURATION)
            .alphaBy(1f)
            .alpha(0f)
            .scaleXBy(1f)
            .scaleYBy(1f)
            .scaleX(0f)
            .scaleY(0f)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator) = Unit
                override fun onAnimationEnd(p0: Animator) {
                    setImageDrawable(null)
                }
                override fun onAnimationCancel(p0: Animator) = Unit
                override fun onAnimationRepeat(p0: Animator) = Unit

            })
    }
}