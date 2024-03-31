package com.cs4520.assignment4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cs4520.assignment4.View.ProductListScreen
import com.cs4520.assignment4.ViewModel.MyViewModelFactory
import com.cs4520.assignment4.ViewModel.ProductViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = MyViewModelFactory(application)
        setContent {
            MyApp(viewModelFactory = factory)
        }
    }
}

@Composable
fun MyApp(viewModelFactory: MyViewModelFactory) {
    val navController = rememberNavController()
    AppNavigation(navController,viewModelFactory)
}

@Composable
fun AppNavigation(navController: NavHostController, viewModelFactory: MyViewModelFactory) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("productList") {
            val productViewModel: ProductViewModel = viewModel(factory = viewModelFactory)
            ProductListScreen(productViewModel)
        }
    }
}


