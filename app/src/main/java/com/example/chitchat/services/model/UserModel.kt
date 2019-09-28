package com.example.chitchat.services.model

class UserModel {
    var name = ""
    var email = ""
    var id = ""

    constructor()
    constructor(name: String, email: String,id:String) {
        this.name = name
        this.email = email
        this.id = id
    }
}