package net.herasevyan.tictactoe.ui.game_history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import net.herasevyan.tictactoe.R
import net.herasevyan.tictactoe.application.App
import net.herasevyan.tictactoe.databinding.FragmentGameHistoryBinding

class GameHistoryFragment : Fragment(R.layout.fragment_game_history) {

    private var bindingNullable: FragmentGameHistoryBinding? = null
    private val binding: FragmentGameHistoryBinding get() = bindingNullable!!

    private val adapter = GameHistoryAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingNullable = FragmentGameHistoryBinding.bind(view)

        binding.historyRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.historyRecyclerView.adapter = adapter

        adapter.updateItems((activity?.application as App).history)
    }

    override fun onDestroyView() {
        bindingNullable = null
        super.onDestroyView()
    }
}