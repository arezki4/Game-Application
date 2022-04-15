package com.example.game_application.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.game_application.MainActivity
import com.example.game_application.R
import com.example.game_application.GameModel
import com.example.game_application.GameRepository
import com.example.game_application.GameRepository.Singleton.downloadUri
import java.util.*

class AddGameFragment(private  val context: MainActivity) : Fragment(){

    private  var file : Uri? = null
    private  var uploadedImage : ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(R.layout.fragment_add_game, container, false)

        //recuperer uploadedImage pour lui associer son composant
        uploadedImage = view.findViewById(R.id.preview_image)

        // recuperer le bouton pour charger l'image
        val pickupImageButton = view.findViewById<Button>(R.id.upload_button)

        //lorsqu'on clique dessus ca ouvre les images du telephone
        pickupImageButton.setOnClickListener{pickupImage()}

        //recuperer le bouton confirmer
        val confirmButton = view.findViewById<Button>(R.id.confirm_button)
        confirmButton.setOnClickListener{sendForm(view)}

        return view
    }

    private fun sendForm(view: View) {
        val repo = GameRepository()
        repo.uploadImage(file!!){

            val gameName = view.findViewById<EditText>(R.id.name_input).text.toString()
            val gameDescription = view.findViewById<EditText>(R.id.description_input).text.toString()
            val type = view.findViewById<Spinner>(R.id.type_spinner).selectedItem.toString()
            val support = view.findViewById<Spinner>(R.id.support_spinner).selectedItem.toString()
            val downloadImageUrl = downloadUri

            //creer un nouvel objet GameModel
            val game = GameModel(
                UUID.randomUUID().toString(),
                gameName,
                gameDescription,
                downloadImageUrl.toString(),
                type,
                support
            )

            //envoyer en bdd
            repo.insertGame(game)
        }

    }

    private fun pickupImage() {
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 47)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 47 && resultCode == Activity.RESULT_OK){

            //verifier si les données sont NULL
            if(data == null || data.data == null) return

            //recuperer l'image
            //val selectedImage = data.data
            file = data.data

            //mettre a jou l'apercu de l'image
            uploadedImage?.setImageURI(file)//selectedImage)

            //heberger sur le bucket
            /*val repo = GameRepository()
            repo.uploadImage(selectedImage!!)//les point d'exclamation c'est pour dire qu'on fait commememe cette instruction malgre le warning*/
        }
    }
}