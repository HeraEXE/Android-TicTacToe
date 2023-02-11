package net.herasevyan.tictactoe.ui.game_history

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import net.herasevyan.tictactoe.R
import net.herasevyan.tictactoe.databinding.FragmentGameHistoryBinding
import net.herasevyan.tictactoe.ui.base.IntentFragment

@AndroidEntryPoint
class GameHistoryFragment : IntentFragment<GameHistoryViewModel>(R.layout.fragment_game_history) {

    override val viewModel: GameHistoryViewModel by viewModels()

    private var bindingNullable: FragmentGameHistoryBinding? = null
    private val binding: FragmentGameHistoryBinding get() = bindingNullable!!

    private val adapter = GameHistoryAdapter { gameField ->
        DeleteGameFieldDialogFragment().apply {
            arguments = bundleOf("gameField" to gameField)
        }.show(parentFragmentManager, null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener("deleteKey") { _, _ ->
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.intent.send(GameHistoryIntent.DeleteHistory)
            }
        }

        setFragmentResultListener("deleteFieldKey") { _, bundle ->
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.intent.send(GameHistoryIntent.DeleteGameField(bundle.getParcelable("gameField")!!))
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingNullable = FragmentGameHistoryBinding.bind(view)

        with(binding) {
            backImg.setOnClickListener { findNavController().navigateUp() }

            deleteHistoryImg.setOnClickListener {
                DeleteGameHistoryDialogFragment().show(parentFragmentManager, null)
            }

            historyRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
            historyRecyclerView.adapter = adapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.intent.send(GameHistoryIntent.LoadHistory)
        }
    }

    override fun onDestroyView() {
        bindingNullable = null
        super.onDestroyView()
    }

    override suspend fun updateState() {
        viewModel.state.collect { state ->
            when (state) {
                GameHistoryState.Inactive -> Unit
                is GameHistoryState.LoadHistory -> adapter.submitList(state.history)
            }
        }
    }
}