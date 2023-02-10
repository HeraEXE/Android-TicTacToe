package net.herasevyan.tictactoe.ui.menu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import net.herasevyan.tictactoe.R
import net.herasevyan.tictactoe.databinding.FragmentMenuBinding

class MenuFragment : Fragment(R.layout.fragment_menu) {

    private var bindingNullable: FragmentMenuBinding? = null
    private val binding: FragmentMenuBinding get() = bindingNullable!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingNullable = FragmentMenuBinding.bind(view)

        binding.startBtn.setOnClickListener {
            findNavController().navigate(R.id.from_menu_to_game)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingNullable = null
    }
}