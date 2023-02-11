package net.herasevyan.tictactoe.ui.game_history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import net.herasevyan.tictactoe.R
import net.herasevyan.tictactoe.data.GameHistoryDao
import net.herasevyan.tictactoe.databinding.FragmentGameHistoryBinding
import javax.inject.Inject

@AndroidEntryPoint
class GameHistoryFragment : Fragment(R.layout.fragment_game_history) {

    private var bindingNullable: FragmentGameHistoryBinding? = null
    private val binding: FragmentGameHistoryBinding get() = bindingNullable!!

    private val adapter = GameHistoryAdapter()

    @Inject lateinit var gameHistoryDao: GameHistoryDao

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingNullable = FragmentGameHistoryBinding.bind(view)

        binding.historyRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.historyRecyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            gameHistoryDao.loadHistory().collect { gameFields ->
                adapter.updateItems(gameFields.map { it.field.split(',').map { c -> c.toInt() } })
            }
        }
    }

    override fun onDestroyView() {
        bindingNullable = null
        super.onDestroyView()
    }
}