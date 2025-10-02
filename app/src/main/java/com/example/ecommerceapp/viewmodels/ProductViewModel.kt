package com.example.ecommerceapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.model.Product
import com.example.ecommerceapp.repositories.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: FirestoreRepository
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products : StateFlow<List<Product>> get() = _products

    fun fetchProducts(categoryId: String){
        viewModelScope.launch {
            try {
                val products = repository.getProductsByCategory(categoryId)
                _products.value = products
            } catch (e: Exception){
                Log.e("TAGY", "Error fetching products ${e.message}")
            }
        }
    }

    private val _allProducts = MutableStateFlow<List<Product>>(emptyList())
    val allProducts : StateFlow<List<Product>> get() = _allProducts

    fun getAllProductsInFirestore(){
        viewModelScope.launch {
            try {
                val allProducts = repository.getAllProductsInFirestore()
                _products.value = allProducts
            } catch (e: Exception){
            }
        }
    }

}