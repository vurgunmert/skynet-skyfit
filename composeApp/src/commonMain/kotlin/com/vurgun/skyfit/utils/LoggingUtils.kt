package com.vurgun.skyfit.utils

object LoggerFactory {
    fun makeLogger(forClass: Any): TaggedLogger {
        val tag = forClass::class.simpleName ?: "UnknownClass"
        return TaggedLogger(tag)
    }
}

class TaggedLogger(private val tag: String) {

    fun log(message: String) {
        println("[$tag] $message")
    }

    fun warn(message: String) {
        println("[WARN] [$tag] $message")
    }

    fun error(message: String, throwable: Throwable? = null) {
        println("[ERROR] [$tag] $message")
        throwable?.printStackTrace()
    }
}
