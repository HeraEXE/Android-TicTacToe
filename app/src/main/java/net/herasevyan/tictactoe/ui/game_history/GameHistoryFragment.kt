package net.herasevyan.tictactoe.ui.game_history

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import net.herasevyan.tictactoe.R
import net.herasevyan.tictactoe.data.GameHistoryDao
import net.herasevyan.tictactoe.databinding.FragmentGameHistoryBinding
import javax.inject.Inject

@AndroidEntryPoint
class GameHistoryFragment : Fragment(R.layout.fragment_game_history) {

    @Inject lateinit var gameHistoryDao: GameHistoryDao

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
                gameHistoryDao.deleteHistory()
            }
        }

        setFragmentResultListener("deleteFieldKey") { _, bundle ->
            viewLifecycleOwner.lifecycleScope.launch {
                gameHistoryDao.deleteGameField(bundle.getParcelable("gameField")!!)
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
            gameHistoryDao.loadHistory().collect { gameFields ->
                adapter.submitList(gameFields)
            }
        }
    }

    override fun onDestroyView() {
        bindingNullable = null
        super.onDestroyView()
    }
}