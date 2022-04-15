package com.example.game_application.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.game_application.MainActivity
import com.example.game_application.GameRepository.Singleton.gameList
import com.example.game_application.R
import com.example.game_application.adapter.GameAdapter
import com.example.game_application.adapter.GameItemDecoration

class CollectionFragment(private  val context: MainActivity) : Fragment()  {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(R.layout.fragment_collection, container, false)

        //recuperer recyclerview
        val collectionRecyvlerView = view.findViewById<RecyclerView>(R.id.collection_recycler_list)
        collectionRecyvlerView.adapter = GameAdapter(context, gameList.filter { it.liked }, R.layout.item_vertical_game) //adapter qui va faire le trie dans la liste de jeux en affichant que les jeux liké
        collectionRecyvlerView.layoutManager = LinearLayoutManager(context)
        collectionRecyvlerView.addItemDecoration(GameItemDecoration())

        return  view
    }
}