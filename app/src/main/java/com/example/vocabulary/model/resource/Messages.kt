package com.example.vocabulary.model.resource

enum class Messages {
    GENERIC_ERROR_MSG {
        override fun getMessage(): String = "Sorry, something went wrong";
    };

    abstract fun getMessage(): String
}