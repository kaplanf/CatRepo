package com.mjdinteractive.applymjd.api

data class CatList(
        val cats: Array<Cat>)

data class Cat(
        var name: String,
        var caption: String,
        var image: String,
        var source: String)

