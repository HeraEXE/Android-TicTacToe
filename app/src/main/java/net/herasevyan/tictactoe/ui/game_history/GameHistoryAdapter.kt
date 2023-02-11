package net.herasevyan.tictactoe.ui.game_history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.herasevyan.tictactoe.R
import net.herasevyan.tictactoe.data.GameField
import net.herasevyan.tictactoe.databinding.ItemGameHistoryBinding
import net.herasevyan.tictactoe.util.viewScope

class GameHistoryAdapter(
    private val onItemClick: (GameField) -> Unit
) : ListAdapter<GameField, GameHistoryAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGameHistoryBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class DiffCallback : DiffUtil.ItemCallback<GameField>() {
        override fun areItemsTheSame(oldItem: GameField, newItem: GameField) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: GameField, newItem: GameField) = oldItem == newItem
    }

    class ViewHolder(
        private val binding: ItemGameHistoryBinding,
        private val onItemClick: (GameField) -> Unit
        ) : RecyclerView.ViewHolder(binding.root) {

        private val itemImgList = binding.run {
            listOf(
                item00Img, item01Img, item02Img,
                item10Img, item11Img, item12Img,
                item20Img, item21Img, item22Img
            )
        }

        init {
            binding.root.viewScope.launch {
                binding.adjustField()
            }
        }

        fun bind(gameField: GameField) {
            val flattenGameField = gameField.field.split(',').map { c -> c.toInt() }
            for (i in flattenGameField.indices) {
                when (flattenGameField[i]) {
                    1 -> itemImgList[i].setImageResource(R.drawable.ic_cross)
                    2 -> itemImgList[i].setImageResource(R.drawable.ic_circle)
                }
            }
            binding.root.setOnClickListener { onItemClick(gameField) }
        }

        private suspend fun ItemGameHistoryBinding.adjustField() {
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
    }
}