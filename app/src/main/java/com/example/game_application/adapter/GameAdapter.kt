package com.example.game_application.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.game_application.MainActivity
import com.example.game_application.R
import com.example.game_application.*

class GameAdapter(
    val context : MainActivity,
    private val gameList : List<GameModel>,
    private  val layoutId: Int) : RecyclerView.Adapter<GameAdapter.ViewHolder>(){

    //boite pour ranger tous les composangts à controler
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        //image  du jeux ?
        val gameImage = view.findViewById<ImageView>(R.id.image_item)
        val gameName : TextView? = view.findViewById(R.id.name_item)//TextView?: on tente de le recuperer mais si il est pas la c'est pas grave
        val gameDescrirtion : TextView? = view.findViewById(R.id.description_item)
        val starIcon = view.findViewById<ImageView>(R.id.star_icon)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layoutId, parent, false)

        return ViewHolder(view)
    }//pour injecter notre layout: item_horizental_game

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {//mettre a jour chaque model avec le jeux en question
        //recuperer les info du jeux
        val currentGame = gameList[position]

        //recuperer le repository
        val repo = GameRepository() //nouvelle instance, qui ne posera pas de probleme sur l'initialisation de la liste grace au singleton

        //utiliser glide pour recuperer l'image a partir de son lien -> composant
        Glide.with(context).load(Uri.parse(currentGame.imageUrl)).into(holder.gameImage) // Uri.parse converti l'url de l'image
        // pour la transformet en action uri qu'on va intenter sur android
        //Mettre a jour le nom du jeux
        holder.gameName?.text = currentGame.name // ? = si le composant est nul, tu ne vas pas changer son text

        //Mettre a jour la description

        holder.gameDescrirtion?.text = currentGame.description

        // verifier si le jeux a ete liké ou non
        if(currentGame.liked){
            holder.starIcon.setImageResource(R.drawable.ic_star)
        }else{
            holder.starIcon.setImageResource(R.drawable.ic_unstar)

        }

        //rajouter une interaction sur cette etoile
        holder.starIcon.setOnClickListener{
            //inverser le bouton si like ou non
            currentGame.liked = !currentGame.liked
            //mettre a jour l'objet gamee
            repo.updateGame(currentGame)
        }

        //interaction lors du clique sur un jeux
        holder.itemView.setOnClickListener{
            //afficher la popup
            GamePopup(this, currentGame).show() // nouvelle instance de gamepopup en passant en parametre l'adapter, et la methode show pour l'afficher
        }

    }

    override fun getItemCount(): Int {
        return gameList.size
    }//renvoyer combien d'item afficher dynamiquement
}