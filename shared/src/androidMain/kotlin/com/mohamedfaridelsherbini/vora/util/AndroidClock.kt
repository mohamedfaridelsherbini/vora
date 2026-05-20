package com.mohamedfaridelsherbini.vora.util

class AndroidClock : Clock {
    override fun currentTimeMillis(): Long = System.currentTimeMillis()
}
