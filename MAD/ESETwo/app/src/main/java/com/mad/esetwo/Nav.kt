package com.mad.esetwo

sealed class Screen(val route: String) {
    object Live: Screen("live")
    object Player: Screen("player")
    object Team: Screen("team")
}