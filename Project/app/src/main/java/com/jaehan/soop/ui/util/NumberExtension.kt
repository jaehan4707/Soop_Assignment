package com.jaehan.soop.ui.util

/**
 *
 *
 * @return K 표기법 변환 문자열 (1000 이하일 경우 그냥 숫자)
 */
fun Long.toKFormat(): String {
    return if (this >= 1000) {
        String.format("%.1fk", this / 1000.0)
    } else {
        this.toString()
    }
}