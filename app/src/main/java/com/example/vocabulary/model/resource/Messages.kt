package com.example.vocabulary.model.resource

enum class Messages {
    GENERIC_ERROR_MSG {
        override fun getMessage(): String = "Sorry, something went wrong";
    },
    NO_DATA_ERROR_MSG {
        override fun getMessage(): String = "No data found";
    };

    abstract fun getMessage(): String
}