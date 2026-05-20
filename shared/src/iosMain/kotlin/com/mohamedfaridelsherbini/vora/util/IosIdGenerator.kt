package com.mohamedfaridelsherbini.vora.util

import platform.Foundation.NSUUID

class IosIdGenerator : IdGenerator {
    override fun randomId(): String = NSUUID().UUIDString()
}
