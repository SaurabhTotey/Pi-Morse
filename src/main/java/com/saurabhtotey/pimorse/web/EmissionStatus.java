package com.saurabhtotey.pimorse.web;

/**
 * A small data class to represent whether the emission of a message was successful
 */
public class EmissionStatus {

    //Whether the emission was a success
    private boolean success;
    //The details of the message
    private String details;

    /**
     * Creating an EmissionStatus takes in its fields
     */
    public EmissionStatus(boolean success, String details) {
        this.success = success;
        this.details = details;
    }

    /**
     * Gets this object's success status
     */
    public boolean getSuccess() {
        return this.success;
    }

    /**
     * Gets this object's message
     */
    public String getDetails() {
        return this.details;
    }

}