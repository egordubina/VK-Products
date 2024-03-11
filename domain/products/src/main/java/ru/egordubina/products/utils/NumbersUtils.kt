package ru.egordubina.products.utils

fun Int.toUsd(): String {
    val result = StringBuilder()
    val reversNumber = this.toString().reversed()
    reversNumber.forEachIndexed { index, char ->
        result.append(char)
        if ((index + 1) % 3 == 0 && (index + 1) != reversNumber.length)
            result.append(",")
    }
    result.reverse()
    result.insert(0, "$")
    return result.toString()
}