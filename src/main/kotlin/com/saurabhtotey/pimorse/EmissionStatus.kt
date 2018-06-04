package com.saurabhtotey.pimorse

/**
 * A small data class to represent whether the emission of a message was successful
 */
data class EmissionStatus(val success: Boolean, val details: String)