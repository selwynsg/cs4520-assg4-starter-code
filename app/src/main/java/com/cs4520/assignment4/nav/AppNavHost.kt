package com.cs4520.assignment4.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cs4520.assignment4.LoginScreen
import com.cs4520.assignment4.View.ProductListScreen
import com.cs4520.assignment4.ViewModel.MyViewModelFactory
import com.cs4520.assignment4.ViewModel.ProductViewModel

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.Login.route,
    viewModelFactory: MyViewModelFactory
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.Login.route) {
            LoginScreen(navController)
        }
        composable(NavigationItem.Product.route) {
            val productViewModel: ProductViewModel = viewModel(factory = viewModelFactory)
            ProductListScreen(navController, productViewModel)
        }
    }
}

sealed class NavigationItem(val route: String) {
    object Login : NavigationItem("login")
    object Product : NavigationItem("productList")
}