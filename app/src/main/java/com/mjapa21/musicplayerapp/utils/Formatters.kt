package com.mjapa21.musicplayerapp.utils

/**
 * Formats a duration in seconds into a playback string (e.g., "3:40" or "0:05").
 */
fun Int.toPlaybackTimeString(): String {
    val minutes = this / 60
    val remainingSeconds = this % 60
    return "%d:%02d".format(minutes, remainingSeconds)
}