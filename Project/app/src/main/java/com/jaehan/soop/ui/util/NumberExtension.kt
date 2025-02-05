package com.jaehan.soop.ui.util

fun Long.toKFormat(): String {
    return if (this >= 1000) {
        String.format("%.1fk", this / 1000.0)
    } else {
        this.toString()
    }
}