package com.example.filmz_project

import java.io.Serializable

class User(
    val nom: String,
    val contrasenya: String?,
    val edat: Int,
    val sexe: Char,
    var puntuacio: Int,
    var jugadorActual: Boolean,
    var difficult: Int?,
    var posicionRanking: Int?
): Serializable