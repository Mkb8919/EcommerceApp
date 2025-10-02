package com.example.ecommerceapp.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ecommerceapp.screens.navigation.Screens
import com.example.ecommerceapp.viewmodels.CategoryViewModel
import com.example.ecommerceapp.viewmodels.ProductViewModel
import com.example.ecommerceapp.viewmodels.SearchViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
            navController: NavController,
            onProfileClick: ()-> Unit,
               onCartClick: ()-> Unit,
               productViewModel: ProductViewModel = hiltViewModel(),
               categoryViewModel: CategoryViewModel = hiltViewModel(),
                searchViewModel: SearchViewModel = hiltViewModel()

) {

    Scaffold(
        topBar = { MyTopAppBar(onProfileClick, onCartClick) },
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            // Search Section
            val searchQuery = remember { mutableStateOf("") }
            val focusManager = LocalFocusManager.current
            SearchBar(
                query = searchQuery.value,
                onQueryChange = { searchQuery.value = it },
                onSearch = { searchViewModel.searchProducts(searchQuery.value)
                           focusManager.clearFocus()},
                navController = navController,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

//            // Search Result Section
//            if(searchQuery.value.isNotBlank()){
//                //SearchResultsSection(
//                    navController = navController
//                )
//            }

            // Categories Section
            SectionTitle(
                title = "Categories",
                actionText = "See All"
            ) {
                navController.navigate(Screens.CategoryList.route)
            }

            // Mock the categories
            val categoriesState = categoryViewModel.categories.collectAsState()
            val categories = categoriesState.value

            // The Selected Category
            val selectedCategory = remember { mutableStateOf(0) }

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories.size) {
                    CategoryChip(
                        icon = categories[it].iconUrl,
                        text = categories[it].name,
                        isSelected = selectedCategory.value == it,
                        onClick = {
                            selectedCategory.value = categories[it].id
                            /** Do the navigation logic  **/
                            navController.navigate(
                                Screens.ProductList.createRoute(selectedCategory.value.toString())
                            )

                        }
                    )
                }

                // ✅ Correct Spacer in LazyRow
                item {
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }

            // Featured Products Section
            Spacer(modifier = Modifier.height(16.dp))
            SectionTitle(
                title = "Featured",
                actionText = "See All") {

                navController.navigate(Screens.CategoryList.route)
            }

            // fetch products when the screen is first displayed
            productViewModel.getAllProductsInFirestore()
            val allProductState = productViewModel.allProducts.collectAsState()
            val allProductFound = allProductState.value

              LazyRow(
                  contentPadding = PaddingValues(horizontal = 16.dp),
                  horizontalArrangement = Arrangement.spacedBy(16.dp)
              ) {
                  items(allProductFound){ product ->

                      FeatureProductCard(product) {
                          //** Handle the click event here**//
                          navController.navigate(
                              Screens.ProductDetails.createRoute(product.id)
                          )

                      }


                  }

              }

        }
    }
}
