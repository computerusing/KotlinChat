package com.example.kotlinchat

class ChatDTO(useR_NAME: String, toString: String) {

    private var userName: String? = null
    private var message: String? = null

    fun ChatDTO()  {}
    fun ChatDTO(userName: String, message: String) {
        this.userName = userName
        this.message = message
    }

    fun setUserName(userName: String) {
        this.userName = userName
    }

    fun setMessage(message: String) {
        this.message = message
    }

    fun getUserName() :String?{
        return userName
    }

    fun getMessage(): String? {
        return message
    }
}