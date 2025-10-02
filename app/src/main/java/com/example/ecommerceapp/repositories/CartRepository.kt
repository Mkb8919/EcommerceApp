package com.example.ecommerceapp.repositories

import android.util.Log
import com.example.ecommerceapp.model.Product
import com.example.ecommerceapp.room.CartDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val cartDao: CartDao
){

    val allCartItems: Flow<List<Product>> =
        cartDao.getAllCartItems()

    suspend fun addtoCart(product: Product){
        val existingItem = cartDao.getCartItemById(product.id)

        if(existingItem != null){
            Log.v("TAGY", "Product Already Added!")
            cartDao.updateCartItem(product)
        }
        else{
            cartDao.insertCartItem(product)
            Log.v("TAGY", "Product Added!")
        }
    }

    suspend fun removeCartItem(product: Product){
        cartDao.deleteCartItem(product)
        Log.v("TAGY", "Product Removed!")
    }

    suspend fun clearCart(){
        cartDao.clearCart()

    }




}