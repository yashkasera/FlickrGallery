package com.yashkasera.flickrgallery.util

import android.os.Message


class SingleEvent(private val message: Message) {
    var hasBeenHandled = false
        private set

    fun getContentIfNotHandledOrReturnNull(): Message? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            message
        }
    }

    fun peekContent(): Message = message
}