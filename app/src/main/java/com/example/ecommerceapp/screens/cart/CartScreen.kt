package com.example.ecommerceapp.screens.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ecommerceapp.viewmodels.CartViewModel


@Composable
fun CartScreen(navController: NavController,
               cartViewModel: CartViewModel = hiltViewModel()

){

    val cartItemsState =  cartViewModel.cartItems
        .collectAsState(initial = emptyList())
    val cartItems = cartItemsState.value


    Column (
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ){

        Text(
            text = "Your Cart",
            style = MaterialTheme.typography.headlineMedium,
            modifier= Modifier.padding(bottom = 16.dp))

        // Display cart items
        cartItems.forEach { product ->
            Text(
                text = "${product.name} - $${product.price}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            )
        }

        if(cartItems.isEmpty()){
            Column (
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                Text(
                    text= "Your Cart is Empty",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier= Modifier.height(16.dp))
                Button(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                   Text(
                       text = "Continue Shopping")
                }

            }
        } else{

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(cartItems) { item ->
                    CartItemCard(item = item,
                        onRemoveItem = {
                            cartViewModel.removeFromCart(item)
                        }

                    )   // ✅ Product pass karo
                }


                }

            // Total and Checkout Section
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(top=16.dp)) {

                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        text = "Total:",
                        style = MaterialTheme.typography.titleMedium)

                    // call the viewmodel to get total price
                    Text(
                        text = "$${cartViewModel.calculateTotal(cartItems)}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                }

                Spacer(modifier= Modifier.height(16.dp))
                Button(
                    onClick = { /** Handle Checkout
                     paypal,
                     stripe,
                     mastercard SDK....
                     **/},
                    modifier = Modifier.fillMaxWidth()
                        .height(50.dp)){
                    Text("Proceed to Checkout")
                }

            }

                }
        }


    }

