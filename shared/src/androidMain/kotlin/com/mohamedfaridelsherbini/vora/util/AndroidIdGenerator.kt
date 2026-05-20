package com.mohamedfaridelsherbini.vora.util

import java.util.UUID

class AndroidIdGenerator : IdGenerator {
    override fun randomId(): String = UUID.randomUUID().toString()
}
