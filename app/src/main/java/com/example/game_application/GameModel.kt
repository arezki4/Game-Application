package com.example.game_application

class GameModel(
    val id : String = "game0",
    val name : String = "Zelda",
    val description : String = "Petite description",
    val imageUrl : String = "https://cdn.pixabay.com/photo/2017/02/23/17/41/illustrator-2092779_960_720.jpg",
    val type : String = "Adventure",
    val support : String = "Nintendo Switch",
    var liked : Boolean = false //var pour changer la valeur du boolean
)