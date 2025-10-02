package com.example.ecommerceapp.repositories

import android.util.Log
import com.example.ecommerceapp.model.Category
import com.example.ecommerceapp.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Suppress("UNREACHABLE_CODE")
@Singleton
class FirestoreRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    fun getCategoriesFlow(): Flow<List<Category>> =
        callbackFlow {

            val listenerRegistration = firestore
                .collection("categories")
                .addSnapshotListener{snapshot, error->
                    if(error != null ){
                        println("Error fetching categories: ${error.message}")
                        close(error)
                        return@addSnapshotListener
                    }
                    if(snapshot != null){
                        val categories = snapshot.toObjects(Category :: class.java)
                        trySend(categories)
                    }
                }

            // close to flow when listener is no longer needed
            awaitClose{
                listenerRegistration.remove()
            }

    }

    suspend fun getProductsByCategory(categoryId: String): List<Product>{


        return try {
            val result = firestore.collection("products")
                .whereEqualTo("categoryId", categoryId)
                .get()
                .await()

            result.toObjects(Product::class.java).also {
                Log.v("TAGY", "Mapped products: $it")
            }
        }catch (e: Exception){
            emptyList()
        }
    }

    suspend fun getProductsById(productId: String): Product?{


        return try {
            val result = firestore.collection("products")
                .document(productId)
                .get()
                .await()

            result.toObject(Product::class.java)
        }catch (e: Exception){
            null
        }
    }

    suspend fun getAllProductsInFirestore(): List<Product> {

        return try {
            // Get all products and filter locally (for small Datasets)
            val allProducts = firestore.collection("products")
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(Product::class.java) }
            allProducts
        } catch (e: Exception) {
            emptyList()
        }
    }

        suspend fun searchProducts(query: String): List<Product>{

            return try {
                // Convert Query to lowercase for case-insensitive search
                val searchQuery = query.lowercase()

                val allProducts = firestore.collection("products")
                    .get()
                    .await()
                    .documents
                    .mapNotNull { it.toObject(Product::class.java) }


                // Filter products where name contains the query
                allProducts.filter { product ->
                    product.name.lowercase().contains(searchQuery)

                }

            }catch (e: Exception){
                Log.v("TAGY", "Error searching products: ${e.message}")
                emptyList()

            }
        }
    }



