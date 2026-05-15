package com.mohamedfaridelsherbini.vora.notes

import platform.Foundation.NSUUID
import platform.Foundation.timeIntervalSince1970

actual fun currentTimeMillis(): Long =
    (platform.Foundation.NSDate().timeIntervalSince1970 * 1000.0).toLong()

internal actual fun randomId(): String = NSUUID().UUIDString()
