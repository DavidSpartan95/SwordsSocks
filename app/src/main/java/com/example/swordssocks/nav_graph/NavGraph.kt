package com.example.swordssocks.nav_graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.swordssocks.database.User
import com.example.swordssocks.database.UserRepository
import com.example.swordssocks.game_components.*
import com.google.gson.Gson

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
        composable(
            route = Screen.Town.route,
            arguments = listOf(navArgument("user"){
                type = NavType.StringType
            })
        ) {backStackEntry ->
            backStackEntry.arguments?.getString("user")?.let {
                json ->
                val user = Gson().fromJson(json, User::class.java)
                TownScreen(navController,userRepository,user)
            }

        }
        composable(
            route = Screen.Arena.route,
            arguments = listOf(navArgument("user"){
                type = NavType.StringType
            })
        ) {backStackEntry ->
            backStackEntry.arguments?.getString("user")?.let {
                    json ->
                val user = Gson().fromJson(json, User::class.java)
                ArenaScreen(navController,userRepository,user)
            }

        }
    }
}