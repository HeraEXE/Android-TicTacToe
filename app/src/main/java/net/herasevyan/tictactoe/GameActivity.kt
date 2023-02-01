package net.herasevyan.tictactoe

import android.animation.Animator
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

class GameActivity : AppCompatActivity() {

    companion object {
        private const val ANIMATION_DURATION = 300L
    }

    private lateinit var game: TicTacToeGame

    private lateinit var dimmerView: View
    private lateinit var resultTv: TextView

    private var itemImgList: List<ImageView>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        dimmerView = findViewById(R.id.dimmer_view)
        resultTv = findViewById(R.id.result_tv)
        val row0Layout: LinearLayout = findViewById(R.id.row0_layout)
        val row1Layout: LinearLayout = findViewById(R.id.row1_layout)
        val row2Layout: LinearLayout = findViewById(R.id.row2_layout)
        val item00Img: ImageView = findViewById(R.id.item00_img)
        val item01Img: ImageView = findViewById(R.id.item01_img)
        val item02Img: ImageView = findViewById(R.id.item02_img)
        val item10Img: ImageView = findViewById(R.id.item10_img)
        val item11Img: ImageView = findViewById(R.id.item11_img)
        val item12Img: ImageView = findViewById(R.id.item12_img)
        val item20Img: ImageView = findViewById(R.id.item20_img)
        val item21Img: ImageView = findViewById(R.id.item21_img)
        val item22Img: ImageView = findViewById(R.id.item22_img)

        item00Img.postDelayed({
            val row0Params = row0Layout.layoutParams
            val row1Params = row1Layout.layoutParams
            val row2Params = row2Layout.layoutParams
            row0Params.height = item00Img.width
            row1Params.height = item00Img.width
            row2Params.height = item00Img.width
            row0Layout.layoutParams = row0Params
            row1Layout.layoutParams = row1Params
            row2Layout.layoutParams = row2Params
        }, 100)

        itemImgList = listOf(
            item00Img, item01Img, item02Img,
            item10Img, item11Img, item12Img,
            item20Img, item21Img, item22Img
        )

        item00Img.setOnClickListener { item00Img.makeMove(0, 0) }
        item01Img.setOnClickListener { item01Img.makeMove(0, 1) }
        item02Img.setOnClickListener { item02Img.makeMove(0, 2) }
        item10Img.setOnClickListener { item10Img.makeMove(1, 0) }
        item11Img.setOnClickListener { item11Img.makeMove(1, 1) }
        item12Img.setOnClickListener { item12Img.makeMove(1, 2) }
        item20Img.setOnClickListener { item20Img.makeMove(2, 0) }
        item21Img.setOnClickListener { item21Img.makeMove(2, 1) }
        item22Img.setOnClickListener { item22Img.makeMove(2, 2) }

        resultTv.setOnClickListener { playAgain() }
        dimmerView.setOnClickListener { playAgain() }

        savedInstanceState?.let {
            game = it.getParcelable("game") ?: TicTacToeGame()
            val flatGameField = game.getField().flatten()
            for (i in flatGameField.indices) {
                val gameCell = flatGameField[i]
                if (gameCell != 0) {
                    itemImgList!![i].animateScaleInFadeIn(gameCell == 1)
                }
            }
            checkWinner(game.winner)
        } ?: run { game = TicTacToeGame() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("game", game)
    }

    private fun ImageView.makeMove(row: Int, column: Int) {
        val isCurrentPlayer1 = game.isPlayer1
        val winner = game.makeMove(row, column)
        if (isCurrentPlayer1 != game.isPlayer1) {
            animateScaleInFadeIn(isCurrentPlayer1)
        }
        checkWinner(winner)
    }

    private fun checkWinner(winner: Winner) {
        if (winner != Winner.NONE) {
            dimmerView.animateFadeIn()
            resultTv.animateFadeIn()
        }
        when (winner) {
            Winner.PLAYER1 -> resultTv.text = "Player 1 won"
            Winner.PLAYER2 -> resultTv.text = "Player 2 won"
            Winner.DRAW -> resultTv.text = "Draw"
            Winner.NONE -> Unit
        }
    }

    private fun ImageView.animateScaleInFadeIn(isCurrentPlayer1: Boolean) {
        alpha = 0f
        scaleX = 0f
        scaleY = 0f
        setImageResource(if (isCurrentPlayer1) R.drawable.ic_cross else R.drawable.ic_circle)
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

    private fun playAgain() {
        for (itemImg in itemImgList!!) {
            if (itemImg.drawable != null) {
                itemImg.animateScaleOutFadeOut()
            }
        }
        game.clear()
        resultTv.animateFadeOut()
        dimmerView.animateFadeOut()
    }
}