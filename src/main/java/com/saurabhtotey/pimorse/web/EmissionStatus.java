package com.saurabhtotey.pimorse.web;

/**
 * A small data class to represent whether the emission of a message was successful
 */
class EmissionStatus {

    //Whether the emission was a success
    boolean success;
    //The details of the message
    String details;

    EmissionStatus(boolean success, String details) {
        this.success = success;
        this.details = details;
    }

}