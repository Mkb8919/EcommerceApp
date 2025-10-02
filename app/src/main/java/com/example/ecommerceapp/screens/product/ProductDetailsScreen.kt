package com.example.ecommerceapp.screens.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.rememberAsyncImagePainter
import com.example.ecommerceapp.viewmodels.CartViewModel
import com.example.ecommerceapp.viewmodels.ProductDetailsViewModel

@Composable
fun ProductDetailsScreen(
    productId: String,
    productViewModel: ProductDetailsViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel()

){

    // Fetch product Details when the screen is
    // first displayed

    LaunchedEffect(productId) {
        productViewModel.fetchProductDetails(productId)
    }

    // collect the product details from the viewmodel
    val productState = productViewModel.product.collectAsState()
    val product = productState.value


    if(product == null){
        Text(text = "Product not found" )
    } else{
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            // Product Image
            Image(
                painter = rememberAsyncImagePainter(
                    model = product.imageUrl),
                contentScale = ContentScale.Crop,
                contentDescription = "Product Image",
                    modifier = Modifier.fillMaxSize()
                        .height(300.dp)
                        .clip(RoundedCornerShape(12.dp))

                )
            Spacer(modifier = Modifier.height(8.dp))

            // Product Name:
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Product Price
            Text(
                text = "$${product.price}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))


            // Product Description
            Text(
                text = product.categoryId?: "No Description Found",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )



            // Display product details


        }

        IconButton(
            onClick = { cartViewModel.addToCart(product) },
            modifier = Modifier.padding(16.dp)
                .background(color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape)
        ){
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Add to Cart",
                tint = Color.White
            )

        }

    }

}