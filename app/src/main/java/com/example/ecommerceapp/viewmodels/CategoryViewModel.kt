package com.example.ecommerceapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.model.Category
import com.example.ecommerceapp.repositories.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: FirestoreRepository
) : ViewModel(){

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> get() = _categories


    init {
        fetchCategories()

    }

    private fun fetchCategories(){
       viewModelScope.launch {
           repository.getCategoriesFlow()
               .catch{
                   println("Error in Flow")

               }
               .collect{
                   categories ->
                   _categories.value = categories
                   println("categories updated in Viewmodel")
               }
       }
    }


}