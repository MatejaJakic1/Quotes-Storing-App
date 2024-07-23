package com.example.antiquote

data class Quotes(var id : Int,var quote: String, var title : String){

    constructor() : this(0, "", ""){
    }
}