package com.mohamedfaridelsherbini.vora.domain.model

enum class VoiceMemoSource(
    val storageValue: String,
    val displayName: String,
) {
    Phone(storageValue = "phone", displayName = "Phone"),
    Watch(storageValue = "watch", displayName = "Watch"),
    Car(storageValue = "car", displayName = "Car");

    companion object {
        fun fromStorage(value: String): VoiceMemoSource = entries.firstOrNull { source ->
            source.storageValue.equals(value, ignoreCase = true) ||
                source.displayName.equals(value, ignoreCase = true)
        } ?: Phone
    }
}
