package com.example.game_application

import android.net.Uri
import com.example.game_application.GameRepository.Singleton.databaseRef
import com.example.game_application.GameRepository.Singleton.downloadUri
import com.example.game_application.GameRepository.Singleton.gameList
import com.example.game_application.GameRepository.Singleton.storageReference
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.util.*

class GameRepository {

    object Singleton{

        //donner le lien pour acceder au bucket
        private val BUCKET_URL: String = "gs://game-application-9efc2.appspot.com"

        //se connecter a notre espace de stockage
        val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(BUCKET_URL)

        //Se cnnecter a la reference "games"
        val databaseRef = FirebaseDatabase.getInstance().getReference("games")

        //créer une liste qui va contenir nos jeux
        val gameList = arrayListOf<GameModel>()

        // contenir le lien de l'image courante
        var downloadUri : Uri? = null
    }

    fun updateData(callback: () -> Unit){
        //absorber les données depuis la databaseRef -> liste de jeux
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //retirer les anciennes qui ont ete enregistrée precedament et mettre a jour vraiment la base
                gameList.clear()
                //recolter la liste
                for(ds in snapshot.children){
                    // construire un objet game
                    val game = ds.getValue(GameModel::class.java)

                    //verifier que game n'est pas NULL
                    if(game != null){
                        //ajouter la plant a notre liste
                        gameList.add(game)
                    }
                }
                // actionner le callback
                callback()
            }

            override fun onCancelled(error: DatabaseError) {
                //au cas ouil ne trouve pas les elements en question
            }

        })
    }

    //creer une fonction pour envoyer des fichier sur le storage
    fun uploadImage(file : Uri, callback: () -> Unit){
        //verifier que ce fichier n'est pas null
        if(file != null){
            val fileName = UUID.randomUUID().toString() + ".jpg" //creer le nom du fichier
            val ref = storageReference.child(fileName) //on dit ou on veut le ranger dans la bdd
            val uploadTask = ref.putFile(file) //on lui associe le contenu a soumettre

            //demarrer la tache d'envoi
            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>>{ task ->

                //s'il y a eu un probleme lors de l'envoi du fichier
                if(!task.isSuccessful){
                    task.exception?.let { throw it }//verifier si y a des erreurs
                }

                return@Continuation ref.downloadUrl

            }).addOnCompleteListener{task ->
                //verifier si tout a bien fonctionné
                if(task.isSuccessful){
                    //recuperer l'image
                    downloadUri = task.result
                    callback()
                }
            }
        }
    }

    //mettre a jour un objet game en base de donnée
    fun updateGame(game: GameModel){
        databaseRef.child(game.id).setValue(game)
    }

    //inserer un nouveau jeux en bdd
    //mettre a jour un objet game en base de donnée
    fun insertGame(game: GameModel){
        databaseRef.child(game.id).setValue(game)
    }

    //supprimer un jeux de la base
    fun deleteGame(game: GameModel) = databaseRef.child(game.id).removeValue()
}