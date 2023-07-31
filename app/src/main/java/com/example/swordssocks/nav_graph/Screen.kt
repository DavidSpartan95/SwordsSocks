package com.example.swordssocks.nav_graph

sealed class Screen(val route: String){
    object Home: Screen(route = "home_screen")
    object Creation: Screen(route = "creation_screen")
    object Load: Screen(route = "load_screen")
    object Town: Screen(route = "town_screen/{user}")
    object Arena:Screen(route = "arena_screen/{user}")
}
