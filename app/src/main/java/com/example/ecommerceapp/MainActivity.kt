package com.example.ecommerceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ecommerceapp.screens.cart.CartScreen
import com.example.ecommerceapp.screens.categories.CategoryScreen
import com.example.ecommerceapp.screens.home.HomeScreen
import com.example.ecommerceapp.screens.navigation.Screens
import com.example.ecommerceapp.screens.product.ProductDetailsScreen
import com.example.ecommerceapp.screens.product.ProductScreen
import com.example.ecommerceapp.screens.profile.LoginScreen
import com.example.ecommerceapp.screens.profile.ProfileScreen
import com.example.ecommerceapp.screens.profile.SignUpScreen
import com.example.ecommerceapp.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            // Navigation System
            val navController = rememberNavController()

            // AuthView Model
            val authViewModel: AuthViewModel = hiltViewModel()

            // properly observe the state
            val isLoggedIn by remember{
                derivedStateOf {
                    authViewModel.isLoggedIn
                }
            }



            // Nav Host: Manages navigation bet. screens
            NavHost(
                navController = navController,
                startDestination = "Home"
            ){
                // define routes using composable(){}
                // for each screen you want to support
                composable(Screens.Home.route) {
                    HomeScreen(navController = navController,
                        onProfileClick = { navController.navigate(Screens.Profile.route) },
                        onCartClick = { navController.navigate(Screens.Cart.route) }
                    )
                }


                composable(Screens.Cart.route){
                    CartScreen(navController = navController)
                }
                composable(Screens.Profile.route) {
                    ProfileScreen(navController = navController,
                        onSignOut = {
                            authViewModel.signOut()
                            navController.navigate(Screens.Login.route)
                        })

                }
                composable(Screens.CategoryList.route){
                    CategoryScreen(navController,
                        onCartClick =
                        { navController.navigate(Screens.Cart.route)},

                        onProfileClick = {
                            // Switch to logging in or display profile
                            if (isLoggedIn) {
                                navController.navigate(Screens.Profile.route)
                            } else {
                                navController.navigate(Screens.Login.route)
                            }
                        }

                    )
                }

                composable(Screens.SignUp.route) {
                    SignUpScreen(
                        onNavigateToLogin = {
                          navController.navigate(Screens.Login.route)
                        },
                        onSignUpSuccess = {
                            navController.navigate(Screens.Home.route)
                        }
                    )
                }

                composable(Screens.Login.route) {
                    LoginScreen(
                        onNavigateToSignUp = {
                            navController.navigate(Screens.SignUp.route)
                        },
                        onLoginSuccess = {
                            navController.navigate(Screens.CategoryList.route)
                        }
                    )
                }

                composable(
                    Screens.ProductDetails.route
                ){
                    val productId = it.arguments?.getString("productId")
                    if(productId != null){
                        ProductDetailsScreen(productId)
                    }
                }
                composable(Screens.ProductList.route) {
                    val categoryId = it.arguments?.getString("categoryId")
                    if(categoryId != null){
                        ProductScreen(categoryId,
                            navController = navController)
                    }

                }

            }


            }
        }
    }


