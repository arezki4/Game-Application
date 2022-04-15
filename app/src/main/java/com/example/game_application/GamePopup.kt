package com.example.game_application

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.Adapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.game_application.adapter.GameAdapter

class GamePopup(
    private  val adapter: GameAdapter,
    private val currentGame : GameModel) : Dialog(adapter.context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE) // pour dire qu'on veut pas de titre sur la fenetre popup
        setContentView(R.layout.popup_game_details)
        setupComponent()
        setupCloseButton()
        setupDeleteButton()
        setupStarButton()
    }

    private fun updateStar(button: ImageView){
        if(currentGame.liked){
            button.setImageResource(R.drawable.ic_star)
        }else{
            button.setImageResource(R.drawable.ic_unstar)
        }
    }

    private fun setupStarButton() {
        //recuperer
        val starButton = findViewById<ImageView>(R.id.star_button)//on creer une variable a starbutton contrairement aux autres car on va le reutiliser, se qui va eviter de devoir le recopier a chaque fois

        updateStar(starButton)
        /*if(currentGame.liked){
            starButton.setImageResource(R.drawable.ic_star)
        }else{
            starButton.setImageResource(R.drawable.ic_unstar)
        }*/

        //interaction avec la base de donnée
        starButton.setOnClickListener{
            currentGame.liked = !currentGame.liked
            val repo = GameRepository()
            repo.updateGame(currentGame)
            updateStar(starButton)
        }
    }

    private fun setupDeleteButton() {
        findViewById<ImageView>(R.id.delete_button).setOnClickListener{
            //supprimer le jeux de la base
            val repo = GameRepository() //generer l'instance du repository
            repo.deleteGame(currentGame)//faire l'instruction a la data base pour mettre a jour
            dismiss()//fermer la popup
        }
    }

    private fun setupCloseButton() {
        findViewById<ImageView>(R.id.close_button).setOnClickListener{
            //fermer le fenetre
            dismiss()
        }
    }

    private fun setupComponent() {
        //actualiser l'image du jeux
        val gameImage = findViewById<ImageView>(R.id.image_item)
        Glide.with(adapter.context).load(Uri.parse(currentGame.imageUrl)).into(gameImage)

        //actualiser le nom du jeux
        findViewById<TextView>(R.id.popup_game_name).text = currentGame.name

        //actualiser la desctription du jeux
        findViewById<TextView>(R.id.popup_game_description_subtitle).text = currentGame.description

        //actualiser le type du jeux
        findViewById<TextView>(R.id.popup_game_type_subtitle).text = currentGame.type

        //actualiser le support du jeuxtype
        findViewById<TextView>(R.id.popup_game_support_subtitle).text = currentGame.support
    }
}