@file:Suppress("KotlinConstantConditions")

package com.example.ecommerceapp.screens.product

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ecommerceapp.screens.navigation.Screens
import com.example.ecommerceapp.viewmodels.CartViewModel
import com.example.ecommerceapp.viewmodels.ProductViewModel

@Composable
fun ProductScreen(
    categoryId: String,
    navController: NavController,
    productViewModel: ProductViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel()
){
    // Fetch products form the viewmodel
    LaunchedEffect(categoryId) {
        productViewModel.fetchProducts(categoryId)
    }

    // Collect the products from the viewmodel
    val productState = productViewModel.products.collectAsState()
    val products = productState.value


    // Display the products
    Column (
        modifier = Modifier.fillMaxSize()){

        Text(text = "Products of Category ID: $categoryId",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )
        //if there is no products
        if(products.isEmpty()){
            Text(
                text = "No Products Found!",
                modifier = Modifier.padding(16.dp)

            )

        }else{
            LazyColumn (
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp)
            ){
                items(products){ product ->
                    ProductItem(
                        product = product,
                       onClick = {
                           navController.navigate(Screens.ProductDetails.createRoute(product.id))
                       },
                        onAddToCart = {
                           // Add the product to cart
                            // Room database insertion function
                            cartViewModel.addToCart(product)
                            Log.v("TAGY", "Product Added to Cart!")

                        }
                        )
                }
            }
        }

    }

}