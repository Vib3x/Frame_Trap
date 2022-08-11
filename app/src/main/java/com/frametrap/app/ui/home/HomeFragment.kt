package com.frametrap.app.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.frametrap.app.MoveList
import com.frametrap.app.databinding.FragmentHomeBinding
import java.util.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val game = "CvS2"
        /*if (game == "CvS2") {
            supportActionBar!!.title = "Capcom vs. SNK 2"
        } else {
            supportActionBar!!.title = game
        }*/
        val characters = context?.assets?.list(game + "_framedata")

        Arrays.sort(characters)

        val listview: LinearLayout = binding.listCharacters

        for (characterfile in characters!!) {
            val character: TextView = TextView(context)
            val charactername = characterfile.substring(0, characterfile.indexOf("."))
            character.text = charactername
            character.textSize = 32f
            character.setPadding(0, 10, 0, 10)
            character.gravity = Gravity.CENTER
            character.isClickable = true
            listview.addView(character)
            character.setOnClickListener { openmovelist(charactername, game) }
        }
        return root
    }

    fun openmovelist(charactername: String?, game: String?) {
        val intent = Intent(this@HomeFragment.context, MoveList::class.java)
        intent.putExtra(EXTRA_CHARACTER_NAME, charactername)
        intent.putExtra(EXTRA_GAME_NAME, game)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val EXTRA_CHARACTER_NAME = "com.frametrap.app.extra_character_name"
        const val EXTRA_GAME_NAME = "com.frametrap.app.extra_game_name"
    }
}