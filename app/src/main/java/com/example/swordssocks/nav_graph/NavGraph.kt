package com.example.swordssocks.nav_graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.swordssocks.database.UserRepository
import com.example.swordssocks.game_components.CreationScreen
import com.example.swordssocks.game_components.LoadGameScreen
import com.example.swordssocks.game_components.MenuScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    userRepository: UserRepository
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(
            route = Screen.Home.route
        ) {
            MenuScreen(navController,userRepository)
        }
        composable(
            route = Screen.Creation.route
        ) {
            CreationScreen(navController,userRepository)
        }
        composable(
            route = Screen.Load.route
        ) {
            LoadGameScreen(navController,userRepository)
        }
    }
}