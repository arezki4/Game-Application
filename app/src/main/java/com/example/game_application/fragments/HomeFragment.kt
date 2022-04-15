package com.example.game_application.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.game_application.GameRepository.Singleton.gameList
import com.example.game_application.R
import com.example.game_application.adapter.GameAdapter
import com.example.game_application.adapter.GameItemDecoration
import com.example.game_application.MainActivity
import com.example.game_application.GameModel

class HomeFragment(private val context : MainActivity) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater?.inflate(R.layout.fragment_home, container, false) //ingecter fragment_home sur la page

        /*DONNÉE UTILISÉE EN LOCAL*/
        /*// créer une liste qui va stocker ces jeux
        val gameList = arrayListOf<GameModel>()Game

        // enregistrer un premier jeux dans notre liste (pissenlit)
        gameList.add(GameModel(
            "Pissenlit",
            "jaune soleil",
            "https://cdn.pixabay.com/photo/2019/05/07/13/54/dandelion-4186029_960_720.jpg",
            false
        ))

        // enregistrer un second jeux dans notre liste (pissenlit)
        gameList.add(GameModel(
            "Rose",
            "ca pique un peu",
            "https://cdn.pixabay.com/photo/2017/05/15/17/37/cacti-2315542_960_720.jpg",
            false
        ))

        // enregistrer un troisieme jeux dans notre liste (pissenlit)
        gameList.add(GameModel(
            "Cactus",
            "c'est' Beaucoup",
            "https://cdn.pixabay.com/photo/2017/05/15/17/37/cacti-2315542_960_720.jpg",
            false
        ))

        // enregistrer un quatrieme jeux dans notre liste (pissenlit)
        gameList.add(GameModel(
            "Tulipe",
            "ca pique un peu",
            "https://cdn.pixabay.com/photo/2021/12/27/14/39/tulips-6897351_960_720.jpg",
            false
        ))*/


        //recuperer le recyclerview:  horizontal_recycler_view
        val horizontalRecyclerView = view.findViewById<RecyclerView>(R.id.horizontal_recycler_view)
        horizontalRecyclerView.adapter = GameAdapter(context, gameList.filter { !it.liked }, R.layout.item_horizontal_game)

        //recuperer le second recyclerview: vertical_recycler_view

        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recycler_view)
        verticalRecyclerView.adapter = GameAdapter(context, gameList, R.layout.item_vertical_game)
        verticalRecyclerView.addItemDecoration(GameItemDecoration())

        return  view
    }
}