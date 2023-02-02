package com.hann.gamelistcompose

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hann.gamelistcompose.ui.navigation.NavigationItem
import com.hann.gamelistcompose.ui.navigation.Screen

@Composable
fun GameApp(
    modifier: Modifier = Modifier,
    navController: NavController =  rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        bottomBar = {

    }){
        NavHost(
            navController = navController as NavHostController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(it)
            ){
                composable(Screen.Home.route){

                }
                composable(Screen.Favorite.route){

                }
                composable(Screen.DetailGame.route){

                }
          }
    }
}


@Composable
fun BottomBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    BottomNavigation(
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val navigationItem = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.menu_favorite),
                icon = Icons.Default.AccountCircle,
                screen = Screen.Favorite
            )
        )
        BottomNavigation {
            navigationItem.map {
                    navigationItem ->
                BottomNavigationItem(
                    selected = currentRoute == navigationItem.screen.route,
                    onClick = { navController.navigate(navigationItem.screen.route){
                        popUpTo(navController.graph.findStartDestination().id){
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    } },
                    label = { Text(text = navigationItem.title)},
                    icon = { Icon(
                        imageVector = navigationItem.icon,
                        contentDescription = navigationItem.title)
                    }
                )
            }
        }
    }
}



@Preview
@Composable
fun GameAppPreview() {

}