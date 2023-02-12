package net.herasevyan.tictactoe.ui.menu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import net.herasevyan.tictactoe.R
import net.herasevyan.tictactoe.databinding.FragmentMenuBinding

class MenuFragment : Fragment(R.layout.fragment_menu) {

    private val binding: FragmentMenuBinding by viewBinding(FragmentMenuBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.startBtn.setOnClickListener {
            findNavController().navigate(R.id.from_menu_to_game)
        }

        binding.historyBtn.setOnClickListener {
            findNavController().navigate(R.id.from_menu_to_game_history)
        }
    }
}