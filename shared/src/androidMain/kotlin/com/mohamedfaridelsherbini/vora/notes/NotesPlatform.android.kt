package com.mohamedfaridelsherbini.vora.notes

actual fun currentTimeMillis(): Long = System.currentTimeMillis()

internal actual fun randomId(): String = java.util.UUID.randomUUID().toString()
