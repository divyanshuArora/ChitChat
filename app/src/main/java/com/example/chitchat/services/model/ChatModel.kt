package com.example.chitchat.services.model

class ChatModel
{
    var id = ""
    var from_name = ""
    var to_name = ""
    var from_id = ""
    var to_id = ""


    constructor(id: String, from_name: String, to_name: String, from_id: String, to_id: String) {
        this.id = id
        this.from_name = from_name
        this.to_name = to_name
        this.from_id = from_id
        this.to_id = to_id
    }
}