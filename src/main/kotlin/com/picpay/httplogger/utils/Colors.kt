package com.picpay.httplogger.utils

class Colors {
    companion object {
        val BLACK = "\u001B[30m"
        val RED = "\u001B[31m"
        val GREEN = "\u001B[32m"
        val YELLOW = "\u001B[33m"
        val BLUE = "\u001B[34m"
        val PURPLE = "\u001B[35m"
        val CYAN = "\u001B[36m"
        val WHITE = "\u001B[37m"
        val RESET = "\u001B[0m"

        fun black(term: String) = "$BLACK$term$RESET"
        fun red(term: String) = "$RED$term$RESET"
        fun green(term: String) = "$GREEN$term$RESET"
        fun yellow(term: String) = "$YELLOW$term$RESET"
        fun blue(term: String) = "$BLUE$term$RESET"
        fun purple(term: String) = "$PURPLE$term$RESET"
        fun cyan(term: String) = "$CYAN$term$RESET"
        fun white(term: String) = "$WHITE$term$RESET"
    }
}