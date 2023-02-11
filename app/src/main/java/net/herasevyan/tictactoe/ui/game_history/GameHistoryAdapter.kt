package net.herasevyan.tictactoe.ui.game_history

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.herasevyan.tictactoe.R
import net.herasevyan.tictactoe.databinding.ItemGameHistoryBinding
import net.herasevyan.tictactoe.util.viewScope

class GameHistoryAdapter : RecyclerView.Adapter<GameHistoryAdapter.ViewHolder>() {

    private val items = mutableListOf<String>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(items: List<String>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGameHistoryBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    class ViewHolder(private val binding: ItemGameHistoryBinding) : RecyclerView.ViewHolder(binding.root) {

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

        fun bind(flattenFieldStr: String) {
            val flattenGameField = flattenFieldStr.split(',').map { it.toInt() }
            for (i in flattenGameField.indices) {
                when (flattenGameField[i]) {
                    1 -> itemImgList[i].setImageResource(R.drawable.ic_cross)
                    2 -> itemImgList[i].setImageResource(R.drawable.ic_circle)
                }
            }
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