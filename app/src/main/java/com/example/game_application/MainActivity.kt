package com.example.game_application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.game_application.fragments.AddGameFragment
import com.example.game_application.fragments.CollectionFragment
import com.example.game_application.fragments.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment(HomeFragment(this), R.string.home_page_title)

        //importer la bottomnavigationview
        val navigationView = findViewById<BottomNavigationView>(R.id.navigation_view)
        navigationView.setOnNavigationItemSelectedListener {
            when(it.itemId)//switch en java
            {
                R.id.home_page -> {
                    loadFragment(HomeFragment(this), R.string.home_page_title)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.collection_page -> {
                    loadFragment(CollectionFragment(this), R.string.collection_page_title)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.add_game_page -> {
                    loadFragment(AddGameFragment(this), R.string.add_game_page_title)
                    return@setOnNavigationItemSelectedListener true
                } else -> false
            }
        }
    }

    private fun loadFragment(fragment:  Fragment, String: Int) {
        //charger notre plant repository
        val repo = GameRepository()

        //actualiser le titre de la page
        findViewById<TextView>(R.id.page_title).text = resources.getString(String)

        //mettre a jour la liste de jeux
        repo.updateData {
            //injecter le fragment dans notre boite (fragment_container)
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)/*HomeFragment(this))*/  //remplacer le fragment container par home fragment
            //transaction.replace(R.id.fragment_container, CollectionFragment(this))//remplacer le fragment container par home fragment
            //transaction.replace(R.id.fragment_container, AddGameFragment(this))
            transaction.addToBackStack(null)//pour pas avoir de retour sur ce composant
            transaction.commit()//envoyer les changement
        }
    }


}