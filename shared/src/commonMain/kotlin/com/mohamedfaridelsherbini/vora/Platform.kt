package com.mohamedfaridelsherbini.vora

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform